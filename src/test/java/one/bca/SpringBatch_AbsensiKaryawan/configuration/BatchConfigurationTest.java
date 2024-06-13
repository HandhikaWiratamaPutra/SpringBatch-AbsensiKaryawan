package one.bca.SpringBatch_AbsensiKaryawan.configuration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.*;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBatchTest
@SpringBootTest
class BatchConfigurationTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;
    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    private JobExecution jobExecution;

    @Test
    void jobStart() throws Exception {
        //pastikan @Bean di job tidak di comment
        Long currentMilis = System.currentTimeMillis();
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time - testing", currentMilis)
                .toJobParameters();

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

        //when Launch the job
        jobExecution = jobLauncherTestUtils.launchStep("masterCreateOutputCSVAbsensi", jobParameters);

        //then
        Assertions.assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
    }

    @Test
    void step1() {
        //pastikan @Bean di job tidak di comment
        Long currentMilis = System.currentTimeMillis();
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time - testing", currentMilis)
                .toJobParameters();

        //when Launch the job
        jobExecution = jobLauncherTestUtils.launchStep("step1", jobParameters);

        //then
        Assertions.assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
    }

    @Test
    void stepAbsensiHarianToBulanan() {
        //pastikan @Bean di job tidak di comment
        Long currentMilis = System.currentTimeMillis();
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time - testing", currentMilis)
                .toJobParameters();

        //when Launch the job
        jobExecution = jobLauncherTestUtils.launchStep("stepAbsensiHarianToBulanan", jobParameters);

        //then
        Assertions.assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
    }
}