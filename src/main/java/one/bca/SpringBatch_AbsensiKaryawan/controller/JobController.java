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
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            // jobLauncher.run(batchConfiguration.jobStart(), jobParameters);
            jobLauncher.run(batchConfiguration.jobPengolahanAbsensiKaryawan(), jobParameters);
            return "Job started successfully";
        } catch (Exception e) {
            return "Job failed to start: " + e.getMessage();
        }
    }
}
