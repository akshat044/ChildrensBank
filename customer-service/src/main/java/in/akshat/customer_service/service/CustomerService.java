package in.akshat.customer_service.service;

import in.akshat.customer_service.dto.*;
import in.akshat.customer_service.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerService {
    CustomerResponse create(CustomerCreateDto dto);
    CustomerResponse update(Long id, CustomerUpdateDto dto);
    void delete(Long id);
    Customer get(Long id);
    Page<Customer> list(Pageable pageable);
}
