package com.uzumtech.court;

import com.uzumtech.court.component.AiContextLoader;
import com.uzumtech.court.entity.AdminEntity;
import com.uzumtech.court.entity.ExternalServiceEntity;
import com.uzumtech.court.entity.JudgeEntity;
import com.uzumtech.court.repository.AdminRepository;
import com.uzumtech.court.repository.ExternalServiceRepository;
import com.uzumtech.court.repository.JudgeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableScheduling
@SpringBootApplication
@RequiredArgsConstructor
public class CourtApplication implements CommandLineRunner {
    private final AdminRepository adminRepository;
    private final JudgeRepository judgeRepository;
    private final ExternalServiceRepository externalServiceRepository;
    private final PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(CourtApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        boolean adminExists = adminRepository.existsByLogin("admin");

        if (!adminExists) {
            var admin = AdminEntity.builder().login("admin").password(passwordEncoder.encode("123123")).build();

            adminRepository.save(admin);
        }

        boolean judgeExists = judgeRepository.existsByEmail("judge@gmail.com");

        if (!judgeExists) {
            var judge = JudgeEntity.builder().email("judge@gmail.com").fullName("name").password(passwordEncoder.encode("123123")).build();

            judgeRepository.save(judge);
        }


        boolean externalServiceExists = externalServiceRepository.existsByLogin("fines");

        if (!externalServiceExists) {
            var service = ExternalServiceEntity.builder()
                .login("fines")
                .password(passwordEncoder.encode("123123")).webhookSecret("secret").webhookUrl("https://localhost:7070").build();

            externalServiceRepository.save(service);
        }
    }
}
