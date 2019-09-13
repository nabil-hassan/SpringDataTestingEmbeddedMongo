package net.nh;

import net.nh.entity.Customer;
import net.nh.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationMain {

    private static final Logger LOG = LoggerFactory.getLogger(ApplicationMain.class);

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext("net.nh.config");

        CustomerRepository customerRepository = context.getBean("customerRepository", CustomerRepository.class);

        long count = customerRepository.count();
        LOG.info("There are currently {} customers in the database", count);
    }

}
