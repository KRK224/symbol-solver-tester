package org.kryun.symbol.pkg.symbolsolver;

import com.github.javaparser.ast.body.MethodDeclaration;
import org.kryun.symbol.model.MethodDeclarationDTO;
import org.kryun.symbol.pkg.symbolsolver.interfaces.originregister.OriginRegister;

public class MethodOriginRegister implements
    OriginRegister<MethodDeclaration, MethodDeclarationDTO> {
    private TypeMapperManager tmm;

    // 상속 방지
    private MethodOriginRegister() {
    }

    private MethodOriginRegister(TypeMapperManager tmm) {
        this.tmm = tmm;
    }

    public static MethodOriginRegister getRegisterMethodOrigin(TypeMapperManager tmm) {
        return new MethodOriginRegister(tmm);
    }

    @Override
    public boolean registerOriginDtoService(MethodDeclaration md, MethodDeclarationDTO mdDto) {
        try {
            // 나 자신의 DTO 등록
            String myHashcode = getMyHashcodeString(md);
            tmm.registerMethodDeclDTO(myHashcode, mdDto);
            tmm.registerMethodDeclIdtoRefList(myHashcode, mdDto);
            return true;
        } catch (Exception e) {
            // System.out.println("registerMethodOriginDtoService Error: " + mdDto.getName()
            // + "\n\t" + e.getMessage());
            return false;
        }
    }

    @Override
    public String getMyHashcodeString(MethodDeclaration md) throws Exception {
        try {
            String hashcode = md.resolve().getQualifiedSignature();
            return hashcode;
        } catch (Exception e) {
            throw e;
        }
    }

}
