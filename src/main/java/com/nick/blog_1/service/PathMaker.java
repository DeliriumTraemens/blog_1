package com.nick.blog_1.service;

import com.nick.blog_1.models.Category;
import com.nick.blog_1.repo.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class PathMaker {
	@Autowired
	CategoryRepository categoryRepository;
//------- SERVICE ------------\\//
	
	//<Category> Crumbs maker
	public List<Category> crumbsMaker(long id){
		LinkedList<Category> crumbList = new LinkedList();
		Category currentCat = categoryRepository.findById(id).get();
		
		crumbList.addFirst(currentCat);
		
		while(currentCat.getParentId()!=0L){
			currentCat=categoryRepository.findById(currentCat.getParentId()).get();
			crumbList.addFirst(currentCat);
		}
		
		return crumbList;
	}
	//String imagePath maker
	public String imagePathMaker(List<Category> crumbs,String name) {
		String imagePath="/";
		for (Category category : crumbs){
			imagePath += category.getName()+"/";
		}
		imagePath+=name;
		return imagePath;
	}
	
	
	//------- SERVICE ------------//\\
}
