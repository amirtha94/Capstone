package com.fms.batch.job;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fms.batch.event.EventProcessor;
import com.fms.batch.event.EventReader;
import com.fms.batch.event.EventWriter;
import com.fms.batch.eventdetail.EventDetailProcessor;
import com.fms.batch.eventdetail.EventDetailReader;
import com.fms.batch.eventdetail.EventDetailWriter;
import com.fms.batch.model.EventDetail;
import com.fms.batch.model.Events;
import com.fms.batch.model.VolunteerDetails;
import com.fms.batch.volunteer.VolunteeProcessor;
import com.fms.batch.volunteer.VolunteeReader;
import com.fms.batch.volunteer.VolunteeWriter;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableBatchProcessing
@Slf4j
public class FmsBatchConfigJob {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Bean
	public Job fmsDataJob(Step firstStep,Step secondStep,Step thirdStep, JobListner jobListner) throws FileNotFoundException, IOException {
		return this.jobBuilderFactory.get("fmsDataJob").incrementer(new RunIdIncrementer()).listener(jobListner)
				.flow(firstStep).next(secondStep).next(thirdStep).end().build();

	}

	@Bean
	public Step firstStep() throws FileNotFoundException, IOException {
		return this.stepBuilderFactory.get("firstStep").<EventDetail, EventDetail>chunk(20).reader(eventDetailReader())
				.processor(eventDetailProcessor()).writer(eventDetailWriter()).build();
	}
	
	@Bean
	public Step secondStep() throws FileNotFoundException, IOException {
		return this.stepBuilderFactory.get("secondStep").<Events, Events>chunk(20).reader(eventReader())
				.processor(eventProcessor()).writer(eventWriter()).build();
	}

	@Bean
	public Step thirdStep() throws FileNotFoundException, IOException {
		return this.stepBuilderFactory.get("thirdStep").<VolunteerDetails, VolunteerDetails>chunk(20).reader(volunteerReader())
				.processor(volunteerProcessor()).writer(volunteerWriter()).build();
	}

	
	@Bean
	public ItemWriter<EventDetail> eventDetailWriter() {
		return new EventDetailWriter();
	}

	@Bean
	public ItemProcessor<EventDetail, EventDetail> eventDetailProcessor() {
		return new EventDetailProcessor();
	}


	@Bean
	public ItemReader<EventDetail> eventDetailReader()  {
		return new EventDetailReader();
		/*
		 * PoiItemReader<EventDetail> reader = new PoiItemReader<>();
		 * reader.setLinesToSkip(1); reader.setResource(new
		 * ClassPathResource("data/students.xlsx")); // Path filePath =
		 * Paths.get("D:\\Capstone\\Inputs\\OutReachEventInfo.xlsx"); //
		 * reader.setResource(new //
		 * FileSystemResource("D:\\Capstone\\Inputs\\OutReachEventInfo1.xls")); try { //
		 * reader.setResource(new //
		 * UrlResource("file://D://Capstone//Inputs//OutReachEventInfo.xlsx"));
		 * 
		 * PushbackInputStream input = new PushbackInputStream(new
		 * FileInputStream("D:\\Capstone\\Inputs\\OutReachEventInfo.xlsx"));
		 * InputStreamResource resource = new InputStreamResource(input);
		 * reader.setResource(resource);
		 * 
		 * } catch (Exception e) { e.printStackTrace();
		 * System.out.println(e.getMessage()); } reader.setRowMapper(excelRowMapper());
		 * return reader;
		 */
	}
	
	@Bean
	public ItemWriter<Events> eventWriter() {
		return new EventWriter();
	}

	@Bean
	public ItemProcessor<Events, Events> eventProcessor() {
		return new EventProcessor();
	}


	@Bean
	public ItemReader<Events> eventReader()  {
		return new EventReader();
	}
	
	@Bean
	public ItemWriter<VolunteerDetails> volunteerWriter() {
		return new VolunteeWriter();
	}

	@Bean
	public ItemProcessor<VolunteerDetails, VolunteerDetails> volunteerProcessor() {
		return new VolunteeProcessor();
	}


	@Bean
	public ItemReader<VolunteerDetails> volunteerReader()  {
		return new VolunteeReader();
	}
	
	
}
