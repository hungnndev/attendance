package com.example.demo.excel;

import com.example.demo.dto.UserExcelDTO;
import com.opencsv.CSVWriter;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.util.List;

public class UserCSVExporter {

    private List<UserExcelDTO> listUsers;

    public UserCSVExporter(List<UserExcelDTO> listUsers) {
        this.listUsers = listUsers;
    }

    public void generateCSV(HttpServletResponse response) throws IOException {
        CSVWriter writer = new CSVWriter(response.getWriter());

        // Write header to CSV file
        writer.writeNext(new String[] {"userName", "userFullName", "position", "department"});

        // Write data to CSV file
        for (UserExcelDTO userDTO : listUsers) {
            writer.writeNext(new String[] {userDTO.getUserName(), userDTO.getUserFullName(), userDTO.getPositions(), userDTO.getDepartments()});
        }
        writer.close();
    }
}
