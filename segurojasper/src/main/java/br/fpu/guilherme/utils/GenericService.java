package br.fpu.guilherme.utils;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

public abstract class GenericService<T extends BaseEntity<ID>, ID extends Serializable>  {

	@Autowired
	protected CrudRepository<T, ID> genericRepository;

	public T save(T entityObject) {
		return this.genericRepository.save(entityObject);
	}

	public Boolean delete(ID entityObjectId) {
		// TODO Auto-generated method stub
		T temp = this.genericRepository.findOne(entityObjectId);
		if (temp != null) {
			this.genericRepository.delete(temp);
			return true;
		}
		return false;
	}

	public T edit(T entityObject) {
		// TODO Auto-generated method stub
		return this.genericRepository.save(entityObject);
	}

	public T find(ID entityObjectId) {
		return this.genericRepository.findOne(entityObjectId);
	}

	public List<T> getAll() {
		// TODO Auto-generated method stub
		Iterable<T> entityObjects = this.genericRepository.findAll();
		return (List<T>) entityObjects;
	}
}
