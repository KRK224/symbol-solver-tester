package org.kryun.symbol.pkg.Excel.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.kryun.symbol.model.ClassDTO;
import org.kryun.symbol.pkg.Excel.interfaces.ExcelServiceInterface;

public class ClassExcelService implements ExcelServiceInterface {

    public ClassExcelService() {
    }
    public ClassExcelService(List<ClassDTO> dataList) {
        this.dataList = dataList;
    }

    private List<String> columnList = new ArrayList<String>(
            Arrays.asList("id", "name", "type", "fullQualifiedNameId", "blockId"));
    private List<ClassDTO> dataList = new ArrayList<ClassDTO>();

    @Override
    public void createExcelSheet(XSSFWorkbook wb) throws Exception {
        XSSFSheet sheet = ExcelServiceInterface.createTitle(wb, columnList, "class");

        // 데이터 입력
        for (int i = 0; i < dataList.size(); i++) {
            Row bodyRow = sheet.createRow(i + 1);
            ClassDTO classDTO = dataList.get(i);
            for (int j = 0; j < columnList.size(); j++) {
                Cell cell = bodyRow.createCell(j);
                switch (columnList.get(j)) {
                    case "id":
                        cell.setCellValue(classDTO.getClassId());
                        break;
                    case "name":
                        cell.setCellValue(classDTO.getName());
                        break;
                    case "type":
                        cell.setCellValue(classDTO.getType());
                        break;
                    case "fullQualifiedNameId":
                        if(classDTO.getFullQualifiedNameId() != null)
                            cell.setCellValue(classDTO.getFullQualifiedNameId());
                        break;
                    case "blockId":
                        cell.setCellValue(classDTO.getBlockId());
                        break;
                    default:
                        System.out.println("classExcelService:: " + classDTO + "column 값을 다시 확인해주세요");
                }
            }
        }
        System.out.println("class excel sheet is completed");
    }
}
