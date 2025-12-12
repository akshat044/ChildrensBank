package in.akshat.auth_service.service;

 
 
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import in.akshat.auth_service.model.Customer;
import in.akshat.auth_service.repository.CustomerRepository;
import in.akshat.auth_service.util.JwtUtil;

@Service
public class AuthService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // ✅ Constructor injection (no need for @Autowired)
    public AuthService(CustomerRepository customerRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public void registerCustomer(String email, String password) {
        if (customerRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }

        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setPwd(passwordEncoder.encode(password));
        customer.setRole("ROLE_USER");
         

        customerRepository.save(customer);
    }

    public String generateJwtToken(String email) {
        return jwtUtil.generateToken(email);
    }

    public Customer getCustomerDetails(String email) {
        return customerRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
    }
}
