package org.kryun.symbol.pkg.Excel.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.kryun.symbol.model.BlockDTO;
import org.kryun.symbol.pkg.Excel.interfaces.ExcelServiceInterface;

public class BlockExcelService implements ExcelServiceInterface {

    public BlockExcelService() {
    }

    public BlockExcelService(List<BlockDTO> dataList) {
        this.dataList = dataList;
    }

    private List<String> columnList = new ArrayList<>(Arrays.asList("id", "parentId", "symbolReferenceId"));
    private List<BlockDTO> dataList = new ArrayList<>();

    @Override
    public void createExcelSheet(XSSFWorkbook wb) throws Exception {
     XSSFSheet sheet = ExcelServiceInterface.createTitle(wb, columnList, "block");

        for (int i = 0; i < dataList.size(); i++) {
            Row bodyRow = sheet.createRow(i + 1);
            BlockDTO blockDTO = dataList.get(i);
            for (int j = 0; j < columnList.size(); j++) {
                Cell cell = bodyRow.createCell(j);
                switch (columnList.get(j)) {
                    case "id":
                        cell.setCellValue(blockDTO.getBlockId());
                        break;
                    case "parentId":
                        if(blockDTO.getParentBlockId()!= null)
                            cell.setCellValue(blockDTO.getParentBlockId());
                        break;
                    case "symbolReferenceId":
                        cell.setCellValue(blockDTO.getSymbolReferenceId());
                        break;
                    default:
                        System.out.println("BlockExcelService:: " + blockDTO + " column 값을 다시 확인해주세요");
                }
            }
        }
        System.out.println("block excel sheet is completed");
    }
}
