package com.nick.blog_1.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String name;
	private String family;
	private String email;
	private String address;
	private String userpic;
	
	
	
//	Articles
//	Comments
//	Orders

//	Constructors
//	Get-Setters
//	Equals Hash
//	ToString

}
