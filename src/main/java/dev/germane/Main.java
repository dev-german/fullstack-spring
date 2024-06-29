package dev.germane;

import dev.germane.customer.Customer;
import dev.germane.customer.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    CommandLineRunner runner(CustomerRepository customerRepository) {
        return args -> {
            Customer german = new Customer("German", "german@gmail.com", 21);
            Customer marita = new Customer("Marita", "marita@gmail.com", 22);
            customerRepository.saveAll(List.of(german, marita));
        };
    }
}
