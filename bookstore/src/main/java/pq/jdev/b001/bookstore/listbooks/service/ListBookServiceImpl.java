package pq.jdev.b001.bookstore.listbooks.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pq.jdev.b001.bookstore.books.model.Book;
import pq.jdev.b001.bookstore.listbooks.repository.ListBookRepository;


@Service("listBookService")
public class ListBookServiceImpl implements ListBookService {
	@Autowired
    private ListBookRepository listBookRepository;

    @Override
    public Iterable<Book> findAll() {
        return listBookRepository.findAll();
    }

    @Override
    public List<Book> search(String q) {
        return listBookRepository.findByTitleContaining(q);
    }

    @Override
    public Book findOne(long id) {
        return listBookRepository.findById(id).get();
    }

    @Override
    public void save(Book book) {
    	listBookRepository.save(book);
    }

    @Override
    public void delete(long id) {
    	listBookRepository.deleteById(id);
    }
}
