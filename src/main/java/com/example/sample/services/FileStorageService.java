package com.example.sample.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.sample.business.DataValidator;
import com.example.sample.model.DataSnapshotModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Service
public class FileStorageService {

	@Autowired
	private DataSnapshotService dsService;

	DataValidator dataValidator;

	public void fileValidator(MultipartFile file) {
		// validate
		// validation methods in business package
		if (dataValidator.firstLineValidator(file))
			System.out.println("first line validated");
		else
			System.out.println("failed");
	}

	public String save(MultipartFile file) {
		String message=null;
		boolean isValid = true;
		String line;
		BufferedReader br;
		InputStream is;
		if (file != null) {
			try {
				is = file.getInputStream();

				br = new BufferedReader(new InputStreamReader(is));
				line = br.readLine();
				if (line == null || !line.equals("PRIMARY_KEY,NAME,DESCRIPTION,UPDATED_TIMESTAMP")) {
					isValid = false;
					message =  "Incorrect file";
					return message;
				}
				if (isValid) {
					while ((line = br.readLine()) != null) {
						
						String[] values = line.split(",");
						
						// Checking whether the input have four values present
						if (values.length == 4) {
							// Checking whether the primary key value is correct or not
							if (!values[0].trim().isEmpty()) {
																
								System.out.println("File can be saved");
								DataSnapshotModel dsModel = new DataSnapshotModel();
								dsModel.setId(values[0]);
								dsModel.setName(values[1]);
								dsModel.setDescription(values[2]);
								dsModel.setTimeStamp(values[3]);
								System.out.println(dsModel);
								String savedID = dsService.add(dsModel);
								System.out.println("Data stored with id " + savedID);
							} else {
								System.out.println("Primary key cannot be empty");
							}
						} else {
							System.out.println("Invalid input");
						}
					}
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			message =  "File does not exist";
			return message;
		}
		message = "Data saved successfully!";
		return message;
	}

}
