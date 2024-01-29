package org.kryun.symbol.pkg.Excel.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.kryun.symbol.model.BlockDTO;
import org.kryun.symbol.model.FullQualifiedNameDTO;
import org.kryun.symbol.pkg.Excel.interfaces.ExcelServiceInterface;

public class FullQualifiedNameExcelService implements ExcelServiceInterface {

    public FullQualifiedNameExcelService() {
    }

    public FullQualifiedNameExcelService(List<FullQualifiedNameDTO> dataList) {
        this.dataList = dataList;
    }

    private List<String> columnList = new ArrayList<>(
        Arrays.asList("id", "fullQualifiedName"));
    private List<FullQualifiedNameDTO> dataList = new ArrayList<>();

    @Override
    public void createExcelSheet(XSSFWorkbook wb) throws Exception {
        XSSFSheet sheet = ExcelServiceInterface.createTitle(wb, columnList, "fullQualifiedName");

        for (int i = 0; i < dataList.size(); i++) {
            Row bodyRow = sheet.createRow(i + 1);
            FullQualifiedNameDTO fullQualifiedNameDTO = dataList.get(i);
            for (int j = 0; j < columnList.size(); j++) {
                Cell cell = bodyRow.createCell(j);
                switch (columnList.get(j)) {
                    case "id":
                        cell.setCellValue(fullQualifiedNameDTO.getFullQualifiedNameId());
                        break;
                    case "fullQualifiedName":
                        cell.setCellValue(fullQualifiedNameDTO.getFullQualifiedName());
                        break;
                    default:
                        System.out.println("FullQualifiedNameExcelService:: " + fullQualifiedNameDTO + "column 값을 다시 확인해주세요");
                }
            }
        }

        System.out.println("fqn excel sheet is completed");
    }
}
