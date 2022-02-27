package com.contacts.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.contacts.models.Contacts;


public interface ContactsRepository extends JpaRepository<Contacts, Long>{
	  
	  List <Contacts>findByfirstNameContaining(String firstName);
}