package pq.jdev.b001.bookstore.publishers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import pq.jdev.b001.bookstore.publishers.model.Publishers;

@Repository
public interface PublisherRepository extends CrudRepository<Publishers, Long>, JpaRepository<Publishers, Long> {

	Publishers findById(long id);
	
	Publishers findByPublisher(String publisher);

}
