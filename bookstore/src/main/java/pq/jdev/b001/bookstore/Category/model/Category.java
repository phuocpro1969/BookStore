package pq.jdev.b001.bookstore.Category.model;

import java.sql.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import pq.jdev.b001.bookstore.books.model.Book;

/*
 * Category Class
 * 
 * Java 12
 * 
 * 17/08/2019
 * 
 * author @nphtu
 * 
 * */


@Entity
@Table(name="category")
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	
	private long id;
	@Column(columnDefinition = "VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_bin")
	private String name;
	private Date createdate;
	private Date updatedate;
	
	public Category() {
		
	}
	
	public Category(long id, String name, Date createdate,Date updatedate) {
		this.id = id;
		this.name = name;
		this.createdate = createdate;
		this.updatedate = updatedate;
	}
	
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "categories", targetEntity = pq.jdev.b001.bookstore.books.model.Book.class)
	private Set<Book> books;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getCreatedate() {
		return createdate;
	}
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	public Date getUpdatedate() {
		return updatedate;
	}
	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}
	
	
}
