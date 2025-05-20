package se.yrgo.services.customers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import se.yrgo.domain.Call;
import se.yrgo.domain.Customer;

public class CustomerManagementMockImpl implements CustomerManagementService {
	private HashMap<String, Customer> customerMap;

	public CustomerManagementMockImpl() {
		customerMap = new HashMap<String, Customer>();
		customerMap.put("OB74", new Customer("OB74", "Fargo Ltd", "some notes"));
		customerMap.put("NV10", new Customer("NV10", "North Ltd", "some other notes"));
		customerMap.put("RM210", new Customer("RM210", "River Ltd", "some more notes"));
	}

	@Override
	public void newCustomer(Customer newCustomer) {
		customerMap.put(newCustomer.getCustomerId(), newCustomer);
	}

	@Override
	public void updateCustomer(Customer changedCustomer) {
		customerMap.put(changedCustomer.getCustomerId(), changedCustomer);
	}

	@Override
	public void deleteCustomer(Customer oldCustomer) {
		customerMap.remove(oldCustomer.getCustomerId(), oldCustomer);
	}

	@Override
	public Customer findCustomerById(String customerId) throws CustomerNotFoundException {
		Customer customerById = customerMap.get(customerId);
		return customerById;
	}

	@Override
	public List<Customer> findCustomersByName(String name) {
		List<Customer> matchingCustomers = new ArrayList<>();
		for (Customer customer : customerMap.values()) {
			if (customer.getCompanyName().equals(name)) {
				matchingCustomers.add(customer);
			}
		}
		return matchingCustomers;
	}

	@Override
	public List<Customer> getAllCustomers() {
		List<Customer> allCustomers = new ArrayList<>();
		for (Customer customer : customerMap.values()) {
			allCustomers.add(customer);
		}
		return allCustomers;
	}

	@Override
	public Customer getFullCustomerDetail(String customerId) throws CustomerNotFoundException {
		Customer customer = customerMap.get(customerId);
		return customer;
	}

	@Override
	public void recordCall(String customerId, Call callDetails) throws CustomerNotFoundException {
		Customer customer = findCustomerById(customerId);
		customer.addCall(callDetails);
	}
}