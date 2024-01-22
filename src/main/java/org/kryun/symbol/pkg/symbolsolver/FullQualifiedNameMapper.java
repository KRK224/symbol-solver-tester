package org.kryun.symbol.pkg.symbolsolver;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.kryun.symbol.model.FullQualifiedNameDTO;

class FullQualifiedNameMapper {
    private ConcurrentHashMap<String, FullQualifiedNameDTO> fullQualifiedNameDTOHashMap = new ConcurrentHashMap<>();

    FullQualifiedNameMapper() {
    }

    void clear() throws Exception {
        try {
            fullQualifiedNameDTOHashMap.clear();
        } catch (Exception e) {
            throw e;
        }
    }

    Optional<FullQualifiedNameDTO> getFullQualifiedNameDTO(String fullQualifiedName) {
        try {
            if (isFullQualifiedNameDTOExist(fullQualifiedName)) {
                return Optional.of(fullQualifiedNameDTOHashMap.get(fullQualifiedName));
            }
            return Optional.empty();
        } catch (Exception e) {
            // TODO: handle exception
            return Optional.empty();
        }
    }

    boolean registerFullQualifiedNameDTO(String fullQualifiedname, FullQualifiedNameDTO fullQualifiedNameDTO)
            throws Exception {
        if (isFullQualifiedNameDTOExist(fullQualifiedname)) {
            return false;
        }
        fullQualifiedNameDTOHashMap.put(fullQualifiedname, fullQualifiedNameDTO);
        return true;
    }

    private boolean isFullQualifiedNameDTOExist(String fullQualifiedName) {
        // System.out.println(fullQualifiedNameDTOHashMap.values());
        return fullQualifiedNameDTOHashMap.containsKey(fullQualifiedName);
    }

}
