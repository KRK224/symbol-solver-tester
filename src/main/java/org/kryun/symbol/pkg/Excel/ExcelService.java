package org.kryun.symbol.pkg.Excel;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.kryun.global.config.AppConfig;
import org.kryun.symbol.pkg.Excel.impl.ClassExcel;
import org.kryun.symbol.pkg.Excel.impl.MceExcel;
import org.kryun.symbol.pkg.Excel.impl.MdExcel;
import org.kryun.symbol.pkg.Excel.impl.MvdExcel;
import org.kryun.symbol.pkg.Excel.impl.SvdExcel;

public class ExcelService {
    private ClassExcel classExcel;
    private MceExcel mceExcel;
    private MdExcel mdExcel;
    private MvdExcel mvdExcel;
    private SvdExcel svdExcel;

    public ExcelService(ClassExcel ce, MceExcel mce, MdExcel mde, MvdExcel mvde, SvdExcel svde) {
        classExcel = ce;
        mceExcel = mce;
        mdExcel = mde;
        mvdExcel = mvde;
        svdExcel = svde;
    }

    public void createExcelFile(String projectName) throws Exception {
        try {
            XSSFWorkbook wb = new XSSFWorkbook();
            classExcel.createExcelSheet(wb);
            mceExcel.createExcelSheet(wb);
            mdExcel.createExcelSheet(wb);
            mvdExcel.createExcelSheet(wb);
            svdExcel.createExcelSheet(wb);

            LocalDateTime now = LocalDateTime.now();
            String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HHmmss_"));

            String path = AppConfig.WORKSPACE_PATH + "/" + projectName + "/result";
            File Folder = new File(path);
            if(!Folder.exists()) {
                try {
                    Folder.mkdir();
                    System.out.println("폴더가 생성되었습니다.");
                } catch(Exception e) {
                    e.getStackTrace();
                }
            } else {
                System.out.println("이미 " + path + " 가 생성되었습니다.");
            }

            String fileLocation = path + "/" +
                formatedNow
                + projectName + ".xlsx";
            System.out.println(fileLocation);
            FileOutputStream fileOutputStream = new FileOutputStream(fileLocation);
            wb.write(fileOutputStream);
            wb.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }

    }
}
