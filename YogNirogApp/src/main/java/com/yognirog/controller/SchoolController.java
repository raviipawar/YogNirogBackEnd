package com.yognirog.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yognirog.model.School;
import com.yognirog.repository.SchoolRepository;

@RestController
@RequestMapping("/schools")
public class SchoolController {
	
    private static final Logger logger = LoggerFactory.getLogger(SchoolController.class);


	@Autowired
	private MongoTemplate mongoTemplate;

	private SchoolRepository schoolRepository;

	public SchoolController(SchoolRepository schoolRepository) {
		this.schoolRepository = schoolRepository;
	}

	@CrossOrigin(origins = "http://localhost:4200/")
	@PostMapping("/add")
	public School addSchool(@RequestBody School school) {
		 logger.debug("Debug message");
	        logger.info("Info message");
	        logger.warn("Warn message");
	        logger.error("Error message");
		return schoolRepository.insert(school);
	}

	@CrossOrigin(origins = "http://localhost:4200/")
	@GetMapping("/all")
	public List<School> getAllSchools() {
		List<School> list = schoolRepository.findAll();
		return list;
	}

	@CrossOrigin(origins = "http://localhost:4200/")
	@GetMapping("/{id}")
	public ResponseEntity<Optional<School>> getById(@PathVariable ObjectId id) {
		Optional<School> existingSchool = schoolRepository.findById(id);
		if (existingSchool.isPresent()) {
			Optional<School> school = schoolRepository.findById(id);
			return ResponseEntity.ok().body(school);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@CrossOrigin(origins = "http://localhost:4200/")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity deleteById(@PathVariable ObjectId id) {
		Optional<School> existingSchool = schoolRepository.findById(id);
		logger.debug("Debug message");
        logger.info("Info message");
        logger.warn("Warn message");
        logger.error("Error message");
		if (existingSchool.isPresent()) {
			schoolRepository.delete(existingSchool.get());
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@CrossOrigin(origins = "http://localhost:4200/")
	@PutMapping("/update/{id}")
	public ResponseEntity<School> updateUser(@PathVariable String id, @RequestBody School updatedUser) {
		Query query = new Query(Criteria.where("_id").is(id));
		School school = mongoTemplate.findOne(query, School.class);
		if (school == null) {
			return ResponseEntity.notFound().build();
		}
		BeanUtils.copyProperties(updatedUser, school);
		mongoTemplate.save(school);
		return ResponseEntity.ok().body(school);
	}

	@CrossOrigin(origins = "http://localhost:4200/")
	@PatchMapping("/{id}")
	public ResponseEntity<School> patchUser(@PathVariable String id, @RequestBody Map<String, Object> patchFields)
			throws JsonMappingException {
		Query query = new Query(Criteria.where("_id").is(id));
		School school = mongoTemplate.findOne(query, School.class);
		if (school == null) {
			return ResponseEntity.notFound().build();
		}
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.updateValue(school, patchFields);
		mongoTemplate.save(school);
		return ResponseEntity.ok().body(school);
	}
}
