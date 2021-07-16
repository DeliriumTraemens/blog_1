package com.nick.blog_1.controllers;

import com.nick.blog_1.models.Category;
import com.nick.blog_1.models.Post;
import com.nick.blog_1.models.Product;
import com.nick.blog_1.repo.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class CategoryController
	{
	@Autowired
	private CategoryRepository categoryRepository;
	
	@GetMapping("/category")
	public String category(Model model)
		{
		model.addAttribute("title", "Категории");
		model.addAttribute("zagolovok", "Текст переданного заголовка");
//		Iterable<Category> categories = categoryRepository.findAll();
//			Long idp= Long.valueOf(19);
			Long idp= 0L;
		Iterable<Category> categories = categoryRepository.findByParentId(idp);
		System.out.println(categories);
		model.addAttribute("category", categories);
		return "category";
		}
	
		//TODO Вызов субкатегории
		
	@GetMapping("/category/add")
	public String categoryAdd(Model model){
	model.addAttribute("title", "Добавить Категорию");
	return "category-add";
		}
	
	@PostMapping("/category/add")
	public String categoryAdd(@RequestParam String catName,  @RequestParam String catDescription){
	Category category =new Category(catName, catDescription);
	categoryRepository.save(category);
	return "redirect:/category";
	}
	//Пример тут?
	@GetMapping("/category/{id}")
	public String blogDetails(@PathVariable(value = "id") long id, Model model) {
		if(!categoryRepository.existsById(id)){
			
			return "redirect:/category";
		}
	
//	Optional<Category> categ =categoryRepository.findById(id);
	Optional<Category> categ =categoryRepository.findById(id);//STOPPED HERE
	ArrayList<Category> rescats = new ArrayList<>();
	categ.ifPresent(rescats :: add);
	model.addAttribute("categ",rescats);
	return "/category-details";
	}
	
	/////
		
		@GetMapping("/subcategory/{id}")
		public String subCategoryList(@PathVariable(value = "id") long id, Model model) {
			if(!categoryRepository.existsById(id)){
				
				return "redirect:/category";
			}

//	Optional<Category> categ =categoryRepository.findById(id);
			Iterable<Category> subCategories =categoryRepository.findByParentId(id);//STOPPED HERE
			ArrayList<Category> rescats = new ArrayList<>();
			subCategories.(rescats :: add);
			model.addAttribute("categ",rescats);
			return "/subcategory";
		}
	
	
	//////
	
	@PostMapping("/category/{id}")
	public String subcategoryAdd(@PathVariable(value = "id") long id,
	                             @RequestParam String catName,
	                             @RequestParam String catDescription,
	                             Model model ){
	Category category = categoryRepository.findById(id).orElseThrow();
	Category subCat = new Category();
	subCat.setParentId(id);
	subCat.setName(catName);
	subCat.setDescription(catDescription);
	System.out.println("Созданная категория "+subCat.toString());
	categoryRepository.save(subCat);
	return "redirect:/category";
	}
}
