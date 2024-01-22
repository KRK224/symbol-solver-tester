package org.kryun.symbol.pkg.symbolsolver;


import com.github.javaparser.ast.body.EnumDeclaration;
import java.util.Optional;
import org.kryun.symbol.pkg.symbolsolver.interfaces.originresolver.OriginResolver;

public class EnumOriginRegister implements OriginResolver<EnumDeclaration> {

    private static class LazyHolder {

        private static final EnumOriginRegister INSTANCE = new EnumOriginRegister();
    }

    public static EnumOriginRegister getInstance() {
        return LazyHolder.INSTANCE;
    }

    @Override
    public Optional<String> getFullQualifiedName(EnumDeclaration originNode)
        throws Exception {
        try {
            String fullQualifiedName = originNode.resolve().getQualifiedName();
            return Optional.of(fullQualifiedName);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
