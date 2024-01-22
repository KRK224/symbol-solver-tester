package org.kryun.symbol.pkg.management;

import com.github.javaparser.ast.Node;
import java.util.ArrayList;
import java.util.List;
import org.kryun.symbol.model.ImportDTO;
import org.kryun.symbol.model.Position;


public class ImportManager {

    private final List<ImportDTO> importDTOList;

    public ImportManager() {
        this.importDTOList = new ArrayList<>();
    }

    public List<ImportDTO> getImportDTOList() {
        return this.importDTOList;
    }

    public void importListClear() {
        this.importDTOList.clear();
    }

    public void buildImport(Long importId, Long blockId, Node node) {
        ImportDTO importDTO = new ImportDTO();

        String nodeValue = node.toString();
        nodeValue = nodeValue.replace(";", "");
        nodeValue = nodeValue.replace("import", "");
        nodeValue = nodeValue.trim();

        importDTO.setImportId(importId);
        importDTO.setBlockId(blockId);
        importDTO.setName(nodeValue);
        importDTO.setPosition(
            new Position(
                node.getRange().get().begin.line,
                node.getRange().get().begin.column,
                node.getRange().get().end.line,
                node.getRange().get().end.column));

        importDTOList.add(importDTO);

    }
}
