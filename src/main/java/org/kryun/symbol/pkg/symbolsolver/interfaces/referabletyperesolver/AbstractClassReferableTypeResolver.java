package org.kryun.symbol.pkg.symbolsolver.interfaces.referabletyperesolver;

import com.github.javaparser.resolution.types.ResolvedReferenceType;
import com.github.javaparser.resolution.types.ResolvedType;
import java.util.Optional;

/** class 추상 골격 클래스 */
public class AbstractClassReferableTypeResolver implements ReferableTypeResolver<ResolvedType> {

    @Override
    public Optional<String> getFullQualifiedName(ResolvedType resolvedType) {
        try {
            if (resolvedType.isReference()) {
                if (resolvedType.isArray()) {
                    // 추후에 배열의 요소 타입도 체크하여 저장
                    return Optional.empty();
                }
                ResolvedReferenceType resolvedReferenceType = resolvedType.asReferenceType();
                String fullQualifiedName = resolvedReferenceType.getQualifiedName();
                return Optional.of(fullQualifiedName);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            return Optional.empty();

        }
    }
}
