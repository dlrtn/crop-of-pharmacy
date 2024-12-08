package lalalabs.pharmacy_crop.business.post.infrastructure.upload;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import lalalabs.pharmacy_crop.common.file.DirectoryType;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class LocalFileUploader {

    private static final String BASE_DIRECTORY = "/app/images";

    @SneakyThrows
    public String upload(MultipartFile file, DirectoryType type) {
        validateFile(file);

        Path filePath = getFilePath(file, type);

        file.transferTo(filePath.toFile());

        return filePath.toString();
    }

    private void validateFile(MultipartFile file) {
        String fileExtension = getFileExtension(file.getOriginalFilename());

        if (!fileExtension.equals(".jpg") && !fileExtension.equals(".jpeg") && !fileExtension.equals(".png")) {
            throw new IllegalArgumentException("지원하지 않는 파일 형식입니다.");
        }
    }

    public String getFileExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            throw new IllegalArgumentException("파일 이름이 올바르지 않습니다.");
        }
        return filename.substring(filename.lastIndexOf("."));
    }

    private Path getFilePath(MultipartFile file, DirectoryType type) throws IOException {
        Path directoryPath = getDirectoryPath(type);

        String uniqueFileName = generateUniqueFileName(file);

        return directoryPath.resolve(uniqueFileName);
    }

    private Path getDirectoryPath(DirectoryType type) throws IOException {
        Path directoryPath = Paths.get(BASE_DIRECTORY, type.getDirectoryName());
        if (!Files.exists(directoryPath)) {
            Files.createDirectories(directoryPath); // 디렉토리 없으면 생성
        }

        return directoryPath;
    }

    private String generateUniqueFileName(MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        String fileExtension = originalFileName != null && originalFileName.contains(".")
                ? originalFileName.substring(originalFileName.lastIndexOf("."))
                : "";

        return UUID.randomUUID() + fileExtension;
    }
}
