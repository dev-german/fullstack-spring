package dev.germane.customer;

import dev.germane.jwt.JWTUtil;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final JWTUtil jwtUtil;

    public CustomerController(CustomerService customerService, JWTUtil jwtUtil) {
        this.customerService = customerService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping
    public List<CustomerDTO> getCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("{customerId}")
    public CustomerDTO getCustomerById(@PathVariable("customerId") Long customerId) {
        return customerService.getCustomer(customerId);
    }

    @PostMapping
    public ResponseEntity<?> registerCustomer(@RequestBody CustomerRegistrationRequest request){
        customerService.addCustomer(request);
        String jwtToken = jwtUtil.issueToken(request.email(), "ROLE_USER");
        return ResponseEntity.ok()
                .header(AUTHORIZATION, jwtToken)
                .build();
    }

    @DeleteMapping("{customerId}")
    public void deleteCustomer(@PathVariable("customerId") Long customerId) {
        customerService.deleteCustomerById(customerId);
    }

    @PutMapping("{customerId}")
    public void updateCustomer(
            @PathVariable("customerId") Long customerId,
            @RequestBody CustomerUpdateRequest request){
        customerService.updateCustomer(customerId, request);
    }

    @PostMapping(
            value = "{customerId}/profile-image",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public void uploadCustomerProfilePicture(
            @PathVariable("customerId") Long customerId,
            @RequestParam("file")MultipartFile file){
        customerService.uploadCustomerProfileImage(customerId, file);
    }

    @GetMapping(value = "{customerId}/profile-image")
    public byte[] getCustomerProfilePicture(
            @PathVariable("customerId") Long customerId){
        return customerService.getCustomerProfileImage(customerId);
    }
}
