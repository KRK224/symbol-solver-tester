package org.kryun.symbol.pkg.symbolsolver;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.resolution.declarations.ResolvedMethodDeclaration;
import org.kryun.symbol.model.interfaces.MethodReferable;
import org.kryun.symbol.pkg.symbolsolver.interfaces.generateresolvedtype.GenerateMethodResolvedType;
import org.kryun.symbol.pkg.symbolsolver.interfaces.referabletyperesolver.AbstractMethodReferableTypeResolver;

/**
 * MethodCallExpr 처리에 대한
 * 
 */
public class ForwardMCETypeResolver
        implements GenerateMethodResolvedType<MethodCallExpr> {

    private TypeMapperManager tmm;
    private AbstractMethodReferableTypeResolver methodTypeResolverImpl;

    public ForwardMCETypeResolver(TypeMapperManager tmm) {
        this.tmm = tmm;
        this.methodTypeResolverImpl = new AbstractMethodReferableTypeResolver(tmm) {

            @Override
            public final boolean registerRefDtoService(Node node, MethodReferable refDto) throws Exception {
                try {
                    if (node instanceof MethodCallExpr) {
                        MethodCallExpr mce = (MethodCallExpr) node;
                        // hashcodeDTO 생성
                        HashcodeDTO hashcodeDTO = generateMDHashcodeDTO(generateResolvedType(mce));
                        // refDTO에 id 등록 또는 자기 자신 등록 로직
                        registerRefDtoByHashcodeDTO(hashcodeDTO, refDto);
                        return true;
                    } else {
                        Exception e = new Exception("전달받은 Node의 타입이 MethodCallExpr이 아닙니다.");
                        throw e;
                    }
                } catch (Exception e) {
                    throw e;
                }
            }
        };
    }

    @Override
    public final ResolvedMethodDeclaration generateResolvedType(MethodCallExpr mce) throws Exception {
        try {
            return mce.resolve();
        } catch (Exception e) {
            throw e;
        }
    }

    // 전달 함수
    public AbstractMethodReferableTypeResolver getTypeResolver() {
        return methodTypeResolverImpl;
    }
}
