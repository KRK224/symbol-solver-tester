package org.kryun.symbol.pkg.symbolsolver;

import java.util.Optional;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import org.kryun.symbol.pkg.symbolsolver.interfaces.originresolver.OriginResolver;

public class ClassOriginRegister implements OriginResolver<ClassOrInterfaceDeclaration> {

    private static class LazyHolder {
        private static final ClassOriginRegister INSTANCE = new ClassOriginRegister();
    }

    public static ClassOriginRegister getInstance() {
        return LazyHolder.INSTANCE;
    }

    // 상속 방지
    private ClassOriginRegister() {
    }

    @Override
    public Optional<String> getFullQualifiedName(ClassOrInterfaceDeclaration originNode) throws Exception {
        try {
            String fullQualifiedName = originNode.resolve().getQualifiedName();
            return Optional.of(fullQualifiedName);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

}
