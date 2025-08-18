package dev.germane.customer;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

  boolean existsCustomerByEmail(String email);

  boolean existsCustomerById(Long customerId);

  Optional<Customer> findByEmail(String email);

  @Modifying(clearAutomatically = true)
  @Query("UPDATE Customer c SET c.profileImageId = ?2 WHERE c.id = ?1")
  void updateProfileImageId(Long customerId, String profileImageId);
}
