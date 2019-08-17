package pq.jdev.b001.bookstore.books.web.dto;

import java.sql.Date;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import pq.jdev.b001.bookstore.books.model.SelectCategory;
import pq.jdev.b001.bookstore.category.model.Category;
import pq.jdev.b001.bookstore.publishers.model.Publishers;

public class UploadInformationDTO {
	@NotEmpty
	private String title;

	@NotEmpty
	private Long price;

	@Nullable
	private String domain;

	@Nullable
	private MultipartFile pictureFile;

	private Date uploadedDate;

	@Nullable
	private String authors;

	private Long publisherId;

	private List<Publishers> publishers;

	@NotEmpty
	private Integer publishedYear;

	private List<String> categoriesId;

	@Nullable
	private List<Category> categories;

	@NotEmpty
	private List<MultipartFile> files;

	private MultipartFile folder;

	private List<SelectCategory> selectCategories;

	private String description;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public MultipartFile getPictureFile() {
		return pictureFile;
	}

	public void setPictureFile(MultipartFile pictureFile) {
		this.pictureFile = pictureFile;
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

	public Long getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(Long publisherId) {
		this.publisherId = publisherId;
	}

	public List<Publishers> getPublishers() {
		return publishers;
	}

	public void setPublishers(List<Publishers> publishers) {
		this.publishers = publishers;
	}

	public Integer getPublishedYear() {
		return publishedYear;
	}

	public void setPublishedYear(Integer publishedYear) {
		this.publishedYear = publishedYear;
	}

	public List<String> getCategoriesId() {
		return categoriesId;
	}

	public void setCategoriesId(List<String> categoriesId) {
		this.categoriesId = categoriesId;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public List<MultipartFile> getFiles() {
		return files;
	}

	public void setFiles(List<MultipartFile> files) {
		this.files = files;
	}

	public MultipartFile getFolder() {
		return folder;
	}

	public void setFolder(MultipartFile folder) {
		this.folder = folder;
	}

	public List<SelectCategory> getSelectCategories() {
		return selectCategories;
	}

	public void setSelectCategories(List<SelectCategory> selectCategories) {
		this.selectCategories = selectCategories;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
