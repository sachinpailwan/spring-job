package com.example.springjob.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JobRunningService {

    private final JobRegistry jobRegistry;
    private final JobLauncher jobLauncher;


    public JobRunningService(JobRegistry jobRegistry, JobLauncher jobLauncher) {
        this.jobRegistry = jobRegistry;
        this.jobLauncher = jobLauncher;
    }

    public void runJob(String jobName, JobParameters jobParameters) {
        Job job;
        try {
            job = jobRegistry.getJob(jobName);
        }catch (NoSuchJobException e){
            log.error("Problem with job name. Job name not found ",e);
            return;
        }
        runJob(job,jobParameters);
    }


    public void runJob(Job job,JobParameters jobParameters){
        try{
            jobLauncher.run(job,jobParameters);
        }catch (JobExecutionException e){
            log.error("Problem occurred when trying to run job",e);
        }
    }
}
