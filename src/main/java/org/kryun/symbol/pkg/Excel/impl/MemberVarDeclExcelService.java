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
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.kryun.symbol.model.MemberVariableDeclarationDTO;
import org.kryun.symbol.pkg.Excel.interfaces.ExcelServiceInterface;

public class MemberVarDeclExcelService implements ExcelServiceInterface {
    private List<String> columnList = new ArrayList<String>(
            Arrays.asList("id", "name", "belongedClassId", "type", "importId", "fullQualifiedNameId", "blockId"));
    private List<MemberVariableDeclarationDTO> dataList = new ArrayList<MemberVariableDeclarationDTO>();

    public MemberVarDeclExcelService() {
    }

    public MemberVarDeclExcelService(List<MemberVariableDeclarationDTO> dataList) {
        this.dataList = dataList;
    }

    @Override
    public void createExcelSheet(XSSFWorkbook wb) throws Exception {

        XSSFSheet sheet = ExcelServiceInterface.createTitle(wb, columnList, "MemberVarDecl");

        for (int i = 0; i < dataList.size(); i++) {
            Row bodyRow = sheet.createRow(i + 1);
            MemberVariableDeclarationDTO memberVariableDeclarationDTO = dataList.get(i);
            for (int j = 0; j < columnList.size(); j++) {
                Cell cell = bodyRow.createCell(j);
                switch (columnList.get(j)) {
                    case "id":
                        cell.setCellValue(memberVariableDeclarationDTO.getVariableId());
                        break;
                    case "name":
                        cell.setCellValue(memberVariableDeclarationDTO.getName());
                        break;
                    case "belongedClassId":
                        cell.setCellValue(memberVariableDeclarationDTO.getBelongedClassId());
                        break;
                    case "type":
                        cell.setCellValue(memberVariableDeclarationDTO.getType());
                        break;
                    case "importId":
                        if(memberVariableDeclarationDTO.getImportId() != null)
                            cell.setCellValue(memberVariableDeclarationDTO.getImportId());
                        break;
                    case "fullQualifiedNameId":
                        if(memberVariableDeclarationDTO.getFullQualifiedNameId() !=null)
                            cell.setCellValue(memberVariableDeclarationDTO.getFullQualifiedNameId());
                        break;
                    case "blockId":
                        cell.setCellValue(memberVariableDeclarationDTO.getBlockId());
                        break;
                    default:
                        System.out.println("MemberVarDecl:: " + memberVariableDeclarationDTO + "column 값을 다시 확인해주세요");
                }
            }
        }
        System.out.println("memberVarDecl excel sheet is completed");
    }
}
