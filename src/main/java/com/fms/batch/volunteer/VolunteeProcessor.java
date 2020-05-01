package com.fms.batch.volunteer;

import org.springframework.batch.item.ItemProcessor;

import com.fms.batch.model.VolunteerDetails;

public class VolunteeProcessor implements ItemProcessor<VolunteerDetails, VolunteerDetails> {

	@Override
	public VolunteerDetails process(VolunteerDetails item) throws Exception {
		return item;
	}

}

