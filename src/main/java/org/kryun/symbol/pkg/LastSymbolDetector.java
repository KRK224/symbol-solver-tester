package org.kryun.symbol.pkg;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.kryun.symbol.model.Position;

@Getter
@Setter
@ToString
public class LastSymbolDetector {
    private String srcPath;
    private String symbolType;
    private String symbolName;
    private Position symbolPostion;

}
