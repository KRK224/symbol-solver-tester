package org.kryun.symbol.pkg;

import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.printer.DotPrinter;
import com.github.javaparser.symbolsolver.utils.SymbolSolverCollectionStrategy;
import com.github.javaparser.utils.CodeGenerationUtils;
import com.github.javaparser.utils.ProjectRoot;
import com.github.javaparser.utils.SourceRoot;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.List;
import java.util.Optional;
import org.kryun.global.config.AppConfig;
import org.kryun.global.enums.SymbolStatusEnum;
import org.kryun.symbol.model.SymbolStatusDTO;

public class ProjectParser {

    public void parseProject(String projectPath, SymbolStatusDTO symbolStatusDTO, Connection conn)
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
                            convertJavaParserToSymbol.visit(cu, symbolStatusDTO.getSymbolStatusId(), srcPath);

                        }
                    }
                }
            } catch (Exception e) {
                // Parsing 실패 시
                symbolStatusDTO.setStatusEnum(SymbolStatusEnum.UNAVAILAbLE);
                throw e;
            }

        } finally {
            convertJavaParserToSymbol.clear();
        }
    }

    public void dotPrinter(String fileName, CompilationUnit cu) throws IOException {
        // Now comes the inspection code:
        DotPrinter printer = new DotPrinter(true);
        try (FileWriter fileWriter = new FileWriter(fileName + ".dot");
                PrintWriter printWriter = new PrintWriter(fileWriter)) {
            printWriter.print(printer.output(cu));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // ProcessBuilder
        ProcessBuilder processBuilder = new ProcessBuilder();
        String command = "dot -Tpng " + fileName + ".dot" + " > " + fileName + ".png";
        processBuilder.command("bash", "-c", command);
        Process process = processBuilder.start();

    }

    private void saveSourceCodesInOutputDir(SourceRoot sourceRoot) {
        // 소스코드 형태로 cu를 재조립해서 output 디렉토리에 저장
        // This saves all the files we just read to an output directory.
        sourceRoot.saveAll(
                // The path of the Maven module/project which contains the LogicPositivizer
                // class.
                CodeGenerationUtils.mavenModuleRoot(ProjectParser.class)
                        // appended with a path to "output"
                        .resolve(Paths.get("output")));
    }

    private ProjectRoot getProjectRoot(String projectPath, int depth) throws Exception {
        System.out.println("find ProjectRoot recursively, depth: " + depth);
        System.out.println("project path: " + projectPath);
        if (depth > 2)
            throw new Exception("can't find ProjectRoot, please check package and src directory");

        if (!projectPath.endsWith("/"))
            projectPath += "/";

        Path root = Paths.get(projectPath);
        SymbolSolverCollectionStrategy symbolSolverCollectionStrategy = new SymbolSolverCollectionStrategy();
        ProjectRoot projectRoot = symbolSolverCollectionStrategy.collect(root);
        if (projectRoot.getSourceRoots().size() == 0) {
            File rootFile = new File(projectPath);
            String[] subSrcPath = rootFile.list(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return new File(dir, name).isDirectory() ? name.startsWith("src") : false;
                }
            });
            if (subSrcPath.length == 0) {
                throw new Exception("Can't find ProjectRoot, please check package and src directory");
            }
            return getProjectRoot(projectPath + subSrcPath[0], ++depth);
        }
        return projectRoot;

    }
}
