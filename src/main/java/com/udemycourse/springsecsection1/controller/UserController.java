package com.udemycourse.springsecsection1.controller;

import com.udemycourse.springsecsection1.model.Customer;
import com.udemycourse.springsecsection1.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CompromisedPasswordChecker compromisedPasswordChecker;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Customer customer) {
        try {
            var decision = compromisedPasswordChecker.check(customer.getPwd());
            if(decision.isCompromised()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        "Password shows up as leaked, please create a new one"
                );
            }
            String hashedPwd = passwordEncoder.encode(customer.getPwd());
            customer.setPwd(hashedPwd);
            Customer savedCustomer = customerRepository.save(customer);
            if(savedCustomer.getId() != null) {
                return ResponseEntity.status(HttpStatus.OK).body(
                    "User details are successfully registered"
                );
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    "User registration failed"
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    "An exception occurred: " + e.getMessage()
            );
        }
    }
}
