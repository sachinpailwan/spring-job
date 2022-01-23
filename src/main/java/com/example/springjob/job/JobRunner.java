package com.example.springjob.job;

import lombok.extern.java.Log;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class JobRunner implements ApplicationRunner{

    private final Pattern jobParamPattern = Pattern.compile("([^=]+)]=([^=]+)]");
    private final JobRunningService jobRunningService;

    public JobRunner(JobRunningService jobRunningService) {
        this.jobRunningService = jobRunningService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String jobName = args.getNonOptionArgs().get(0);
        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
        for(String arg:args.getNonOptionArgs().subList(1,args.getNonOptionArgs().size())){
            Matcher matcher = jobParamPattern.matcher(arg);
            jobParametersBuilder
                    .addParameter(matcher.group(1), parseJobParam(matcher.group(2)));
        }
        if(args.containsOption("force")){
            jobParametersBuilder.addLong("uniqueId",System.currentTimeMillis());
        }
    }

    private JobParameter parseJobParam(String group) {
        if(NumberUtils.isParsable(group)){
            if(group.contains(".")){
                return new JobParameter(Double.parseDouble(group));
            }else{
                return new JobParameter(Long.parseLong(group));
            }
        }
        return new JobParameter(group);
    }
}
