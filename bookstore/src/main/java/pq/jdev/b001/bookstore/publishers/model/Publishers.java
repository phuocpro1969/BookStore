package pq.jdev.b001.bookstore.publishers.model;

import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import pq.jdev.b001.bookstore.books.model.Book;
import pq.jdev.b001.bookstore.users.constraint.ValidName;

@Entity
@Table
public class Publishers {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	@ValidName
	@Column(name = "PUBLISHINGHOUSE", columnDefinition = "VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_bin")
	private String publisher;

	@Column(name = "CREATEDID")
	private Long createId;

	@CreationTimestamp
	@Column(name = "CREATEDDATE")
	private Timestamp createDate;

	@Column(name = "UPLOADID")
	private Long updateId;

	@UpdateTimestamp
	@Column(name = "UPDATEDATE")
	private Timestamp updateDate;

	@OneToMany(mappedBy = "publisher")
	private Set<Book> books;

	public Publishers() {
	}

	public Publishers(Long createId, Long updateId) {
		this.createId = createId;
		this.updateId = updateId;
	}

	public Publishers(String publisher, Timestamp createDate, Timestamp updateDate, Long createId, Long updateId) {
		this.publisher = publisher;
		this.createId = createId;
		this.createDate = createDate;
		this.updateId = updateId;
		this.updateDate = updateDate;
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

	public Long getCreateId() {
		return createId;
	}

	public void setCreateId(Long createId) {
		this.createId = createId;
	}

	public Long getUpdateId() {
		return updateId;
	}

	public void setUpdateId(Long updateId) {
		this.updateId = updateId;
	}

	public Timestamp getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	public Set<Book> getBooks() {
		return books;
	}

	public void setBooks(Set<Book> books) {
		this.books = books;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((books == null) ? 0 : books.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Publishers other = (Publishers) obj;
		if (books == null) {
			if (other.books != null)
				return false;
		} else if (!books.equals(other.books))
			return false;
		return true;
	}
}
