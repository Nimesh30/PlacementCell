package com.Project.PlacementCell.Service;

import com.Project.PlacementCell.DTO.AdminDTO.StudentExportDTO;
import com.Project.PlacementCell.Entity.JobApplications;
import com.Project.PlacementCell.Entity.Student;
import com.Project.PlacementCell.Entity.StudentProfile;
import com.Project.PlacementCell.Repository.JobApplicationsRepository;
import com.Project.PlacementCell.Repository.StudentRepository;
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
import java.util.stream.Collectors;

@Service
public class ExportService {

    @Autowired
    private JobApplicationsRepository repo;

    @Autowired
    private StudentRepository studentRepo;

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

    public ByteArrayInputStream exportStudentsData(String type) throws Exception {

        List<Student> students;

        if ("placed".equals(type)) {
            students = studentRepo.findByPlacedWithProfile(true);
        } else if ("notPlaced".equals(type)) {
            students = studentRepo.findByPlacedWithProfile(false);
        } else {
            students = studentRepo.findAllWithProfile();
        }

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Students");

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Name");
        header.createCell(1).setCellValue("Student ID");
        header.createCell(2).setCellValue("Email");
        header.createCell(3).setCellValue("Status");
        header.createCell(4).setCellValue("Department");
        header.createCell(5).setCellValue("Branch");
        header.createCell(6).setCellValue("CGPA");
        header.createCell(7).setCellValue("Passing Year");
        header.createCell(8).setCellValue("Company");

        int rowIdx = 1;

        for (Student s : students) {
            Row row = sheet.createRow(rowIdx++);

            row.createCell(0).setCellValue(s.getUsername());
            row.createCell(1).setCellValue(s.getStudentId());
            row.createCell(2).setCellValue(s.getEmail());
            row.createCell(3).setCellValue(
                    Boolean.TRUE.equals(s.getPlaced()) ? "Placed" : "Not Placed"
            );

            // ✅ PROFILE DATA
            StudentProfile p = s.getProfile();

            row.createCell(4).setCellValue(p != null ? p.getDepartment() : "N/A");
            row.createCell(5).setCellValue(
                    p != null && p.getBranch() != null ? p.getBranch().toString() : "N/A"
            );
            row.createCell(6).setCellValue(p != null && p.getBachelorsCgpa() != null ? p.getBachelorsCgpa() : 0);
            row.createCell(7).setCellValue(p != null && p.getPassingYear() != null ? p.getPassingYear() : 0);

            // ✅ COMPANY DATA
            String company = "";

            if (Boolean.TRUE.equals(s.getPlaced())) {
                List<JobApplications> apps = repo.findSelectedByStudentId((long) s.getId());

                if (!apps.isEmpty()) {
                    company = apps.stream()
                            .map(app-> app.getJob().getCompanyName())
                            .distinct()
                            .collect(Collectors.joining(", "));
                }
            }

            row.createCell(8).setCellValue(company);
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();

        return new ByteArrayInputStream(out.toByteArray());
    }
}