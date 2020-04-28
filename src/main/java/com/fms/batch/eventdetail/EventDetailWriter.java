package com.fms.batch.eventdetail;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.fms.batch.model.EventDetail;
import com.fms.batch.repository.EventDetailsRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EventDetailWriter implements ItemWriter<EventDetail> {

	@Autowired
	private EventDetailsRepository repo;

	@Autowired
	MongoTemplate template;

	@Override
	public void write(List<? extends EventDetail> items) throws Exception {
		log.info("Inside Item Writer. Total Size is {}", items.size());
		try {
			repo.saveAll(items);
			log.info("<=====Data Saved Successfully!=====>");
			
			/*
			 * for (EventDetail i : items) { System.out.println(i.getEmployeeId()); Query q
			 * = Query.query(Criteria.where("employeeId").is(i.getEmployeeId()));
			 * template.remove(q, EventDetail.class); } repo.deleteAll();
			 */
			
		} catch (Exception e) {
			log.error("Exception Occuredin writer -  {}", e);
		}
	}

}
