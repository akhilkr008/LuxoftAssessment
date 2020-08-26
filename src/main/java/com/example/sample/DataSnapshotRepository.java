package com.example.sample;

import org.springframework.data.repository.CrudRepository;

import com.example.sample.entity.DataSnapshot;

/**
 * Interface that provides basic CRUD operations
 *  by extending CrudRepository interface
 */
public interface DataSnapshotRepository extends CrudRepository<DataSnapshot, String>  {

}
