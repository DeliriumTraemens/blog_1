package com.nick.blog_1.controllers;

import com.nick.blog_1.models.Post;
import com.nick.blog_1.models.Product;
import com.nick.blog_1.repo.CategoryRepository;
import com.nick.blog_1.repo.ProductRepository;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

@Controller
public class ProductController {
	@Value("${upload.path}")
	private String uploadPath;
	
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private CategoryRepository catRepo;
	
	
	@GetMapping("/product")
	public String product(Model model) {
		model.addAttribute("title", "Список Товаров");
//			Iterable<Product> products = productRepository.findAll();
//			model.addAttribute("products",products);
		model.addAttribute("products", productRepository.findAll());
		model.addAttribute("productes", productRepository.findAll());
		return "product";
	}
	
	//Edit product
	
	@GetMapping("/productEdit/{id}")
	public String productEdit(@PathVariable(value = "id") long id, Model model) {
		model.addAttribute("curProd", productRepository.findById(id).get());
		return "product-edit";
	}
	
	@PostMapping("/productEdit")
	public String productEditor(
			@RequestParam (required=false) String id ,
			@RequestParam (required=false) String categoryId,
			@RequestParam (required=false) String name,
			@RequestParam (required=false) String description,
			@RequestParam (required=false) String price,
			@RequestParam("file")MultipartFile file
	                           ) throws IOException {
			/*id categoryId name description price file */
		Product productToEdit = productRepository.findById(Long.valueOf(id)).get();
		
		if (file != null && !file.getOriginalFilename().isEmpty()) {
			File pathMaker = new File(uploadPath);
			if (!pathMaker.exists()) {
				pathMaker.mkdir();
			}
			file.transferTo(new File(uploadPath + "/" + file.getOriginalFilename()));
			productToEdit.setImagePath(file.getOriginalFilename());
		}
		
			if(categoryId !=null){
				productToEdit.setCategoryId(Long.valueOf(categoryId));
			}
			if (name != null) {
				productToEdit.setName(name);
			}
			if (description!=null) {
				productToEdit.setDescription(description);
			}
			
			if (price!=null) {
				productToEdit.setPrice(Float.valueOf(price));
			}
		
			
		productRepository.save(productToEdit);
		
		return "redirect:/product";
	}
	
	//\\Edit product
	
	
	@PostMapping("/product/add")
	public String productAdd(@RequestParam String prodName, @RequestParam String prodDescription, @RequestParam String prodPrice) {
		Product product = new Product(prodName, prodDescription, Float.valueOf(prodPrice));
		productRepository.save(product);
		return "redirect:/product";
	}
	
	//Delete Product
	@GetMapping("/productRemove/{id}")
	public String productRemover(@PathVariable(value = "id") long id){
		productRepository.deleteById(Long.valueOf(id));
		return "redirect:/product";
	}
	
	@GetMapping("/product/{id}")
	public String prodDetails(@PathVariable(value = "id") long id, Model model) {
		if (! productRepository.existsById(id)) {
			return "redirect:/product";
		}
		
		Optional<Product> pro = productRepository.findById(id);
		ArrayList<Product> prod = new ArrayList<>();
		pro.ifPresent(prod::add);
		model.addAttribute("product", prod);
		return "/product-details";
	}
	
}
