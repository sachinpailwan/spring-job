package com.example.springjob.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class HelloWorkJob  {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job(){
       return jobBuilderFactory.get("hello-world")
                .start(hello())
                .build();
    }

    private Step hello() {
        return stepBuilderFactory.get("hello")
                .tasklet((stepContribution, chunkContext) -> {
                    log.info("Running Hello World job");
                    return RepeatStatus.FINISHED;
                }).build();
    }
}
