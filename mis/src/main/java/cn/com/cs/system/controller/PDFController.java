package cn.com.cs.system.controller;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

public class PDFController {
	// 利用模板生成pdf
	public static void fillTemplate() {
		// 模板路径
		String templatePath = "/Users/billy/Downloads/UA071_01.pdf";
		// 生成的新文件路径
		String newPDFPath = "/Users/billy/Downloads/03_1.pdf";
		try {
			FileOutputStream out = new FileOutputStream(newPDFPath);// 输出流
			PdfReader reader = new PdfReader(templatePath);// 读取pdf模板
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			PdfStamper stamper = new PdfStamper(reader, bos);
			AcroFields form = stamper.getAcroFields();

			BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);  
			System.out.println(form.setFieldProperty("appUserName", "Billy", bfChinese, null));;
			form.setField("appUserName", "Billy");
			form.setField("appBirthday", "2017-01-01");
			form.setField("appSex", "马里山");
			form.setField("applicationNumber", "62100000029123");
			
			stamper.setFormFlattening(true);// 如果为false那么生成的PDF文件还能编辑，一定要设为true
			stamper.close();

			Document doc = new Document();
			PdfCopy copy = new PdfCopy(doc, out);
			doc.open();
			PdfImportedPage importPage = copy.getImportedPage(new PdfReader(bos.toByteArray()), 1);
			copy.addPage(importPage);
			doc.close();
		} catch (IOException e) {
			System.out.println(1);
			e.printStackTrace();
		} catch (DocumentException e) {
			System.out.println(2);
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		fillTemplate();
	}
}
