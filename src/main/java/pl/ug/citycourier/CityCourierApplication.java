package pl.ug.citycourier;

import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;
import pl.ug.citycourier.internal.security.boundary.RoleName;
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
            roleRepository.save(new Role(1, RoleName.CLIENT));
            roleRepository.save(new Role(2, RoleName.ADMIN));
            roleRepository.save(new Role(3, RoleName.COURIER));
        };
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

}
