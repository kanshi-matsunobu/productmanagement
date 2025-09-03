package com.example.demo.config;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.demo.entity.Admin;
import com.example.demo.repository.AdminRepository;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Bean
    Job notifyInactiveAdminsJob() {
        return new JobBuilder("notifyInactiveAdminsJob", jobRepository)
                .start(notifyInactiveAdminsStep())
                .build();
    }

    @Bean
    Step notifyInactiveAdminsStep() {
        return new StepBuilder("notifyInactiveAdminsStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    LocalDateTime oneWeekAgo = LocalDateTime.now().minusWeeks(1);
                    List<Admin> inactiveAdmins = adminRepository.findAll().stream()
                            .filter(admin -> admin.getLastLoginAt() == null || admin.getLastLoginAt().isBefore(oneWeekAgo))
                            .toList();

                    for (Admin admin : inactiveAdmins) {
                        SimpleMailMessage message = new SimpleMailMessage();
                        message.setTo(admin.getEmail());
                        message.setSubject("【通知】1週間以上ログインがありません");
                        message.setText(admin.getLastName() + " " + admin.getFirstName() + " 様\n\n"
                                + "1週間以上ログインされていません。\nログインをお願いいたします。");
                        mailSender.send(message);
                        System.out.println("通知メール送信：" + admin.getEmail());
                    }

                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build();
    }
}