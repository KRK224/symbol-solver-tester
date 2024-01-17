package org.kryun.symbol.pkg.symbolsolver;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.resolution.types.ResolvedType;
import com.tmax.symbol.model.dto.interfaces.ClassReferable;
import com.tmax.symbol.pkg.symbolsolver.interfaces.generateresolvedtype.GenerateClassResolvedType;
import com.tmax.symbol.pkg.symbolsolver.interfaces.referabletyperesolver.AbstractClassReferableTypeResolver;

public class ForwardFDTypeResolver implements GenerateClassResolvedType<FieldDeclaration> {
    private TypeMapperManager tmm;
    private AbstractClassReferableTypeResolver fdTypeResolverImpl;

    public ForwardFDTypeResolver(TypeMapperManager tmm) {
        this.tmm = tmm;
        this.fdTypeResolverImpl = new AbstractClassReferableTypeResolver(tmm) {

            @Override
            public final boolean registerRefDtoService(Node node, ClassReferable refDto) throws Exception {
                try {
                    if (node instanceof FieldDeclaration) {
                        FieldDeclaration fd = (FieldDeclaration) node;
                        HashcodeDTO hashcodeDTO = generateClassHashcodeDTO(generateResolvedType(fd));
                        registerRefDtoByHashcodeDTO(hashcodeDTO, refDto);
                        return true;
                    } else {
                        Exception e = new Exception("전달받은 Node의 타입이 FieldDeclaration 아닙니다.");
                        throw e;
                    }
                } catch (Exception e) {
                    throw e;
                }
            }
        };
    }

    @Override
    public final ResolvedType generateResolvedType(FieldDeclaration fd) throws Exception {
        try {
            return fd.getVariable(0).getType().resolve();
        } catch (Exception e) {
            throw e;
        }
    }

    public AbstractClassReferableTypeResolver getTypeResolver() {
        return fdTypeResolverImpl;
    }

}
