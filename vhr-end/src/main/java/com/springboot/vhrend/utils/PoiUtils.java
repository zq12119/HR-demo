package com.springboot.vhrend.utils;

import com.springboot.vhrend.model.Position;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class PoiUtils {
//       使用ResponseEntity来进行导出excel
    public static ResponseEntity<byte[]> exportData(List<Position> positions) {
//          创建 excel文件
        XSSFWorkbook workbook = new XSSFWorkbook();

//         创建单元格样式
        XSSFCellStyle headerStyle = workbook.createCellStyle();
//        设置单元格的前景颜色
        headerStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
//       填充模式
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//         日期样式
        XSSFCellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));
//       创建EXCEL工作表对象
        XSSFSheet sheet = workbook.createSheet("职位信息表");
//        设置列高
        sheet.setColumnWidth(0, 5 * 256);
        sheet.setColumnWidth(1, 15 * 256);
        sheet.setColumnWidth(2, 20 * 256);
        sheet.setColumnWidth(3, 10 * 256);
//        创建Excel工作表第一行
        Row titleRow = sheet.createRow(0);
        Cell c0 = titleRow.createCell(0);
        Cell c1 = titleRow.createCell(1);
        Cell c2 = titleRow.createCell(2);
        Cell c3 = titleRow.createCell(3);
        c0.setCellStyle(headerStyle);
        c1.setCellStyle(headerStyle);
        c2.setCellStyle(headerStyle);
        c3.setCellStyle(headerStyle);
//        设置单元格内容
        c0.setCellValue("编号");
        c1.setCellValue("职位名称");
        c2.setCellValue("创建时间");
        c3.setCellValue("是否启用");
//         循环写入 postions数据
        for (int i = 0; i < positions.size(); i++) {
            Position position = positions.get(i);
            Row row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(position.getId());
            row.createCell(1).setCellValue(position.getName());
            Cell cell = row.createCell(2);
            cell.setCellStyle(dateCellStyle);
            cell.setCellValue(position.getCreateDate());
            row.createCell(3).setCellValue(position.getEnabled());
        }
//     保存文件
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        HttpHeaders headers = new HttpHeaders();
        try {
            headers.setContentDispositionFormData("attachment",
                    new String("职位表.xlsx".getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(out.toByteArray(), headers, HttpStatus.CREATED);
    }

    public static List<Position> importData(MultipartFile file) {
        List<Position> positions = new ArrayList<>();
        Position position = null;
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
//            获取 workbook的 sheet数量
            int numberOfSheets = workbook.getNumberOfSheets();
            for (int i = 0; i < numberOfSheets; i++) {
//               获取 sheet
                XSSFSheet sheet = workbook.getSheetAt(i);
                int rows = sheet.getPhysicalNumberOfRows();
                for (int j = 1; j < rows; j++) {
//                    遍历数据集创建的行
                    XSSFRow row = sheet.getRow(j);
                    if (row != null) {
                        position = new Position();
                        int cellNum = row.getPhysicalNumberOfCells();
                        for (int k = 0; k < cellNum; k++) {
                            XSSFCell cell = row.getCell(k);
                            switch (cell.getCellType()) {
                                case STRING:
                                    if (k == 1) {
                                        position.setName(cell.getStringCellValue());
                                    }
                                    break;
                                case BOOLEAN:
                                    if(k == 3) {
                                        position.setEnabled(cell.getBooleanCellValue());
                                    }
                                    break;
                                default:
                                    if(k == 2) {
                                        position.setCreateDate(cell.getDateCellValue());
                                    }break;
                            }
                        }
                        positions.add(position);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return positions;
    }
}
