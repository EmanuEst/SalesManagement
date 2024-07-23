package com.SalesManagement.SalesManagement.services;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HeaderFooter;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.SalesManagement.SalesManagement.dto.SaleItemsWithSalesDTO;
import com.SalesManagement.SalesManagement.entities.Product;
import com.SalesManagement.SalesManagement.repositories.ProductRepository;
import com.SalesManagement.SalesManagement.repositories.SaleItemRepository;
import com.SalesManagement.SalesManagement.repositories.SaleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportService {

    @Autowired
    private final SaleRepository saleRepository;

    @Autowired
    private final ProductRepository prodRepository;

    private final SaleItemRepository siRepository;

    public ByteArrayInputStream generateExcelFile(String sheetName, String[] headers, List<Object[]> data)
            throws IOException {
        ByteArrayOutputStream excelOutput = new ByteArrayOutputStream();
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(sheetName);

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.BLACK.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);
        headerStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        headerStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        headerStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        headerStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        headerStyle.setAlignment(HorizontalAlignment.CENTER);

        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        headerStyle.setFont(headerFont);

        CellStyle dataStyle = workbook.createCellStyle();
        dataStyle.setAlignment(HorizontalAlignment.CENTER);
        dataStyle.setBorderTop(BorderStyle.THIN);
        dataStyle.setBorderBottom(BorderStyle.THIN);
        dataStyle.setBorderLeft(BorderStyle.THIN);
        dataStyle.setBorderRight(BorderStyle.THIN);
        dataStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        dataStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        dataStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        dataStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());

        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }

        int rowIdx = 1;
        for (Object[] rowData : data) {
            Row row = sheet.createRow(rowIdx++);
            for (int i = 0; i < rowData.length; i++) {
                Cell cell = row.createCell(i);
                if (rowData[i] instanceof String) {
                    cell.setCellValue((String) rowData[i]);
                } else if (rowData[i] instanceof Number) {
                    cell.setCellValue(((Number) rowData[i]).doubleValue());
                } else if (rowData[i] instanceof LocalDate) {
                    cell.setCellValue(((LocalDate) rowData[i]).format(DateTimeFormatter.ofPattern("MM/dd/YYYY")));
                }
                cell.setCellStyle(dataStyle);
            }
        }

        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
            int larguraAtual = sheet.getColumnWidth(i);
            sheet.setColumnWidth(i, (int) (larguraAtual * 1.5));
        }

        HeaderFooter header = sheet.getHeader();
        header.setRight("&\"Times New Roman,Bold\"&18 S.M.");
        header.setLeft("&\"Times New Roman\"&22 " + sheetName);

        HeaderFooter footer = sheet.getFooter();
        footer.setLeft("Download Date: " + new SimpleDateFormat("MM/dd/YYYY").format(new Date()));
        footer.setRight("Page &P of &N");

        PrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setFooterMargin(0.5);
        printSetup.setHeaderMargin(0.25);
        printSetup.setLandscape(true);
        printSetup.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);

        workbook.write(excelOutput);
        workbook.close();

        return new ByteArrayInputStream(excelOutput.toByteArray());
    }

    public ByteArrayInputStream productsExcel() throws IOException {

        List<Product> products = prodRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));

        String[] header = {
                "Product", "Description", "Price $", "Stock Quantity"
        };

        List<Object[]> data = products.stream()
                .map(product -> new Object[] {
                        product.getName(), product.getDescription(), product.getPrice(), product.getStockQuantity()
                })
                .toList();

        return generateExcelFile("Products", header, data);
    }

    public ByteArrayInputStream saleItemsExcel(Pageable pageable) throws IOException {
        Page<SaleItemsWithSalesDTO> saleItemsPage = siRepository.getAllSaleItems(pageable);
        List<SaleItemsWithSalesDTO> saleItems = saleItemsPage.getContent();

        String[] header = {
                "Product", "Description", "Price $", "Sale Date"
        };

        List<Object[]> data = saleItems.stream()
                .map(saleItem -> new Object[] {
                        saleItem.product(), saleItem.description(), saleItem.price(), saleItem.saleDate()
                })
                .toList();

        return generateExcelFile("Sale Items", header, data);
    }

    public ByteArrayInputStream saleItemsPerPeriod(Pageable pageable, LocalDate initialDate, LocalDate finalDate)
            throws IOException {
        Page<SaleItemsWithSalesDTO> saleItemsPagePeriod = siRepository.getSaleItemsPerPeriod(pageable, initialDate,
                finalDate);
        List<SaleItemsWithSalesDTO> saleItemsPeriod = saleItemsPagePeriod.getContent();

        String[] header = {
                "Product", "Description", "Price $", "Sale Date"
        };

        List<Object[]> data = saleItemsPeriod.stream()
                .map(saleItemPeriod -> new Object[] {
                        saleItemPeriod.product(), saleItemPeriod.description(), saleItemPeriod.price(),
                        saleItemPeriod.saleDate()
                })
                .toList();

        return generateExcelFile("Sale Items - Period", header, data);
    }
}
