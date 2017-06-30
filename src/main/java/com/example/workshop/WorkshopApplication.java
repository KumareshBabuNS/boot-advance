package com.example.workshop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.Entity;
import javax.servlet.http.HttpServletRequest;

@SpringBootApplication
public class WorkshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorkshopApplication.class, args);
	}


}

@RestController
@RequestMapping("/person")
class PersonController{

	@Autowired
	PersonRepository pr;

	@GetMapping("/{id}")
	public Person getPerson(@PathVariable Long id) {

		return pr.findOne(id);
	}

	@PostMapping
	public ResponseEntity<Person> create(@RequestBody Person toCreate, HttpServletRequest request){
		Person created = pr.save(toCreate);
		return ResponseEntity.created(UriComponentsBuilder.fromHttpRequest(new ServletServerHttpRequest(request)).path("/{id}").buildAndExpand(created.getId()).toUri()).build();
	}
}

interface PersonRepository extends CrudRepository<Person,Long>{

}

@Entity
class Person extends AbstractPersistable<Long>{

	String firstName;
	String lastName;

	public Person() {
	}

	public Person(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}

