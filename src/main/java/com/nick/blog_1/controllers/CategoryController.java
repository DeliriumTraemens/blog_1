package com.nick.blog_1.controllers;

import com.nick.blog_1.models.Category;
import com.nick.blog_1.models.Post;
import com.nick.blog_1.models.Product;
import com.nick.blog_1.repo.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

@Controller
public class CategoryController {
	@Value("${upload.path}")
	private String uploadPath;
	
	@Value("${upload.path2}")
	private String uploadPath2;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@GetMapping("/category")
	public String category(Model model) {
		model.addAttribute("title", "Категории");
		model.addAttribute("zagolovok", "Текст переданного заголовка");
//
		model.addAttribute("categoryList",categoryRepository.findByParentId(0L));
		return "category";
	}
	
	//TODO Вызов субкатегории
	
	@GetMapping("/category/add")
	public String categoryAdd(Model model) {
		model.addAttribute("title", "Добавить Категорию");
		return "category-add";
	}
	
	@PostMapping("/category/add")
	public String categoryAdd(@RequestParam String catName, @RequestParam String catDescription) {
		Category category = new Category(catName, catDescription);
		categoryRepository.save(category);
		return "redirect:/category";
	}
	
	//	Category Edit
	@GetMapping("/categoryEdit/{id}")
	public String categoryEdit(@PathVariable(value = "id") long id, Model model) {
		if (! categoryRepository.existsById(id)) {
			return "redirect:/category";
		}
		model.addAttribute("catToEdit", categoryRepository.findById(id).get());
		return "categoryEdit";
		
	}
	

	
	@PostMapping("/catedit")
	public String catEditTest(@RequestParam String catName,
	                          @RequestParam String id,
	                          @RequestParam String catParentId,
	                          @RequestParam String catDescription,
	                          @RequestParam("file") MultipartFile file) throws IOException {
		
		if (file != null && !file.getOriginalFilename().isEmpty()) {
			File directoryMaker = new File(uploadPath);
			if (! directoryMaker.exists()) {
				directoryMaker.mkdir();
			}
		}
		
		Category categoryForEdit = categoryRepository.findById(Long.valueOf(id)).get();
		
		
			if (catName != null) {
				categoryForEdit.setName(catName);
			}
			if (categoryForEdit.getParentId() != Integer.valueOf(catParentId)) {
				categoryForEdit.setParentId(Integer.valueOf(catParentId));
			}
			if (categoryForEdit.getDescription() != catDescription) {
				categoryForEdit.setDescription(catDescription);
			}
			
			categoryForEdit.setImagePath(file.getOriginalFilename());
		
		file.transferTo(new File(uploadPath + "/" + file.getOriginalFilename()));
		
		categoryRepository.save(categoryForEdit);
		
		return "redirect:/category";
	}

//	Category Edit
	
	
	//Добавление подкатегории
	// TODO: 28.10.2021 Добавить картинки и упорядочить
	@GetMapping("/category/{id}")
	public String blogDetails(@PathVariable(value = "id") long id, Model model) {
		if (! categoryRepository.existsById(id)) {
			return "redirect:/category";
		}
		
		model.addAttribute("parentCategory", categoryRepository.findById(id).get());
		return "/category-details";
	}
	
	@PostMapping("/category/{id}")
	public String subcategoryAdd(
			@PathVariable(value = "id") long id,
			@RequestParam String catName,
			@RequestParam String catDescription,
			@RequestParam("file") MultipartFile file,
			Model model) throws IOException {
		
		if (file!=null && !file.getOriginalFilename().isEmpty()){
			File pathMaker = new File(uploadPath);
				if (!pathMaker.exists()) {
					pathMaker.mkdir();
				}
		}
		
		Category subCat = new Category();
			subCat.setParentId(id);
			subCat.setName(catName);
			subCat.setDescription(catDescription);
			subCat.setImagePath(file.getOriginalFilename());
		
		file.transferTo(new File(uploadPath+"/"+file.getOriginalFilename()));
		categoryRepository.save(subCat);
		return "redirect:/category";
	}
	
	
	/////
	
	@GetMapping("/subcategory/{id}")
	public String subCategoryList(@PathVariable(value = "id") long id, Model model) {
		if (! categoryRepository.existsById(id)) {
			return "redirect:/category";
		}
		model.addAttribute("subcategories", categoryRepository.findByParentId(id));
		model.addAttribute("parentName",categoryRepository.findById(id).get().getName());
		model.addAttribute("title","Подкатегории");
		return "/subcategory";
	}
	
	
	//////
	
	
}
