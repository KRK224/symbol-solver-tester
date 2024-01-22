package org.kryun.symbol.pkg.Excel.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.kryun.symbol.model.MethodDeclarationDTO;
import org.kryun.symbol.pkg.Excel.interfaces.ExcelServiceInterface;

public class MethodDeclExcelService implements ExcelServiceInterface {
    private List<String> columnList = new ArrayList<String>(
            Arrays.asList("id", "name", "belongedClassId", "blockId", "fullQualifiedNameId"));
    private List<MethodDeclarationDTO> dataList = new ArrayList<MethodDeclarationDTO>();

    public MethodDeclExcelService() {
    }

    public MethodDeclExcelService(List<MethodDeclarationDTO> dataList) {
        this.dataList = dataList;
    }

    @Override
    public void createExcelSheet(XSSFWorkbook wb) throws Exception {
        XSSFSheet sheet = ExcelServiceInterface.createTitle(wb, columnList, "MethodDecl");

        // 데이터 입력
        for (int i = 0; i < dataList.size(); i++) {
            Row bodyRow = sheet.createRow(i + 1);
            MethodDeclarationDTO methodDeclarationDTO = dataList.get(i);

            for (int j = 0; j < columnList.size(); j++) {
                Cell cell = bodyRow.createCell(j);
                switch (columnList.get(j)) {
                    case "id":
                        cell.setCellValue(methodDeclarationDTO.getMethodDeclId());
                        break;
                    case "name":
                        cell.setCellValue(methodDeclarationDTO.getName());
                        break;
                    case "belongedClassId":
                        cell.setCellValue(methodDeclarationDTO.getBelongedClassId());
                        break;
                    case "blockId":
                        cell.setCellValue(methodDeclarationDTO.getBlockId());
                        break;
                    case "fullQualifiedNameId":
                        if(methodDeclarationDTO.getFullQualifiedNameId() !=null)
                            cell.setCellValue(methodDeclarationDTO.getFullQualifiedNameId());
                        break;
                    default:
                        System.out.println("MethodDecl:: " + methodDeclarationDTO + " column 값을 다시 확인해주세요");
                }
            }
        }
        System.out.println("methodDecl excel sheet is completed");
    }

}
