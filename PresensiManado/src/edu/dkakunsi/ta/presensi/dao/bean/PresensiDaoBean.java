package edu.dkakunsi.ta.presensi.dao.bean;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import edu.dkakunsi.ta.presensi.dao.PresensiDao;
import edu.dkakunsi.ta.presensi.exception.EntityException;
import edu.dkakunsi.ta.presensi.model.entity.Presensi;

@Repository("presensiDao")
public class PresensiDaoBean extends GenericDao<Presensi> implements PresensiDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Presensi> getAllEmptyPulang() throws EntityException {
		Query query = sessionFactory.getCurrentSession().getNamedQuery("getNullInKeluar");

		return checkNull(query.list());
	}
}
