package com.fms.batch.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document //Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDetail {

	@Id
	private String id;
	
	private String eventId;
	
	private String baseLocation;
	
	private String beneficiaryName;
	
	private String councilName;
	
	private String eventName;
	
	private String eventDescription;
	
	private LocalDate eventDate;
	
	private int employeeId;
	
	private String employeeName;
	
	private double volunteerHours;
	
	private double travelHours;
	
	private double livesImpacted;
	
	private String businessUnit;
	
	private String status;
	
	private String iiep;
}
