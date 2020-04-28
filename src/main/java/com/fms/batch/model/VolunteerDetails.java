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
public class VolunteerDetails {

	@Id
	@Field("volunteerId")
	public String id;
	
	private String eventId;
	
	private String eventName;
	
	private String beneficiaryName;
	
	private String baseLocation;
	
	private LocalDate eventDate;
	
	private int employeeId;
	
	private String volunteerStatus;
}
