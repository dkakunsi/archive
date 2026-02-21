package itb.sdrank;

import java.util.List;

import itb.sdrank.exception.RepositoryException;
import itb.sdrank.model.Entity;

public interface Repository<T extends Entity> {

	void save(T entity) throws RepositoryException;

	void save(List<T> entities) throws RepositoryException;
	
	void clean() throws RepositoryException;

	T get(String id) throws RepositoryException;
}
