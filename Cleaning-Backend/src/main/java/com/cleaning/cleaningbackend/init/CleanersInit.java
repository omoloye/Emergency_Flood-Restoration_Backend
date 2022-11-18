package com.cleaning.cleaningbackend.init;

import com.cleaning.cleaningbackend.models.Cleaner;
import com.cleaning.cleaningbackend.repository.CleanerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CleanersInit implements CommandLineRunner {

    private CleanerRepository cleanerRepository;

    public CleanersInit(CleanerRepository cleanerRepository) {
        this.cleanerRepository = cleanerRepository;
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
    }
}
