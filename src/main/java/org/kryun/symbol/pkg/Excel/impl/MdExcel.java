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
import org.kryun.symbol.model.MethodDeclarationDTO;
import org.kryun.symbol.model.ReturnMapperDTO;
import org.kryun.symbol.pkg.Excel.interfaces.ExcelServiceInterface;

public class MdExcel implements ExcelServiceInterface {
    private List<String> columnList = new ArrayList<String>(
            Arrays.asList("id", "name", "belongedClassId", "returnMapper::id", "returnMapper::methodDeclId",
                    "returnMapper::type", "returnMapper::typeClassId"));
    private List<MethodDeclarationDTO> dataList = new ArrayList<MethodDeclarationDTO>();

    @Override
    public void createExcelSheet(XSSFWorkbook wb) throws Exception {
        XSSFSheet sheet = wb.createSheet("MethodDeclarationDTO");

        CellStyle headStyle = wb.createCellStyle();

        XSSFFont title_font = wb.createFont();
        title_font.setFontName(HSSFFont.FONT_ARIAL);
        title_font.setFontHeightInPoints((short) 12);
        title_font.setBold(true);

        headStyle.setFont(title_font);
        headStyle.setAlignment(HorizontalAlignment.CENTER);
        headStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        XSSFRow titleRow = sheet.createRow(0);
        // 타이틀 컬럼 값 지정
        for (int i = 0; i < columnList.size(); i++) {
            XSSFCell cell = titleRow.createCell(i);
            cell.setCellStyle(headStyle);
            cell.setCellValue(columnList.get(i));
        }

        // 데이터 입력
        for (int i = 0; i < dataList.size(); i++) {
            Row bodyRow = sheet.createRow(i + 1);
            MethodDeclarationDTO current_md = dataList.get(i);
            ReturnMapperDTO rm = current_md.getReturnMapper();

            for (int j = 0; j < columnList.size(); j++) {
                Cell cell = bodyRow.createCell(j);
                switch (columnList.get(j)) {
                    case "id":
                        cell.setCellValue(current_md.getMethodDeclId());
                        break;
                    case "name":
                        cell.setCellValue(current_md.getName());
                        break;
                    case "belongedClassId":
                        cell.setCellValue(current_md.getBelongedClassId());
                        break;
                    case "returnMapper::id":
                        if (rm.getReturnMapperId() != null)
                            cell.setCellValue(rm.getReturnMapperId());
                        break;
                    case "returnMapper::methodDeclId":
                        if (rm.getMethodDeclId() != null)
                            cell.setCellValue(rm.getMethodDeclId());
                        break;
                    case "returnMapper::type":
                        if (rm.getType() != null)
                            cell.setCellValue(rm.getType());
                        break;
                    case "returnMapper::typeClassId":
                        if (rm.getTypeClassId() != null)
                            cell.setCellValue(rm.getTypeClassId());
                        break;
                    default:
                        System.out.println("column 값을 다시 확인해주세요");
                }
            }
        }
    }

    public List<String> getColumnList() {
        return columnList;
    }

    public void setColumnList(List<String> columnList) {
        this.columnList = columnList;
    }

    public List<MethodDeclarationDTO> getDataList() {
        return dataList;
    }

    public void setDataList(List<MethodDeclarationDTO> dataList) {
        this.dataList = dataList;
    }

}
