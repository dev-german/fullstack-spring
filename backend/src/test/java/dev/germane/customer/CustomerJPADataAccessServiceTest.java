package dev.germane.customer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

class CustomerJPADataAccessServiceTest {

    private CustomerJPADataAccessService underTest;
    private AutoCloseable autoCloseable;
    @Mock
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new CustomerJPADataAccessService(customerRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void selectAllCustomers() {
        // When
        underTest.selectAllCustomers();

        // Then
        verify(customerRepository)
                .findAll();
    }

    @Test
    void selectCustomerById() {
        // Given
        Long id = 1L;

        // When
        underTest.selectCustomerById(id);

        // Then
        verify(customerRepository).findById(id);
    }

    @Test
    void insertCustomer() {
        // Given
        Customer customer = new Customer(
                "German",
                "german@gmail.com",
                "password", 20,
                Gender.MALE);
        // When
        underTest.insertCustomer(customer);
        // Then
        verify(customerRepository).save(customer);
    }

    @Test
    void existsCustomerWithEmail() {
        // Given
        String email = "newEmail@gmail.com";
        // When
        underTest.existsCustomerWithEmail(email);

        // Then
        verify(customerRepository).existsCustomerByEmail(email);
    }

    @Test
    void existsCustomerWithId() {
        // Given
        Long id = 1L;
        // When
        underTest.existsCustomerWithId(id);
        // Then
        verify(customerRepository).existsCustomerById(id);
    }

    @Test
    void deleteById() {
        // Given
        Long id = 1L;
        // When
        underTest.deleteById(id);

        // Then
        verify(customerRepository).deleteById(id);
    }

    @Test
    void updateCustomer() {
        // Given
        Customer customer = new Customer(
                "German",
                "german@gmail.com",
                "password", 20,
                Gender.MALE);

        // When
        underTest.updateCustomer(customer);
        // Then
        verify(customerRepository).save(customer);
    }

    @Test
    void canUpdateProfileImageId() {
        // Given
        String profileImageId = "profileImageId1234";
        Long customerId = 1L;

        // When
        underTest.updateProfileImageId(customerId, profileImageId);

        // Then
        verify(customerRepository).updateProfileImageId(customerId, profileImageId);
    }
}