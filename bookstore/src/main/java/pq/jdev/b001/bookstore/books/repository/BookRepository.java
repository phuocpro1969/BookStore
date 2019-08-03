package pq.jdev.b001.bookstore.books.repository;

import java.sql.Date;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pq.jdev.b001.bookstore.Category.model.Category;
import pq.jdev.b001.bookstore.books.model.Book;
import pq.jdev.b001.bookstore.publisher.models.Publishers;
import pq.jdev.b001.bookstore.users.model.Person;

@Repository
public interface BookRepository extends CrudRepository<Book, Long>, JpaRepository<Book, Long> {

	@Query("select book from Book book where book.person.id = ?1 and book.id = ?2")
	public Book findByPersonIdandBookId(Long personId, Long bookid);

	@Modifying
	@Query("update Book book set book.picture =:picture where book.id =:bookid")
	public int saveUpdatePicture(@Param("bookid") Long bookid, @Param("picture") String picture);

	@Modifying
	@Query("update Book book set book.categories = :categories where book.id =:bookid")
	public int saveUpdateCategories(@Param("bookid") Long bookid, @Param("categories") Set<Category> categories);

	@Modifying
	@Query("update Book book set book.title =:title where book.id =:bookid")
	public int saveUpdateTitle(@Param("bookid") Long bookid, @Param("title") String title);

	@Modifying
	@Query("update Book book set book.price =:price where book.id =:bookid")
	public int saveUpdatePrice(@Param("bookid") Long bookid, @Param("price") Long price);

	@Modifying
	@Query("update Book book set book.domain =:domain where book.id =:bookid")
	public int saveUpdateDomain(@Param("bookid") Long bookid, @Param("domain") String domain);

	@Modifying
	@Query("update Book book set book.uploadedDate =:uploadedDate where book.id =:bookid")
	public int saveUpdateUploadedDate(@Param("bookid") Long bookid, @Param("uploadedDate") Date uploadedDate);

	@Modifying
	@Query("update Book book set book.authors =:authors where book.id =:bookid")
	public int saveUpdateAuthors(@Param("bookid") Long bookid, @Param("authors") String authors);

	@Modifying
	@Query("update Book book set book.person =:person where book.id =:bookid")
	public int saveUpdatePerson(@Param("bookid") Long bookid, @Param("person") Person person);

	@Modifying
	@Query("update Book book set book.publishedYear =:publishedYear where book.id =:bookid")
	public int saveUpdatePublishedYear(@Param("bookid") Long bookid, @Param("publishedYear") Integer publishedYear);

	@Modifying
	@Query("update Book book set book.publisher =:publisher where book.id =:bookid")
	public int saveUpdatePublisher(@Param("bookid") Long bookid, @Param("publisher") Publishers publisher);
}
