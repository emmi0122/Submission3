package se.yrgo.services.customers;

import java.util.List;

import se.yrgo.dataaccess.CustomerDao;
import se.yrgo.dataaccess.RecordNotFoundException;
import se.yrgo.domain.Call;
import se.yrgo.domain.Customer;

public class CustomerManagementServiceProductionImpl implements CustomerManagementService {
    private CustomerDao dao; 

    public CustomerManagementServiceProductionImpl(CustomerDao dao) {
        this.dao = dao;
    }

    @Override
    public void newCustomer(Customer newCustomer) {
        dao.create(newCustomer);
    }

    @Override
    public void updateCustomer(Customer changedCustomer) {
        try {
            dao.update(changedCustomer);
        } catch (RecordNotFoundException e) {
            throw new RuntimeException("Kunde inte uppdatera kund: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteCustomer(Customer oldCustomer) {
        try {
            dao.delete(oldCustomer);
        } catch (RecordNotFoundException e) {
            throw new RuntimeException("Kunde inte ta bort kund: " + e.getMessage(), e);
        }
    }

    @Override
    public Customer findCustomerById(String customerId) throws CustomerNotFoundException {
        try {
            return dao.getById(customerId);
        } catch (RecordNotFoundException e) {
            throw new CustomerNotFoundException();
        }
    }

    @Override
    public List<Customer> findCustomersByName(String name) {
        return dao.getByName(name);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return dao.getAllCustomers();
    }

    @Override
    public Customer getFullCustomerDetail(String customerId) throws CustomerNotFoundException {
        try {
            return dao.getFullCustomerDetail(customerId);
        } catch (RecordNotFoundException e) {
            throw new CustomerNotFoundException();
        }
    }

    @Override
    public void recordCall(String customerId, Call callDetails) throws CustomerNotFoundException {
        try {
            dao.addCall(callDetails, customerId);
        } catch (RecordNotFoundException e) {
            throw new CustomerNotFoundException();
        }
    }
}
