package com.nick.blog_1.service;

import com.nick.blog_1.models.Category;
import com.nick.blog_1.repo.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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
	public String imagePathMakerAdd(List<Category> crumbs, String name) {
//	public String imagePathMaker(List<Category> crumbs) {
		String imagePath="/";
		for (Category category : crumbs){
			imagePath += category.getName()+"/";
		}
		imagePath+=name;
		return imagePath;
	}
	
	////Path maker 2 non top level
	public String imagePathMakerForEditor(List<Category> crumbs) {
		String imagePath="/";
		for (Category category : crumbs){
			imagePath += category.getName()+"/";
		}
		return imagePath;
	}
	
	//Mkdir
	public void directoryMaker(String directoryPath){
		File pathMaker = new File(directoryPath);
		if(!pathMaker.exists()){
			pathMaker.mkdir();
		}
	}
	
	public String resultPathForTransfer(String uploadRoot, String dirPath, String pictureName ){
//		return uploadRoot +"/"+ dirPath +"/"+ pictureName;
//		return uploadRoot +"/"+ dirPath + pictureName;
		return uploadRoot + dirPath + pictureName;
//			file.transferTo(new File(uploadPath+"/"+imagePathForPicture+"/"+file.getOriginalFilename()));
	}
	
//	imagePathForPicture /Тушка/Кумпол/
//	directoryPathMkDir /D:/docs/pics/blog/Тушка/Кумпол/
//	transferPath /D:/docs/pics/blog//Тушка/Кумпол/morda.jpg
//	upload.path /D:/docs/pics/blog
	
	//------- SERVICE ------------//\\
}
