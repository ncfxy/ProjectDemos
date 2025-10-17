package com.ncfxy.SpringBootDemo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Stream;

@SpringBootApplication
public class SpringBootDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemoApplication.class, args);
    }

    @Bean
    ApplicationRunner run(CustomerRespository cr) {
        return args -> Stream.of("Jane", "Onsi", "Dave", "Mia")
                .forEach(
                        x -> cr.save(new Customer(null, x))
                );
    }
}

@Configuration
@EnableWebSecurity
class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Bean
    UserDetailsService users() {
        return new InMemoryUserDetailsManager(Collections.singleton(User.withUsername("ncfxy").roles("ADMIN").password("pw").build()));
    }
}

@Component
class CustomHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {
        return Health.status("I <3 Production!").build();
    }
}

@RestController
class CustomerRestController {

    private final CustomerRespository customerRespository;

    CustomerRestController(CustomerRespository customerRespository) {
        this.customerRespository = customerRespository;
    }

    @GetMapping("/customers")
    Collection<Customer> customerCollections () {
        return this.customerRespository.findAll();
    }
}

interface CustomerRespository extends JpaRepository<Customer, Long> {}

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
class Customer {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
}