package com.manaswini.students;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner seed(StudentRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.saveAll(List.of(
                        new Student("Asha Rao", "asha@example.com", "Computer Science", 3),
                        new Student("Ravi Kumar", "ravi@example.com", "Electronics", 2),
                        new Student("Meera Nair", "meera@example.com", "Data Science", 4),
                        new Student("Kiran Reddy", "kiran@example.com", "Computer Science", 1),
                        new Student("Sneha Iyer", "sneha@example.com", "Mechanical", 2),
                        new Student("Arjun Mehta", "arjun@example.com", "Data Science", 3)));
            }
        };
    }
}
