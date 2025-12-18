package in.akshat.customer_service.controller;

import in.akshat.customer_service.config.CustomerConfig;
import in.akshat.customer_service.dto.*;
import in.akshat.customer_service.entity.Customer;
import in.akshat.customer_service.mapper.CustomerMapper;
import in.akshat.customer_service.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RefreshScope
@RestController
@RequestMapping("/api/customers") 
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService service;
    
     private final CustomerConfig customerConfig;
    
    // just for checking autorefresh
    @GetMapping("/print")
    public String hello() {
    	return "Hey there Buddy!!! i am customer and update message : " + customerConfig.getMessage();
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> create(@Valid @RequestBody CustomerCreateDto dto){
        return ResponseEntity.ok(
        		 service.create(dto)  // in service we are converting dto to entity
        		);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CustomerResponse> update(@PathVariable Long id, @Valid @RequestBody CustomerUpdateDto dto){
    	return ResponseEntity.ok(
       		 service.update(id,dto)  // in service we are converting dto to entity
       		);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> get(@PathVariable Long id){
        return ResponseEntity.ok(CustomerMapper.toResponse(service.get(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<CustomerResponse>> list(Pageable pageable) {

        Page<Customer> customersPage = service.list(pageable);

        Page<CustomerResponse> dtoPage = customersPage.map(customer -> CustomerMapper.toResponse(customer));

        return ResponseEntity.ok(dtoPage);
    }

}
