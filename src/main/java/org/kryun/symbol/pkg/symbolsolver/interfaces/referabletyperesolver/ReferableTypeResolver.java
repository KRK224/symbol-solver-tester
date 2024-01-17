package org.kryun.symbol.pkg.symbolsolver.interfaces.referabletyperesolver;

import com.github.javaparser.ast.Node;
import com.github.javaparser.resolution.TypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;

public interface ReferableTypeResolver<T, F> {

    public static final class HashcodeDTO {
        private String hashcode; // 찾은 origin의 구분값
        private boolean isResolved = false; // symbol solver로 찾았는지 확인
        private String type; // primitive, reference, jdk, md
        private boolean isArray = false; // 배열 값인지 확인 -> 배열이 가지고 있는 타입 추후에 넣을 수도
        private boolean hasTypeParameter = false; // <T> 값을 가지고 있는지 확인 -> 추후에 넣을 수도

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getHashcode() {
            return hashcode;
        }

        public void setHashcode(String hashcode) {
            this.hashcode = hashcode;
        }

        public boolean getIsResolved() {
            return isResolved;
        }

        public void setIsResolved(boolean isResolved) {
            this.isResolved = isResolved;
        }

        public boolean getIsArray() {
            return isArray;
        }

        public void setIsArray(boolean isArray) {
            this.isArray = isArray;
        }

        public boolean getHasTypeParameter() {
            return hasTypeParameter;
        }

        public void setHasTypeParameter(boolean hasTypeParameter) {
            this.hasTypeParameter = hasTypeParameter;
        }
    }

    public static final TypeSolver reflectionSolver = new ReflectionTypeSolver();

    /**
     * JDK Package인지 검사하는 함수
     * 
     * @param packageName
     * @return
     */
    static boolean isJdkPackage(String packageName) {
        try {
            reflectionSolver.solveType(packageName);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * RefDTO에 Resolved DTO ID 등록 또는 Resolved Type Node List에 자신 등록
     * 
     * @param node   JavaParser node
     * @param refDto Referable
     * @return
     * @throws Exception
     */
    public abstract boolean registerRefDtoService(Node node, F refDto) throws Exception;

    /**
     * HashcodeDTO를 통해 refDTO 등록
     * 
     * @param hashcodeDto
     * @param refDto      Referable
     * @throws Exception
     */
    public abstract void registerRefDtoByHashcodeDTO(HashcodeDTO hashcodeDto, F refDto) throws Exception;

    /**
     * hashcode String을 통해 OriginDTO(T) 반환 함수
     * 
     * @param hashcode
     * @return
     * @throws Exception
     */
    public abstract T getOriginDTO(String hashcode);
}
