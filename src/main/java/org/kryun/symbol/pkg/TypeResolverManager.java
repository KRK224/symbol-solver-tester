package org.kryun.symbol.pkg;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import org.kryun.symbol.model.ClassDTO;
import org.kryun.symbol.model.MemberVariableDeclarationDTO;
import org.kryun.symbol.model.MethodCallExprDTO;
import org.kryun.symbol.model.MethodDeclarationDTO;
import org.kryun.symbol.model.ParameterDTO;
import org.kryun.symbol.model.ReturnMapperDTO;
import org.kryun.symbol.model.StmtVariableDeclarationDTO;
import org.kryun.symbol.pkg.symbolsolver.ClassOriginRegister;
import org.kryun.symbol.pkg.symbolsolver.ForwardFDTypeResolver;
import org.kryun.symbol.pkg.symbolsolver.ForwardMCETypeResolver;
import org.kryun.symbol.pkg.symbolsolver.ForwardParamTypeResolver;
import org.kryun.symbol.pkg.symbolsolver.ForwardRMTypeResolver;
import org.kryun.symbol.pkg.symbolsolver.ForwardVDTypeResolver;
import org.kryun.symbol.pkg.symbolsolver.MethodOriginRegister;
import org.kryun.symbol.pkg.symbolsolver.TypeMapperManager;

class TypeResolverManager {
    private final TypeMapperManager typeMapperManager = TypeMapperManager.getTypeMapperManager();
    private final ForwardRMTypeResolver returnMapperTypeResolver = new ForwardRMTypeResolver(typeMapperManager);
    private final ForwardMCETypeResolver methodCallExprTypeResolver = new ForwardMCETypeResolver(typeMapperManager);
    private final ForwardFDTypeResolver fieldDeclTypeResolver = new ForwardFDTypeResolver(typeMapperManager);
    private final ForwardVDTypeResolver variableDeclTypeResolver = new ForwardVDTypeResolver(typeMapperManager);
    private final ForwardParamTypeResolver parameterTypeResolver = new ForwardParamTypeResolver(typeMapperManager);
    private final ClassOriginRegister classOriginRegister = ClassOriginRegister
            .getRegisterClassOrigin(typeMapperManager);
    private final MethodOriginRegister methodOriginRegister = MethodOriginRegister
            .getRegisterMethodOrigin(typeMapperManager);

    protected TypeResolverManager() {
    };

    boolean registerOriginClass(ClassOrInterfaceDeclaration ClassOrInterfaceDeclaration,
            ClassDTO classDto) {
        try {
            return classOriginRegister.registerOriginDtoService(ClassOrInterfaceDeclaration, classDto);
        } catch (Exception e) {
            // System.out.println(e.getMessage());
            return false;
        }
    }

    boolean registerOriginMethod(MethodDeclaration md, MethodDeclarationDTO mdDto) {
        try {
            return methodOriginRegister.registerOriginDtoService(md, mdDto);
        } catch (Exception e) {
            // System.out.println(e.getMessage());
            return false;
        }
    }

    boolean registerReturnType(MethodDeclaration md, ReturnMapperDTO rmDto) {
        try {
            return returnMapperTypeResolver.getTypeResolver().registerRefDtoService(md, rmDto);
        } catch (Exception e) {
            // System.out.println(e.getMessage());
            return false;
        }
    }

    boolean registerParameter(Parameter pm, ParameterDTO pmDto) {
        try {
            return parameterTypeResolver.getTypeResolver().registerRefDtoService(pm, pmDto);
        } catch (Exception e) {
            // System.out.println(e.getMessage());
            return false;
        }
    }

    boolean registerMethodCallExpr(MethodCallExpr mce, MethodCallExprDTO mceDto) {
        try {
            return methodCallExprTypeResolver.getTypeResolver().registerRefDtoService(mce, mceDto);
        } catch (Exception e) {
            // System.out.println(e.getMessage());
            return false;
        }
    }

    boolean registerFieldDecl(FieldDeclaration fd, MemberVariableDeclarationDTO mvdDto) {
        try {
            return fieldDeclTypeResolver.getTypeResolver().registerRefDtoService(fd, mvdDto);
        } catch (Exception e) {
            // System.out.println(e.getMessage());
            return false;
        }
    }

    boolean registerVariableDecl(VariableDeclarationExpr vdExpr, StmtVariableDeclarationDTO stmtDto) {
        try {
            return variableDeclTypeResolver.getTypeResolver().registerRefDtoService(vdExpr, stmtDto);
        } catch (Exception e) {
            // System.out.println(e.getMessage());
            return false;
        }
    }

    void clear() {
        try {
            typeMapperManager.clearTypeMapper();
        } catch (Exception e) {
            // System.out.println(e.getMessage());
        }

    }
}
