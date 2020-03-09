package pl.ug.citycourier;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import pl.ug.citycourier.internal.security.entity.Role;
import pl.ug.citycourier.internal.security.internal.repository.RoleRepository;

@SpringBootApplication
@ComponentScan(basePackages = "pl.ug.citycourier")
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

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
