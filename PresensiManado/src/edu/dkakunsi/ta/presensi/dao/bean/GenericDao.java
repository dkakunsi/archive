package edu.dkakunsi.ta.presensi.dao.bean;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import edu.dkakunsi.ta.presensi.exception.EntityException;
import edu.dkakunsi.ta.presensi.util.DateTime;

/**
 * Data access object digunakan untuk melakukan proses manipulasi data di dalam database
 * @author dkakunsi
 * @param <T> Kelas yang merepresentasikan Tabel di dalam database
 * @param <U> Kelas yang merepresentasikan Kelas Rekap
 */
public class GenericDao<T> {
	protected Class<T> domainClass;
	
	/**
	 * Object yang menciptakan session ke dalam database
	 */
	@Autowired protected SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	public GenericDao() {
		this.domainClass = (Class<T>)((ParameterizedType)
                getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
	}

	private Session getSession() {
		Session session = sessionFactory.getCurrentSession();
		if(!session.isOpen())
			session = sessionFactory.openSession();
		return session;
	}
	
	/**
	 * Menyimpan data ke dalam database, sesuai parameter T
	 * @param data Data yang akan disimpan
	 */
	public void save(T data) {
		getSession().saveOrUpdate(data);
	}

	/**
	 * Menghapus data di dalam database, sesuai parameter T
	 * @param data Data yang akan dihapus dari dalam database
	 */
	public void delete(T data) {
		getSession().delete(data);
	}
	
	/**
	 * Mengambil data yang berasosi dengan 'id' sebagai primary key
	 * dari dalam database, sesuai parameter T
	 * @param id Primary key data yang akan di ambil
	 * @return Data yang berasosiasi dengan 'id' sebagai primary key
	 */
	@SuppressWarnings("unchecked")
	public T get(Object id) throws EntityException {
		return checkNull((T)getSession().get(domainClass, (Serializable) id));
	}

	/**
	 * Mengambil semua data yang ada di dalam database, sesuai parameter T
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> getAll() throws EntityException {
		Query query = getSession().getNamedQuery("getAll" + domainClass.getSimpleName());
		
		return checkNull(query.list());
	}
	
	/**
	 * Mengambil semua data yang ada di dalam database menurut 'nip', sesuai parameter T
	 * @param nip Prameter pencarian
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> getByPegawai(String nip) throws EntityException {
		Query query = getSession().getNamedQuery("getByPegawai" + domainClass.getSimpleName());
		query.setParameter("nip", nip);

		return checkNull(query.list());
	}

	@SuppressWarnings("unchecked")
	public List<T> getByPegawai(String nip, Date tanggalMulai, Date tanggalSelesai) throws EntityException {
		Query query = getSession().getNamedQuery("getByPegawaiBulanan" + domainClass.getSimpleName());
		query.setParameter("nip", nip);
		query.setParameter("tanggalMulai", new DateTime(tanggalMulai).getSqlDate());
		query.setParameter("tanggalSelesai", new DateTime(tanggalSelesai).getSqlDate());

		return checkNull(query.list());
	}
	
	/**
	 * Mengambil semua data yang ada di dalam database menurut 'tanggal', sesuai parameter T
	 * @param tanggal Parameter pencarian
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> getByTanggal(Date tanggal) throws EntityException {
		Query query = getSession().getNamedQuery("getByTanggal" + domainClass.getSimpleName());
		query.setParameter("tanggal", new DateTime(tanggal).getSqlDate());

		return checkNull(query.list());
	}

	@SuppressWarnings("unchecked")
	public List<T> getByDepartment(String kode)
			throws EntityException {
		Query query = getSession().getNamedQuery("getByDepartment" + domainClass.getSimpleName());
		query.setParameter("department", kode);

		return checkNull(query.list());
	}

	@SuppressWarnings("unchecked")
	public List<T> getByDepartment(String kode, Date tanggal)
			throws EntityException {
		Query query = getSession().getNamedQuery("getByDepartment&Tanggal" + domainClass.getSimpleName());
		query.setParameter("department", kode);
		query.setParameter("tanggal", new DateTime(tanggal).getSqlDate());

		return checkNull(query.list());
	}

	@SuppressWarnings("unchecked")
	public List<T> getByDepartment(String kode, Date tanggalMulai, Date tanggalSelesai)
			throws EntityException {
		Query query = getSession().getNamedQuery("getByDepartmentBulanan" + domainClass.getSimpleName());
		query.setParameter("department", kode);
		query.setParameter("tanggalMulai", new DateTime(tanggalMulai).getSqlDate());
		query.setParameter("tanggalSelesai", new DateTime(tanggalSelesai).getSqlDate());

		return checkNull(query.list());
	}

	protected T checkNull(T entity) throws EntityException {
		if(entity == null)
			throw new EntityException("Entitas '" + domainClass.getSimpleName() + "' tidak ditemukan di dalam basis data");
		return entity;
	}
	
	protected List<T> checkNull(List<T> ls) throws EntityException {
		if(ls == null)
			throw new EntityException("Entitas '" + domainClass.getSimpleName() + "' tidak ditemukan di dalam basis data");
		return ls;
	}
}
