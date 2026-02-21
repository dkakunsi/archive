package com.unitedvision.sangihe.monev.document.pdf;

import java.awt.Color;
import java.sql.Date;
import java.time.Month;
import java.util.List;
import java.util.Map;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPTable;
import com.unitedvision.sangihe.monev.entity.RekapKegiatan;
import com.unitedvision.sangihe.monev.util.DateUtil;

public class RekapKegiatanPdfView extends CustomAbstractPdfView {

	@Override
	public Document create(Map<String, Object> model, Document doc) throws DocumentException {
		@SuppressWarnings("unchecked")
		List<RekapKegiatan> list = (List<RekapKegiatan>) model.get("rekap");
		Long tahun = (Long) model.get("tahun");
		Month bulan = (Month) model.get("bulan");
		
		doc.newPage();
		
		createTitle(doc);
		addEmptyLine(doc, 1);
		
		createSubTitle(doc, tahun, bulan);
		addEmptyLine(doc, 1);
		
		createContent(doc, list);
		addEmptyLine(doc, 2);
		
		return doc;
	}

	@Override
	protected void createTitle(Document doc) throws DocumentException {
		Paragraph title = new Paragraph();
		title.add(new Paragraph("Pemerintah Kabupaten Kepulauan Sangihe", fontTitle));
		title.add(new Paragraph("Rekap Kegiatan Satuan Kerja", fontSubTitle));
		title.setAlignment(Element.ALIGN_CENTER);
		
		doc.add(title);
	}

	protected void createSubTitle(Document doc, Long tahun, Month bulan) throws DocumentException {
		Paragraph subTitle = new Paragraph();
		subTitle.setAlignment(Element.ALIGN_CENTER);
		
		float[] columnWidth = {5, 5};
		
		PdfPTable table = new PdfPTable(columnWidth);
		table.setWidthPercentage(tablePercentage);
		
		insertCell(table, String.format("Periode : %s %d", bulan, tahun), align, 1, fontHeader, Rectangle.BOX, Color.WHITE, null);
		Date tanggalCetak = DateUtil.getDate();
		insertCell(table, String.format("Tanggal Cetak: %s", DateUtil.toFormattedStringDate(tanggalCetak, "-")), Element.ALIGN_RIGHT, 1, fontHeader, Rectangle.BOX, Color.WHITE, null);
		
		subTitle.add(table);
		
		doc.add(subTitle);
	}

	@Override
	protected void createContent(Document doc, List<?> list) throws DocumentException {
		
		Paragraph content = new Paragraph();
		content.setAlignment(Element.ALIGN_CENTER);
		
		// 8 Kolom
		float[] columnWidth = {7, 5, 5, 5, 5, 3};

		PdfPTable table = new PdfPTable(columnWidth);
		table.setWidthPercentage(tablePercentage);

		insertCell(table, "Nama Kegiatan", align, 1, fontHeader, Rectangle.BOX, null, null);
		insertCell(table, "Pagu Anggaran", align, 1, fontHeader, Rectangle.BOX, null, null);
		insertCell(table, "Rencana Anggaran", align, 1, fontHeader, Rectangle.BOX, null, null);
		insertCell(table, "Realisasi Anggaran", align, 1, fontHeader, Rectangle.BOX, null, null);
		insertCell(table, "Deviasi Anggaran", align, 1, fontHeader, Rectangle.BOX, null, null);
		insertCell(table, "Realisasi Fisik", align, 1, fontHeader, Rectangle.BOX, null, null);
		table.setHeaderRows(1);
		
		String deterUnitKerja = null;
		String deterProgram = null;
		String deterSubProgram = null;
		for (Object object : list) {

			RekapKegiatan rekap = (RekapKegiatan)object;
			String unitKerja = rekap.getUnitKerja();
			String program = rekap.getProgram();
			String subProgram = rekap.getSubProgram();
			
			if ( unitKerja != null && !unitKerja.equals(deterUnitKerja) ) {
				deterUnitKerja = unitKerja;
				insertCell(table, deterUnitKerja, align, 6, fontHeader, Rectangle.BOX, null, Color.GRAY);
			}
			
			if ( program != null && !program.equals(deterProgram) ) {
				deterProgram = program;
				insertCell(table, deterProgram, align, 6, fontContent, Rectangle.BOX, null, Color.YELLOW);
			}

			if ( subProgram != null && !subProgram.equals(deterSubProgram) ) {
				deterSubProgram = subProgram;
				insertCell(table, deterSubProgram, align, 6, fontContent, Rectangle.BOX, null, Color.GREEN);
			}

			insertCell(table, rekap.getKegiatan(), align, 1, fontContent, Rectangle.BOX, null, null);
			insertCell(table, String.format("Rp %s", (rekap.getPaguAnggaran() != null ? rekap.getPaguAnggaran() : "-")), align, 1, fontContent, Rectangle.BOX, null, null);
			insertCell(table, String.format("Rp %s", (rekap.getRencanaAnggaran() != null ? rekap.getRencanaAnggaran() : "-")), align, 1, fontContent, Rectangle.BOX, null, null);
			insertCell(table, String.format("Rp %s", (rekap.getRealisasiAnggaran() != null ? rekap.getRealisasiAnggaran() : "-")), align, 1, fontContent, Rectangle.BOX, null, null);
			insertCell(table, String.format("Rp %s", (rekap.getDeviasiAnggaran() != null ?rekap.getDeviasiAnggaran() : "-")), align, 1, fontContent, Rectangle.BOX, null, null);
			insertCell(table, String.format("%s %s", (rekap.getRealisasiFisik() != null ? rekap.getRealisasiFisik() : "-"), "%"), align, 1, fontContent, Rectangle.BOX, null, null);
		}

		content.add(table);
		
		doc.add(content);
	}
	
	@Override
	protected Document newDocument() {
		return new Document(PageSize.A4.rotate(), 0.1f, 0.1f, 0.1f, 0.1f);
	}
}
