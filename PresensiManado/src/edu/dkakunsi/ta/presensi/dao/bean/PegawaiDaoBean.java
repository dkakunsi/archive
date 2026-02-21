package edu.dkakunsi.ta.presensi.dao.bean;

import org.springframework.stereotype.Repository;

import edu.dkakunsi.ta.presensi.dao.PegawaiDao;
import edu.dkakunsi.ta.presensi.model.entity.Pegawai;

@Repository("pegawaiDao")
public class PegawaiDaoBean extends GenericDao<Pegawai> implements PegawaiDao {
}
