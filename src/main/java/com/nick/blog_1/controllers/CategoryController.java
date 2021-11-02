package com.nick.blog_1.controllers;

import com.nick.blog_1.models.Category;
import com.nick.blog_1.models.Post;
import com.nick.blog_1.models.Product;
import com.nick.blog_1.repo.CategoryRepository;
import com.nick.blog_1.service.PathMaker;
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
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Controller
public class CategoryController {
	@Value("${upload.path}")
	private String uploadPath;
	
	@Value("${upload.path2}")
	private String uploadPath2;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private PathMaker pm;
	
	
	
	@GetMapping("/category")
	public String category(Model model) {
		model.addAttribute("title", "Категории");
		model.addAttribute("zagolovok", "Текст переданного заголовка");
//
		model.addAttribute("categoryList",categoryRepository.findByParentId(0L));
		return "category";
	}
	
	//TODO Вызов субкатегории
	//Add NEW Category
	@GetMapping("/category/add")
	public String categoryAdd(Model model) {
		model.addAttribute("title", "Добавить Категорию");
		return "category-add";
	}
	
	@PostMapping("/category/add")
	public String categoryAdd(@RequestParam String catName,
	                          @RequestParam String catParent,
	                          @RequestParam String catDescription,
	                          @RequestParam ("file") MultipartFile file) throws IOException {
		//catName catParent catDescription file
		Category category = new Category();
		if (catName!=null){
			category.setName(catName);
		}
		if (catParent!=null){
			category.setParentId(Long.valueOf(catParent));
		}
		if (catDescription!=null){
			category.setDescription(catDescription);
		}
		// ----Path Maker
		
		String directoryPathMkDir = uploadPath+"/"+catName;
		String imagePathForPicture = catName;
		
		// ----Path Maker
		
		
		if (file!=null && !file.getOriginalFilename().isEmpty()){
			File pathMaker = new File(directoryPathMkDir);
			if (!pathMaker.exists()) {
				pathMaker.mkdir();
			}
			
			
//			category.setImagePath(file.getOriginalFilename());
			category.setImagePath(imagePathForPicture+"/"+ file.getOriginalFilename());
			file.transferTo(new File(directoryPathMkDir + "/"+file.getOriginalFilename()));
		}
		
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
		
		Category categoryForEdit = categoryRepository.findById(Long.valueOf(id)).get();
//		Path maker
		String imagePathForPicture = pm.imagePathMaker(pm.crumbsMaker(Long.valueOf(id)),catName);
		String directoryPathMkDir = uploadPath+imagePathForPicture;
//		Path maker
		if (file != null && !file.getOriginalFilename().isEmpty()) {
			File directoryMaker = new File(directoryPathMkDir);
			if (! directoryMaker.exists()) {
				directoryMaker.mkdir();
			}
			
			categoryForEdit.setImagePath(imagePathForPicture+"/"+ file.getOriginalFilename());
			file.transferTo(new File(uploadPath+"/"+imagePathForPicture+"/"+file.getOriginalFilename()));
		}
		
			if (catName != null) {
				categoryForEdit.setName(catName);
			}
			if (categoryForEdit.getParentId() != Integer.valueOf(catParentId)) {
				categoryForEdit.setParentId(Integer.valueOf(catParentId));
			}
			if (categoryForEdit.getDescription() != catDescription) {
				categoryForEdit.setDescription(catDescription);
			}
			
		
		categoryRepository.save(categoryForEdit);
		
		return "redirect:/category";
	}

//	Category Edit
	
	
	
	//Delete Category
	@GetMapping("/categoryRemove/{id}")
	public String categoryRemover(@PathVariable(value = "id") long id){
		categoryRepository.deleteById(id);
		return "redirect:/category";
	}
	
	//Добавление подкатегории
	@GetMapping("/category/{id}")
	public String blogDetails(@PathVariable(value = "id") long id, Model model) {
		if (! categoryRepository.existsById(id)) {
			return "redirect:/category";
		}
		
		model.addAttribute("parentCategory", categoryRepository.findById(id).get());
		return "/category-details";
	}
	
	// Добавление СУБКАТЕГОРИИ ПОСТ
	@PostMapping("/category/{id}")
	public String subcategoryAdd(
			@PathVariable(value = "id") long id,
			@RequestParam String catName,
			@RequestParam String catDescription,
			@RequestParam("file") MultipartFile file,
			Model model) throws IOException {
		
		Category subCat = new Category();
			subCat.setParentId(id);
			subCat.setName(catName);
			subCat.setDescription(catDescription);
			
		// ----Path Maker
		String imagePathForPicture = pm.imagePathMaker(pm.crumbsMaker(id),catName);
		String directoryPathMkDir = uploadPath+imagePathForPicture;
		{
			System.out.println("\n-----------------------------------\n");
			System.out.println("imagePathForPicture " + imagePathForPicture);
			System.out.println("directoryPathMkDir " + directoryPathMkDir);
			System.out.println("\n-----------------------------------\n");
		}
		// ----Path Maker
		if (file!=null && !file.getOriginalFilename().isEmpty()){
			
			pm.directoryMaker(directoryPathMkDir);
			
			subCat.setImagePath(imagePathForPicture+"/"+ file.getOriginalFilename());
			{
				System.out.println("\n---------------------------\n");
				System.out.println(subCat.getImagePath());
				System.out.println("\n---------------------------\n");
			}
			
			file.transferTo(new File(uploadPath+"/"+imagePathForPicture+"/"+file.getOriginalFilename()));
			
		}
		
		
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
