package lalalabs.pharmacy_crop.common.file;

import lombok.Getter;

@Getter
public enum DirectoryType {
    Announcement("announcement");

    private final String directoryName;

    DirectoryType(String directoryName) {
        this.directoryName = directoryName;
    }
}
