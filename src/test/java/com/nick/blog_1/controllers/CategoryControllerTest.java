package com.nick.blog_1.controllers;

import com.nick.blog_1.models.Category;
import com.nick.blog_1.repo.CategoryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
@SpringBootTest
@RunWith(SpringRunner.class)
public class CategoryControllerTest {
	@Autowired
	CategoryRepository categoryRepository;
	
	@Test
	public void setUpPath() {
		LinkedList<Category> crumbsList = new LinkedList();
		Category currentCat = categoryRepository.findById(26L).get();
		
		crumbsList.addFirst(currentCat);
		
		while(currentCat.getParentId()!=0L){
			currentCat=categoryRepository.findById(currentCat.getParentId()).get();
			crumbsList.addFirst(currentCat);
		}
		System.out.println(crumbsList);
		System.out.println("Parent id "+currentCat.getParentId() +" name is "+currentCat.getName());
		
		String path ="/";
		for (Category cat:crumbsList ){
			path+=cat.getName()+"/";
			System.out.print("/"+cat.getName());
		}
		System.out.print("\nResult path is  "+path);
		
	}
	
	@Test
	public void imagePathMaker() {
		String a = "aaa";
		System.out.println(a);
		a+="/";
		a += "bb";
		System.out.println(a);
		
	}
	
}