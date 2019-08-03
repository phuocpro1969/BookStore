package pq.jdev.b001.bookstore.books.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import pq.jdev.b001.bookstore.Category.model.Category;
import pq.jdev.b001.bookstore.publisher.models.Publishers;
import pq.jdev.b001.bookstore.users.model.Person;

@Entity
@Table(name = "books")
public class Book implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private long id;

	@Column(name = "TITLE")
	private String title;

	@Column(name = "PRICE")
	private long price;

	@Column(name = "DOMAIN")
	private String domain;

	@Column(name = "PICTURE")
	private String picture;

	@Column(name = "UPLOADED_DATE")
	private Date uploadedDate;

	@Column(name = "AUTHORS")
	private String authors;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "PERSON_ID")
	private Person person;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "PUBLISHER_ID ")
	private Publishers publisher;

	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "book_category", joinColumns = { @JoinColumn(name = "BOOK_ID") }, inverseJoinColumns = {
			@JoinColumn(name = "CATEGORY_ID") })
	private Set<Category> categories;

	@OneToMany(mappedBy = "book")
	private Set<Upload> uploads;

	@Column(name = "PUBLISHED_YEAR")
	private int publishedYear;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public Date getUploadedDate() {
		return uploadedDate;
	}

	public void setUploadedDate(Date uploadedDate) {
		this.uploadedDate = uploadedDate;
	}

	public String getAuthors() {
		return authors;
	}

	public void setAuthors(String authors) {
		this.authors = authors;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Publishers getPublisher() {
		return publisher;
	}

	public void setPublisher(Publishers publisher) {
		this.publisher = publisher;
	}

	public Set<Category> getCategories() {
		return categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}

	public Set<Upload> getUploads() {
		return uploads;
	}

	public void setUploads(Set<Upload> uploads) {
		this.uploads = uploads;
	}

	public int getPublishedYear() {
		return publishedYear;
	}

	public void setPublishedYear(int publishedYear) {
		this.publishedYear = publishedYear;
	}

}
