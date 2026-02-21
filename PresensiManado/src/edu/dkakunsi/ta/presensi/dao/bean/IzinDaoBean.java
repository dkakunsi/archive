package edu.dkakunsi.ta.presensi.dao.bean;

import org.springframework.stereotype.Repository;

import edu.dkakunsi.ta.presensi.dao.IzinDao;
import edu.dkakunsi.ta.presensi.model.entity.Izin;

@Repository("izinDao")
public class IzinDaoBean extends GenericDao<Izin> implements IzinDao {

}
