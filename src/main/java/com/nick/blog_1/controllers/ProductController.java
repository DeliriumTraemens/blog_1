package com.nick.blog_1.controllers;

import com.nick.blog_1.models.Post;
import com.nick.blog_1.models.Product;
import com.nick.blog_1.repo.ProductRepository;
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
public class ProductController
	{
		@Autowired
		private ProductRepository productRepository;
		
		@PostMapping("/product/add")
		public String productAdd (@RequestParam String prodName, @RequestParam String prodDescription, @RequestParam String prodPrice){
		Product product =new Product(prodName, prodDescription, Float.valueOf(prodPrice));
		productRepository.save(product);
		return "redirect:/product";
		}
	
		@GetMapping("/product/{id}")
		public String prodDetails(@PathVariable(value = "id") long id, Model model) {
		if(!productRepository.existsById(id)){
			return "redirect:/product";
		}
		
		Optional<Product> pro =productRepository.findById(id);
		ArrayList<Product> prod = new ArrayList<>();
		pro.ifPresent(prod :: add);
		model.addAttribute("product",prod);
		return "/product-details";
		}
		
	}
