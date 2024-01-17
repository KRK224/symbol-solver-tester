package org.kryun.symbol.pkg.symbolsolver;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import org.kryun.symbol.model.ClassDTO;
import org.kryun.symbol.pkg.symbolsolver.interfaces.originregister.OriginRegister;

public class ClassOriginRegister implements OriginRegister<ClassOrInterfaceDeclaration, ClassDTO> {
    private TypeMapperManager tmm;

    // 상속 방지
    private ClassOriginRegister() {
    }

    ClassOriginRegister(TypeMapperManager tmm) {
        this.tmm = tmm;
    }

    public static ClassOriginRegister getRegisterClassOrigin(TypeMapperManager tmm) {
        return new ClassOriginRegister(tmm);
    }

    @Override
    public final boolean registerOriginDtoService(ClassOrInterfaceDeclaration classorInterface, ClassDTO classDto) {
        try {
            String myHashcode = getMyHashcodeString(classorInterface);
            tmm.registerClassDTO(myHashcode, classDto);
            tmm.registerClassIdtoRefList(myHashcode, classDto);
            return true;
        } catch (Exception e) {
            // System.out.println("registerClassOriginDtoService Error: " +
            // classDto.getName() + "\n\t" + e.getMessage());
            return false;
        }
    }

    @Override
    public final String getMyHashcodeString(ClassOrInterfaceDeclaration cid) throws Exception {
        try {
            String hashcode = cid.resolve().getQualifiedName();
            return hashcode;
        } catch (Exception e) {
            throw e;
        }
    }

}
