package org.kryun.symbol.pkg.symbolsolver;

import java.util.Optional;

import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.resolution.types.ResolvedType;
import org.kryun.symbol.pkg.symbolsolver.interfaces.generateresolvedtype.GenerateClassResolvedType;
import org.kryun.symbol.pkg.symbolsolver.interfaces.referabletyperesolver.AbstractClassReferableTypeResolver;

public class ForwardVDTypeResolver implements GenerateClassResolvedType<VariableDeclarationExpr> {
    private static class LazyHolder {
        private static final ForwardVDTypeResolver INSTANCE = new ForwardVDTypeResolver();
    }

    public static ForwardVDTypeResolver getInstance() {
        return LazyHolder.INSTANCE;
    }

    private AbstractClassReferableTypeResolver vdTypeResolverImpl;

    private ForwardVDTypeResolver() {
        this.vdTypeResolverImpl = new AbstractClassReferableTypeResolver();
    }

    @Override
    public final ResolvedType generateResolvedType(VariableDeclarationExpr vde) throws Exception {
        try {
            return vde.getVariable(0).getType().resolve();
        } catch (Exception e) {
            throw e;
        }
    }

    public Optional<String> getFullQualifiedName(VariableDeclarationExpr vde) {
        try {
            return vdTypeResolverImpl.getFullQualifiedName(generateResolvedType(vde));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

}
