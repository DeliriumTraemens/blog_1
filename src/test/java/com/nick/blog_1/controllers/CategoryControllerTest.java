package com.nick.blog_1.controllers;

import com.nick.blog_1.models.Category;
import com.nick.blog_1.repo.CategoryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

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
		
		while (currentCat.getParentId() != 0L) {
			currentCat = categoryRepository.findById(currentCat.getParentId()).get();
			crumbsList.addFirst(currentCat);
		}
		System.out.println(crumbsList);
		System.out.println("Parent id " + currentCat.getParentId() + " name is " + currentCat.getName());
		
		String path = "/";
		for (Category cat : crumbsList) {
			path += cat.getName() + "/";
			System.out.print("/" + cat.getName());
		}
		System.out.print("\nResult path is  " + path);
		
	}
	
	@Test
	public void imagePathMaker() {
		String a = "aaa";
		System.out.println(a);
		a += "/";
		a += "bb";
		System.out.println(a);
		
	}
	
	@Test
	public void catTree() {
		Map<String, Category> treeCat = new HashMap<String, Category>();
		Iterable<Category> topLevelCat = categoryRepository.findByParentId(0L);//Start array
		LinkedList<List> currLevel = new LinkedList<>();
		ArrayList<List> branch=new ArrayList<>();
		HashSet<Category> parentList = new HashSet<>();
		
		for (Category cat : topLevelCat) {
			currLevel.addFirst(Collections.singletonList(categoryRepository.findByParentId(cat.getId())));
			System.out.println("\n`````` currLevel`````````````\n");
			System.out.println(currLevel);
			System.out.println("\n~~~~~~~~~~ currlevel~~~~~~~~~~~~"+topLevelCat.iterator());
		}
//		System.out.println("currLevel contains\n" + currLevel.toString());
		
		for (int i = 0; i<currLevel.size();i++){
			branch.add(currLevel.get(i));
//			branch.add(list);
			System.out.println("\n-------*************------\n");
			System.out.println("branch is ----- \n"+branch);
			System.out.println("\n-------^^^^^^^^^^^^^^------\n\n");
		}
//		System.out.println("branch ---"+branch.toString());
		
		
		/*
		 * Список категорий топа
		 * достаем первую категорию -- берем Ид Парент Имя
		 * генерим список по парент Ид первого элемента (List<Category> currLevel)
		 * кладем список в контейнер Мапы - имя парента и currLevel
		 * итерируемся
		 *
		 * Мапа: Имя, Массив категорий
		 *
		 * */
//		treeCat.put(cat.getName(),cat);
		String categoryName;
	}
	
	@Test
	public void catalogTree(){
		Iterable<Category> all = categoryRepository.findAll();
		Set<Long> parentSet = new TreeSet<>();
		
		//Лист парентов
		for (Category c:all){
			parentSet.add(c.getParentId());
		}
		
		//Идем по листу парентов
		parentExtractor(parentSet);
		
		
		/*Получаем категорию из массива по паренту, ложим в Лист
		* Бухло Парент 0 Ид 5
		*    Пивас   Парент 5 Ид 10
		*       Лагер     Парент 10 Ид 15
		*       Пильзень  Парент 10 Ид 15
		*
		* основная категория
		*   массив потомков
		*
		* */
		
		for (Long idx:parentSet){
		
		}
		
		
		
	}//method end
	
	private Set<Long> parentExtractor(Set<Long> parentSet) {
			Set<Long> childsParent = new TreeSet<>();//детские паренты
		for (Long currId : parentSet) {
			Optional<Category> byId = categoryRepository.findById(currId);//лист чилдренов данного парента
		}
		return childsParent;
	}
	
	@Test
	public void catalogBuilder(){
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
			
			System.out.println("\n-------------++++++Level Map++++++++-------------");
//			System.out.println(byParentId);
			System.out.println("Level map "+levelMap);
			System.out.println("\n-------------^^^^^^^Level Map^^^^^^^-------------");
			
			
			
		}
		
		System.out.println("\n\n------------------- Finish ---------------------");
		System.out.println(levelMap.toString());
		System.out.println("\n\n------------^^^^^^^ Finish ^^^^^^^--------------");
		
		
	}
	
}