package com.fms.batch.job;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class JobListner implements JobExecutionListener {

	@Override
	public void beforeJob(JobExecution jobExecution) {
		 log.info("<====== JOB STARTED =======>");

	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		if( jobExecution.getStatus() == BatchStatus.COMPLETED ){
	        log.info("<====== JOB COMPLETED =======>");
	    }
	    else if(jobExecution.getStatus() == BatchStatus.FAILED){
	        log.error("<====== JOB FAILED =======>");
	    }

	}

}
