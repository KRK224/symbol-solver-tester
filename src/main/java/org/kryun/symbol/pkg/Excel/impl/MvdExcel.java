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

public class MvdExcel implements ExcelServiceInterface {
    private List<String> columnList = new ArrayList<String>(
            Arrays.asList("id", "name", "belongedClassId", "type", "typeClassId"));
    private List<MemberVariableDeclarationDTO> dataList = new ArrayList<MemberVariableDeclarationDTO>();

    public MvdExcel() {
    }

    public List<String> getColumnList() {
        return columnList;
    }

    public void setColumnList(List<String> columnList) {
        this.columnList = columnList;
    }

    public List<MemberVariableDeclarationDTO> getDataList() {
        return dataList;
    }

    public void setDataList(List<MemberVariableDeclarationDTO> dataList) {
        this.dataList = dataList;
    }

    @Override
    public void createExcelSheet(XSSFWorkbook wb) throws Exception {

        XSSFSheet sheet = wb.createSheet("MemberVariableDeclarationDTO");

        CellStyle headStyle = wb.createCellStyle();

        XSSFFont title_font = wb.createFont();
        title_font.setFontName(HSSFFont.FONT_ARIAL);
        title_font.setFontHeightInPoints((short) 12);
        title_font.setBold(true);

        headStyle.setFont(title_font);
        headStyle.setAlignment(HorizontalAlignment.CENTER);
        headStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        Row titleRow = sheet.createRow(0);
        // 타이틀 컬럼 값 지정
        for (int i = 0; i < columnList.size(); i++) {
            Cell cell = titleRow.createCell(i);
            cell.setCellStyle(headStyle);
            cell.setCellValue(columnList.get(i));
        }

        // 데이터 입력
        for (int i = 0; i < dataList.size(); i++) {
            Row bodyRow = sheet.createRow(i + 1);
            MemberVariableDeclarationDTO current_mvd = dataList.get(i);
            for (int j = 0; j < columnList.size(); j++) {
                Cell cell = bodyRow.createCell(j);
                switch (columnList.get(j)) {
                    case "id":
                        cell.setCellValue(current_mvd.getVariableId());
                        break;
                    case "name":
                        cell.setCellValue(current_mvd.getName());
                        break;
                    case "belongedClassId":
                        cell.setCellValue(current_mvd.getBelongedClassId());
                        break;
                    case "type":
                        cell.setCellValue(current_mvd.getType());
                        break;
                    case "typeClassId":
                        cell.setCellValue(current_mvd.getTypeClassId());
                        break;
                    default:
                        System.out.println("column 값을 다시 확인해주세요");
                }
            }
        }

    }

}
