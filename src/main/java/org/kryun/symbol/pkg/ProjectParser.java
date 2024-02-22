package org.kryun.symbol.pkg;

import com.github.javaparser.ParseResult;
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JarTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;
import com.github.javaparser.symbolsolver.utils.SymbolSolverCollectionStrategy;
import com.github.javaparser.utils.ProjectRoot;
import com.github.javaparser.utils.SourceRoot;
import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import org.kryun.global.config.AppConfig;
import org.kryun.global.enums.SymbolStatusEnum;
import org.kryun.symbol.model.SymbolStatusDTO;
import org.kryun.symbol.pkg.Excel.ExcelService;
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

public class ProjectParser {

    public void parseProject(String projectPath, SymbolStatusDTO symbolStatusDTO, String projName)
        throws Exception {
        ConvertJavaParserToSymbol convertJavaParserToSymbol = new ConvertJavaParserToSymbol();
        try {

            try {
                ProjectRoot projectRoot = getProjectRoot(projectPath, 0);

                // 전체 프로젝트 ast로 조회
                System.out.println("[3/5] (1) projectRoot: " + projectRoot);
                List<SourceRoot> sourceRootList = projectRoot.getSourceRoots();
                System.out.println("#######for 0 ######## : " + sourceRootList);
                for (SourceRoot sourceRoot : sourceRootList) {
                    System.out.println("#######for 1 ########");
                    List<ParseResult<CompilationUnit>> parseResults = sourceRoot.tryToParse();
                    for (ParseResult<CompilationUnit> parseResult : parseResults) {
                        Optional<CompilationUnit> optionalCompilationUnit = parseResult.getResult();
                        if (optionalCompilationUnit.isPresent()) {
                            CompilationUnit cu = optionalCompilationUnit.get();

                            // System.out.println(cu.toString());

                            String fileName = cu.getStorage().get().getPath().toString();
                            String srcPath = fileName.replace(AppConfig.WORKSPACE_PATH + "/", "");

                            System.out.println("fileName:" + fileName);

                            // cu를 활용
                            convertJavaParserToSymbol.visit(cu, symbolStatusDTO.getSymbolStatusId(),
                                srcPath);

                        }
                    }
                }

                try {
                    System.out.println("[3/5] (2) projId: " + symbolStatusDTO.getProjId()
                        + ", Saving into excel start");
                    BlockExcelService blockExcelService = new BlockExcelService(
                        convertJavaParserToSymbol.getBlockDTOList());
                    ClassExcelService classExcelService = new ClassExcelService(
                        convertJavaParserToSymbol.getClassDTOList());
                    FullQualifiedNameExcelService fullQualifiedNameExcelService = new FullQualifiedNameExcelService(
                        convertJavaParserToSymbol.getFullQualifiedNameDTOList());
                    MemberVarDeclExcelService memberVarDeclExcelService = new MemberVarDeclExcelService(
                        convertJavaParserToSymbol.getMemberVariableDeclarationDTOList());
                    MethodCallExprExcelService methodCallExprExcelService = new MethodCallExprExcelService(
                        convertJavaParserToSymbol.getMethodCallExprDTOList());
                    MethodDeclExcelService methodDeclExcelService = new MethodDeclExcelService(
                        convertJavaParserToSymbol.getMethodDeclarationDTOList());
                    ParameterExcelService parameterExcelService = new ParameterExcelService(
                        convertJavaParserToSymbol.getParameterDTOList());
                    ReturnMapperExcelService returnMapperExcelService = new ReturnMapperExcelService(
                        convertJavaParserToSymbol.getReturnMapperDTOList());
                    StmtVarDeclExcelService stmtVarDeclExcelService = new StmtVarDeclExcelService(
                        convertJavaParserToSymbol.getStmtVariableDeclarationDTOList());
                    SymbolReferenceExcelService symbolReferenceExcelService = new SymbolReferenceExcelService(
                        convertJavaParserToSymbol.getSymbolReferenceDTOList());

                    ExcelService excelService = new ExcelService(blockExcelService,
                        classExcelService, fullQualifiedNameExcelService, memberVarDeclExcelService,
                        methodCallExprExcelService, methodDeclExcelService, parameterExcelService,
                        returnMapperExcelService, stmtVarDeclExcelService,
                        symbolReferenceExcelService);
                    excelService.createExcelFile(projName);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

            } catch (Exception e) {
                // Parsing 실패 시
                symbolStatusDTO.setStatusEnum(SymbolStatusEnum.UNAVAILAbLE);
                throw e;
            }

        } finally {
            System.out.println("마지막 심볼 추적:: \n" + convertJavaParserToSymbol.printLastSymbol());
            convertJavaParserToSymbol.clear();
        }
    }

    private ProjectRoot getProjectRoot(String projectPath, int depth) throws Exception {
        System.out.println("find ProjectRoot recursively, depth: " + depth);
        System.out.println("project path: " + projectPath);
        if (depth > 5) {
            throw new Exception("can't find ProjectRoot, please check package and src directory");
        }

        if (!projectPath.endsWith("/")) {
            projectPath += "/";
        }

        Path root = Paths.get(projectPath);
//        CombinedTypeSolver typeSolver = new CombinedTypeSolver(new ReflectionTypeSolver(false));

//        Path jars = Paths.get( projectPath + "src/main/resources/jsqlparser-4.7.jar");
//        CombinedTypeSolver typeSolver = new CombinedTypeSolver(new ReflectionTypeSolver(false));
//        typeSolver.add(new JarTypeSolver(jars));


//        ParserConfiguration parserConfiguration = new ParserConfiguration();
//        parserConfiguration.setSymbolResolver(new JavaSymbolSolver(typeSolver));

        SymbolSolverCollectionStrategy symbolSolverCollectionStrategy = new SymbolSolverCollectionStrategy();

        ProjectRoot projectRoot = symbolSolverCollectionStrategy.collect(root);

        if (projectRoot.getSourceRoots().size() == 0) {
            System.out.println("projectRoot = " + projectRoot);
            File rootFile = new File(projectPath);
            String[] subSrcPath = rootFile.list(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return new File(dir, name).isDirectory() ? name.startsWith("src") : false;
                }
            });

            if (subSrcPath == null || subSrcPath.length == 0) {
                throw new Exception(
                    "Can't find ProjectRoot, please check package and src directory");
            }
            return getProjectRoot(projectPath + subSrcPath[0], ++depth);
        }
        return projectRoot;

    }
}
