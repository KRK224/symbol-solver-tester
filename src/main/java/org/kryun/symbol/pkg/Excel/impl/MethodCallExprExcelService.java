package org.kryun.symbol.pkg.Excel.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.kryun.symbol.model.MethodCallExprDTO;
import org.kryun.symbol.pkg.Excel.interfaces.ExcelServiceInterface;

public class MethodCallExprExcelService implements ExcelServiceInterface {

    private List<String> columnList = new ArrayList<String>(
        Arrays.asList("id", "name", "nameExpr", "nameExprFullQualifiedNameId",
            "fullQualifiedNameId", "blockId"));
    private List<MethodCallExprDTO> dataList = new ArrayList<MethodCallExprDTO>();

    public MethodCallExprExcelService() {
    }

    public MethodCallExprExcelService(List<MethodCallExprDTO> dataList) {
        this.dataList = dataList;
    }

    @Override
    public void createExcelSheet(XSSFWorkbook wb) throws Exception {
        XSSFSheet sheet = ExcelServiceInterface.createTitle(wb, columnList, "MethodCallExpr");

        // 데이터 입력
        for (int i = 0; i < dataList.size(); i++) {
            Row bodyRow = sheet.createRow(i + 1);
            MethodCallExprDTO methodCallExprDTO = dataList.get(i);
            System.out.println("methodCallExprDTO = " + methodCallExprDTO);
            for (int j = 0; j < columnList.size(); j++) {
                Cell cell = bodyRow.createCell(j);
                switch (columnList.get(j)) {
                    case "id":
                        cell.setCellValue(methodCallExprDTO.getMethodCallExprId());
                        break;
                    case "name":
                        cell.setCellValue(methodCallExprDTO.getName());
                        break;
                    case "nameExpr":
                        cell.setCellValue(methodCallExprDTO.getScopeSimpleName());
                        break;
                    case "nameExprFullQualifiedNameId":
                        if (methodCallExprDTO.getNameExprFullQualifiedNameId() != null) {
                            cell.setCellValue(methodCallExprDTO.getNameExprFullQualifiedNameId());
                        }
                        break;
                    case "fullQualifiedNameId":
                        if (methodCallExprDTO.getFullQualifiedNameId() != null) {
                            cell.setCellValue(methodCallExprDTO.getFullQualifiedNameId());
                        }
                        break;
                    case "blockId":
                        cell.setCellValue(methodCallExprDTO.getBlockId());
                        break;
                    default:
                        System.out.println(
                            "MethodCallExpr:: " + methodCallExprDTO + "column 값을 다시 확인해주세요");
                }
            }
        }
        System.out.println("methodCallExpr excel sheet is completed");
    }

}
