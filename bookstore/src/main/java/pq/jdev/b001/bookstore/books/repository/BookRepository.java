package pq.jdev.b001.bookstore.books.repository;

import java.sql.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pq.jdev.b001.bookstore.books.model.Book;
import pq.jdev.b001.bookstore.publishers.model.Publishers;
import pq.jdev.b001.bookstore.users.model.Person;

@Repository
public interface BookRepository extends CrudRepository<Book, Long>, JpaRepository<Book, Long> {

	@Query("select book from Book book where book.person.id = ?1 and book.id = ?2")
	public Book findByPersonIdandBookId(Long personId, Long bookid);

	@Modifying
	@Query("update Book book set book.picture =:picture where book.id =:bookid")
	public void saveUpdatePicture(@Param("bookid") Long bookid, @Param("picture") String picture);

	@Modifying
	@Query("update Book book set book.title =:title where book.id =:bookid")
	public void saveUpdateTitle(@Param("bookid") Long bookid, @Param("title") String title);

	@Modifying
	@Query("update Book book set book.price =:price where book.id =:bookid")
	public void saveUpdatePrice(@Param("bookid") Long bookid, @Param("price") Long price);

	@Modifying
	@Query("update Book book set book.domain =:domain where book.id =:bookid")
	public void saveUpdateDomain(@Param("bookid") Long bookid, @Param("domain") String domain);

	@Modifying
	@Query("update Book book set book.uploadedDate =:uploadedDate where book.id =:bookid")
	public void saveUpdateUploadedDate(@Param("bookid") Long bookid, @Param("uploadedDate") Date uploadedDate);

	@Modifying
	@Query("update Book book set book.authors =:authors where book.id =:bookid")
	public void saveUpdateAuthors(@Param("bookid") Long bookid, @Param("authors") String authors);

	@Modifying
	@Query("update Book book set book.person =:person where book.id =:bookid")
	public void saveUpdatePerson(@Param("bookid") Long bookid, @Param("person") Person person);

	@Modifying
	@Query("update Book book set book.publishedYear =:publishedYear where book.id =:bookid")
	public void saveUpdatePublishedYear(@Param("bookid") Long bookid, @Param("publishedYear") Integer publishedYear);

	@Modifying
	@Query("update Book book set book.publisher =:publisher where book.id =:bookid")
	public void saveUpdatePublisher(@Param("bookid") Long bookid, @Param("publisher") Publishers publisher);
	
	@Modifying
	@Query("update Book book set book.description =:description where book.id =:bookid")
	public void saveUpdateDescription(@Param("bookid") Long bookid, @Param("description") String description);
}
