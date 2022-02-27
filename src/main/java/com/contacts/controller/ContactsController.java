package com.contacts.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.contacts.Repository.ContactsRepository;
import com.contacts.models.Contacts;

@RestController
@RequestMapping("/contacts")
public class ContactsController {
	
	@Autowired
	ContactsRepository repo;

	@PostMapping("/saveContact")
	public ResponseEntity<Contacts> SaveContacts(@RequestBody Contacts request) {

		try {
			Contacts contacts = repo.save(request);
			return new ResponseEntity<Contacts>(contacts, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/getAllContacts")
	public List<Contacts> getHeloText() {
		return repo.findAll();
	}
	
	
	@PostMapping("/updateContacts")
	public ResponseEntity<Contacts> updateContact(@RequestBody Contacts request) {
	
		try {
			Optional<Contacts>	data = repo.findById(request.getId());

			if (data.isPresent()) {
				Contacts contact = new Contacts();
				contact.setId(request.getId());
				contact.setFirstName(request.getFirstName());
				contact.setLastName(request.getLastName());
				contact.setAddressLine1(request.getAddressLine1());
				contact.setAddressLine2(request.getAddressLine2());
				contact.setCountry(request.getCountry());
				contact.setState(request.getState());
				contact.setZipCode(request.getZipCode());
				contact.setEmail(request.getEmail());
				contact.setPhone(request.getPhone());
				return new ResponseEntity<Contacts>(repo.save(contact), HttpStatus.OK);
			}

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	
	@GetMapping("/getContactBy/{id}")
	public ResponseEntity<Contacts> getContactById(@PathVariable("id") long id) {
		Optional<Contacts> contactInfo = repo.findById(id);

		if (contactInfo.isPresent()) {
			return new ResponseEntity<>(contactInfo.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/searchBy/{name}")
	public ResponseEntity<List<Contacts>> getContactById(@PathVariable("name") String firstName) {
		List<Contacts> contact = new ArrayList<Contacts>();
		repo.findByfirstNameContaining(firstName).forEach(contact::add);
		return new ResponseEntity<>(contact, HttpStatus.OK);

	}
	
	   @PostMapping("/delete/{id}")
	    public ResponseEntity<Contacts> deleteContact(@PathVariable long id) {
	       repo.deleteById(id);
	       return new ResponseEntity<>(null, HttpStatus.OK);
	    }
}
