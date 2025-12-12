package in.akshat.customer_service.repository;

import in.akshat.customer_service.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);
    Optional<Customer> findByPhoneNo(String phoneNo);
    Optional<Customer> findByPanNo(String panNo);
    Optional<Customer> findByAadhaarNo(String aadhaarNo);
    
    Optional<Customer> findByEmailOrPhoneNoOrAadhaarNoOrPanNo(
            String email,
            String phoneNo,
            String aadhaarNo,
            String panNo
    );
    
    boolean existsByEmailAndCustomerIdNot(String email, Long id);
    boolean existsByPhoneNoAndCustomerIdNot(String phoneNo, Long id);
    boolean existsByAadhaarNoAndCustomerIdNot(String aadhaarNo, Long id);
    boolean existsByPanNoAndCustomerIdNot(String panNo, Long id);

}
