package com.unitedvision.sangihe.monev.util;

import com.unitedvision.sangihe.monev.entity.Anggaran;
import com.unitedvision.sangihe.monev.entity.Fisik;
import com.unitedvision.sangihe.monev.entity.Kegiatan;
import com.unitedvision.sangihe.monev.entity.Program;
import com.unitedvision.sangihe.monev.entity.RekapKegiatan;
import com.unitedvision.sangihe.monev.entity.RekapProgram;
import com.unitedvision.sangihe.monev.entity.Token;

public class EntityRestMessage<T> extends RestMessage {
	private T model;
	
	public EntityRestMessage() {
		super();
	}
	
	protected EntityRestMessage(Exception ex) {
		super(ex);
	}
	
	protected EntityRestMessage(T model) {
		super("Berhasil", Type.ENTITY, model);
		this.model = model;
	}
	
	public T getModel() {
		return model;
	}
	
	public static <T> EntityRestMessage<T> entityError(Exception cause) {
		return new EntityRestMessage<T>(cause);
	}
	
	public static EntityRestMessage<Kegiatan> create(Kegiatan kegiatan) {
		return new EntityRestMessage<Kegiatan>(kegiatan);
	}
	
	public static EntityRestMessage<Program> create(Program program) {
		return new EntityRestMessage<Program>(program);
	}
	
	public static EntityRestMessage<Anggaran> create(Anggaran anggaran) {
		return new EntityRestMessage<Anggaran>(anggaran);
	}
	
	public static EntityRestMessage<Fisik> create(Fisik fisik) {
		return new EntityRestMessage<Fisik>(fisik);
	}
	
	public static EntityRestMessage<Token> create(Token token) {
		return new EntityRestMessage<Token>(token);
	}

	public static EntityRestMessage<RekapKegiatan> create(RekapKegiatan rekap) {
		return new EntityRestMessage<RekapKegiatan>(rekap);
	}

	public static EntityRestMessage<RekapProgram> create(RekapProgram rekap) {
		return new EntityRestMessage<RekapProgram>(rekap);
	}
}
