package com.dbsys.rs.lib;

/**
 * ObatFarmasi untuk pasien.
 * 
 * @author Deddy Christoper Kakunsi
 *
 */
public interface Obat {

	/**
	 * Mengambil nama obat.
	 * 
	 * @return nama
	 */
	String getNama();
	
	/**
	 * Mengatur nama obat.
	 * 
	 * @param nama
	 */
	void setNama(String nama);
	
	/**
	 * Mengambil keterangan pemakaian obat.
	 * 
	 * @return keterangan
	 */
	String getKeterangan();
	
	/**
	 * Mengatur keterangan pemakaian obat.
	 * 
	 * @param keterangan
	 */
	void setKeterangan(String keterangan);
}
