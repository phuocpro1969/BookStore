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
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "upload")
public class Upload implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2713424559932800332L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "ORIGINAL_FILE_NAME")
	private String originalFileName;

	@Column(name = "MODIFIED_FILE_NAME")
	private String modifiedFileName;

	@Column(name = "UPLOADED_DATE")
	private Date uploadedDate;

	@OneToMany(mappedBy = "uploads", cascade = CascadeType.ALL)
	private Set<Book> books;

	@Column(name = "BOOK_ID")
	private Long bookId;

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOriginalFileName() {
		return originalFileName;
	}

	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}

	public String getModifiedFileName() {
		return modifiedFileName;
	}

	public void setModifiedFileName(String modifiedFileName) {
		this.modifiedFileName = modifiedFileName;
	}

	public Date getUploadedDate() {
		return uploadedDate;
	}

	public void setUploadedDate(Date uploadedDate) {
		this.uploadedDate = uploadedDate;
	}

	public Set<Book> getBooks() {
		return books;
	}

	public void setBooks(Set<Book> books) {
		this.books = books;
	}

	public Upload() {
	}

	public Upload(Date uploadedDate, Long bookId) {
		this.uploadedDate = uploadedDate;
		this.bookId = bookId;
	}
	
	public Upload(String originalFileName, String modifiedFileName, Date uploadedDate, Long bookId) {
		this.originalFileName = originalFileName;
		this.modifiedFileName = modifiedFileName;
		this.uploadedDate = uploadedDate;
		this.bookId = bookId;
	}
}
