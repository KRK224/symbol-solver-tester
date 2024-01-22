package org.kryun.symbol.pkg.symbolsolver.interfaces.referabletyperesolver;

import com.github.javaparser.resolution.TypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;
import java.util.Optional;

public interface ReferableTypeResolver<T> {

    public static final TypeSolver reflectionSolver = new ReflectionTypeSolver();

    /**
     * JDK Package인지 검사하는 함수
     *
     * @param packageName
     * @return
     */
    static boolean isJdkPackage(String packageName) {
        try {
            reflectionSolver.solveType(packageName);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    static boolean isJdkPackage(String fqn, String methodName) {
        try {
            // com.tmax.com.tmax.symbol.pkg.symbolsolver.interfaces.referabletyperesolver.ReferableTypeResolver.isJdkPackage()...
            int position = fqn.indexOf(methodName);
            String packageName = fqn.substring(0, position - 1);
            reflectionSolver.solveType(packageName);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public abstract Optional<String> getFullQualifiedName(T resolvedType);

}
