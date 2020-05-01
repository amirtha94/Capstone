package com.fms.batch.volunteer;

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

import com.fms.batch.model.VolunteerDetails;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VolunteeReader implements ItemReader<VolunteerDetails>, StepExecutionListener {

	@Value("${volunteer.unregistered.file.name}")
	private String volUnRegister;

	@Value("${volunteer.notattended.file.name}")
	private String volNotAttend;

	List<VolunteerDetails> volunteerData = new ArrayList<>();

	@Override
	public VolunteerDetails read()
			throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		if (!volunteerData.isEmpty()) {
			return volunteerData.remove(0);
		}
		return null;
	}

	@Override
	public void beforeStep(StepExecution stepExecution) {
		File fileUnRegister = new File(volUnRegister);
		File fileNotattended = new File(volNotAttend);
		if (fileUnRegister.exists()) {
			try (InputStream in = new FileInputStream(fileUnRegister)) {
				Workbook workbook = WorkbookFactory.create(in);
				Sheet sheet = workbook.getSheetAt(0);
				for (Row row : sheet) {
					if (row.getRowNum() == 0) {
						continue;
					}
					if (row.getCell(0) != null) {
						assignValue(row, volunteerData, "unregistered");
					}

				}
			} catch (FileNotFoundException e) {
				log.error("File Not Found Exception {}", e);
			} catch (IOException e) {
				log.error("Exception while reading {}", e);
			}
		} else {
			log.warn("File does not exists at path :: {}", fileUnRegister);
		}
		if (fileNotattended.exists()) {
			try (InputStream in = new FileInputStream(fileNotattended)) {
				Workbook workbook = WorkbookFactory.create(in);
				Sheet sheet = workbook.getSheetAt(0);
				for (Row row : sheet) {
					if (row.getRowNum() == 0) {
						continue;
					}
					if (row.getCell(0) != null) {
						
						assignValue(row, volunteerData, "notattended");
					}

				}
			} catch (FileNotFoundException e) {
				log.error("File Not Found Exception {}", e);
			} catch (IOException e) {
				log.error("Exception while reading {}", e);
			}
		} else {
			log.warn("File does not exists at path :: {}", fileNotattended);
		}
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		log.info("Step Status::{} , Step Name:: {} ", stepExecution.getExitStatus(), stepExecution.getStepName());
		return stepExecution.getExitStatus();
	}

	public void assignValue(Row row, List<VolunteerDetails> volunterrData, String status) {
		VolunteerDetails data = new VolunteerDetails();
		data.setEventId(row.getCell(0).toString());
		data.setEventName(row.getCell(1).toString());
		data.setBeneficiaryName(row.getCell(2).toString());
		data.setBaseLocation(row.getCell(3).toString());
		data.setEventDate(LocalDate.parse(row.getCell(4).toString(), DateTimeFormatter.ofPattern("dd-MM-yy")));
		data.setEmployeeId((int) row.getCell(5).getNumericCellValue());
		data.setVolunteerStatus(status);
		this.volunteerData.add(data);
	}

	public String convertNumericType(Cell cell) {
		long value = (long) cell.getNumericCellValue();
		return String.valueOf(value);

	}
}
