package org.kryun.symbol.pkg.management;

import java.util.ArrayList;
import java.util.List;
import org.kryun.symbol.model.SymbolReferenceDTO;

public class SymbolReferenceManager {

    private final List<SymbolReferenceDTO> symbolReferencdDTOList;

    public SymbolReferenceManager() {
        this.symbolReferencdDTOList = new ArrayList<>();
    }

    public List<SymbolReferenceDTO> getSymbolReferenceDTOList() {
        return this.symbolReferencdDTOList;
    }

    public void symbolReferenceListClear() {
        this.symbolReferencdDTOList.clear();
    }

    public SymbolReferenceDTO buildSymbolReference(Long symbolReferenceId, Long symbolStatusId, String src_path) {
        SymbolReferenceDTO symbolReferenceDTO = new SymbolReferenceDTO();

        symbolReferenceDTO.setSymbolReferenceId(symbolReferenceId);
        symbolReferenceDTO.setSymbolStatusId(symbolStatusId);
        symbolReferenceDTO.setSrcPath(src_path);

        symbolReferencdDTOList.add(symbolReferenceDTO);

        return symbolReferenceDTO;
    }

}
