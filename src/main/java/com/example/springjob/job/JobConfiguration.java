package com.example.springjob.job;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.support.ExitCodeMapper;
import org.springframework.batch.core.launch.support.SimpleJvmExitCodeMapper;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableBatchProcessing
public class JobConfiguration {

    public ExitCodeMapper exitCodeMapper(){
        final SimpleJvmExitCodeMapper exitCodeMapper = new SimpleJvmExitCodeMapper();
        Map<String,Integer> map = new HashMap<>();
        map.put("NONE_PROCESSED",3);
        exitCodeMapper.setMapping(map);
        return exitCodeMapper;
    }
}
