package pq.jdev.b001.bookstore.publishers.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import pq.jdev.b001.bookstore.publisher.models.Publishers;

@Repository
public interface PublisherRepository extends CrudRepository<Publishers, Long>, JpaRepository<Publishers, Long> {

	/* List<Publishers> findByName(String name); */

}
