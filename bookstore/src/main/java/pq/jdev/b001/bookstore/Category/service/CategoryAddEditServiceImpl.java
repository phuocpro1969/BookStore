package pq.jdev.b001.bookstore.Category.service;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pq.jdev.b001.bookstore.Category.model.Category;
import pq.jdev.b001.bookstore.Category.repository.CategoryRepository;
import pq.jdev.b001.bookstore.Category.web.CategoryWeb;
@Service
public class CategoryAddEditServiceImpl implements CategoryAddEditService{
	@Autowired
	private CategoryRepository categoryrepository;
	@Override
	public Category save(CategoryWeb categoryweb) {
		Category category = new Category();
		category.setName(categoryweb.getName());
		category.setCreatedate(new Date(Calendar.getInstance().getTime().getTime()));
		category.setUpdatedate(new Date(Calendar.getInstance().getTime().getTime()));
		return categoryrepository.save(category);
	}
	@Override
	public List<Category> findAll() {
		return (List<Category>) categoryrepository.findAll();
	}
	@Override
	public List<Category> findByName(String name) {
		return (List<Category>) categoryrepository.findByName(name);
	}




}
