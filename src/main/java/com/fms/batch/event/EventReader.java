package com.fms.batch.event;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
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

import com.fms.batch.model.Events;
import com.fms.batch.model.pocDetails;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EventReader implements ItemReader<Events>, StepExecutionListener {

	@Value("${event.file.name}")
	private String eventFileName;

	List<Events> events = new ArrayList<>();

	@Override
	public Events read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		if (!events.isEmpty()) {
			return events.remove(0);
		}
		return null;
	}

	@Override
	public void beforeStep(StepExecution stepExecution) {
		File file = new File(eventFileName);
		if (file.exists()) {
			try (InputStream in = new FileInputStream(file)) {
				Workbook workbook = WorkbookFactory.create(in);
				Sheet sheet = workbook.getSheetAt(0);
				for (Row row : sheet) {
					if (row.getRowNum() == 0) {
						continue;
					}
					assignValue(row, events);

				}
			} catch (FileNotFoundException e) {
				log.error("File Not Found Exception {}", e);
			} catch (IOException e) {
				log.error("Exception while reading {}", e);
			}
		} else {
			log.warn("File does not exists at path :: {}", eventFileName);
		}
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		log.info("Step Status::{} , Step Name:: {} ", stepExecution.getExitStatus(), stepExecution.getStepName());
		return stepExecution.getExitStatus();
	}

	public void assignValue(Row row, List<Events> eventList) {
		Events events = new Events();
		pocDetails pocDetails = new pocDetails();
		events.setEventId(row.getCell(0).toString());
		events.setMonth(row.getCell(1).toString());
		events.setVenueAddress(row.getCell(4).toString());
		events.setProject(row.getCell(6).toString());
		events.setCategory(row.getCell(7).toString());
		events.setEventDate(LocalDate.parse(row.getCell(10).toString(), DateTimeFormatter.ofPattern("dd-MM-yy")));
		events.setTotalVolunteers(row.getCell(11).getNumericCellValue());
		events.setTotalVolunteersHours(row.getCell(12).getNumericCellValue());
		events.setTotalTravelHours(row.getCell(13).getNumericCellValue());
		events.setOverallVolunteerHours(row.getCell(14).getNumericCellValue());
		events.setLivesImpacted(row.getCell(15).getNumericCellValue());
		events.setActivityType(row.getCell(16).getNumericCellValue());
		events.setStatus(row.getCell(17).toString());
		pocDetails.setPocId(convertNumericType(row.getCell(18)));
		pocDetails.setPocName(row.getCell(19).toString());
		pocDetails.setPocNumber(convertNumericType(row.getCell(20)));
		events.setPocDetail(pocDetails);
		this.events.add(events);
	}

	public String convertNumericType(Cell cell) {
		long value = (long) cell.getNumericCellValue();
		return String.valueOf(value);

	}

}
