package top.ibase4j.core.support.excel;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author ShenHuaJie
 *
 */
public class Excel2007Reader extends DefaultHandler {
    private static Logger logger = LoggerFactory.getLogger(Excel2007Reader.class);
    private IRowReader rowReader;

    public void setRowReader(IRowReader rowReader) {
        this.rowReader = rowReader;
    }

    public Excel2007Reader() {
    }

    public Excel2007Reader(IRowReader rowReader) {
        this.rowReader = rowReader;
    }

    private SharedStringsTable sst;
    private String lastContents;
    private boolean nextIsString;

    private int sheetIndex = -1;
    private List<String> rowlist = new ArrayList<String>();
    private int curRow = 0;
    private int curCol = 0;

    // 只遍历一个sheet，其中sheetId为要遍历的sheet索引，从1开始，1-3
    public void processOneSheet(String filename, int sheetId) throws Exception {
        OPCPackage pkg = OPCPackage.open(filename);
        XSSFReader r = new XSSFReader(pkg);
        SharedStringsTable sst = r.getSharedStringsTable();

        XMLReader parser = fetchSheetParser(sst);

        // rId2 found by processing the Workbook
        // 根据 rId# 或 rSheet# 查找sheet
        InputStream sheet2 = r.getSheet("rId" + sheetId);
        sheetIndex++;
        InputSource sheetSource = new InputSource(sheet2);
        parser.parse(sheetSource);
        sheet2.close();
    }

    /** 
     * 遍历 excel 文件 
     */
    public void process(InputStream stream) throws Exception {
        OPCPackage pkg = OPCPackage.open(stream);
        XSSFReader r = new XSSFReader(pkg);
        SharedStringsTable sst = r.getSharedStringsTable();

        XMLReader parser = fetchSheetParser(sst);

        Iterator<InputStream> sheets = r.getSheetsData();
        while (sheets.hasNext()) {
            curRow = 0;
            sheetIndex++;
            InputStream sheet = sheets.next();
            InputSource sheetSource = new InputSource(sheet);
            parser.parse(sheetSource);
            sheet.close();
        }
    }

    /** 
     * 遍历 excel 文件 
     */
    public void process(String filename) throws Exception {
        OPCPackage pkg = OPCPackage.open(filename);
        XSSFReader r = new XSSFReader(pkg);
        SharedStringsTable sst = r.getSharedStringsTable();

        XMLReader parser = fetchSheetParser(sst);

        Iterator<InputStream> sheets = r.getSheetsData();
        while (sheets.hasNext()) {
            curRow = 0;
            sheetIndex++;
            InputStream sheet = sheets.next();
            InputSource sheetSource = new InputSource(sheet);
            parser.parse(sheetSource);
            sheet.close();
        }
    }

    public XMLReader fetchSheetParser(SharedStringsTable sst) {
        try {
            XMLReader parser = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
            this.sst = sst;
            parser.setContentHandler(this);
            return parser;
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
    }

    public void startElement(String uri, String localName, String name, Attributes attributes) {
        // c => 单元格
        if ("c".equals(name)) {
            // 如果下一个元素是 SST 的索引，则将nextIsString标记为true
            String cellType = attributes.getValue("t");
            if (cellType != null && "s".equals(cellType)) {
                nextIsString = true;
            } else {
                nextIsString = false;
            }
        }
        // 置空
        lastContents = "";
    }

    public void endElement(String uri, String localName, String name) {
        // 根据SST的索引值的到单元格的真正要存储的字符串
        // 这时characters()方法可能会被调用多次
        if (nextIsString && StringUtils.isNumeric(lastContents)) {
            try {
                int idx = Integer.parseInt(lastContents);
                lastContents = new XSSFRichTextString(sst.getEntryAt(idx)).toString();
            } catch (Exception e) {
                logger.error("", e);
            }
        }

        // v => 单元格的值，如果单元格是字符串则v标签的值为该字符串在SST中的索引
        // 将单元格内容加入rowlist中，在这之前先去掉字符串前后的空白符
        if ("v".equals(name)) {
            String value = lastContents.trim();
            value = "".equals(value) ? " " : value;
            rowlist.add(curCol, value);
            curCol++;
        } else {
            // 如果标签名称为 row ，这说明已到行尾，调用 optRows() 方法
            if ("row".equals(name)) {
                if (rowReader != null && !rowlist.isEmpty()) { // 每行结束时， 调用getRows() 方法
                    try {
                        rowReader.getRows(sheetIndex, curRow, rowlist);
                    } catch (Exception e) {
                        logger.error("", "", e);
                    }
                }
                rowlist.clear();
                curRow++;
                curCol = 0;
            }
        }
    }

    public void characters(char[] ch, int start, int length) {
        // 得到单元格内容的值
        lastContents += new String(ch, start, length);
    }
}
