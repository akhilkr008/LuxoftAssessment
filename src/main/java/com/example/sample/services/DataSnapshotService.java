package com.example.sample.services;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.sample.DataSnapshotRepository;
import com.example.sample.entity.DataSnapshot;
import com.example.sample.model.DataSnapshotModel;

@Service
public class DataSnapshotService {

	@Autowired
	private DataSnapshotRepository dataSnapshotRepository;

	public String add(DataSnapshotModel dsModel) {
		DataSnapshot ds = new DataSnapshot();
		ds.setId(dsModel.getId());
		ds.setName(dsModel.getName());
		ds.setDescription(dsModel.getDescription());
		Date parsedDate = null;
		try {
		    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
		    parsedDate = dateFormat.parse(dsModel.getTimeStamp());
		    
		} catch(Exception e) { //this generic but you can control another types of exception
		    // look the origin of excption 
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(parsedDate);
		ds.setTimestamp(calendar );
		DataSnapshot result = dataSnapshotRepository.save(ds);
		return result.getId();
	}

	public Optional<DataSnapshot> getDataSnapshot(String id) {
		return dataSnapshotRepository.findById(id);
	}

	public String deleteDataSnapshot(String id) {
		String message = null;
		try {
			dataSnapshotRepository.deleteById(id);
			message = "Record deleted successfully";
		} catch (EmptyResultDataAccessException e) {
			message = "Record does not exist";
		}
		return message;
	}

}
