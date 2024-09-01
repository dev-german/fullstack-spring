package dev.germane.customer;

import dev.germane.exception.DuplicateResourceException;
import dev.germane.exception.RequestValidationException;
import dev.germane.exception.ResourceNotFoundException;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    private CustomerService underTest;

    @Mock
    CustomerDao customerDao;

    @BeforeEach
    void setUp() {
        underTest = new CustomerService(customerDao);
    }

    @Test
    void getAllCustomers() {
        // When
        underTest.getAllCustomers();

        // Then
        verify(customerDao).selectAllCustomers();
    }

    @Test
    void canGetCustomer() {
        // Given
        Long id = 1L;
        Customer customer = new Customer(
                id, "German", "german@gmail.com", 20
        );

        when(customerDao.selectCustomerById(id)).thenReturn(
                Optional.of(customer)
        );

        // When
        Customer actual = underTest.getCustomer(id);

        // Then
        assertThat(actual).isEqualTo(customer);
        verify(customerDao).selectCustomerById(id);
    }

    @Test
    void willThrowWhenGetCustomerReturnEmptyOptional() {
        // Given
        Long id = 1L;

        when(customerDao.selectCustomerById(id)).thenReturn(
                Optional.empty()
        );

        // When
        // Then
        assertThatThrownBy(() -> underTest.getCustomer(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage(("customer with id [%s] not found").formatted(id));
    }

    @Test
    void addCustomer() {
        // Given
        String email = "ggrandos@gmail.com";

        when(customerDao.existsCustomerWithEmail(email)).thenReturn(false);

        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
                "German", email, 20);

        // When
        underTest.addCustomer(request);

        // Then
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor
                .forClass(Customer.class);

        verify(customerDao).insertCustomer(customerArgumentCaptor.capture());

        Customer capturedCustomer = customerArgumentCaptor.getValue();

        assertThat(capturedCustomer.getId()).isNull();
        assertThat(capturedCustomer.getName()).isEqualTo(request.name());
        assertThat(capturedCustomer.getEmail()).isEqualTo(request.email());
        assertThat(capturedCustomer.getAge()).isEqualTo(request.age());
    }

    @Test
    void willThrowWhenEmailExistsWhileAddingACostumer() {
        // Given
        String email = "ggrandos@gmail.com";

        when(customerDao.existsCustomerWithEmail(email)).thenReturn(true);

        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
                "German", email, 20);

        // When
        assertThatThrownBy(() -> underTest.addCustomer(request))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("Email already taken");

        // Then
        verify(customerDao, never()).insertCustomer(any());
    }

    @Test
    void deleteCustomerById() {
        // Given
        Long id = 1L;

        when(customerDao.existsCustomerWithId(id)).thenReturn(true);
        // When
        underTest.deleteCustomerById(id);

        // Then
        verify(customerDao).deleteById(id);
    }

    @Test
    void willThrowDeleteCustomerByIdNotExists() {
        // Given
        Long id = 1L;

        when(customerDao.existsCustomerWithId(id)).thenReturn(false);
        // When
        // Then
        assertThatThrownBy(() -> underTest.deleteCustomerById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Customer with id [%s] not found".formatted(id));

        verify(customerDao, never()).deleteById(id);
    }

    @Test
    void canUpdateAllCustomersProperties() {
        // Given
        Long id = 1L;
        Customer customer = new Customer(
                id, "German", "german@gmail.com", 20
        );

        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));

        String newEmail = "germani@gmail.com";
        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest("Germani",
                newEmail, 21);

        when(customerDao.existsCustomerWithEmail(newEmail)).thenReturn(false);

        // When
        underTest.updateCustomer(id, updateRequest);

        // Then
        ArgumentCaptor<Customer> customerArgumentCaptor =
                ArgumentCaptor.forClass(Customer.class);

        verify(customerDao).updateCustomer(customerArgumentCaptor.capture());

        Customer capturedCostumer = customerArgumentCaptor.getValue();

        assertThat(capturedCostumer.getName()).isEqualTo(updateRequest.name());
        assertThat(capturedCostumer.getEmail()).isEqualTo(updateRequest.email());
        assertThat(capturedCostumer.getAge()).isEqualTo(updateRequest.age());
    }

    @Test
    void canUpdateOnlyCustomerName() {
        // Given
        Long id = 1L;
        Customer customer = new Customer(
                id, "German", "german@gmail.com", 20
        );

        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));

        String newName = "Germani";

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(newName,
                null, null);

        // When
        underTest.updateCustomer(id, updateRequest);

        // Then
        ArgumentCaptor<Customer> customerArgumentCaptor =
                ArgumentCaptor.forClass(Customer.class);

        verify(customerDao).updateCustomer(customerArgumentCaptor.capture());

        Customer capturedCostumer = customerArgumentCaptor.getValue();

        assertThat(capturedCostumer.getName()).isEqualTo(updateRequest.name());
        assertThat(capturedCostumer.getEmail()).isEqualTo(customer.getEmail());
        assertThat(capturedCostumer.getAge()).isEqualTo(customer.getAge());
    }

    @Test
    void canUpdateOnlyCustomerEmail() {
        // Given
        Long id = 1L;
        Customer customer = new Customer(
                id, "German", "german@gmail.com", 20
        );

        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));

        String newEmail = "germani@gmail.com";

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(null,
                newEmail, null);

        when(customerDao.existsCustomerWithEmail(newEmail)).thenReturn(false);

        // When
        underTest.updateCustomer(id, updateRequest);

        // Then
        ArgumentCaptor<Customer> customerArgumentCaptor =
                ArgumentCaptor.forClass(Customer.class);

        verify(customerDao).updateCustomer(customerArgumentCaptor.capture());

        Customer capturedCostumer = customerArgumentCaptor.getValue();

        assertThat(capturedCostumer.getName()).isEqualTo(customer.getName());
        assertThat(capturedCostumer.getEmail()).isEqualTo(updateRequest.email());
        assertThat(capturedCostumer.getAge()).isEqualTo(customer.getAge());
    }

    @Test
    void canUpdateOnlyCustomerAge() {
        // Given
        Long id = 1L;
        Customer customer = new Customer(
                id, "German", "german@gmail.com", 20
        );

        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));

        int newAge = 2;
        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(null,
                null, newAge);

        // When
        underTest.updateCustomer(id, updateRequest);

        // Then
        ArgumentCaptor<Customer> customerArgumentCaptor =
                ArgumentCaptor.forClass(Customer.class);

        verify(customerDao).updateCustomer(customerArgumentCaptor.capture());

        Customer capturedCostumer = customerArgumentCaptor.getValue();

        assertThat(capturedCostumer.getName()).isEqualTo(customer.getName());
        assertThat(capturedCostumer.getEmail()).isEqualTo(customer.getEmail());
        assertThat(capturedCostumer.getAge()).isEqualTo(updateRequest.age());
    }

    @Test
    void willThrowWhenTryingToUpdateCustomerEmailWhenAlreadyTaken() {
        // Given
        Long id = 1L;
        Customer customer = new Customer(
                id, "German", "german@gmail.com", 20
        );

        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));

        String newEmail = "germani@gmail.com";

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(null,
                newEmail, null);

        when(customerDao.existsCustomerWithEmail(newEmail)).thenReturn(true);

        // When
        // Then
        assertThatThrownBy(() -> underTest.updateCustomer(id, updateRequest))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("Email already taken");

        verify(customerDao, never()).updateCustomer(any());
    }

    @Test
    void willThrowWhenCustomerUpdateHasNoChanges() {
        // Given
        Long id = 1L;
        Customer customer = new Customer(
                id, "German", "german@gmail.com", 20
        );

        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(customer.getName(),
                customer.getEmail(), customer.getAge());

        // When
        // Then
        assertThatThrownBy(() -> underTest.updateCustomer(id,  updateRequest))
                .isInstanceOf(RequestValidationException.class)
                .hasMessage("no data changes found");

        verify(customerDao, never()).updateCustomer(customer);

    }
}