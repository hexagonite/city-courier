package pl.ug.citycourier;

import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.ug.citycourier.internal.security.entity.Role;
import pl.ug.citycourier.internal.security.internal.repository.RoleRepository;

@SpringBootApplication
public class CityCourierApplication {

    public static void main(String[] args) {
        SpringApplication.run(CityCourierApplication.class, args);
    }

    @Autowired
    private RoleRepository roleRepository;

    @Bean
    public SmartInitializingSingleton addUserRoles() {
        return () -> {
            roleRepository.save(new Role(1, "CLIENT"));
            roleRepository.save(new Role(2, "ADMIN"));
            roleRepository.save(new Role(3, "COURIER"));
        };
    }

}
