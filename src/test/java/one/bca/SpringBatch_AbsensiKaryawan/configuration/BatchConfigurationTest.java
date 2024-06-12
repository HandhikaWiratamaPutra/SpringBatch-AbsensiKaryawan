package one.bca.SpringBatch_AbsensiKaryawan.configuration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.core.*;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

//@Configuration
//@ExtendWith(SpringExtension.class)
@SpringBatchTest
@SpringBootTest
class BatchConfigurationTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;
    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    private JobExecution jobExecution;

    @AfterEach
    void cleanup() {
        // Clean up job executions after each test
//        jobRepositoryTestUtils.removeJobExecutions();
    }

    @Test
    void jobStart() throws Exception {
        //pastikan @Bean di job tidak di comment
        Long currentMilis = System.currentTimeMillis();
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time - testing", currentMilis)
                .toJobParameters();
//        jobLauncherTestUtils.setJob(job);

        //when Launch the job
        jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        //then
        Assertions.assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
    }

    @Test
    void masterCreateOutputCSVAbsensi() throws Exception {
        //pastikan @Bean di job tidak di comment
        Long currentMilis = System.currentTimeMillis();
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time - testing", currentMilis)
                .toJobParameters();
//        jobLauncherTestUtils.setJob(job);

        //when Launch the job
        jobExecution = jobLauncherTestUtils.launchStep("masterCreateOutputCSVAbsensi", jobParameters);

        //then
        Assertions.assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
    }
}