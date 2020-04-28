package com.fms.batch.event;

import org.springframework.batch.item.ItemProcessor;

import com.fms.batch.model.Events;

public class EventProcessor implements ItemProcessor<Events, Events> {

	@Override
	public Events process(Events item) throws Exception {
		return item;
	}

}
