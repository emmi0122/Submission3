package se.yrgo.client;

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import se.yrgo.domain.Customer;
import se.yrgo.services.calls.CallHandlingService;
import se.yrgo.services.customers.CustomerManagementService;

import se.yrgo.domain.Call;
import se.yrgo.domain.Action;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class SimpleClient {

    public static void main(String[] args) {
        // Del 1
        /*
         * ClassPathXmlApplicationContext container = new
         * ClassPathXmlApplicationContext("application.xml");
         * CustomerManagementService service =
         * container.getBean("CustomerManagementService",
         * CustomerManagementService.class);
         * 
         * 
         * List<Customer> allCustomers = service.getAllCustomers();
         * for (Customer customer : allCustomers) {
         * System.out.println(customer);
         * }
         * 
         * container.close();
         */

        // Del 2
        ClassPathXmlApplicationContext container = new ClassPathXmlApplicationContext("application.xml");
        CallHandlingService callService = container.getBean("CallHandlingService", CallHandlingService.class);

        Call call = new Call("Test call from client");

        Calendar requiredBy = Calendar.getInstance();
        requiredBy.add(Calendar.DAY_OF_YEAR, 7);

        Action action1 = new Action("Follow up", requiredBy, "user1");
        Action action2 = new Action("Send email", requiredBy, "user1");

        try {
            callService.recordCall("OB74", call, Arrays.asList(action1, action2));
            System.out.println("Samtal och actions registrerade!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        container.close();
    }
}