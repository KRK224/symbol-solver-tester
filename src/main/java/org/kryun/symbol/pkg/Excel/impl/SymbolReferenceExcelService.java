package org.kryun.symbol.pkg.Excel.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.kryun.symbol.model.SymbolReferenceDTO;
import org.kryun.symbol.pkg.Excel.interfaces.ExcelServiceInterface;

public class SymbolReferenceExcelService implements ExcelServiceInterface {
    private List<String> columnList = new ArrayList<String>(
        Arrays.asList("id", "srcPath"));
    private List<SymbolReferenceDTO> dataList = new ArrayList<>();

    public SymbolReferenceExcelService() {
    }

    public SymbolReferenceExcelService(List<SymbolReferenceDTO> dataList) {
        this.dataList = dataList;
    }

    @Override
    public void createExcelSheet(XSSFWorkbook wb) throws Exception {
        XSSFSheet sheet = ExcelServiceInterface.createTitle(wb, columnList, "symbol_reference");

        // 데이터 입력
        for (int i = 0; i < dataList.size(); i++) {
            Row bodyRow = sheet.createRow(i + 1);
            SymbolReferenceDTO symbolReferenceDTO = dataList.get(i);
            for (int j = 0; j < columnList.size(); j++) {
                Cell cell = bodyRow.createCell(j);
                switch (columnList.get(j)) {
                    case "id":
                        cell.setCellValue(symbolReferenceDTO.getSymbolReferenceId());
                        break;
                    case "srcPath":
                        cell.setCellValue(symbolReferenceDTO.getSrcPath());
                        break;
                    default:
                        System.out.println("symbolReference:: " + symbolReferenceDTO + "column 값을 다시 확인해주세요");
                }
            }
        }
        System.out.println("symbolReference excel sheet is completed");
    }
}
