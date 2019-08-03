package pq.jdev.b001.bookstore.Category.web;

import javax.validation.constraints.NotEmpty;

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
