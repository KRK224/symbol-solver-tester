package org.kryun.symbol.pkg.symbolsolver.interfaces.referabletyperesolver;

import com.github.javaparser.resolution.declarations.ResolvedMethodDeclaration;
import com.tmax.global.enums.SymbolReferenceEnum;
import com.tmax.symbol.model.dto.MethodDeclarationDTO;
import com.tmax.symbol.model.dto.interfaces.ClassReferable;
import com.tmax.symbol.model.dto.interfaces.MethodReferable;
import com.tmax.symbol.pkg.symbolsolver.TypeMapperManager;

/** method 추상 골격 클래스 */
public abstract class AbstractMethodReferableTypeResolver
        implements ReferableTypeResolver<MethodDeclarationDTO, MethodReferable> {

    private TypeMapperManager tmm;

    public AbstractMethodReferableTypeResolver(TypeMapperManager tmm) {
        this.tmm = tmm;
    }

    /* referable node에서 resove한 값 받아와 생성 */
    protected final HashcodeDTO generateMDHashcodeDTO(ResolvedMethodDeclaration rmd) {
        try {
            String mdQualifiedSignature = rmd.getQualifiedSignature();
            String packageClassName = rmd.getPackageName() + "." + rmd.getClassName();
            HashcodeDTO hashcodeDto = new HashcodeDTO();
            hashcodeDto.setType("md");
            hashcodeDto.setIsResolved(true);

            // Jdk Package인 경우, 타입 변경후 종료
            if (ReferableTypeResolver.isJdkPackage(packageClassName)) {
                hashcodeDto.setType("jdk");
                return hashcodeDto;
            }

            hashcodeDto.setHashcode(mdQualifiedSignature);
            return hashcodeDto;
        } catch (Exception e) {
            // System.out.println("generateMDHashcodeDTO 에러: " + "\n\t" + e.getMessage());
            return new HashcodeDTO();
        }
    }

    @Override
    public final void registerRefDtoByHashcodeDTO(HashcodeDTO hashcodeDto, MethodReferable refDto) throws Exception {
        try {
            // resolve 실패한 경우 -100L 등록 후 종료
            if (!hashcodeDto.getIsResolved()) {
                refDto.setMethodDeclIdImpl(SymbolReferenceEnum.NULL.getReferenceTypeId());
                if (refDto instanceof ClassReferable) {
                    ClassReferable tempCr = (ClassReferable) refDto;
                    tempCr.setTypeClassIdImpl(SymbolReferenceEnum.NULL.getReferenceTypeId());
                }
                return;
            }

            // jdk인 경우
            if (hashcodeDto.getType().equals("jdk")) {
                refDto.setMethodDeclIdImpl(SymbolReferenceEnum.JDK.getReferenceTypeId());
                if (refDto instanceof ClassReferable) {
                    ClassReferable tempCr = (ClassReferable) refDto;
                    tempCr.setTypeClassIdImpl(SymbolReferenceEnum.JDK.getReferenceTypeId());
                }
                return;
            }

            // resolve 성공한 경우
            String hashcode = hashcodeDto.getHashcode();
            // 현재 TypeMapper에 originDTO 존재 여부 확인
            MethodDeclarationDTO originDTO = getOriginDTO(hashcode);
            if (originDTO == null) { // 없으면 자기 자신 등록
                tmm.addMethodReferable(hashcode, refDto);
            } else { // 있으면 내 DTO에 originDTO id 등록
                Long typeMethodDeclId = originDTO.getMethodDeclId();
                Long typeClassId = originDTO.getBelongedClassId();
                refDto.setMethodDeclIdImpl(typeMethodDeclId);
                if (refDto instanceof ClassReferable) {
                    ClassReferable tempCr = (ClassReferable) refDto;
                    tempCr.setTypeClassIdImpl(typeClassId);
                }
            }
            return;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public final MethodDeclarationDTO getOriginDTO(String hashcode) {
        try {
            return tmm.getMethodDeclarationDTO(hashcode);
        } catch (NullPointerException npe) {
            // 로직상 존재하지 않을 때 던지는 에러
            // System.out.println("hashcode: " + hashcode + "\n" + npe.getMessage());
            return null;
        } catch (Exception e) {
            // System.out.println("hashcode: " + hashcode + "\n" + e.getMessage());
            return null;
        }
    }

}
