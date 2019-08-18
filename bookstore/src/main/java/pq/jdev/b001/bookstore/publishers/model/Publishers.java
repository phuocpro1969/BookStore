package pq.jdev.b001.bookstore.publishers.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import pq.jdev.b001.bookstore.books.model.Book;

@Entity(name = "Publish")
@Table(name = "publish")
public class Publishers {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name = "PUBLISHINGHOUSE", columnDefinition = "VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_bin")
	private String publisher;

	@Column(name = "CREATEDUSER")
	private String createName;

	@Column(name = "CREATEDDATE")
	private String createDate;

	@Column(name = "UPLOADUSER")
	private String updateName;

	@Column(name = "UPDATEDATE")
	private String updateDate;

	@OneToMany(mappedBy = "publisher")
	private Set<Book> books;

	public Publishers() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public Set<Book> getBooks() {
		return books;
	}

	public void setBooks(Set<Book> books) {
		this.books = books;
	}

	public Publishers(Long id, String publisher, String createName, String createDate, String updateName,
			String updateDate) {
		super();
		this.id = id;
		this.publisher = publisher;
		this.createName = createName;
		this.createDate = createDate;
		this.updateName = updateName;
		this.updateDate = updateDate;
	}

	@Override
	public String toString() {
		return "Publishers [id=" + id + ", publisher=" + publisher + ", createName=" + createName + ", createDate="
				+ createDate + ", updateName=" + updateName + ", updateDate=" + updateDate + "]";
	}

}
