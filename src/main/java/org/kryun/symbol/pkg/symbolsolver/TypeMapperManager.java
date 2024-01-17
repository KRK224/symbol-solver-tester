package org.kryun.symbol.pkg.symbolsolver;

import com.tmax.symbol.model.dto.ClassDTO;
import com.tmax.symbol.model.dto.MethodDeclarationDTO;
import com.tmax.symbol.model.dto.interfaces.ClassReferable;
import com.tmax.symbol.model.dto.interfaces.MethodReferable;
import com.tmax.symbol.model.dto.interfaces.Referable;
import java.util.ArrayList;
import java.util.List;

/**
 * TypeResolver가 처리한 Data를 TypeMapperManager통해 TypeMapper에 저장
 * 
 * Todo. 만약 저장할 Origin DTO(class, methodDecl)의 종류가 늘어나면 인터페이스화 고려할 것
 */
public class TypeMapperManager {

    private final TypeMapper<MethodDeclarationDTO, MethodReferable> methodTypeMapper = new TypeMapper<>();
    private final TypeMapper<ClassDTO, ClassReferable> classTypeMapper = new TypeMapper<>();

    // 상속(확장) 방지
    private TypeMapperManager() {
    }

    public static TypeMapperManager getTypeMapperManager() {
        return new TypeMapperManager();
    }

    // static으로 물고 있는 typeMapper clear;
    public boolean clearTypeMapper() {
        try {
            methodTypeMapper.clear();
            classTypeMapper.clear();
            return true;
        } catch (Exception e) {
            // System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean registerClassDTO(String hashcode, ClassDTO classDto) {
        try {
            return classTypeMapper.setOriginDto(hashcode, classDto);
        } catch (Exception e) {
            // 로거로 stacktrace 및 error 출력하기
            // System.out.println("hashcode: " + hashcode + "\n" + e.getMessage());
            return false;
        }
    }

    public boolean registerMethodDeclDTO(String hashcode, MethodDeclarationDTO mdDto) {
        try {
            return methodTypeMapper.setOriginDto(hashcode, mdDto);
        } catch (Exception e) {
            // 로거로 stacktrace 및 error 출력하기
            // System.out.println("hashcode: " + hashcode + "\n" + e.getMessage());
            return false;
        }
    }

    /*
     * ClassDTO id를 Referable DTO에 저장하는 로직
     * 
     * @return
     * true: 성공
     * false: 실패 (리스트가 존재하지 않는 경우도 포함)
     */
    public boolean registerClassIdtoRefList(String hashcode, ClassDTO classDto) {
        try {
            Long classDtoId = classDto.getClassId();
            List<ClassReferable> classRefDtoList = classTypeMapper.depensiveGetRefDtoList(hashcode);
            // id 등록 로직
            classRefDtoList.stream().forEach((crd) -> {
                crd.setTypeClassIdImpl(classDtoId);
            });
            // Ref List 초기화
            classTypeMapper.clearRefList(hashcode);
            return true;
        } catch (NullPointerException npe) {
            // 로직상 존재하지 않을 경우 던지는 에러 - 디버깅 시에만 출력
            // System.out.println("hashcode: " + hashcode + "\n" + npe.getMessage());
            return false;
        } catch (Exception e) {
            // System.out.println("hashcode: " + hashcode + "\n" + e.getMessage());
            return false;
        }
    }

    public boolean registerMethodDeclIdtoRefList(String hashcode, MethodDeclarationDTO mdDto) {
        try {
            Long methodDeclId = mdDto.getMethodDeclId();
            Long typeClassid = mdDto.getBelongedClassId();

            // MethodCallExpr은 ClassReferable이기도 하다.
            // id 등록 과정
            List<Referable> methodRefDtoList = new ArrayList<Referable>(
                    methodTypeMapper.depensiveGetRefDtoList(hashcode));

            methodRefDtoList.stream().forEach((r) -> {
                if (r instanceof MethodReferable) {
                    MethodReferable mr = (MethodReferable) r;
                    mr.setMethodDeclIdImpl(methodDeclId);
                }
                if (r instanceof ClassReferable) {
                    ClassReferable cr = (ClassReferable) r;
                    cr.setTypeClassIdImpl(typeClassid);
                }
            });
            // Ref List 초기화
            methodTypeMapper.clearRefList(hashcode);
            return true;
        } catch (NullPointerException npe) {
            // 로직상 존재하지 않을 경우 던지는 에러 - 디버깅 시에만 출력
            // System.out.println("hashcode: " + hashcode + "\n" + npe.getMessage());
            return false;
        } catch (Exception e) {
            // System.out.println("hashcode: " + hashcode + "\n" + e.getMessage());
            return false;
        }
    }

    // Ref DTO 처리를 위한 함수들

    public MethodDeclarationDTO getMethodDeclarationDTO(String hashcode) throws Exception {
        try {
            return methodTypeMapper.getOriginDto(hashcode);
        } catch (Exception e) {
            throw e;
        }
    }

    public ClassDTO getClassDTO(String hashcode) throws Exception {
        try {
            return classTypeMapper.getOriginDto(hashcode);
        } catch (Exception e) {
            throw e;
        }
    }

    public void addMethodReferable(String hashcode, MethodReferable mr) throws Exception {
        try {
            methodTypeMapper.addReferenceDto(hashcode, mr);
        } catch (Exception e) {
            throw e;
        }
    }

    public void addClassReferable(String hashcode, ClassReferable cr) throws Exception {
        try {
            classTypeMapper.addReferenceDto(hashcode, cr);
        } catch (Exception e) {
            throw e;
        }
    }
}
