package dev.germane.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CustomerRepository
        extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {

    boolean existsCustomerByEmail(String email);
    boolean existsCustomerById(Long customerId);

    Optional<Customer> findByEmail(String email);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Customer c SET c.profileImageId = ?2 WHERE c.id = ?1")
    void updateProfileImageId(Long customerId, String profileImageId);
}
