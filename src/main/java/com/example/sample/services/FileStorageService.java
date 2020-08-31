package com.example.sample.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.sample.DataSnapshotApplication;
import com.example.sample.model.DataSnapshotModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Service class for validating the data from file and sending it to
 * DataSnapshotService class
 *
 */
@Service
public class FileStorageService {

	private static final Logger logger = LoggerFactory.getLogger(DataSnapshotApplication.class);

	@Autowired
	private DataSnapshotService dsService;

	/**
	 * Method for sending the valid data to DataSnapshotService class
	 * 
	 * @param MultiPart file
	 * @return String - status of the operation
	 */
	public String save(MultipartFile file) {
		String message = null;
		boolean isValid = true;
		String line;
		BufferedReader br;
		InputStream is;
		if (file != null) {
			try {
				is = file.getInputStream();
				br = new BufferedReader(new InputStreamReader(is));
				line = br.readLine();
				/*
				 * Checking the first line of the input file to know whether it follows the
				 * required format
				 */
				if (line == null || !line.equals("PRIMARY_KEY,NAME,DESCRIPTION,UPDATED_TIMESTAMP")) {
					logger.info("Incorrect file");
					isValid = false;
					message = "Incorrect file";
					return message;
				}
				if (isValid) {
					logger.info("Validated first line");
					while ((line = br.readLine()) != null) {

						String[] values = line.split(",");

						/*
						 * Checking whether the input have four values present
						 */
						if (values.length == 4) {

							/*
							 * Checking whether the primary key value is correct or not
							 */
							if (!values[0].trim().isEmpty()) {
								logger.info("Primary key " + values[0].trim() + " validated successfully");
								// Time stamp validation
								Boolean isCorrectDate = null;
								Date parsedDate = null;
								try {
									SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
									parsedDate = dateFormat.parse(values[3]);
									isCorrectDate = true;

								} catch (Exception e) {
									isCorrectDate = false;
									e.printStackTrace();
								}
								if (isCorrectDate) {
									logger.info("Timestamp for id " + values[0].trim() + " validated successfully!");
									DataSnapshotModel dsModel = new DataSnapshotModel();
									dsModel.setId(values[0].trim());
									dsModel.setName(values[1].trim());
									dsModel.setDescription(values[2].trim());
									dsModel.setTimeStamp(values[3].trim());
									dsService.add(dsModel);
								} else {
									logger.warn("Timestamp validation for id " + values[0].trim() + " failed!");
								}

							} else {
								logger.error("Primary key is empty");
							}
						} else {
							logger.info("Input record with id " + values[0].trim() + " do not have four data entry present");
						}
					}
					message = "Data saved successfully!";
				}

			} catch (IOException e) {
				logger.error("File open error");
			}
		} else {
			logger.error("Empty file");
			message = "Empty file";
			return message;
		}

		return message;
	}

}
