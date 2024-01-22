package org.kryun.symbol.pkg.symbolsolver;

import java.util.Optional;

import com.github.javaparser.ast.body.MethodDeclaration;
import org.kryun.symbol.pkg.symbolsolver.interfaces.originresolver.OriginResolver;

public class MethodOriginRegister implements OriginResolver<MethodDeclaration> {
    private static class LazyHolder {
        private static final MethodOriginRegister INSTANCE = new MethodOriginRegister();
    }

    public static MethodOriginRegister getInstance() {
        return LazyHolder.INSTANCE;
    }

    // 상속 방지
    private MethodOriginRegister() {
    }

    @Override
    public Optional<String> getFullQualifiedName(MethodDeclaration originNode) throws Exception {
        try {
            String fullQualifiedSignature = originNode.resolve().getQualifiedSignature();
            return Optional.of(fullQualifiedSignature);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

}
