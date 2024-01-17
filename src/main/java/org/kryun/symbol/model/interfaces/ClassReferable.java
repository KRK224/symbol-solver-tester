package org.kryun.symbol.model.interfaces;

public interface ClassReferable extends Referable {
    /*
     * 자신의 TypeClassId를 반환하는 Getter 함수 호출
     */
    public abstract Long getTypeClassIdImpl();

    /**
     * 자신의 TypeClassId를 저장하는 Setter 함수 호출
     */
    public abstract void setTypeClassIdImpl(Long classId);
}
