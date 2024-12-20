package lalalabs.pharmacy_crop.common.file;

import lombok.Getter;

@Getter
public enum DirectoryType {
    ANNOUNCEMENT("announcement"),
    FARM_GUARD("farm-guard");

    private final String directoryName;

    DirectoryType(String directoryName) {
        this.directoryName = directoryName;
    }
}
