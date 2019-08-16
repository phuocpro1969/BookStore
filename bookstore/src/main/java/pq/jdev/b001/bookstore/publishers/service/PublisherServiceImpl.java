package pq.jdev.b001.bookstore.publishers.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pq.jdev.b001.bookstore.publisher.models.Publishers;
import pq.jdev.b001.bookstore.publishers.repository.PublisherRepository;

@Service("publisherService")
public class PublisherServiceImpl implements PublisherService {
	@Autowired
	PublisherRepository publisherRepository;

	@Override
	public List<Publishers> findAll() {
		// TODO Auto-generated method stub
		return (List<Publishers>) publisherRepository.findAll();
	}

	@Override
	public void deletePublisher(int publisherId) {
		// TODO Auto-generated method stub
		publisherRepository.deleteById((long) publisherId);

	}

	@Override
	public Publishers find(long publisherId) {
		// TODO Auto-generated method stub
		return publisherRepository.findById(publisherId).get();
	}

	@Override
	public Long count() {
		// TODO Auto-generated method stub
		return publisherRepository.count();
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		publisherRepository.deleteById((long) id);
	}

	@Override
	public Publishers findOne(int id) {
		// TODO Auto-generated method stub
		return publisherRepository.findById((long) id).get();
	}

	@Override
	public void save(Publishers contact) {
		// TODO Auto-generated method stub
		publisherRepository.save(contact);
	}

	/*
	 * @Override public List<Publishers> search(String name) { // TODO
	 * Auto-generated method stub return publisherRepository.findByName(name); }
	 */

	

}
