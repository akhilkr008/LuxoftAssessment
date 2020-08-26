package com.example.sample.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.sample.model.DataSnapshotModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Service class for validating the data from file and sending it to
 * DataSnapshotService class
 *
 */
@Service
public class FileStorageService {

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
					isValid = false;
					message = "Incorrect file";
					return message;
				}
				if (isValid) {
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
								DataSnapshotModel dsModel = new DataSnapshotModel();
								dsModel.setId(values[0].trim());
								dsModel.setName(values[1].trim());
								dsModel.setDescription(values[2].trim());
								dsModel.setTimeStamp(values[3].trim());
								dsService.add(dsModel);
							}
						}
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			message = "File does not exist";
			return message;
		}
		message = "Data saved successfully!";
		return message;
	}

}
