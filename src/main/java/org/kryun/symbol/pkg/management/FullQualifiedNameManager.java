package org.kryun.symbol.pkg.management;


import java.util.ArrayList;
import java.util.List;
import org.kryun.symbol.model.FullQualifiedNameDTO;

public class FullQualifiedNameManager {

    private final List<FullQualifiedNameDTO> fullQualifiedNameDTOList;

    public FullQualifiedNameManager() {
        this.fullQualifiedNameDTOList = new ArrayList<>();


    }

    public List<FullQualifiedNameDTO> getFullQualifiedNameDTOList() {
        return fullQualifiedNameDTOList;
    }

    public void fullQualifiedNameListClear() {
        this.fullQualifiedNameDTOList.clear();
    }

    public FullQualifiedNameDTO buildFullQualifiedName(Long fullQualifiedNameId,
        Long symbolStatusId, String fullQualifiedName, Boolean isJdk) {
        FullQualifiedNameDTO fullQualifiedNameDTO = new FullQualifiedNameDTO();

        fullQualifiedNameDTO.setFullQualifiedNameId(fullQualifiedNameId);
        fullQualifiedNameDTO.setSymbolStatusId(symbolStatusId);
        fullQualifiedNameDTO.setFullQualifiedName(fullQualifiedName);
        fullQualifiedNameDTO.setIsJDK(isJdk);

        fullQualifiedNameDTOList.add(fullQualifiedNameDTO);
        return fullQualifiedNameDTO;
    }
}
