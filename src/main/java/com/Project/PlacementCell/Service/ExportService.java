package com.Project.PlacementCell.Service;

import com.Project.PlacementCell.DTO.AdminDTO.StudentExportDTO;
import com.Project.PlacementCell.Repository.JobApplicationsRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExportService {

    @Autowired
    private JobApplicationsRepository repo;

    public ByteArrayInputStream exportStudents(Integer jobId) throws IOException {

            List<StudentExportDTO> students = repo.getStudentsByJobId(jobId);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Students");

        Row headerRow = sheet.createRow(0);

        String[] headers = {"Student ID", "Name", "Email", "Branch", "Phone", "Department"};

        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        int rowIdx = 1;

        for (StudentExportDTO s : students) {

            Row row = sheet.createRow(rowIdx++);

            row.createCell(0).setCellValue(s.getStudentId());
            row.createCell(1).setCellValue(s.getFullName());
            row.createCell(2).setCellValue(s.getEmail());
            row.createCell(3).setCellValue(s.getStream());
            row.createCell(4).setCellValue(s.getMobileNumber());
            row.createCell(5).setCellValue(s.getDepartment());
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();

        return new ByteArrayInputStream(out.toByteArray());
    }
}