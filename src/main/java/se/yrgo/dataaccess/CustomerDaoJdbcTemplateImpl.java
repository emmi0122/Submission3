package se.yrgo.dataaccess;

import java.sql.*;
import java.util.*;

import org.springframework.jdbc.core.*;

import se.yrgo.domain.*;

public class CustomerDaoJdbcTemplateImpl implements CustomerDao {
    private static final String DELETE_SQL = "DELETE FROM CUSTOMER WHERE CUSTOMER_ID=?";
    private static final String UPDATE_SQL = "UPDATE CUSTOMER SET COMPANY_NAME=?, EMAIL=?, TELEPHONE=?, NOTES=? WHERE CUSTOMER_ID=?";
    private static final String INSERT_SQL = "INSERT INTO CUSTOMER (CUSTOMER_ID, COMPANY_NAME, EMAIL, TELEPHONE, NOTES) VALUES (?, ?, ?, ?, ?)";

    private static final String SELECT_BY_ID_SQL = "SELECT * FROM CUSTOMER WHERE CUSTOMER_ID=?";
    private static final String SELECT_BY_NAME_SQL = "SELECT * FROM CUSTOMER WHERE COMPANY_NAME=?";
    private static final String SELECT_ALL_SQL = "SELECT * FROM CUSTOMER";

    private JdbcTemplate template;

    public CustomerDaoJdbcTemplateImpl(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public void create(Customer customer) {
        template.update(INSERT_SQL,
                customer.getCustomerId(),
                customer.getCompanyName(),
                customer.getEmail(),
                customer.getTelephone(),
                customer.getNotes());   
    }

    @Override
    public Customer getById(String customerId) throws RecordNotFoundException {
        List<Customer> results = template.query(SELECT_BY_ID_SQL, new CustomerRowMapper(), customerId);

        if (results.isEmpty()) {
            throw new RecordNotFoundException();
        }
        return results.get(0);
    }

    @Override
    public List<Customer> getByName(String name) {
        return template.query(SELECT_BY_NAME_SQL, new CustomerRowMapper(), name);
    }

    @Override
    public void update(Customer customerToUpdate) throws RecordNotFoundException {
        int rowsAffected = template.update(UPDATE_SQL,
                customerToUpdate.getCompanyName(),
                customerToUpdate.getEmail(),
                customerToUpdate.getTelephone(),
                customerToUpdate.getNotes(),
                customerToUpdate.getCustomerId());
                
        if (rowsAffected == 0) {
            throw new RecordNotFoundException();
        }
    }

    @Override
    public void delete(Customer oldCustomer) throws RecordNotFoundException {
        int rowsAffected = template.update(DELETE_SQL, oldCustomer.getCustomerId());

        if (rowsAffected == 0) {
            throw new RecordNotFoundException();
        }
    }

    @Override
    public List<Customer> getAllCustomers() {
        return template.query(SELECT_ALL_SQL, new CustomerRowMapper());
    }

    @Override
    public Customer getFullCustomerDetail(String customerId) throws RecordNotFoundException {
        return getById(customerId);
    }

    @Override
    public void addCall(Call newCall, String customerId) throws RecordNotFoundException {
        throw new UnsupportedOperationException("Call-hantering är inte implementerad än...");
    }
}

class CustomerRowMapper implements RowMapper<Customer> {
	public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
        String customerId = rs.getString("CUSTOMER_ID");
        String companyName = rs.getString("COMPANY_NAME");
        String email = rs.getString("EMAIL");
        String telephone = rs.getString("TELEPHONE");
        String notes = rs.getString("NOTES");

        Customer customer = new Customer(customerId, companyName, email, telephone, notes);
        return customer;
    }
}