package com.xmyy.common.util;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Function;

public class ExportExcel {

	public static <T> void exportXlsx(HttpServletResponse response, String excelName, String sheetName, List<T> datas,
			LinkedHashMap<String, Function<T, ?>> cells)
			throws IOException {
		response.setContentType("octets/stream");
		// response.addHeader("Content-Disposition", "attachment;filename=test.xls");
		// 转码防止乱码
		String fileName;
		try {
			fileName = new String(excelName.getBytes("utf-8"), "ISO8859-1") + ".xlsx";
		} catch (UnsupportedEncodingException e) {
			fileName = new Date().getTime() + ".xlsx";
		}
		response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
		ExcelWirte.wirteXlsxOutPuts(sheetName != null ? sheetName
				: excelName, datas, response.getOutputStream(), cells);
	}

	public static <T> void exportXls(HttpServletResponse response, String excelName, String sheetName, List<T> datas,
			LinkedHashMap<String, Function<T, ?>> cells)
			throws IOException {
		response.setContentType("octets/stream");
		// response.addHeader("Content-Disposition", "attachment;filename=test.xls");
		// 转码防止乱码
		String fileName;
		try {
			fileName = new String(excelName.getBytes("utf-8"), "ISO8859-1") + ".xls";
		} catch (UnsupportedEncodingException e) {
			fileName = new Date().getTime() + ".xls";
		}
		response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
		ExcelWirte.wirteXlsOutPuts(sheetName != null ? sheetName : excelName, datas, response.getOutputStream(), cells);
	}
}
