package pq.jdev.b001.bookstore.publishers.service;

import java.util.List;

import org.springframework.stereotype.Service;

import pq.jdev.b001.bookstore.publisher.models.Publishers;

@Service
public interface PublisherService {
	List<Publishers> findAll();

	public void deletePublisher(int publisherId);

	public Publishers find(long publisherId);
	/* List<Publishers> search(String q); */
	
	public Long count();
	void delete(int id);
	Publishers findOne(int id);
	void save(Publishers contact);
}
