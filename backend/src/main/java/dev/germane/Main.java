package dev.germane;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import dev.germane.customer.Customer;
import dev.germane.customer.CustomerRepository;
import dev.germane.customer.Gender;
import dev.germane.s3.S3Buckets;
import dev.germane.s3.S3Service;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    CommandLineRunner runner(
            CustomerRepository customerRepository,
            PasswordEncoder passwordEncoder//,
            //S3Service s3Service,
            //S3Buckets s3Buckets
    ) {
        return args -> {
            createRandomCustomer(customerRepository, passwordEncoder);
//            testBucketUploadAndDownload(s3Service, s3Buckets);
        };
    }

    private static void testBucketUploadAndDownload(S3Service s3Service, S3Buckets s3Buckets) {
        s3Service.putObject(
                s3Buckets.getCustomer(),
                "foo/bar/devgerman",
                "Hello World".getBytes());

        byte[] obj = s3Service.getObject(
                s3Buckets.getCustomer(),
                "foo/bar/devgerman");

        System.out.println("HOoorday: " + new String(obj));
    }

    private static void createRandomCustomer(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        Faker faker = new Faker();
        Name name = faker.name();
        String firstName = name.firstName();
        String lastName = name.lastName();
        Gender gender = faker.options().option(Gender.class);


        Customer customer = new Customer(
                firstName + " " + lastName,
                (firstName + "." + lastName).toLowerCase() + "@gmail.com",
                passwordEncoder.encode(UUID.randomUUID().toString()),
                faker.random().nextInt(10, 100), gender);
        customerRepository.save(customer);
    }
}
