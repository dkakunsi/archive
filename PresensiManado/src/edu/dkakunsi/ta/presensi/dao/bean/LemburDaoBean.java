package edu.dkakunsi.ta.presensi.dao.bean;

import org.springframework.stereotype.Repository;

import edu.dkakunsi.ta.presensi.dao.LemburDao;
import edu.dkakunsi.ta.presensi.model.entity.Lembur;

@Repository("lemburDao")
public class LemburDaoBean extends GenericDao<Lembur> implements LemburDao {

}
