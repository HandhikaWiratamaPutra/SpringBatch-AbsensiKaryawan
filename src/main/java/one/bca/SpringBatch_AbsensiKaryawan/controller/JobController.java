package one.bca.SpringBatch_AbsensiKaryawan.controller;
import one.bca.SpringBatch_AbsensiKaryawan.configuration.BatchConfiguration;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JobController {

    private final JobLauncher jobLauncher;
    private final BatchConfiguration batchConfiguration;

    public JobController(JobLauncher jobLauncher, BatchConfiguration batchConfiguration) {
        this.jobLauncher = jobLauncher;
        this.batchConfiguration = batchConfiguration;
    }


    @GetMapping("/start-job")
    public String startJob() {
        try {
            Long currentMilis = System.currentTimeMillis();
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", currentMilis)
                    .toJobParameters();
            jobLauncher.run(batchConfiguration.jobStart(), jobParameters);
            return "Job started with parameter :" + currentMilis;
        } catch (Exception e) {
            return "Job failed to start: " + e.getMessage();
        }
    }

    @GetMapping("/start-job/{milisTime}")
    public String startJob(@PathVariable("milisTime") Long milisTime) {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", milisTime)
                    .toJobParameters();
            jobLauncher.run(batchConfiguration.jobStart(), jobParameters);
            return "Job started with parameter :" + milisTime;
        } catch (Exception e) {
            return "Job failed to start: " + e.getMessage();
        }
    }
}
