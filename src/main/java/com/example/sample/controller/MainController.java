package com.example.sample.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.sample.DataSnapshotRepository;
import com.example.sample.entity.DataSnapshot;
import com.example.sample.services.DataSnapshotService;
import com.example.sample.services.FileStorageService;

/**
 * Controller for performing the data storing/retrieving/deleting operations
 */
@RestController
@RequestMapping(path = "/datasnapshot")
public class MainController {
	@Autowired
	private DataSnapshotRepository dataSnapshotRepository;

	@Autowired
	private DataSnapshotService dsService;

	@Autowired
	private FileStorageService fileStorageService;

	/**
	 * REQUIRMENT-01 Method for uploading a file using HTTP POST request, plain text
	 * file with comma-separated data. Here both .txt/.csv file can be uploaded
	 */
	@PostMapping("/fileupload")
	public @ResponseBody String uploadFile(@RequestParam("file") MultipartFile file) {

		String result = fileStorageService.save(file);
		return result;

	}

	/**
	 * REQUIRMENT-02 Method for access data persisted via HTTP GET request. Values
	 * of single record to be provided for PRIMARY_KEY(id) supplied via request URL
	 */
	@GetMapping(path = "/get/{id}")
	public @ResponseBody Optional<DataSnapshot> getSingleDataSnapshots(@PathVariable(value = "id") String id) {

		return dsService.getDataSnapshot(id);

	}

	/**
	 * REQUIRMENT-03 Method for removing record from storage via HTTP DELETE request
	 * by single PRIMARY_KEY (id) for reconciliation purpose
	 */
	@DeleteMapping(path = "/del/{id}")
	public @ResponseBody String deleteDataSnapshots(@PathVariable(value = "id") String id) {

		return dsService.deleteDataSnapshot(id);

	}

}
