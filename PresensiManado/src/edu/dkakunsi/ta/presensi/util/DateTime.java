package edu.dkakunsi.ta.presensi.util;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import edu.dkakunsi.ta.presensi.exception.GeneralException;

/**
 * Kelas untuk proses manipulasi tanggal dan waktu
 * @author dkakunsi
 *
 */
public class DateTime {
	private Calendar calendar;
	private TimeZone timeZone;
	private int tahun, bulan, tanggal, jam, menit, detik;
	
	public DateTime() {
		initiate();
		this.setAttribute();
	}
	
	public DateTime(long arg0) {
		initiate();
		calendar.setTimeInMillis(arg0);
		this.setAttribute();
	}
	
	public DateTime(int tahun, int bulan, int tanggal) {
		initiate();
		calendar.set(tahun, (bulan-1), tanggal, 0, 0, 0);
		this.tahun = tahun;
		this.bulan = bulan;
		this.tanggal = tanggal;
	}
	
	public DateTime(int tahun, int bulan, int tanggal, int jam, int menit, int detik) {
		initiate();
		calendar.set(tahun, (bulan-1), tanggal, jam, menit, detik);
		this.tahun = tahun;
		this.bulan = bulan;
		this.tanggal = tanggal;
		this.jam = jam;
		this.menit = menit;
		this.detik = detik;
	}
	
	public DateTime(String arg0) throws GeneralException {
		initiate();

		try {
			tanggal = Integer.parseInt(arg0.substring(0,2));
			bulan = Integer.parseInt(arg0.substring(3, 5));
			tahun = Integer.parseInt(arg0.substring(6, 10));
		} catch(Exception e) {
			throw new GeneralException("Format Tanggal Salah! Seharusnya : dd-MM-yyyy");
		}

		calendar.set(tahun, (bulan-1), tanggal, 0, 0, 0);
	}

	public DateTime(Date date) {
		initiate();
		calendar.setTimeInMillis(date.getTime());
	}
	
	public DateTime(Calendar calendar) {
		this.calendar = calendar;
	}
	
	private void initiate() {
		timeZone = TimeZone.getTimeZone("Asia/Makassar");
		calendar = Calendar.getInstance(timeZone);
	}

	/* Setter and Getter */
	public Calendar getCalendar() {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(getInMillis());
		return cal;
	}
	
	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
	}
	
	public TimeZone getTimeZone() {
		return timeZone;
	}
	
	public void setTimeZone(TimeZone timeZone) {
		this.timeZone = timeZone;
	}
	
	public int getTanggal() {
		return this.tanggal;
	}
	
	public int getBulan() {
		return this.bulan;
	}
	
	public int getTahun() {
		return this.tahun;
	}

	public int getJam() {
		return this.jam;
	}
	
	public int getMenit() {
		return this.menit;
	}
	
	public int getDetik() {
		return this.detik;
	}

	/* All Method */
	private long getInMillis(){
		//return calendar.getTimeInMillis() + 43200000; // tambah 12 jam, karena perbedaan waktu
		//return getInMillisReal() + 28800000; // tambah 8 jam, karena perbedaan waktu
		return getInMillisReal();
	}
	
	private long getInMillisReal(){
		return calendar.getTimeInMillis();
	}
	
	public java.sql.Time getTime() {
		return new java.sql.Time(getInMillis());
	}
	
	public Date getUtilDate() {
		return new Date(getInMillis());
	}
	
	public java.sql.Date getSqlDate() {
		return new java.sql.Date(getInMillis());
	}
	
	public long getTodayInMillis() {
		return getInMillis();
	}
	
	public long toMillis(Calendar cal) {
		cal.setTimeZone(timeZone);
		return cal.getTimeInMillis();
	}
	
	private void setAttribute() {
		tahun = calendar.get(Calendar.YEAR);
		bulan = calendar.get(Calendar.MONTH);
		tanggal = calendar.get(Calendar.DATE);
		jam = calendar.get(Calendar.HOUR_OF_DAY);
		menit = calendar.get(Calendar.MINUTE);
		detik = calendar.get(Calendar.SECOND);
	}
}
