package com.xmyy.common.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;

public class ExcelWirte { 

	@FunctionalInterface
	public static interface WirteHead {
		public int createHead(Workbook workbook, Sheet sheet);

	} 

	@FunctionalInterface
	public static interface WirteRow<T> {
		public void wirteRow(Workbook workbook, Sheet sheet, Row row, T t);

	}

	public static int createHeadRow(Workbook workbook, Sheet sheet, String... titles) {
		return createHeadRow(0, workbook, sheet, titles);
	}

	public static int createHeadRow(int startRow, Workbook workbook, Sheet sheet, String... titles) {
		Row row = sheet.createRow(startRow);
		CellStyle cs = workbook.createCellStyle();
		Font f = workbook.createFont();
		// f.setColor((short) 0xE);
		f.setBold(true);
		cs.setFont(f);
		Cell cell = null;
		for (int i = 0; i < titles.length; i++) {
			String title = titles[i];
			cell = row.createCell(i);
			cell.setCellValue(title);
			cell.setCellStyle(cs);
		}

		return startRow + 1;
	}

	@SafeVarargs
	public static <T> void wirteRow(Workbook workbook, Sheet sheet, Row row, T t, Function<T, ?>... cells) {
		wirteRow(workbook, sheet, row, null, t, cells);
	}

	@SafeVarargs
	public static <T> void wirteRow(Workbook workbook, Sheet sheet, Row row, CellStyle[] cs, T t,
			Function<T, ?>... cells) {
		Cell cell = null;
		SimpleDateFormat simpleDateFormat = null;
		for (int i = 0; i < cells.length; i++) {
			Function<T, ?> c = cells[i];
			cell = row.createCell(i);
			CellStyle cellStyle = null;
			if (cs != null && cs.length > i) {
				if (cs[i] != null) {
					cellStyle = cs[i];
					cell.setCellStyle(cellStyle);
				}
			}
			Object v = c.apply(t);
			if (v != null) {
				if (v instanceof Double) {
					cell.setCellValue((double) v);
				} else if (v instanceof Boolean) {
					cell.setCellValue((boolean) v);
				} else if (v instanceof Calendar) {
					cell.setCellValue((Calendar) v);
				} else if (v instanceof Date) {
					if (cellStyle == null) {
						if (simpleDateFormat == null) {
							simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						}
						cell.setCellValue(simpleDateFormat.format((Date) v).replaceAll("(0000-00-00)|( 00:00:00)", ""));
					} else {
						cell.setCellValue((Date) v);
					}
				} else if (v instanceof String) {
					cell.setCellValue((String) v);
				} else if (v instanceof RichTextString) {
					cell.setCellValue((RichTextString) v);
				} else {
					cell.setCellValue(String.valueOf(v));
				}
			}
		}

	}

	@SafeVarargs
	public static <T> void wirteOutPut(String sheetName, List<T> datas, Workbook workbook, OutputStream out,
			String[] titles,
			Function<T, ?>... cells) throws IOException {
		wirteOutPut(sheetName, datas, workbook, out, (Workbook wb, Sheet sheet) -> {
			return createHeadRow(wb, sheet, titles);
		}, (Workbook wb, Sheet sheet, Row row, T t) -> {
			wirteRow(wb, sheet, row, t, cells);
		});
	}

	@SuppressWarnings("unchecked")
	public static <T> void wirteOutPuts(String sheetName, List<T> datas, String outFilePath,
			LinkedHashMap<String, Function<T, ?>> cells) throws IOException {
		String[] titles = new String[cells.size()];
		Function<T, Object>[] cs = new Function[cells.size()];
		Iterator<String> iterator = cells.keySet().iterator();
		int i = 0;
		while (iterator.hasNext()) {
			titles[i] = (String) iterator.next();
			cs[i] = (Function<T, Object>) cells.get(titles[i]);
			i++;
		}
		wirteOutPut(sheetName, datas, outFilePath, (Workbook wb, Sheet sheet) -> {
			return createHeadRow(wb, sheet, titles);
		}, (Workbook wb, Sheet sheet, Row row, T t) -> {
			wirteRow(wb, sheet, row, t, cs);
		});
	}

