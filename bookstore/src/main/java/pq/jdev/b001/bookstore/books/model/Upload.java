package pq.jdev.b001.bookstore.books.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
	private long id;

	@Column(name = "ORIGINAL_FILE_NAME")
	private String originalFileName;

	@Column(name = "MODIFIED_FILE_NAME")
	private String modifiedFileName;

	@Column(name = "MODIFIED_FILE_PATH")
	private String modifiedFilePath;

	@Column(name = "UPLOADED_DATE")
	private Date uploadedDate;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "BOOK_ID")
	private Book book;

	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	public String getModifiedFilePath() {
		return modifiedFilePath;
	}

	public void setModifiedFilePath(String modifiedFilePath) {
		this.modifiedFilePath = modifiedFilePath;
	}

	public Date getUploadedDate() {
		return uploadedDate;
	}

	public void setUploadedDate(Date uploadedDate) {
		this.uploadedDate = uploadedDate;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

}
