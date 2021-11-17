package com.nick.blog_1.service;

import com.nick.blog_1.models.Category;
import com.nick.blog_1.repo.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

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
		return uploadRoot + dirPath + pictureName;
	}
	
//	imagePathForPicture /Тушка/Кумпол/
//	directoryPathMkDir /D:/docs/pics/blog/Тушка/Кумпол/
//	transferPath /D:/docs/pics/blog//Тушка/Кумпол/morda.jpg
//	upload.path /D:/docs/pics/blog
	
	public ArrayList<List> catalogList()
	{
		Iterable<Category> topLevelCat = categoryRepository.findByParentId(0L);//Start array
		LinkedList<List> currLevel = new LinkedList<>();
		ArrayList<List> branch=new ArrayList<>();
		
		for (Category cat : topLevelCat) {
			currLevel.addFirst(Collections.singletonList(categoryRepository.findByParentId(cat.getId())));
		}
		
		for (int i = 0; i<currLevel.size();i++){
			branch.add(currLevel.get(i));
		}
		
		return branch;
	}
	
	//catalog category builder
	public Map<String, Iterable> catalogBuilder(){
		ArrayList<Category> all = (ArrayList<Category>) categoryRepository.findAll();//общий список
		Set<Long>parentIdSet=new TreeSet<Long>();//список уникальных Парентов
		Map<String,Iterable> levelMap=new HashMap<String,Iterable>();
		
		//Получаем общий список
		for (Category c:all){
			parentIdSet.add(c.getParentId());
		}
		
		for(Long parId:parentIdSet){
			/*берем элемент и грузим лист категорий по этому паренту*/
			Iterable<Category> byParentId = categoryRepository.findByParentId(parId);
			
			if(parId==0L){
				levelMap.put("top", byParentId);
			}else{
				String levelName =categoryRepository.findById(parId).get().getName();
				levelMap.put(levelName,byParentId);
			}
		}
		return levelMap;
	}
	
	//------- SERVICE ------------//\\
}
