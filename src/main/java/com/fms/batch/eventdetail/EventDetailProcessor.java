package com.fms.batch.eventdetail;

import org.springframework.batch.item.ItemProcessor;

import com.fms.batch.model.EventDetail;

public class EventDetailProcessor implements ItemProcessor<EventDetail,EventDetail> {

	@Override
	public EventDetail process(EventDetail item) throws Exception {
		return item;
	}

}
