package org.kryun.symbol.pkg.symbolsolver;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.resolution.types.ResolvedType;
import org.kryun.symbol.model.interfaces.ClassReferable;
import org.kryun.symbol.pkg.symbolsolver.interfaces.generateresolvedtype.GenerateClassResolvedType;
import org.kryun.symbol.pkg.symbolsolver.interfaces.referabletyperesolver.AbstractClassReferableTypeResolver;

public class ForwardVDTypeResolver implements GenerateClassResolvedType<VariableDeclarationExpr> {
    private TypeMapperManager tmm;
    private AbstractClassReferableTypeResolver vdTypeResolverImpl;

    public ForwardVDTypeResolver(TypeMapperManager tmm) {
        this.tmm = tmm;
        this.vdTypeResolverImpl = new AbstractClassReferableTypeResolver(tmm) {
            @Override
            public final boolean registerRefDtoService(Node node, ClassReferable refDto) throws Exception {
                try {
                    if (node instanceof VariableDeclarationExpr) {
                        VariableDeclarationExpr vde = (VariableDeclarationExpr) node;
                        HashcodeDTO hashcodeDTO = generateClassHashcodeDTO(generateResolvedType(vde));
                        registerRefDtoByHashcodeDTO(hashcodeDTO, refDto);
                        return true;
                    } else {
                        Exception e = new Exception("전달받은 Node의 타입이 VariableDelcarationExpr이 아닙니다.");
                        throw e;
                    }
                } catch (Exception e) {
                    throw e;
                }
            }
        };
    }

    @Override
    public final ResolvedType generateResolvedType(VariableDeclarationExpr vde) throws Exception {
        try {
            return vde.getVariable(0).getType().resolve();
        } catch (Exception e) {
            throw e;
        }
    }

    public AbstractClassReferableTypeResolver getTypeResolver() {
        return vdTypeResolverImpl;
    }

}
