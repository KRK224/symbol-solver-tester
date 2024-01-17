package org.kryun.symbol.pkg.symbolsolver;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.resolution.types.ResolvedType;
import org.kryun.symbol.model.interfaces.ClassReferable;
import org.kryun.symbol.pkg.symbolsolver.interfaces.generateresolvedtype.GenerateClassResolvedType;
import org.kryun.symbol.pkg.symbolsolver.interfaces.referabletyperesolver.AbstractClassReferableTypeResolver;

public class ForwardRMTypeResolver implements GenerateClassResolvedType<MethodDeclaration> {
    private TypeMapperManager tmm;
    private AbstractClassReferableTypeResolver rmTypeResolverImpl;

    public ForwardRMTypeResolver(TypeMapperManager tmm) {
        this.tmm = tmm;
        this.rmTypeResolverImpl = new AbstractClassReferableTypeResolver(tmm) {

            @Override
            public final boolean registerRefDtoService(Node node, ClassReferable refDto) throws Exception {
                try {
                    if (node instanceof MethodDeclaration) {
                        MethodDeclaration md = (MethodDeclaration) node;
                        HashcodeDTO hashcodeDTO = generateClassHashcodeDTO(generateResolvedType(md));
                        registerRefDtoByHashcodeDTO(hashcodeDTO, refDto);
                        return true;
                    } else {
                        Exception e = new Exception("전달받은 Node의 타입이 MethodDelcaration이 아닙니다.");
                        throw e;
                    }
                } catch (Exception e) {
                    throw e;
                }
            }
        };
    }

    @Override
    public final ResolvedType generateResolvedType(MethodDeclaration md) throws Exception {
        try {
            return md.resolve().getReturnType();
        } catch (Exception e) {
            throw e;
        }
    }

    public AbstractClassReferableTypeResolver getTypeResolver() {
        return rmTypeResolverImpl;
    }

}
