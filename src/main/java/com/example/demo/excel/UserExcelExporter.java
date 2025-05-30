//package com.example.demo.excel;
//import java.io.IOException;
//import java.util.List;
//
////import javax.servlet.ServletOutputStream;
////import javax.servlet.http.HttpServletResponse;
//
//import com.example.demo.dto.UserExcelDTO;
//import com.example.demo.model.User;
//import jakarta.servlet.ServletOutputStream;
//import jakarta.servlet.http.HttpServletResponse;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.CellStyle;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.xssf.usermodel.XSSFFont;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//
//public class UserExcelExporter {
//    private XSSFWorkbook workbook;
//    private XSSFSheet sheet;
//    private List<UserExcelDTO> listUsers;
//
//    public UserExcelExporter(List<UserExcelDTO> listUsers) {
//        this.listUsers = listUsers;
//        workbook = new XSSFWorkbook();
//    }
//
//
//    private void writeHeaderLine() {
//        sheet = workbook.createSheet("Users");
//
//        Row row = sheet.createRow(0);
//
//        CellStyle style = workbook.createCellStyle();
//        XSSFFont font = workbook.createFont();
//        font.setBold(true);
//        font.setFontHeight(16);
//        style.setFont(font);
//
//        createCell(row, 0, "No.", style);
//        createCell(row, 1, "UserName", style);
//        createCell(row, 2, "User FullName", style);
//        createCell(row, 3, "Departments", style);
//        createCell(row, 4, "Positions", style);
//
//    }
//
//    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
//        sheet.autoSizeColumn(columnCount);
//        Cell cell = row.createCell(columnCount);
//        if (value instanceof Integer) {
//            cell.setCellValue((Integer) value);
//        } else if (value instanceof Boolean) {
//            cell.setCellValue((Boolean) value);
//        } else {
//            cell.setCellValue((String) value);
//        }
//        cell.setCellStyle(style);
//    }
//
//    private void writeDataLines() {
//        int rowCount = 1;
//
//        CellStyle style = workbook.createCellStyle();
//        XSSFFont font = workbook.createFont();
//        font.setFontHeight(14);
//        style.setFont(font);
//
//        for (UserExcelDTO user : listUsers) {
//            Row row = sheet.createRow(rowCount++);
//            int columnCount = 0;
//
//            createCell(row, columnCount++, rowCount, style);
//            createCell(row, columnCount++, user.getUserName(), style);
//            createCell(row, columnCount++, user.getUserFullName(), style);
//            createCell(row, columnCount++, user.getDepartments().toString(), style);
//            createCell(row, columnCount++, user.getPositions(), style);
//
//        }
//    }
//
//    public void export(HttpServletResponse response) throws IOException {
//        writeHeaderLine();
//        writeDataLines();
//
//        ServletOutputStream outputStream = response.getOutputStream();
//        workbook.write(outputStream);
//        workbook.close();
//
//        outputStream.close();
//
//    }
//}
