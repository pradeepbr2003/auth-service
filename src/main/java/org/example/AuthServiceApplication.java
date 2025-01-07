package org.example;

import org.example.dto.Student;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.stream.IntStream;

@SpringBootApplication
public class AuthServiceApplication {

    public static void main(String[] args) {

        ApplicationContext context = SpringApplication.run(AuthServiceApplication.class, args);
    }

    @Bean
    public List<Student> students() {
        return IntStream.rangeClosed(1, 100)
                .mapToObj(i -> Student.builder().id(i)
                        .name("student-" + i).course("course-" + i)
                        .build()).toList();
    }
}
