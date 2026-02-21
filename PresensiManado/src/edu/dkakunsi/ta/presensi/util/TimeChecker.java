package edu.dkakunsi.ta.presensi.util;

import java.sql.Time;
import java.util.Calendar;

import edu.dkakunsi.ta.presensi.exception.GeneralException;
import edu.dkakunsi.ta.presensi.exception.PulangException;

/**
 * Kelas untuk proses pengecekan batas waktu
 * @author dkakunsi
 *
 */
public class TimeChecker {

	private static Calendar getWaktuDatang() {
		Calendar cal = new DateTime().getCalendar();
		cal.set(Calendar.HOUR_OF_DAY, 7);
		cal.set(Calendar.MINUTE, 30);
		cal.set(Calendar.SECOND, 0);

		return cal;
	}
	
	private static Calendar getWaktuPulang() {
		Calendar cal = new DateTime().getCalendar();
		
		cal.set(Calendar.HOUR_OF_DAY, 16);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);

		return cal;
	}
	
	public static long getWaktuDatangInMillis() {
		return getWaktuDatang().getTimeInMillis();
	}
	
	public static long getWaktuPulangInMillis() {
		return getWaktuPulang().getTimeInMillis();
	}
	
	public static Time getWaktuDatangInTime() {
		return new Time(getWaktuDatangInMillis());
	}
	
	public static Time getWaktuPulangInTime() {
		return new Time(getWaktuPulangInMillis());
	}
	
	/**
	 * Memastikan jam masuk. Terlambat atau tidak
	 * @param time Waktu datang
	 * @return Keterangan terlambat
	 */
	public static String checkWaktuDatang(Time time) {
		if(time.after(getWaktuDatangInTime()))			
			return "Anda Datang Terlambat";
		return "Selamat Datang dan Selamat Bekerja";
	}

	/**
	 * Memastikan jam pulang. Mendahului atau tidak
	 * @param time Waktu pulang
	 * @return Keterangan mendahului
	 * @throws PulangException 
	 * @throws GeneralException 
	 */
	public static String checkWaktuPulang(Time time) throws PulangException {
		if(time.before(getWaktuPulangInTime()))
			throw new PulangException("Maaf! Belum Jam Pulang");
		return "Terima Kasih!";
	}
}
