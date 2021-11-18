package com.nick.blog_1.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Article {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long Id;
	
	private Long topicId;
	
	private String tag;
	
	private String text;
	
	//Comments
	
//	Constructors
//	Get-Setters
//	Equals Hash
//	ToString
}
