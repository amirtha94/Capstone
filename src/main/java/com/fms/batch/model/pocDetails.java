package com.fms.batch.model;

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
public class pocDetails {

	@Id
	@Field("pocId")
	private String pocId;
	
	private String pocName;
	
	private String  pocNumber;
	
}
