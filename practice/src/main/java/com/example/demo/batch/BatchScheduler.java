package com.example.demo.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BatchScheduler {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job notifyInactiveAdminsJob;

    // ✅ 毎日午前2時に定期実行
    @Scheduled(cron = "0 0 2 * * *")
    public void runScheduled() throws Exception {
        System.out.println("定期バッチスケジューラ実行");

        JobParameters params = new JobParametersBuilder()
                .addLong("timestamp", System.currentTimeMillis())
                .toJobParameters();

        jobLauncher.run(notifyInactiveAdminsJob, params);
    }
}