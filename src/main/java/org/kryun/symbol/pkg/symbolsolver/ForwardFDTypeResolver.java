package org.kryun.symbol.pkg.symbolsolver;

import java.util.Optional;

import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.resolution.types.ResolvedType;
import org.kryun.symbol.pkg.symbolsolver.interfaces.generateresolvedtype.GenerateClassResolvedType;
import org.kryun.symbol.pkg.symbolsolver.interfaces.referabletyperesolver.AbstractClassReferableTypeResolver;

public class ForwardFDTypeResolver implements GenerateClassResolvedType<FieldDeclaration> {
    private static class LazyHolder {
        private static final ForwardFDTypeResolver INSTANCE = new ForwardFDTypeResolver();
    }

    public static ForwardFDTypeResolver getInstance() {
        return LazyHolder.INSTANCE;
    }

    private AbstractClassReferableTypeResolver fdTypeResolverImpl;

    private ForwardFDTypeResolver() {
        this.fdTypeResolverImpl = new AbstractClassReferableTypeResolver();
    }

    @Override
    public final ResolvedType generateResolvedType(FieldDeclaration fd) throws Exception {
        try {
            return fd.getVariable(0).getType().resolve();
        } catch (Exception e) {
            throw e;
        }
    }

    public Optional<String> getFullQualifiedName(FieldDeclaration fd) {
        try {
            return fdTypeResolverImpl.getFullQualifiedName(generateResolvedType(fd));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
