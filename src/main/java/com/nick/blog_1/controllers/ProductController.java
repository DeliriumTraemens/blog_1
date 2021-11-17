package com.nick.blog_1.controllers;

import com.nick.blog_1.models.Post;
import com.nick.blog_1.models.Product;
import com.nick.blog_1.repo.CategoryRepository;
import com.nick.blog_1.repo.ProductRepository;
import com.nick.blog_1.service.PathMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
	@Autowired
	private PathMaker pm;
	
	
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
		model.addAttribute("catalog",pm.catalogList());
		
		model.addAttribute("catalogLevel", pm.catalogBuilder());
		
		model.addAttribute("categList", catRepo.findAll());
		
		model.addAttribute("curProd", productRepository.findById(id).get());
		return "product-edit";
	}
	
	@PostMapping("/productEdit")
	public String productEditor(
			@RequestParam(required = false) String id,
			@RequestParam(required = false) String categoryId,
			@RequestParam(required = false) String categoriList,
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String description,
			@RequestParam(required = false) String price,
			@RequestParam("file") MultipartFile file
	                           ) throws IOException {
		/*id categoryId name description price file */
		Product productToEdit = productRepository.findById(Long.valueOf(id)).get();
		
		// ----Path Maker
		String catName = catRepo.findById(Long.valueOf(categoryId)).get().getName();
		String imagePathForPicture = pm.imagePathMakerForEditor(pm.crumbsMaker(Long.valueOf(categoryId)));
		String directoryPathMkDir = uploadPath + imagePathForPicture;
		String transferPath = pm.resultPathForTransfer(uploadPath, imagePathForPicture, file.getOriginalFilename());
		{
			System.out.println("\n--------------PPPPPPPPPP---------------------\n");
			System.out.println("imagePathForPicture " + imagePathForPicture);
			System.out.println("directoryPathMkDir " + directoryPathMkDir);
			System.out.println("transferPath " + transferPath);
			System.out.println("upload.path " + uploadPath);
			System.out.println("\n---------------dddddddddd--------------------\n");
		}
		
		// ----Path Maker
		
		if (file != null && ! file.getOriginalFilename().isEmpty()) {
			pm.directoryMaker(directoryPathMkDir);
			
			productToEdit.setImagePath(imagePathForPicture + file.getOriginalFilename());
			file.transferTo(new File(transferPath));
			
		}
		
		if (categoryId != null) {
			productToEdit.setCategoryId(Long.valueOf(categoryId));
		}
		if (categoriList != null) {
			productToEdit.setCategoryId(Long.valueOf(categoriList));
		}
		if (name != null) {
			productToEdit.setName(name);
		}
		if (description != null) {
			productToEdit.setDescription(description);
		}
		
		if (price != null) {
			productToEdit.setPrice(Float.valueOf(price));
		}
		
		
		productRepository.save(productToEdit);
		
		return "redirect:/product";
	}
	
	//\\Edit product
	
	// ------Add Product
	@GetMapping("/product/add")
	public String productAdd(Model model) {
		model.addAttribute("title", "Добавить Товар");
		model.addAttribute("categoriesList", catRepo.findAll());
		
		return "product-add";
	}
	
	@PostMapping("/product/add")
	public String productAdd(@RequestParam String prodName, @RequestParam String prodDescription, @RequestParam String prodPrice) {
		Product product = new Product(prodName, prodDescription, Float.valueOf(prodPrice));
		productRepository.save(product);
		return "redirect:/product";
	}
	
	@GetMapping("/category/product-add/{id}")
	public String productCategoryAdd(@PathVariable(value = "id") long id, Model model) {
		model.addAttribute("categoryes", catRepo.findAll());
		model.addAttribute("currCat", catRepo.findById(id).get());
		model.addAttribute("productCat", id);
		return "product-category-add";
	}
	
	@ModelAttribute(name = "product")
	public Product product() {
		return new Product();
	}
	
	@PostMapping("/category/product-add")
	public String productCategoryBuilder(Product product,  @RequestParam("file") MultipartFile file) throws IOException {
		
		String imagePathForPicture = pm.imagePathMakerForEditor(pm.crumbsMaker(Long.valueOf(product.getCategoryId())));
		String directoryPathMkDir = uploadPath + imagePathForPicture;
		String transferPath = pm.resultPathForTransfer(uploadPath, imagePathForPicture, file.getOriginalFilename());
		
		if (file != null && ! file.getOriginalFilename().isEmpty()) {
			pm.directoryMaker(directoryPathMkDir);
			
			product.setImagePath(imagePathForPicture + file.getOriginalFilename());
			file.transferTo(new File(transferPath));
			
		}
		productRepository.save(product);
		{
			System.out.println("\n----------------------------\n");
			System.out.println(product);
			System.out.println(file.getOriginalFilename());
			System.out.println("\n----------------------------\n");
		}
		return "redirect:/category";
	}
	
	//------------Add Product end
	
	//Delete Product
	@GetMapping("/productRemove/{id}")
	public String productRemover(@PathVariable(value = "id") long id) {
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
