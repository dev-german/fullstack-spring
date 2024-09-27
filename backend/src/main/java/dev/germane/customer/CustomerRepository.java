package dev.germane.customer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository
        extends JpaRepository<Customer, Long> {

    boolean existsCustomerByEmail(String email);
    boolean existsCustomerById(Long customerId);

    Optional<Customer> findByEmail(String email);
}