	public static <T> void wirteXlsOutPuts(String sheetName, List<T> datas, OutputStream out,
			LinkedHashMap<String, Function<T, ?>> cells) throws IOException {
		Workbook workbook = new HSSFWorkbook();
		wirteOutPuts(sheetName, datas, workbook, out, cells);

	}

	public static <T> void wirteXlsxOutPuts(String sheetName, List<T> datas, OutputStream out,
			LinkedHashMap<String, Function<T, ?>> cells) throws IOException {
		Workbook workbook = new XSSFWorkbook();
		wirteOutPuts(sheetName, datas, workbook, out, cells);

	}

	@SuppressWarnings("unchecked")
	public static <T> void wirteOutPuts(String sheetName, List<T> datas, Workbook workbook, OutputStream out,
			LinkedHashMap<String, Function<T, ?>> cells) throws IOException {
		String[] titles = new String[cells.size()];
		Function<T, Object>[] cs = new Function[cells.size()];
		Iterator<String> iterator = cells.keySet().iterator();
		int i = 0;
		while (iterator.hasNext()) {
			titles[i] = (String) iterator.next();
			cs[i] = (Function<T, Object>) cells.get(titles[i]);
			i++;
		}
		wirteOutPut(sheetName, datas, workbook, out, (Workbook wb, Sheet sheet) -> {
			return createHeadRow(wb, sheet, titles);
		}, (Workbook wb, Sheet sheet, Row row, T t) -> {
			wirteRow(wb, sheet, row, t, cs);
		});
	}

	@SafeVarargs
	public static <T> void wirteOutPut(String sheetName, List<T> datas, String outFilePath, String[] titles,
			Function<T, ?>... cells) throws IOException {
		wirteOutPut(sheetName, datas, outFilePath, (Workbook wb, Sheet sheet) -> {
			return createHeadRow(wb, sheet, titles);
		}, (Workbook wb, Sheet sheet, Row row, T t) -> {
			wirteRow(wb, sheet, row, t, cells);
		});
	}

	public static <T> void wirteOutPut(String name, List<T> datas, String outFilePath, WirteHead wirteHead,
			WirteRow<T> wirteRow) throws IOException {
		FileOutputStream out = new FileOutputStream(outFilePath);

		Workbook wb = null;
		if (outFilePath.endsWith(".xls")) {
			wb = new HSSFWorkbook();
		} else if (outFilePath.endsWith(".xlsx")) {
			wb = new XSSFWorkbook();
		}
		wirteOutPut(name, datas, wb, out, wirteHead, wirteRow);
		out.close();
	}

	public static <T> void wirteOutPut(String sheetName, List<T> datas, Workbook wb, OutputStream out,
			WirteHead wirteHead,
			WirteRow<T> wirteRow) throws IOException {
		try {

			createSheet(wb, sheetName, datas, wirteHead, wirteRow);
			wb.write(out);
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			if (wb != null)
				wb.close();
		}
	}

	private static <T> void createSheet(Workbook wb, String sheetName, List<T> datas, WirteHead wirteHead,
			WirteRow<T> wirteRow) {
		Sheet sheet = wb.createSheet();
		wb.setSheetName(0, sheetName);
		Row r = null;

		int rownum = 0;
		int start = 0;
		if (wirteHead != null) {
			start = wirteHead.createHead(wb, sheet);
		}
		if (wirteRow != null) {
			for (rownum = 0; rownum < datas.size(); rownum++) {
				r = sheet.createRow(rownum + start);
				wirteRow.wirteRow(wb, sheet, r, datas.get(rownum));
			}
		} else {
			throw new RuntimeException("未实现！");
		}

	}
}
