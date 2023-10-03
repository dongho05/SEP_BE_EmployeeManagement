package com.project.SEP_BE_EmployeeManagement.exportExcel;

import com.project.SEP_BE_EmployeeManagement.model.Department;
import com.project.SEP_BE_EmployeeManagement.model.User;
import com.project.SEP_BE_EmployeeManagement.repository.DepartmentRepository;
import com.project.SEP_BE_EmployeeManagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.Color;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ExcelExportUser {
    private XSSFWorkbook workbook;

    private XSSFSheet sheet;

    private UserRepository userRepository;

    private DepartmentRepository departmentRepository;

    private List<Department> listDeparts;

    private List<User> listUsers;

    private Long departId;

    public ExcelExportUser(List<User> listUsers, long departId, DepartmentRepository departmentRepository){
        this.departmentRepository = departmentRepository;
        workbook = new XSSFWorkbook();
        this.departId = departId;
        this.listUsers = listUsers;
        this.listDeparts = departmentRepository.findAll();
    }

    public ExcelExportUser(List<User> listUsers, DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
        workbook = new XSSFWorkbook();
        this.listUsers = listUsers;
        this.listDeparts = departmentRepository.findAll();

    }


    private void writeHeader() {


        XSSFFont fontHeaderBold = workbook.createFont();
        fontHeaderBold.setFontName("Arial");
        fontHeaderBold.setBold(true);
        fontHeaderBold.setFontHeight(16);


        CellStyle styleBold = workbook.createCellStyle();
        styleBold.setVerticalAlignment(VerticalAlignment.CENTER);
        styleBold.setFont(fontHeaderBold);

        sheet = workbook.createSheet("Danh sách nhân viên");

        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue("DANH SÁCH NHÂN VIÊN");
        cell.setCellStyle(styleBold);
    }

    private void writeTitleTable() {
        XSSFFont fontHeaderBold = workbook.createFont();
        fontHeaderBold.setBold(true);
        fontHeaderBold.setFontName("Arial");
        fontHeaderBold.setFontHeight(10);
        IndexedColorMap colorMap = workbook.getStylesSource().getIndexedColors();
        fontHeaderBold.setColor(new XSSFColor(Color.decode("#2596be"), colorMap));


        // style Titles Bold
        CellStyle styleTitleBold = workbook.createCellStyle();
        styleTitleBold.setBorderBottom(BorderStyle.THIN);
        styleTitleBold.setBorderTop(BorderStyle.THIN);
        styleTitleBold.setBorderLeft(BorderStyle.THIN);
        styleTitleBold.setBorderRight(BorderStyle.THIN);
        styleTitleBold.setAlignment(HorizontalAlignment.CENTER);
        styleTitleBold.setVerticalAlignment(VerticalAlignment.CENTER);
        styleTitleBold.setWrapText(true);
        styleTitleBold.setFont(fontHeaderBold);

        Row row = sheet.createRow(1);
        Cell cell;
        cell = row.createCell(0);
        cell.setCellValue("TT");
        cell.setCellStyle(styleTitleBold);

        cell = row.createCell(1);
        cell.setCellValue("Mã nhân viên");
        cell.setCellStyle(styleTitleBold);
        sheet.setColumnWidth(1, 3500);

        cell = row.createCell(2);
        cell.setCellValue("Họ và tên");
        cell.setCellStyle(styleTitleBold);
        sheet.setColumnWidth(2, 9000);

        cell = row.createCell(3);
        cell.setCellValue("Ngày vào làm");
        cell.setCellStyle(styleTitleBold);
        sheet.setColumnWidth(3, 5000);

        cell = row.createCell(4);
        cell.setCellValue("Phòng ban");
        cell.setCellStyle(styleTitleBold);
        sheet.setColumnWidth(4, 8000);

        cell = row.createCell(5);
        cell.setCellValue("Email");
        cell.setCellStyle(styleTitleBold);
        sheet.setColumnWidth(5, 9000);

        cell = row.createCell(6);
        cell.setCellValue("Vị trí");
        cell.setCellStyle(styleTitleBold);
        sheet.setColumnWidth(6, 5000);

        cell = row.createCell(7);
        cell.setCellValue("Trạng thái");
        cell.setCellStyle(styleTitleBold);
        sheet.setColumnWidth(7, 5000);

    }

    private void writeBodyTable() {
        XSSFFont fontHeaderThin = workbook.createFont();
        fontHeaderThin.setFontName("Arial");
        fontHeaderThin.setFontHeight(10);

        // style Titles Thin
        CellStyle styleTitleThin = workbook.createCellStyle();
        styleTitleThin.setBorderBottom(BorderStyle.THIN);
        styleTitleThin.setBorderTop(BorderStyle.THIN);
        styleTitleThin.setBorderLeft(BorderStyle.THIN);
        styleTitleThin.setBorderRight(BorderStyle.THIN);
        styleTitleThin.setAlignment(HorizontalAlignment.CENTER);
        styleTitleThin.setVerticalAlignment(VerticalAlignment.CENTER);
        styleTitleThin.setWrapText(true);
        styleTitleThin.setFont(fontHeaderThin);

        // style Titles Thin for user Locked
        CellStyle styleTitleThin_UserLocked = workbook.createCellStyle();
        styleTitleThin_UserLocked.setBorderBottom(BorderStyle.THIN);
        styleTitleThin_UserLocked.setBorderTop(BorderStyle.THIN);
        styleTitleThin_UserLocked.setBorderLeft(BorderStyle.THIN);
        styleTitleThin_UserLocked.setBorderRight(BorderStyle.THIN);
        styleTitleThin_UserLocked.setAlignment(HorizontalAlignment.CENTER);
        styleTitleThin_UserLocked.setVerticalAlignment(VerticalAlignment.CENTER);
        styleTitleThin_UserLocked.setVerticalAlignment(VerticalAlignment.CENTER);
        styleTitleThin_UserLocked.setFillForegroundColor(IndexedColors.CORAL.getIndex());
        styleTitleThin_UserLocked.setWrapText(true);
        styleTitleThin_UserLocked.setFont(fontHeaderThin);

        // style Titles Thin for Probation
        CellStyle styleTitleThin_Probation = workbook.createCellStyle();
        styleTitleThin_Probation.setBorderBottom(BorderStyle.THIN);
        styleTitleThin_Probation.setBorderTop(BorderStyle.THIN);
        styleTitleThin_Probation.setBorderLeft(BorderStyle.THIN);
        styleTitleThin_Probation.setBorderRight(BorderStyle.THIN);
        styleTitleThin_Probation.setAlignment(HorizontalAlignment.CENTER);
        styleTitleThin_Probation.setVerticalAlignment(VerticalAlignment.CENTER);
        styleTitleThin_Probation.setVerticalAlignment(VerticalAlignment.CENTER);
        styleTitleThin_Probation.setFillForegroundColor(IndexedColors.AQUA.getIndex());
        styleTitleThin_Probation.setWrapText(true);
        styleTitleThin_Probation.setFont(fontHeaderThin);

        // style Titles Thin for Leave
        CellStyle styleTitleThin_Leave = workbook.createCellStyle();
        styleTitleThin_Leave.setBorderBottom(BorderStyle.THIN);
        styleTitleThin_Leave.setBorderTop(BorderStyle.THIN);
        styleTitleThin_Leave.setBorderLeft(BorderStyle.THIN);
        styleTitleThin_Leave.setBorderRight(BorderStyle.THIN);
        styleTitleThin_Leave.setAlignment(HorizontalAlignment.CENTER);
        styleTitleThin_Leave.setVerticalAlignment(VerticalAlignment.CENTER);
        styleTitleThin_Leave.setVerticalAlignment(VerticalAlignment.CENTER);
        styleTitleThin_Leave.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
        styleTitleThin_Leave.setWrapText(true);
        styleTitleThin_Leave.setFont(fontHeaderThin);

        CellStyle styleTitleThinLeftBGC = workbook.createCellStyle();
        styleTitleThinLeftBGC.setBorderBottom(BorderStyle.THIN);
        styleTitleThinLeftBGC.setBorderTop(BorderStyle.THIN);
        styleTitleThinLeftBGC.setBorderLeft(BorderStyle.THIN);
        styleTitleThinLeftBGC.setBorderRight(BorderStyle.THIN);
        styleTitleThinLeftBGC.setAlignment(HorizontalAlignment.LEFT);
        styleTitleThinLeftBGC.setVerticalAlignment(VerticalAlignment.CENTER);
        styleTitleThinLeftBGC.setFillForegroundColor(IndexedColors.TAN.getIndex());
        styleTitleThinLeftBGC.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        styleTitleThinLeftBGC.setWrapText(true);
        styleTitleThinLeftBGC.setFont(fontHeaderThin);

        Row row = null;
        Cell cell;
        int tt = 1;
        for(Department department: listDeparts){
            row = sheet.createRow(sheet.getLastRowNum()+1);
            sheet.addMergedRegion(new CellRangeAddress(sheet.getLastRowNum(), sheet.getLastRowNum(), 0, 7));

            cell = row.createCell(0);
            cell.setCellValue(department.getName());
            cell.setCellStyle(styleTitleThinLeftBGC);
            for(User user: listUsers){
                if(user.getDepartment().getId() == department.getId()){
                    row = sheet.createRow(sheet.getLastRowNum()+1);
                    cell = row.createCell(0);
                    cell.setCellValue(tt++);
                    cell.setCellStyle(styleTitleThin);

                    cell = row.createCell(1);
                    cell.setCellValue(user.getUserCode());
                    cell.setCellStyle(styleTitleThin);

                    cell = row.createCell(2);
                    cell.setCellValue(user.getFullName());
                    cell.setCellStyle(styleTitleThin);

                    cell = row.createCell(3);
                    cell.setCellValue(user.getStartWork().toString());
                    cell.setCellStyle(styleTitleThin);

                    cell = row.createCell(4);
                    cell.setCellValue(user.getDepartment().getName());
                    cell.setCellStyle(styleTitleThin);

                    cell = row.createCell(5);
                    cell.setCellValue(user.getUsername());
                    cell.setCellStyle(styleTitleThin);

                    cell = row.createCell(6);
                    cell.setCellValue(user.getPosition().getPositionName());
                    cell.setCellStyle(styleTitleThin);

                    cell = row.createCell(7);
                    if(user.getStatus()==1){
                        cell.setCellValue("Có hiệu lực");
                    }
                    else  cell.setCellValue("Vô hiệu lực");

                    cell.setCellStyle(styleTitleThin);
                }

            }
        }

    }

    private void writeFooter() {
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeader();
        writeTitleTable();
        writeBodyTable();
        writeFooter();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}
