package com.fms.batch.eventdetail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Value;

import com.fms.batch.model.EventDetail;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EventDetailReader implements ItemReader<EventDetail>, StepExecutionListener {

	@Value("${eventDetails.file.name}")
	private String eventFileName;

	List<EventDetail> eventList = new ArrayList<>();

	@Override
	public EventDetail read()
			throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		if (!eventList.isEmpty()) {
			return eventList.remove(0);
		}
		return null;

		/*
		 * if (count < eventList.size()) { return eventList.get(count++); } else {
		 * return null; }
		 */
	}

	@Override
	public void beforeStep(StepExecution stepExecution) {
		File file = new File(eventFileName);
		if (file.exists()) {
			try (

					InputStream in = new FileInputStream(file)) {
				Workbook workbook = WorkbookFactory.create(in);
				System.out.println("Retrieving Sheets using Iterator");
				for (Sheet sheet : workbook) {
					System.out.println("=> " + sheet.getSheetName());
				}
				Sheet sheet = workbook.getSheetAt(0);
				for (Row row : sheet) {
					if (row.getRowNum() == 0) {
						continue;
					}
					assignValue(row, eventList);

				}
			} catch (FileNotFoundException e) {
				log.error("File Not Found Exception {}", e);
			} catch (IOException e) {
				log.error("Exception while reading {}", e);
			}

		}
		else {
			log.warn("File does not exists at path:: {}",eventFileName);
		}

	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		log.info("Step Status::{} , Step Name:: {} ", stepExecution.getExitStatus(), stepExecution.getStepName());
		return stepExecution.getExitStatus();
	}

	public void assignValue(Row row, List<EventDetail> eventList) {
		EventDetail events = new EventDetail();
		events.setEventId(row.getCell(0).toString());
		events.setBaseLocation(row.getCell(1).toString());
		events.setBeneficiaryName(row.getCell(2).toString());
		events.setCouncilName(row.getCell(3).toString());
		events.setEventName(row.getCell(4).toString());
		events.setEventDescription(row.getCell(5).toString());
		events.setEventDate(LocalDate.parse(row.getCell(6).toString(), DateTimeFormatter.ofPattern("dd-MM-yy")));
		events.setEmployeeId((int) row.getCell(7).getNumericCellValue());
		events.setEmployeeName(row.getCell(8).toString());
		events.setVolunteerHours(row.getCell(9).getNumericCellValue());
		events.setTravelHours(row.getCell(10).getNumericCellValue());
		events.setLivesImpacted(row.getCell(11).getNumericCellValue());
		events.setBusinessUnit(row.getCell(12).toString());
		events.setStatus(row.getCell(13).toString());
		events.setIiep(row.getCell(14).toString());
		this.eventList.add(events);
	}
}
