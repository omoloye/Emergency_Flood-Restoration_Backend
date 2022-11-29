package com.cleaning.cleaningbackend.init;

import com.cleaning.cleaningbackend.models.Cleaner;
import com.cleaning.cleaningbackend.models.Service;
import com.cleaning.cleaningbackend.models.User;
import com.cleaning.cleaningbackend.models.enums.Role;
import com.cleaning.cleaningbackend.repository.CleanerRepository;
import com.cleaning.cleaningbackend.repository.ServiceRepository;
import com.cleaning.cleaningbackend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CleanersInit implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;

    private final CleanerRepository cleanerRepository;

    private final ServiceRepository serviceRepository;

    private final UserRepository userRepository;

    public CleanersInit(PasswordEncoder passwordEncoder, CleanerRepository cleanerRepository, ServiceRepository serviceRepository, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.cleanerRepository = cleanerRepository;
        this.serviceRepository = serviceRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Cleaner cleaner1 = new Cleaner(1L,"Tony","12345678", false);
        cleanerRepository.save(cleaner1);
        Cleaner cleaner2 = new Cleaner(2L,"Tomi", "12345678", false);
        cleanerRepository.save(cleaner2);
        Cleaner cleaner3 = new Cleaner(3L,"Emmanuel","12345678",false );
        cleanerRepository.save(cleaner3);
        Cleaner cleaner4 = new Cleaner(4L,"Tolu", "12345678", false);
        cleanerRepository.save(cleaner4);
        Cleaner cleaner5 = new Cleaner(5L,"GB", "12345678", false);
        cleanerRepository.save(cleaner5);
        Service service1 = new Service(1L,"Sewage_Clean_Up",6000.00);
        serviceRepository.save(service1);
        Service service2 = new Service(2L,"Gutter_Cleaning",4000.00);
        serviceRepository.save(service2);
        Service service3 = new Service(3L,"Flood_Water_Damage_Cleaning",7000.00);
        serviceRepository.save(service3);
        Service service4 = new Service(4L,"Flood_Water_Cleaning",5000.00);
        serviceRepository.save(service4);

        User user = new User("admin","admin","admin@gmail.com", passwordEncoder.encode("0147"), Role.ADMIN );
        userRepository.save(user);


    }
}
