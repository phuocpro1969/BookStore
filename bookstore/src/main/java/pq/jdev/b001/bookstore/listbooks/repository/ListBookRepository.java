package pq.jdev.b001.bookstore.listbooks.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import pq.jdev.b001.bookstore.books.model.Book;


@Repository
public interface ListBookRepository extends CrudRepository<Book, Long> {
	List<Book> findByTitleContaining(String q);
}
