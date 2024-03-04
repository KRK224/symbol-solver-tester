package org.kryun.symbol.pkg.Excel.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.kryun.symbol.model.StmtVariableDeclarationDTO;
import org.kryun.symbol.pkg.Excel.interfaces.ExcelServiceInterface;

public class StmtVarDeclExcelService implements ExcelServiceInterface {

    List<String> columnList = new ArrayList<String>(
        Arrays.asList("id", "name", "type", "importId", "fullQualifiedNameId", "blockId"));
    private List<StmtVariableDeclarationDTO> dataList = new ArrayList<StmtVariableDeclarationDTO>();

    public StmtVarDeclExcelService(List<StmtVariableDeclarationDTO> dataList) {
        this.dataList = dataList;
    }

    public StmtVarDeclExcelService() {
    }

    @Override
    public void createExcelSheet(XSSFWorkbook wb) throws Exception {
        XSSFSheet sheet = ExcelServiceInterface.createTitle(wb, columnList, "StmtVarDecl");

        // 데이터 입력
        for (int i = 0; i < dataList.size(); i++) {
            Row bodyRow = sheet.createRow(i + 1);
            StmtVariableDeclarationDTO stmtVariableDeclarationDTO = dataList.get(i);
            for (int j = 0; j < columnList.size(); j++) {
                Cell cell = bodyRow.createCell(j);
                switch (columnList.get(j)) {
                    case "id":
                        cell.setCellValue(stmtVariableDeclarationDTO.getVariableId());
                        break;
                    case "name":
                        cell.setCellValue(stmtVariableDeclarationDTO.getName());
                        break;
                    case "type":
                        cell.setCellValue(stmtVariableDeclarationDTO.getType());
                        break;
                    case "importId":
                        if(stmtVariableDeclarationDTO.getImportId() != null)
                            cell.setCellValue(stmtVariableDeclarationDTO.getImportId());
                        break;
                    case "fullQualifiedNameId":
                        if (stmtVariableDeclarationDTO.getFullQualifiedNameId() != null) {
                            cell.setCellValue(stmtVariableDeclarationDTO.getFullQualifiedNameId());
                        }
                        break;
                    case "blockId":
                        cell.setCellValue(stmtVariableDeclarationDTO.getBlockId());
                        break;
                    default:
                        System.out.println("StmtVarDecl:: " + stmtVariableDeclarationDTO + "column 값을 다시 확인해주세요");
                }
            }
        }
        System.out.println("StmtVarDecl excel sheet is completed");
    }
}
