package com.dbsys.rs.lib.interceptor;

import java.util.List;

import javax.persistence.PersistenceException;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RepositoryInterceptor {

	@AfterReturning(
		pointcut = "execution(public * com.dbsys.rs.*.repository.*.find*(..))",
		returning = "object"
	)
	public void nullEntityReturned(Object object) throws PersistenceException {
		
		if (object == null)
			throw new PersistenceException("Data tidak ditemukan");
		
		if ((object instanceof List) && ((List<?>)object).size() <= 0)
			throw new PersistenceException("Tidak ada data yang ditemukan");
	}

	@AfterThrowing(
		pointcut = "execution(public * com.dbsys.rs.*.repository.*.save(..))",
		throwing = "ex")
	public void errorThrownInSave(PersistenceException ex) throws PersistenceException {
		throw new PersistenceException(createMessage(ex));
	}

	@AfterThrowing(
		pointcut = "execution(public void com.dbsys.rs.*.repository.*.*(..))",
		throwing = "ex")
	public void errorThrown(PersistenceException ex) throws PersistenceException {
		throw new PersistenceException(createMessage(ex));
	}
	
	private String createMessage(Exception ex) {
		String message = ex.getMessage();
		System.out.println(String.format("Exception: %s", message));
		
		if (message.contains("username")) {
			return "Username yang anda masukkan sudah digunakan";
		} else if (message.contains("kode")) {
			return "Kode yang anda masukkan sudah digunakan";
		} else if (message.contains("nik")) {
			return "NIK yang anda masukkan sudah digunakan";
		} else if (message.contains("nip")) {
			return "NIP yang anda masukkan sudah digunakan";
		} else if (message.contains("telepon")) {
			return "Telepon yang anda masukkan sudah digunakan";
		} else {
			return message;
		}
	}

}
