package com.unitedvision.sangihe.document.test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfWriter;
import com.unitedvision.sangihe.monev.configuration.ApplicationConfig;
import com.unitedvision.sangihe.monev.document.pdf.RekapProgramPdfView;
import com.unitedvision.sangihe.monev.entity.RekapProgram;
import com.unitedvision.sangihe.monev.service.ProgramService;

public class RekapProgramPdfViewTest extends RekapProgramPdfView {

	private static RekapProgramPdfViewTest pdf = new RekapProgramPdfViewTest();
	private static ApplicationContext appContext = new AnnotationConfigApplicationContext(ApplicationConfig.class);
	private static ProgramService programService = appContext.getBean(ProgramService.class);
	
	public static void main(String[] args) {
        Document document = pdf.newDocument();

        try {
            PdfWriter.getInstance(document, new FileOutputStream("E:\\rekap-program.pdf"));

            document.open();

            List<RekapProgram> list = programService.rekap(2015L);

            Map<String, Object> model = new HashMap<>();
            model.put("rekap", list);
            model.put("tahun", 2015);
            
            pdf.create(model, document);
            document.close();

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
			e.printStackTrace(); 
		}
        
        System.out.println("DONE...");
	}
	
	@Override
	protected Document newDocument() {
		return super.newDocument();
	}
	
}
