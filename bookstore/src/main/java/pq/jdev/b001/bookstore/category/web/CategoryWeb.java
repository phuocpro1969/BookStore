package pq.jdev.b001.bookstore.category.web;

import javax.validation.constraints.NotEmpty;

/*
 * CategoryWeb Class
 * 
 * Java 12
 * 
 * 17/08/2019
 * 
 * author @nphtu
 * 
 * */


public class CategoryWeb {
	
	@NotEmpty
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
