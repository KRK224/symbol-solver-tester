package org.kryun.symbol.pkg.symbolsolver.interfaces.referabletyperesolver;

import com.github.javaparser.resolution.types.ResolvedReferenceType;
import com.github.javaparser.resolution.types.ResolvedType;
import org.kryun.global.enums.SymbolReferenceEnum;
import org.kryun.symbol.model.ClassDTO;
import org.kryun.symbol.model.interfaces.ClassReferable;
import org.kryun.symbol.pkg.symbolsolver.TypeMapperManager;

/** class 추상 골격 클래스 */
public abstract class AbstractClassReferableTypeResolver implements ReferableTypeResolver<ClassDTO, ClassReferable> {
    private TypeMapperManager tmm;

    public AbstractClassReferableTypeResolver(TypeMapperManager tmm) {
        this.tmm = tmm;
    }

    /* referable node에서 resove한 값 받아와 생성 */
    protected final HashcodeDTO generateClassHashcodeDTO(ResolvedType rt) {
        try {
            HashcodeDTO hashcodeDto = new HashcodeDTO();
            hashcodeDto.setIsResolved(true);
            if (rt.isReference()) { // 레퍼런스 타입인지 확인
                if (rt.isArray()) {// 배열인지 확인
                    hashcodeDto.setIsArray(true);
                    return hashcodeDto;
                }
                ResolvedReferenceType rrt = rt.asReferenceType();
                // 배열이 아니면서 레퍼런스 타입인 변수
                String classWithPackage = rrt.getQualifiedName();

                // 우선 typeParameter가 존재하는지 체크
                if (!rrt.typeParametersValues().isEmpty()) {
                    hashcodeDto.setHasTypeParameter(true);
                }

                if (ReferableTypeResolver.isJdkPackage(classWithPackage)) { // jdk package인 경우
                    hashcodeDto.setType("jdk");
                    return hashcodeDto;
                } else { // 우리가 만든 패키지
                    hashcodeDto.setType("reference");
                    hashcodeDto.setHashcode(classWithPackage);
                    return hashcodeDto;
                }

            } else { // primitive 타입으로 종료
                hashcodeDto.setType("primitive");
                return hashcodeDto;
            }

        } catch (Exception e) {
            // System.out.println("generateClassHashcodeDTO 에러: " + "\n\t" +
            // e.getMessage());
            return new HashcodeDTO();
        }
    }

    @Override
    public final void registerRefDtoByHashcodeDTO(HashcodeDTO hashcodeDTO, ClassReferable refDTO) throws Exception {
        try {
            // resolve 실패한 경우 -100L 등록 후 종료
            if (!hashcodeDTO.getIsResolved()) {
                refDTO.setTypeClassIdImpl(SymbolReferenceEnum.NULL.getReferenceTypeId());
                return;
            }

            // 배열인 경우
            if (hashcodeDTO.getIsArray()) {
                refDTO.setTypeClassIdImpl(SymbolReferenceEnum.ARRAY.getReferenceTypeId());
                return;
            }

            if (hashcodeDTO.getType().equals("primitive")) {
                refDTO.setTypeClassIdImpl(SymbolReferenceEnum.PRIMITIVE.getReferenceTypeId());
                return;
            }
            if (hashcodeDTO.getType().equals("jdk")) {
                refDTO.setTypeClassIdImpl(SymbolReferenceEnum.JDK.getReferenceTypeId());
                return;
            }

            String hashcode = hashcodeDTO.getHashcode();
            ClassDTO originDTO = getOriginDTO(hashcode);

            if (originDTO == null) { // 현재 originDTO 생성 전
                tmm.addClassReferable(hashcode, refDTO);
            } else {
                Long typeClassId = originDTO.getClassId();
                refDTO.setTypeClassIdImpl(typeClassId);
            }
            return;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public final ClassDTO getOriginDTO(String hashcode) {
        try {
            return tmm.getClassDTO(hashcode);
        } catch (NullPointerException npe) {
            // 로직상 존재하지 않을 때 던지는 에러
            // System.out.println("hashcode: " + hashcode + "\n" + npe.getMessage());
            return null;
        } catch (Exception e) {
            // System.out.println(e.getMessage());
            return null;
        }
    }
}
