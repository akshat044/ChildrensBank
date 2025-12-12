package in.akshat.auth_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import in.akshat.auth_service.model.Customer;

public interface CustomerRepository  extends JpaRepository<Customer,Long>{
	Optional<Customer> findByEmail(String email);
}
