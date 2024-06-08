package one.bca.SpringBatch_AbsensiKaryawan.configuration;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
public class BatchConfiguration {
    private final DataSourceTransactionManager transactionManager;
    private final JobRepository jobRepository;

    public BatchConfiguration(DataSourceTransactionManager transactionManager, JobRepository jobRepository) {
        this.transactionManager = transactionManager;
        this.jobRepository = jobRepository;
    }

    public Job jobStart() throws Exception {
        return new JobBuilder("jobBatch0304-35", jobRepository)
                .start(stepCreateOutputCSVAbsensi())
                .build();
    }

    public Step stepCreateOutputCSVAbsensi() {
        return new StepBuilder("stepCreateOutputCSVAbsensi", jobRepository)
                .<>chunk(10, transactionManager)
                .reader()
                .processor()
                .writer()
                .build();
    }
}
