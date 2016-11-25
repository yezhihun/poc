package com.yingu.poc.common.utils;

import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

public class ExcelUtil {

	public static XSSFWorkbook exportExcel(String title, String[] headers,
			String[] column, List<Map<String, Object>> data) {
		// 声明一个工作薄
		XSSFWorkbook workbook = new XSSFWorkbook();
		try {
			// 生成一个样式
			XSSFCellStyle style = workbook.createCellStyle();
			// 设置这些样式
			style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
			style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
			style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			style.setBorderRight(XSSFCellStyle.BORDER_THIN);
			style.setBorderTop(XSSFCellStyle.BORDER_THIN);
			style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
			// 生成一个字体
			XSSFFont font = workbook.createFont();
			font.setColor(HSSFColor.VIOLET.index);
			font.setFontHeightInPoints((short) 12);
			font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
			// 把字体应用到当前的样式
			style.setFont(font);
			// 生成并设置另一个样式
			XSSFCellStyle style2 = workbook.createCellStyle();
			style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
			style2.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
			style2.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			style2.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			style2.setBorderRight(XSSFCellStyle.BORDER_THIN);
			style2.setBorderTop(XSSFCellStyle.BORDER_THIN);
			style2.setAlignment(XSSFCellStyle.ALIGN_CENTER);
			style2.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
			// 生成另一个字体
			XSSFFont font2 = workbook.createFont();
			font2.setBoldweight(XSSFFont.BOLDWEIGHT_NORMAL);
			// 把字体应用到当前的样式
			style2.setFont(font2);
			
			int dataSize = data.size();
			int headerSize = column.length;
//			int sheetCount = dataSize/SHEET_SIZE + (dataSize%SHEET_SIZE==0?0:1);
			
			// 生成一个表格
			XSSFSheet sheet = workbook.createSheet(title);
			// 设置表格默认列宽度为15个字节
			sheet.setDefaultColumnWidth((short) 15);
			// 声明一个画图的顶级管理器
			XSSFDrawing patriarch = sheet.createDrawingPatriarch();
			// 定义注释的大小和位置,详见文档
			XSSFComment comment = patriarch.createCellComment(new XSSFClientAnchor(0,
					0, 0, 0, (short) 4, 2, (short) 6, 5));
			// 设置注释内容
			comment.setString(new XSSFRichTextString("可以在POI中添加注释！"));
			// 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
			comment.setAuthor("leno");
			
			
			// 产生表格标题行
			XSSFRow row = sheet.createRow(0);
			for (short i = 0; i < headers.length; i++) {
				XSSFCell cell = row.createCell(i);
				cell.setCellStyle(style);
				XSSFRichTextString text = new XSSFRichTextString(headers[i]);
				cell.setCellValue(text);
			}
	
			
			for(int i=0;i<dataSize;i++){
				row = sheet.createRow(i+1);
				Map<String,Object> map = data.get(i);
				
				for(int j=0;j<headerSize;j++){
					String key = column[j];
					Object val = map.get(key);
					XSSFCell cell = row.createCell(j);
					cell.setCellValue(val==null?"":val.toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return workbook;
	}
}
