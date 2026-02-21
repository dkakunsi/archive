package com.dbsys.rs.lib;

import java.util.List;

import com.dbsys.rs.lib.entity.Barang;
import com.dbsys.rs.lib.entity.KategoriTindakan;
import com.dbsys.rs.lib.entity.Operator;
import com.dbsys.rs.lib.entity.Pasien;
import com.dbsys.rs.lib.entity.Pegawai;
import com.dbsys.rs.lib.entity.Pelayanan;
import com.dbsys.rs.lib.entity.Pemakaian;
import com.dbsys.rs.lib.entity.Pembayaran;
import com.dbsys.rs.lib.entity.Penduduk;
import com.dbsys.rs.lib.entity.Stok;
import com.dbsys.rs.lib.entity.Tindakan;
import com.dbsys.rs.lib.entity.Unit;

/**
 * Kelas untuk men-generate daftar entity menjadi objek JSON.
 * 
 * @author Deddy Christoper Kakunsi
 *
 * @param <T>
 */
public class ListEntityRestMessage<T> extends RestMessage {
	private List<T> list;
	
	public ListEntityRestMessage() {
		super();
	}
	
	protected ListEntityRestMessage(Exception ex) {
		super(ex);
	}

	protected ListEntityRestMessage(List<T> list) {
		super("Berhasil", Type.LIST);
		setList(list);
	}
	
	public List<T> getList() {
		return list;
	}
	
	public void setList(List<T> list) {
		this.list = list;
	}

	/**
	 * Buat objek JSON utuk pesan error.
	 * 
	 * @param cause
	 * 
	 * @return
	 */
	public static <T> ListEntityRestMessage<T> listEntityError(Exception cause) {
		return new ListEntityRestMessage<T>(cause);
	}

	public static ListEntityRestMessage<Unit> createListUnit(List<Unit> list) {
		return new ListEntityRestMessage<Unit>(list);
	}

	public static ListEntityRestMessage<Operator> createListOperator(List<Operator> list) {
		return new ListEntityRestMessage<Operator>(list);
	}

	public static ListEntityRestMessage<Penduduk> createListPenduduk(List<Penduduk> list) {
		return new ListEntityRestMessage<Penduduk>(list);
	}

	public static ListEntityRestMessage<Tindakan> createListTindakan(List<Tindakan> list) {
		return new ListEntityRestMessage<Tindakan>(list);
	}

	public static ListEntityRestMessage<Barang> createListBarang(List<Barang> list) {
		return new ListEntityRestMessage<Barang>(list);
	}

	public static ListEntityRestMessage<Pegawai> createListPegawai(List<Pegawai> list) {
		return new ListEntityRestMessage<Pegawai>(list);
	}

	public static ListEntityRestMessage<KategoriTindakan> createListKategori(List<KategoriTindakan> list) {
		return new ListEntityRestMessage<KategoriTindakan>(list);
	}

	public static ListEntityRestMessage<Stok> createListStok(List<Stok> list) {
		return new ListEntityRestMessage<>(list);
	}

	public static ListEntityRestMessage<Pemakaian> createListPemakaian(List<Pemakaian> list) {
		return new ListEntityRestMessage<Pemakaian>(list);
	}

	public static ListEntityRestMessage<Pasien> createListPasien(List<Pasien> list) {
		return new ListEntityRestMessage<Pasien>(list);
	}

	public static ListEntityRestMessage<Pelayanan> createListPelayanan(List<Pelayanan> list) {
		return new ListEntityRestMessage<Pelayanan>(list);
	}

	public static ListEntityRestMessage<Pembayaran> createListPembayaran(List<Pembayaran> list) {
		return new ListEntityRestMessage<Pembayaran>(list);
	}
}
