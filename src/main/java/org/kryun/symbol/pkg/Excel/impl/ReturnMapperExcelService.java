package org.kryun.symbol.pkg.Excel.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.kryun.symbol.model.ParameterDTO;
import org.kryun.symbol.model.ReturnMapperDTO;
import org.kryun.symbol.pkg.Excel.interfaces.ExcelServiceInterface;

public class ReturnMapperExcelService implements ExcelServiceInterface {
    private List<String> columnList = new ArrayList<String>(
        Arrays.asList("id", "methodDeclId", "fullQualifiedNameId", "type"));

    private List<ReturnMapperDTO> dataList = new ArrayList<>();

    public ReturnMapperExcelService() {
    }

    public ReturnMapperExcelService(List<ReturnMapperDTO> dataList) {
        this.dataList = dataList;
    }

    @Override
    public void createExcelSheet(XSSFWorkbook wb) throws Exception {
        XSSFSheet sheet = ExcelServiceInterface.createTitle(wb, columnList, "Return");
        // 데이터 입력
        for (int i = 0; i < dataList.size(); i++) {
            Row bodyRow = sheet.createRow(i + 1);
            ReturnMapperDTO returnMapperDTO = dataList.get(i);
            for (int j = 0; j < columnList.size(); j++) {
                Cell cell = bodyRow.createCell(j);
                switch (columnList.get(j)) {
                    case "id":
                        cell.setCellValue(returnMapperDTO.getReturnMapperId());
                        break;
                    case "methodDeclId":
                        cell.setCellValue(returnMapperDTO.getMethodDeclId());
                        break;
                    case "fullQualifiedNameId":
                        if(returnMapperDTO.getFullQualifiedNameId() != null)
                            cell.setCellValue(returnMapperDTO.getFullQualifiedNameId());
                        break;
                    case "type":
                        cell.setCellValue(returnMapperDTO.getType());
                        break;
                    default:
                        System.out.println("ReturnMapper:: " + returnMapperDTO + "column 값을 다시 확인해주세요");
                }
            }
        }
        System.out.println("return excel sheet is completed");
    }
}
