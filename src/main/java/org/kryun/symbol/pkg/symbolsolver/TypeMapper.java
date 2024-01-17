package org.kryun.symbol.pkg.symbolsolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class TypeMapper<T, R> {
    private HashMap<String, T> originDtoHashMap = new HashMap<String, T>();
    private HashMap<String, List<R>> referenceDtoListHashMap = new HashMap<String, List<R>>();

    // 생성자
    TypeMapper() {
    }

    void clear() throws Exception {
        try {
            originDtoHashMap = new HashMap<String, T>();
            referenceDtoListHashMap = new HashMap<String, List<R>>();
            // System.gc();
        } catch (Exception e) {
            throw e;
        }
    }

    // OriginDTO 저장 후에는 RefList 초기화
    void clearRefList(String hashcode) throws Exception {
        try {
            depensiveSetRefDtoList(hashcode, new ArrayList<R>());
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * @param hashcode
     * @return
     * @throws Exception
     */
    T getOriginDto(String hashcode) throws Exception {
        if (!isOrignDtoExist(hashcode)) {
            NullPointerException e = new NullPointerException("origin " + hashcode + " DTO가 존재하지 않습니다.");
            throw e;
        }
        return originDtoHashMap.get(hashcode);
    }

    List<R> depensiveGetRefDtoList(String hashcode) throws Exception {
        if (!isRefDtoListExist(hashcode)) {
            NullPointerException e = new NullPointerException("참조 " + hashcode + " DTO 리스트가 존재하지 않습니다.");
            throw e;
        }
        List<R> shallowCopyList = new ArrayList<R>(referenceDtoListHashMap.get(hashcode));
        return shallowCopyList;
    }

    boolean setOriginDto(String hashcode, T originDto) throws Exception {
        if (isOrignDtoExist(hashcode)) {
            // System.out.println("등록하려는 originDto가 존재합니다 " + hashcode);
            return false;
        }
        originDtoHashMap.put(hashcode, originDto);
        return true;
    }

    boolean addReferenceDto(String hashcode, R refDto) throws Exception {
        try {
            // 현재 originType에 대한 refenceList 존재
            if (referenceDtoListHashMap.containsKey(hashcode)) {
                List<R> shallowCopyList = depensiveGetRefDtoList(hashcode);
                shallowCopyList.add(refDto);
                referenceDtoListHashMap.put(hashcode, shallowCopyList);
            } else {
                List<R> newList = new ArrayList<R>();
                newList.add(refDto);
                referenceDtoListHashMap.put(hashcode, newList);
            }
            return true;

        } catch (Exception e) {
            Exception myException = new Exception(hashcode + " addReferenceDto 에러  " + e.getMessage());
            myException.initCause(e);
            throw myException;
        }
    }

    private boolean depensiveSetRefDtoList(String hashcode, List<R> refDtoList) throws Exception {
        try {
            // 일단 얕은 복사로 전달
            List<R> tempList = new ArrayList<R>(refDtoList);
            referenceDtoListHashMap.put(hashcode, tempList);
            return true;
        } catch (Exception e) {
            Exception myException = new Exception(hashcode + " setRefDtoList 에러  " + e.getMessage());
            myException.initCause(e);
            throw myException;
        }
    }

    private boolean isOrignDtoExist(String hashcode) {
        return originDtoHashMap.containsKey(hashcode);
    }

    private boolean isRefDtoListExist(String hashcode) {
        return referenceDtoListHashMap.containsKey(hashcode);
    }

}
