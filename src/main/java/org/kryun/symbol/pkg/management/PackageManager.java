package org.kryun.symbol.pkg.management;

import com.github.javaparser.ast.Node;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.kryun.symbol.model.PackageDTO;
import org.kryun.symbol.model.Position;

public class PackageManager {

    private final List<PackageDTO> packageDTOList;

    public PackageManager() {
        this.packageDTOList = new ArrayList<>();
    }

    public List<PackageDTO> getPackageDTOList() {
        return this.packageDTOList;
    }

    public void packageListClear() {
        this.packageDTOList.clear();
    }

    public PackageDTO buildPackage(Long packageId, Long blockId, Node node) {
        PackageDTO packageDTO = new PackageDTO();

        String packageName = null;
        String nodeValue = node.toString();
        String pattern = "[a-z0-9\\.]+\\;"; // 마지막에 $ 붙이면 왜 안되지
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(nodeValue);
        while (m.find()) {
            packageName = m.group();
        }
        if (packageName != null) {
            packageName = packageName.replace(";", "");
        }
        // nodeValue = nodeValue.replace("package", "");
        // nodeValue = nodeValue.trim();

        packageDTO.setPackageId(packageId);
        packageDTO.setBlockId(blockId);
        packageDTO.setName(packageName);

        packageDTO.setPosition(
                new Position(
                        node.getRange().get().begin.line,
                        node.getRange().get().begin.column,
                        node.getRange().get().end.line,
                        node.getRange().get().end.column));

        packageDTOList.add(packageDTO);

        return packageDTO;
    }
}
