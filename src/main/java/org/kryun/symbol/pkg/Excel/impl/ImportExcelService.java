package org.kryun.symbol.pkg.Excel.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.kryun.symbol.model.BlockDTO;
import org.kryun.symbol.model.ImportDTO;
import org.kryun.symbol.pkg.Excel.interfaces.ExcelServiceInterface;


public class ImportExcelService implements ExcelServiceInterface {
    private final List<String> columnList = new ArrayList<>(
        Arrays.asList("id", "blockId", "name", "packageName", "className", "memberName", "isStatic","isAsterisk"));
    private List<ImportDTO> dataList = new ArrayList<>();

    public ImportExcelService(List<ImportDTO> dataList) {
        this.dataList = dataList;
    }

    @Override
    public void createExcelSheet(XSSFWorkbook wb) throws Exception {
        XSSFSheet sheet = ExcelServiceInterface.createTitle(wb, columnList, "import");

        for (int i = 0; i < dataList.size(); i++) {
            Row bodyRow = sheet.createRow(i + 1);
            ImportDTO importDTO = dataList.get(i);
            for (int j = 0; j < columnList.size(); j++) {
                Cell cell = bodyRow.createCell(j);
                switch (columnList.get(j)) {
                    case "id":
                        cell.setCellValue(importDTO.getImportId());
                        break;
                    case "blockId":
                        cell.setCellValue(importDTO.getBlockId());
                        break;
                    case "name":
                        cell.setCellValue(importDTO.getName());
                        break;
                    case "packageName":
                        cell.setCellValue(importDTO.getPackageName());
                        break;
                    case "className":
                        cell.setCellValue(importDTO.getClassName());
                        break;
                    case "memberName":
                        cell.setCellValue(importDTO.getMemberName());
                        break;
                    case "isStatic":
                        cell.setCellValue(importDTO.getIsStatic());
                        break;
                    case "isAsterisk":
                        cell.setCellValue(importDTO.getIsAsterisk());
                        break;
                    default:
                        System.out.println("BlockExcelService:: " + importDTO + " column 값을 다시 확인해주세요");
                }
            }
        }
        System.out.println("block excel sheet is completed");
    }
}
