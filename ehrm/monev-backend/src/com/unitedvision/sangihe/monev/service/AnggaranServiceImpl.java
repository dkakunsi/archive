package com.unitedvision.sangihe.monev.service;

import java.time.Month;
import java.util.List;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unitedvision.sangihe.monev.entity.Anggaran;
import com.unitedvision.sangihe.monev.entity.Kegiatan;
import com.unitedvision.sangihe.monev.exception.AnggaranException;
import com.unitedvision.sangihe.monev.exception.WrongYearException;
import com.unitedvision.sangihe.monev.repository.AnggaranRepository;
import com.unitedvision.sangihe.monev.repository.KegiatanRepository;

@Service
@Transactional(readOnly = true)
public class AnggaranServiceImpl implements AnggaranService {

	@Autowired
	private AnggaranRepository anggaranRepository;
	@Autowired
	private KegiatanRepository kegiatanRepository;
	
	@Override
	@Transactional(readOnly = false)
	public Anggaran simpan(Anggaran anggaran) throws AnggaranException, WrongYearException {
		validate(anggaran);
		
		return anggaranRepository.save(anggaran);
	}
	
	@Override
	@Transactional(readOnly = false)
	public Anggaran simpan(Anggaran anggaran, Long idKegiatan) throws AnggaranException, WrongYearException {
		Kegiatan kegiatan = kegiatanRepository.findOne(idKegiatan);
		anggaran.setKegiatan(kegiatan);
		
		return simpan(anggaran);
	}

	@Override
	@Transactional(readOnly = false)
	public Anggaran realisasi(Long id, Long jumlahRealisasi) throws AnggaranException, WrongYearException {
		Anggaran anggaran = get(id);
		anggaran.setRealisasi(jumlahRealisasi);

		validate(anggaran);
		
		anggaranRepository.updateRealisasi(id, jumlahRealisasi);
		
		return anggaran;
	}
	
	private Anggaran validate(Anggaran anggaran) throws AnggaranException, WrongYearException {
		anggaran.validate();

		Long totalRencana = 0L;
		Long totalRealisasi = 0L;

		try {
			for (Anggaran a : getByKegiatan(anggaran.getKegiatan().getId())) {
				totalRencana += a.getRencana();
				totalRealisasi += a.getRealisasi();
			}
		} catch (PersistenceException e) { }
		
		if (totalRencana + anggaran.getRencana() > anggaran.getKegiatan().getPaguAnggaran())
			throw new AnggaranException("Total rencana anggaran melebihi anggaran kegiatan");
		
		if (totalRealisasi + anggaran.getRealisasi() > anggaran.getKegiatan().getPaguAnggaran())
			throw new AnggaranException("Total realisasi anggaran melebihi anggaran kegiatan");
		
		return anggaran;
	}

	@Override
	@Transactional(readOnly = false)
	public void hapus(Long id) {
		anggaranRepository.delete(id);
	}

	@Override
	public Anggaran get(Long id) {
		return anggaranRepository.findOne(id);
	}

	@Override
	public Anggaran get(Long idKegiatan, Integer tahun, Month bulan) {
		return anggaranRepository.findByKegiatan_IdAndTahunAndBulan(idKegiatan, tahun, bulan);
	}

	@Override
	public List<Anggaran> getByProgram(Long id) {
		return anggaranRepository.findByKegiatan_Program_Id(id);
	}

	@Override
	public List<Anggaran> getByKegiatan(Long id) {
		return anggaranRepository.findByKegiatan_Id(id);
	}

}
