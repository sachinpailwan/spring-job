package com.example.springjob.job;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class JobRunner implements ApplicationRunner {

    private final Pattern jobParameterPattern = Pattern.compile(
            "(\\w+)        # Match an alphanumeric identifier, capture in group 1\n" +
                    "=             # Match =                                             \n" +
                    "(             # Match and capture in group 2:                       \n" +
                    " (?:          # Either...                                           \n" +
                    "  \\[         #  a [                                                \n" +
                    "  [^\\[\\]]*  #  followed by any number of characters except [ or ] \n" +
                    "  \\]         #  followed by a ]                                    \n" +
                    " |            # or...                                               \n" +
                    "  [^\\[\\],]* #  any number of characters except commas, [ or ]     \n" +
                    " )            # End of alternation                                  \n" +
                    ")             # End of capturing group",
            Pattern.COMMENTS
            );

    private final JobRunningService jobRunningService;

    public JobRunner(JobRunningService jobRunningService) {
        this.jobRunningService = jobRunningService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String jobName= args.getNonOptionArgs().get(0);
        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
        for (String arg:args.getNonOptionArgs().subList(1,args.getNonOptionArgs().size())){
            String [] split = arg.split("=");

            jobParametersBuilder.addParameter(split[0],
                    parseJobParam(split[1]));
        }
        if(args.containsOption("force")){
            jobParametersBuilder.addLong("uniqueId",System.currentTimeMillis());
        }
        jobRunningService.runJob(jobName,jobParametersBuilder.toJobParameters());
    }

    private JobParameter parseJobParam(String value) {
        if(NumberUtils.isParsable(value)){
            if(value.contains(".")){
                return new JobParameter(Double.parseDouble(value));
            }else{
                return new JobParameter(Long.parseLong(value));
            }
        }
        return new JobParameter(value);
    }
}
