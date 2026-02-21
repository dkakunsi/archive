package com.unitedvision.sangihe.monev.interceptor;

import java.util.List;

import javax.persistence.PersistenceException;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RepositoryInterceptor {

	@AfterReturning(
		pointcut = "execution(public * com.unitedvision.sangihe.monev.repository.*.find*(..))",
		returning = "returnValue")
	public void nullObjectReturned(Object returnValue) throws PersistenceException {
		
		if (returnValue == null)
			throw new PersistenceException("Data tidak ditemukan");
		
		if ((returnValue instanceof List) && (((List<?>)returnValue).size() <= 0))
			throw new PersistenceException("Tidak ada data yang ditemukan");
		
	}

	@AfterThrowing(
		pointcut = "execution(public * com.unitedvision.sangihe.monev.repository.*.save(..))",
		throwing = "ex")
	public void errorThrown(PersistenceException ex) throws PersistenceException {
		throw new PersistenceException(createMessage(ex.getMessage()));
	}

	@AfterThrowing(
		pointcut = "execution(public * com.unitedvision.sangihe.monev.repository.*.save(..))",
		throwing = "ex")
	public void errorThrown(DataIntegrityViolationException ex) throws PersistenceException {
		throw new PersistenceException(createMessage(ex.getMessage()));
	}

	@AfterThrowing(
		pointcut = "execution(public * com.unitedvision.sangihe.monev.repository.*.save(..))",
		throwing = "ex")
	public void errorThrown(ConstraintViolationException ex) throws PersistenceException {
		throw new PersistenceException(createMessage(ex.getMessage()));
	}
	
	private String createMessage(String key) {
		System.out.println( String.format( "LOG: %s", key) );
		
		if (key.equals("Credential Error")) {
			return "Username atau password salah";
		} else if(key.contains("anggaran_kegiatan")) {
			return "Anggaran sudah terdaftar";
		} else if(key.contains("fisik_kegiatan")) {
			return "Fisik sudah terdaftar";
		} else if(key.contains("program_tahunan")) {
			return "Program sudah terdaftar";
		} else if(key.contains("program_kegiatan")) {
			return "Kegiatan sudah terdaftar";
		} else {
			return "Terdapat kesalahan";
		}
	}
}
