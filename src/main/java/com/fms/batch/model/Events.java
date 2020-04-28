package com.fms.batch.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document //Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Events {

	@Id
	@Field("eventId")
	private String eventId;
	
	private String month;
	
	private String venueAddress;
	
	private String project;
	
	private String category;
	
	private LocalDate eventDate;
	
	private double totalVolunteers;
	
	private double totalVolunteersHours;
	
	private double totalTravelHours;
	
	private double livesImpacted;
	
	private double overallVolunteerHours;
	
	private double activityType;
	
	private String status;
	
	private pocDetails pocDetail;
	
}
