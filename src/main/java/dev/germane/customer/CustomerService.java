package dev.germane.customer;

import dev.germane.exception.DuplicateResourceException;
import dev.germane.exception.RequestValidationException;
import dev.germane.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(@Qualifier("jdbc") CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public List<Customer> getAllCustomers(){
        return customerDao.selectAllCustomers();
    }

    public Customer getCustomer(Long id){
        return customerDao.selectCustomerById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ("customer with id [%s] not found").formatted(id)
                ));
    }

    public void addCustomer(
            CustomerRegistrationRequest customerRegistrationRequest){
        //check if email exists
        if(customerDao.existsCustomerWithEmail(customerRegistrationRequest.email())){
            throw new DuplicateResourceException("Email already taken");
        }
        //add
        Customer customer = new Customer(
                customerRegistrationRequest.name(),
                customerRegistrationRequest.email(),
                customerRegistrationRequest.age()
        );

        customerDao.insertCustomer(customer);
    }

    public void deleteCustomerById(Long customerId) {
        //check if customer exists
        if(!customerDao.existsCustomerWithId(customerId)){
            throw new ResourceNotFoundException(
                    "Customer with id [%s] not found".formatted(customerId)
            );
        };
        customerDao.deleteById(customerId);
    }

    public void updateCustomer(Long customerId, CustomerUpdateRequest request) {
        Customer  customer = getCustomer(customerId);

        boolean changes = false;

        if(request.name() != null && !request.name().equals(customer.getName())){
            customer.setName(request.name());
            changes = true;
        }

        if(request.age() != null && !request.age().equals(customer.getAge())){
            customer.setAge(request.age());
            changes = true;
        }

        if(request.email() != null && !request.email().equals(customer.getEmail())){
            if(customerDao.existsCustomerWithEmail(request.email())){
                throw new DuplicateResourceException("Email already taken");
            }
            customer.setEmail(request.email());
            changes = true;
        }

        if(!changes){
            throw new RequestValidationException("no data changes found");
        }

        customerDao.updateCustomer(customer);
    }
}
