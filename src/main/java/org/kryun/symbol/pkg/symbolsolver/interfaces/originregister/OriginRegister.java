package org.kryun.symbol.pkg.symbolsolver.interfaces.originregister;

/**
 * T: Javaparser의 Class or MethodDecl Node
 * F: DB에 저장하기 위해 생성한 DTO
 */
public interface OriginRegister<T, F> {
    /**
     * TypeMapperManager에 내 자신의 DTO를 등록하는 함수
     * 
     * @param originNode
     * @param originDTO
     * @return
     * @throws Exception
     */
    public abstract boolean registerOriginDtoService(T originNode, F originDTO);

    /**
     * TypeMapperManager에 등록하기 위한 Key값을 생성해주는 함수
     * 
     * @param originNode
     * @return
     * @throws Exception
     */
    public abstract String getMyHashcodeString(T originNode) throws Exception;
}
