package com.example.sample.business;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.web.multipart.MultipartFile;

public class DataValidator {

	public boolean firstLineValidator(MultipartFile file) {
		String line = null;
		BufferedReader br;
		InputStream is;
		try {
			is = file.getInputStream();
			br = new BufferedReader(new InputStreamReader(is));
			line = br.readLine();
			

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(line == "PRIMARY_KEY,NAME,DESCRIPTION,UPDATED_TIMESTAMP")
			return true;
		else
			return false;
	}

}
