package org.kryun;

import org.kryun.global.config.AppConfig;
import org.kryun.global.enums.SymbolStatusEnum;
import org.kryun.symbol.model.SymbolStatusDTO;
import org.kryun.symbol.pkg.ProjectParser;

public class Main {

    public static void main(String[] args) throws Exception {
//        Long timeout = 60 *10L;
        SymbolStatusDTO symbolStatusDTO = new SymbolStatusDTO(1L, 1L, 1L);
        symbolStatusDTO.setStatusEnum(SymbolStatusEnum.ON_GOING);
        symbolStatusDTO.setSymbolStatusId(1L);

        ProjectParser projectParser = new ProjectParser();
        String projName = "netty-all";
        String projectPath = AppConfig.WORKSPACE_PATH + "/" + projName + "/";

        // Connection 넘겨주기
        projectParser.parseProject(projectPath, symbolStatusDTO, projName);
        symbolStatusDTO.setStatusEnum(SymbolStatusEnum.COMPLETED);
    }

}