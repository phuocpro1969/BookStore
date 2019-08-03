package pq.jdev.b001.bookstore.books.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import pq.jdev.b001.bookstore.books.model.Upload;

@Repository
public interface UploadRepository extends CrudRepository<Upload, Long> {

}
