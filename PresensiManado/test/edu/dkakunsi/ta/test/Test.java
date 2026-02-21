package edu.dkakunsi.ta.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.dkakunsi.ta.presensi.util.DateTime;

public class Test {
	@SuppressWarnings("unused")
	private static ApplicationContext appContext;

	public static void main(String[] args) {
        appContext = new ClassPathXmlApplicationContext("AppContext.xml");

		DateTime dt = new DateTime(2013, 10, 7);
		System.out.print(dt.getTanggal()+"-");
		System.out.print(dt.getBulan()+"-");
		System.out.print(dt.getTahun());
        /*
		try {
			//System.out.println(pegawai.get("09002"));
			//System.out.println(presensi.getByDepartment("SKPD02", new DateTime("01-06-2013").getUtilDate(), new DateTime("30-06-2013").getUtilDate()).size());
			//System.out.println(presensi.getByTanggal(new DateTime("10-06-2013").getUtilDate()));
			//System.out.println(presensi.getByDepartment("skpd02", new DateTime("01-06-2013").getUtilDate(), new DateTime("31-06-2013").getUtilDate()).size());
			//System.out.println(new DateTime(2013, 10, 7).getUtilDate());
			//List<RekapPresensi> ls = presensi.rekap("skpd02", new DateTime(9, 6, 2013));
			//List<RekapPresensi> ls = presensi.rekap("skpd02", new DateTime("09-06-2013"));
			/*for(RekapPresensi pr : ls) {
				System.out.println(pr.getNip());
				System.out.println(pr.getNama());
				System.out.println(pr.getSkpd());
				System.out.println(pr.getJumlahPresensi());
			}*
			//System.out.println(presensi.rekap("skpd02", new DateTime("01-06-2013")).size());
			//throw new GeneralException();
		} catch (GeneralException | EntityException e) {
			e.getMessage();
			e.printStackTrace();
		}*/
	}
}
