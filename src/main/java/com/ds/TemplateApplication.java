package com.ds;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TemplateApplication implements CommandLineRunner {

	@Autowired
	private PdfCreator pdfCreator;

	public static void main(String[] args) {
		SpringApplication.run(TemplateApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String documentName = "FirstDoc.pdf";
		pdfCreator.createTemplate("Boas cú de frango", documentName);

		pdfCreator.update(documentName, "Tás mesmo bom?", "resultado.pdf");
	}
}
