package com.fms.batch.event;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.fms.batch.model.Events;
import com.fms.batch.repository.EventRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EventWriter implements ItemWriter<Events> {

	
	@Autowired
	EventRepository eventRepo;
	
	@Override
	public void write(List<? extends Events> items) throws Exception {
		log.info("Total records to save {}",items.size());
		eventRepo.saveAll(items);
		System.out.println(eventRepo.findById(items.get(0).getEventId()));
	}

}
