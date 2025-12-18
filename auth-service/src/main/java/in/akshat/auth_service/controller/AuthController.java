package in.akshat.auth_service.controller;
 
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import in.akshat.auth_service.dto.RegisterRequest;
import in.akshat.auth_service.model.Customer;
import in.akshat.auth_service.service.AuthService;
import jakarta.validation.Valid;

import java.util.Map;

@RefreshScope
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final AuthService authService;

    // ✅ Constructor injection (recommended)
    public AuthController(AuthenticationManager authenticationManager,
                          AuthService authService) {
        this.authenticationManager = authenticationManager;
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request) {
        authService.registerCustomer(request.getEmail(), request.getPassword());
        return ResponseEntity.ok("Customer registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");


        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
            );
        } catch (org.springframework.security.core.AuthenticationException ex) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
        }

        String token = authService.generateJwtToken(email);

        return ResponseEntity.ok(Map.of("token", token));
    }
    
    
    
    // ***** for our case, security context will be empty because api gateway will stored security context and token validation if we want to 
    // to put our own validation here then we need to pass down the header , secuiryt context will be empty so it will say for now that customer not found
    @GetMapping("/account")
    public ResponseEntity<Customer> getAccount() {
        // ✅ Get current logged-in user's email from SecurityContext (set by JwtRequestFilter)
        String email = SecurityContextHolder.getContext()
                                            .getAuthentication()
                                            .getName();

        Customer customer = authService.getCustomerDetails(email);
        return ResponseEntity.ok(customer);
    }
}
