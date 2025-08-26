package dev.germane.customer;

import java.util.List;
import java.util.Optional;

public interface CustomerDao {

    List<Customer> selectAllCustomers();
    List<Customer> selectCustomersByFilter(String name, String email);
    Optional<Customer> selectCustomerById(Long customerId);
    void insertCustomer(Customer customer);
    boolean existsCustomerWithEmail(String email);
    boolean existsCustomerWithId(Long customerId);
    void deleteById(Long customerId);
    void updateCustomer(Customer customer);
    Optional<Customer> selectUserByEmail(String email);
    void updateProfileImageId(Long customerId, String profileImageId);
}
