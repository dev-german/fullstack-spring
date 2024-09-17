package dev.germane;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import dev.germane.customer.Customer;
import dev.germane.customer.CustomerRepository;
import dev.germane.customer.Gender;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    CommandLineRunner runner(CustomerRepository customerRepository) {
        return args -> {
            Faker faker = new Faker();
            Name name = faker.name();
            String firstName = name.firstName();
            String lastName = name.lastName();
            Gender gender = faker.options().option(Gender.class);


            Customer customer = new Customer(
                    firstName + " " + lastName,
                    (firstName + "." + lastName).toLowerCase() + "@gmail.com",
                    faker.random().nextInt(10, 100), gender);
            customerRepository.save(customer);
        };
    }
}
