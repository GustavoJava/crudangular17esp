package com.todotic.contactlistapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.todotic.contactlistapi.entity.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {

}
