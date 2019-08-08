package pq.jdev.b001.bookstore.Category.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import pq.jdev.b001.bookstore.Category.model.Category;

/*
 * CategoryRepository Class
 * 
 * Java 12
 * 
 * 17/08/2019
 * 
 * author @nphtu
 * 
 * */

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>, CrudRepository<Category, Long>{
	List<Category> findByName(String name);
	

}
