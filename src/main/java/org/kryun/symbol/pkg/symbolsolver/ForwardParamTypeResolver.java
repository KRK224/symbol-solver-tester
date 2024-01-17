package org.kryun.symbol.pkg.symbolsolver;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.resolution.types.ResolvedType;
import org.kryun.symbol.model.interfaces.ClassReferable;
import org.kryun.symbol.pkg.symbolsolver.interfaces.generateresolvedtype.GenerateClassResolvedType;
import org.kryun.symbol.pkg.symbolsolver.interfaces.referabletyperesolver.AbstractClassReferableTypeResolver;

public class ForwardParamTypeResolver implements GenerateClassResolvedType<Parameter> {
    private TypeMapperManager tmm;
    private AbstractClassReferableTypeResolver paramTypeResolverImpl;

    public ForwardParamTypeResolver(TypeMapperManager tmm) {
        this.tmm = tmm;
        this.paramTypeResolverImpl = new AbstractClassReferableTypeResolver(tmm) {

            @Override
            public final boolean registerRefDtoService(Node node, ClassReferable refDto) throws Exception {
                try {
                    if (node instanceof Parameter) {
                        Parameter parameter = (Parameter) node;
                        HashcodeDTO hashcodeDTO = generateClassHashcodeDTO(generateResolvedType(parameter));
                        registerRefDtoByHashcodeDTO(hashcodeDTO, refDto);
                        return true;
                    } else {
                        Exception e = new Exception("전달받은 Node의 타입이 Parameter 아닙니다.");
                        throw e;
                    }
                } catch (Exception e) {
                    throw e;
                }
            }

        };
    }

    @Override
    public ResolvedType generateResolvedType(Parameter parameter) throws Exception {
        try {
            return parameter.getType().resolve();
        } catch (Exception e) {
            throw e;
        }
    }

    public AbstractClassReferableTypeResolver getTypeResolver() {
        return paramTypeResolverImpl;
    }

}
