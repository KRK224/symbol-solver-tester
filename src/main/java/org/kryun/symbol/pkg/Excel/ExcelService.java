package org.kryun.symbol.pkg.Excel;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.kryun.global.config.AppConfig;
import org.kryun.symbol.model.ParameterDTO;
import org.kryun.symbol.pkg.Excel.impl.BlockExcelService;
import org.kryun.symbol.pkg.Excel.impl.ClassExcelService;
import org.kryun.symbol.pkg.Excel.impl.FullQualifiedNameExcelService;
import org.kryun.symbol.pkg.Excel.impl.MethodCallExprExcelService;
import org.kryun.symbol.pkg.Excel.impl.MethodDeclExcelService;
import org.kryun.symbol.pkg.Excel.impl.MemberVarDeclExcelService;
import org.kryun.symbol.pkg.Excel.impl.ParameterExcelService;
import org.kryun.symbol.pkg.Excel.impl.ReturnMapperExcelService;
import org.kryun.symbol.pkg.Excel.impl.StmtVarDeclExcelService;
import org.kryun.symbol.pkg.Excel.impl.SymbolReferenceExcelService;

public class ExcelService {
    private BlockExcelService blockExcelService;
    private ClassExcelService classExcelService;
    private FullQualifiedNameExcelService fullQualifiedNameExcelService;
    private MemberVarDeclExcelService memberVarDeclExcelService;
    private MethodCallExprExcelService methodCallExprExcelService;
    private MethodDeclExcelService methodDeclExcelService;
    private ParameterExcelService parameterExcelService;
    private ReturnMapperExcelService returnMapperExcelService;
    private StmtVarDeclExcelService stmtVarDeclExcelService;
    private SymbolReferenceExcelService symbolReferenceExcelService;


    public ExcelService(BlockExcelService blockExcelService, ClassExcelService classExcelService,
        FullQualifiedNameExcelService fullQualifiedNameExcelService,
        MemberVarDeclExcelService memberVarDeclExcelService,
        MethodCallExprExcelService methodCallExprExcelService,
        MethodDeclExcelService methodDeclExcelService, ParameterExcelService parameterExcelService,
        ReturnMapperExcelService returnMapperExcelService,
        StmtVarDeclExcelService stmtVarDeclExcelService,
        SymbolReferenceExcelService symbolReferenceExcelService) {
        this.blockExcelService = blockExcelService;
        this.classExcelService = classExcelService;
        this.fullQualifiedNameExcelService = fullQualifiedNameExcelService;
        this.memberVarDeclExcelService = memberVarDeclExcelService;
        this.methodCallExprExcelService = methodCallExprExcelService;
        this.methodDeclExcelService = methodDeclExcelService;
        this.parameterExcelService = parameterExcelService;
        this.returnMapperExcelService = returnMapperExcelService;
        this.stmtVarDeclExcelService = stmtVarDeclExcelService;
        this.symbolReferenceExcelService = symbolReferenceExcelService;
    }

    public void createExcelFile(String projectName) throws Exception {
        try {
            XSSFWorkbook wb = new XSSFWorkbook();

            this.blockExcelService.createExcelSheet(wb);
            this.classExcelService.createExcelSheet(wb);
            this.fullQualifiedNameExcelService.createExcelSheet(wb);
            this.memberVarDeclExcelService.createExcelSheet(wb);
            this.methodCallExprExcelService.createExcelSheet(wb);
            this.methodDeclExcelService.createExcelSheet(wb);
            this.parameterExcelService.createExcelSheet(wb);
            this.returnMapperExcelService.createExcelSheet(wb);
            this.stmtVarDeclExcelService.createExcelSheet(wb);
            this.symbolReferenceExcelService.createExcelSheet(wb);


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
