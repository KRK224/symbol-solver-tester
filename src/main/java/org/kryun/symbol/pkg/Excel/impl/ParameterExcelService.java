package org.kryun.symbol.pkg.Excel.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.kryun.symbol.model.ParameterDTO;
import org.kryun.symbol.pkg.Excel.interfaces.ExcelServiceInterface;

public class ParameterExcelService implements ExcelServiceInterface {

    public ParameterExcelService(List<ParameterDTO> dataList) {
        this.dataList = dataList;
    }

    public ParameterExcelService() {
    }

    private List<String> columnList = new ArrayList<String>(
        Arrays.asList("id", "name", "methodDeclId", "fullQualifiedNameId", "type", "importId"));
    private List<ParameterDTO> dataList = new ArrayList<ParameterDTO>();
    @Override
    public void createExcelSheet(XSSFWorkbook wb) throws Exception {
        XSSFSheet sheet = ExcelServiceInterface.createTitle(wb, columnList, "paramters");


        for (int i = 0; i < dataList.size(); i++) {
            Row bodyRow = sheet.createRow(i + 1);
            ParameterDTO parameterDTO = dataList.get(i);
            for (int j = 0; j < columnList.size(); j++) {
                Cell cell = bodyRow.createCell(j);
                switch (columnList.get(j)) {
                    case "id":
                        cell.setCellValue(parameterDTO.getParameterId());
                        break;
                    case "name":
                        cell.setCellValue(parameterDTO.getName());
                        break;
                    case "methodDeclId":
                        cell.setCellValue(parameterDTO.getMethodDeclId());
                        break;
                    case "fullQualifiedNameId":
                        if(parameterDTO.getFullQualifiedNameId() != null)
                            cell.setCellValue(parameterDTO.getFullQualifiedNameId());
                        break;
                    case "type":
                        cell.setCellValue(parameterDTO.getType());
                        break;
                    case "importId":
                        if(parameterDTO.getImportId() != null)
                            cell.setCellValue(parameterDTO.getImportId());
                        break;
                    default:
                        System.out.println("Paramemter:: " + parameterDTO + "column 값을 다시 확인해주세요");
                }
            }
        }
        System.out.println("parameter excel sheet is completed");
    }
}
