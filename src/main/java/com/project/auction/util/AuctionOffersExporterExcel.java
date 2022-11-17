package com.project.auction.util;

import com.project.auction.model.Item;
import com.project.auction.model.Person;
import com.project.auction.model.relation.AuctionOffer;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

public class AuctionOffersExporterExcel {

    private XSSFWorkbook book;
    private XSSFSheet sheetOffers;
    private XSSFSheet sheetItem;

    private Item item;
    private List<AuctionOffer> listAuctionOffers;

    public AuctionOffersExporterExcel(Item item) {
        this.item = item;
        if (item != null) {
            this.listAuctionOffers = item.getOffersInOrder();
        }
        book = new XSSFWorkbook();
        sheetOffers = book.createSheet("Offers");
        sheetItem = book.createSheet("Item");
    }

    private void writeTableHeaderOffers() {
        Row row = sheetOffers.createRow(0);

        CellStyle style = book.createCellStyle();
        XSSFFont font = book.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        Cell cell = row.createCell(0);
        cell.setCellValue("Position");
        cell.setCellStyle(style);

        cell = row.createCell(1);
        cell.setCellValue("Username");
        cell.setCellStyle(style);

        cell = row.createCell(2);
        cell.setCellValue("Email");
        cell.setCellStyle(style);

        cell = row.createCell(3);
        cell.setCellValue("Offer");
        cell.setCellStyle(style);
    }

    private void writeTableDataOffers() {
        int rowNumber = 1;

        CellStyle style = book.createCellStyle();
        XSSFFont font = book.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (AuctionOffer auctionOffer : this.listAuctionOffers) {
            Row row = sheetOffers.createRow(rowNumber++);
            Person person = auctionOffer.getPerson();
            Cell cell = row.createCell(0);
            cell.setCellValue((rowNumber-1));
            sheetOffers.autoSizeColumn(0);
            cell.setCellStyle(style);

            cell = row.createCell(1);
            cell.setCellValue(person.getUsername());
            sheetOffers.autoSizeColumn(1);
            cell.setCellStyle(style);

            cell = row.createCell(2);
            cell.setCellValue(person.getEmail());
            sheetOffers.autoSizeColumn(2);
            cell.setCellStyle(style);

            cell = row.createCell(3);
            cell.setCellValue(auctionOffer.getOffer());
            sheetOffers.autoSizeColumn(3);
            cell.setCellStyle(style);
        }
    }

    private void writeTableHeaderItem() {
        Row row = sheetItem.createRow(0);

        CellStyle style = book.createCellStyle();
        XSSFFont font = book.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        Cell cell = row.createCell(0);
        cell.setCellValue("ID");
        cell.setCellStyle(style);

        cell = row.createCell(1);
        cell.setCellValue("Name");
        cell.setCellStyle(style);

        cell = row.createCell(2);
        cell.setCellValue("Description");
        cell.setCellStyle(style);

        cell = row.createCell(3);
        cell.setCellValue("Start price");
        cell.setCellStyle(style);

        cell = row.createCell(4);
        cell.setCellValue("Min Next Offer");
        cell.setCellStyle(style);

        cell = row.createCell(5);
        cell.setCellValue("Start date");
        cell.setCellStyle(style);

        cell = row.createCell(6);
        cell.setCellValue("Finish date");
        cell.setCellStyle(style);
    }

    private void writeTableDataItem() {
        int rowNumber = 1;
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        CellStyle style = book.createCellStyle();
        XSSFFont font = book.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        Row row = sheetItem.createRow(rowNumber++);
        Cell cell = row.createCell(0);
        cell.setCellValue(item.getId());
        sheetItem.autoSizeColumn(0);
        cell.setCellStyle(style);

        cell = row.createCell(1);
        cell.setCellValue(item.getName());
        sheetOffers.autoSizeColumn(1);
        cell.setCellStyle(style);

        cell = row.createCell(2);
        cell.setCellValue(item.getDescription());
        sheetOffers.autoSizeColumn(2);
        cell.setCellStyle(style);

        cell = row.createCell(3);
        cell.setCellValue(item.getStartPrice());
        sheetOffers.autoSizeColumn(3);
        cell.setCellStyle(style);

        cell = row.createCell(4);
        cell.setCellValue(item.getMinNextOffer());
        sheetOffers.autoSizeColumn(4);
        cell.setCellStyle(style);

        cell = row.createCell(5);
        cell.setCellValue(format.format(item.getStartDate()));
        sheetOffers.autoSizeColumn(5);
        cell.setCellStyle(style);

        cell = row.createCell(6);
        cell.setCellValue(format.format(item.getFinishDate()));
        sheetOffers.autoSizeColumn(6);
        cell.setCellStyle(style);
    }

    public byte[] export() throws IOException {
        writeTableHeaderOffers();
        writeTableDataOffers();
        writeTableHeaderItem();
        writeTableDataItem();

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            book.write(bos);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        bos.close();
        return bos.toByteArray();
    }
}
