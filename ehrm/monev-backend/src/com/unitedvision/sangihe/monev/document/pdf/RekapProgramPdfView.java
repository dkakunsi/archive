package com.unitedvision.sangihe.monev.document.pdf;

import java.awt.Color;
import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPTable;
import com.unitedvision.sangihe.monev.entity.RekapProgram;
import com.unitedvision.sangihe.monev.util.DateUtil;

public class RekapProgramPdfView extends CustomAbstractPdfView {

	@Override
	public Document create(Map<String, Object> model, Document doc) throws DocumentException {
		@SuppressWarnings("unchecked")
		List<RekapProgram> list = (List<RekapProgram>) model.get("rekap");
		Long tahun = (Long) model.get("tahun");
		
		doc.newPage();
		
		createTitle(doc);
		addEmptyLine(doc, 1);
		
		createSubTitle(doc, tahun);
		addEmptyLine(doc, 1);
		
		createContent(doc, list);
		addEmptyLine(doc, 2);
		
		return doc;
	}

	@Override
	protected void createTitle(Document doc) throws DocumentException {
		Paragraph title = new Paragraph();
		title.add(new Paragraph("Pemerintah Kabupaten Kepulauan Sangihe", fontTitle));
		title.add(new Paragraph("Rekap Program Satuan Kerja", fontSubTitle));
		title.setAlignment(Element.ALIGN_CENTER);
		
		doc.add(title);
	}

	protected void createSubTitle(Document doc, Long tahun) throws DocumentException {
		Paragraph subTitle = new Paragraph();
		subTitle.setAlignment(Element.ALIGN_CENTER);
		
		float[] columnWidth = {12};
		
		PdfPTable table = new PdfPTable(columnWidth);
		table.setWidthPercentage(tablePercentage);
		
		insertCell(table, String.format("Tahun: %d", tahun), align, 1, fontHeader, Rectangle.BOX, Color.WHITE, null);
		Date tanggalCetak = DateUtil.getDate();
		insertCell(table, String.format("Tanggal Cetak: %s", DateUtil.toFormattedStringDate(tanggalCetak, "-")), align, 1, fontHeader, Rectangle.BOX, Color.WHITE, null);
		
		subTitle.add(table);
		
		doc.add(subTitle);
	}

	@Override
	protected void createContent(Document doc, List<?> list) throws DocumentException {
		
		Paragraph content = new Paragraph();
		content.setAlignment(Element.ALIGN_CENTER);
		
		// 8 Kolom
		float[] columnWidth = {7, 5, 5, 3};

		PdfPTable table = new PdfPTable(columnWidth);
		table.setWidthPercentage(tablePercentage);

		insertCell(table, "Nama Program", align, 1, fontHeader, Rectangle.BOX, null, null);
		insertCell(table, "Pagu Anggaran", align, 1, fontHeader, Rectangle.BOX, null, null);
		insertCell(table, "Realisasi Anggaran", align, 1, fontHeader, Rectangle.BOX, null, null);
		insertCell(table, "Realisasi Fisik", align, 1, fontHeader, Rectangle.BOX, null, null);
		table.setHeaderRows(1);
		
		String deter = null;
		for (Object object : list) {

			RekapProgram rekap = (RekapProgram)object;
			String namaUnitKerja = rekap.getNamaUnitKerja();
			
			if ( !namaUnitKerja.equals(deter) ) {
				deter = namaUnitKerja;
				insertCell(table, deter, align, 4, fontHeader, Rectangle.BOX, null, Color.GRAY);
			}

			insertCell(table, rekap.getNamaProgram(), align, 1, fontContent, Rectangle.BOX, null, null);
			insertCell(table, Long.toString(rekap.getPaguAnggaran()), align, 1, fontContent, Rectangle.BOX, null, null);
			insertCell(table, Long.toString(rekap.getRealisasiAnggaran()), align, 1, fontContent, Rectangle.BOX, null, null);
			insertCell(table, Integer.toString(rekap.getRealisasiFisik()), align, 1, fontContent, Rectangle.BOX, null, null);
		}

		content.add(table);
		
		doc.add(content);
	}
	
	@Override
	protected Document newDocument() {
		return new Document(PageSize.A4.rotate(), 0.1f, 0.1f, 0.1f, 0.1f);
	}
}
