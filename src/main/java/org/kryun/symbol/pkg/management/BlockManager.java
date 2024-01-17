package org.kryun.symbol.pkg.management;

import com.github.javaparser.ast.Node;
import java.util.ArrayList;
import java.util.List;
import org.kryun.symbol.model.BlockDTO;
import org.kryun.symbol.model.Position;

public class BlockManager {

    private final List<BlockDTO> blockDTOList;

    public BlockManager() {
        this.blockDTOList = new ArrayList<>();
    }

    public List<BlockDTO> getBlockDTOList() {
        return this.blockDTOList;
    }

    public void blockListClear() {
        this.blockDTOList.clear();
    }

    // Todo. Node 필드 필요 여부 파악
    public BlockDTO buildBlock(Long blockId, Integer depth, Long ParentBlockId, String blockType, Node node,
            Long symbolReferenceId) {
        BlockDTO blockDTO = new BlockDTO();

        blockDTO.setBlockId(blockId);
        blockDTO.setDepth(depth);
        blockDTO.setParentBlockId(ParentBlockId);
        blockDTO.setBlockType(blockType);
        blockDTO.setNode(node);
        blockDTO.setSymbolReferenceId(symbolReferenceId);
        blockDTO.setPosition(
                new Position(
                        node.getRange().get().begin.line,
                        node.getRange().get().begin.column,
                        node.getRange().get().end.line,
                        node.getRange().get().end.column));
        blockDTOList.add(blockDTO);

        return blockDTO;
    }
}
