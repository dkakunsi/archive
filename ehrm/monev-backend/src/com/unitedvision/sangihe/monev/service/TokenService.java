package com.unitedvision.sangihe.monev.service;

import com.unitedvision.sangihe.monev.entity.Pegawai;
import com.unitedvision.sangihe.monev.entity.Token;
import com.unitedvision.sangihe.monev.exception.OutOfDateEntityException;
import com.unitedvision.sangihe.monev.exception.UnauthenticatedAccessException;

public interface TokenService {

	Token get(String token) throws OutOfDateEntityException, UnauthenticatedAccessException;

	Token create(Pegawai pegawai);
	
	Token create(String nip, String password);
	
	Token lock(String token);
	
	void hapus();
	
	Pegawai login(String username);

}
