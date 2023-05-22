package app.module.core.application.path;

import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public final class AppPathResolver {
    public Path getAppPath(String path) {
        return Paths.get("./".concat(path)).toAbsolutePath().normalize();
    }

    public Path getAppDatasetPath(String path) {
        return getAppPath("datasets/".concat(path));
    }

    public Path getStoragePath(String path) {
        return getAppPath("storage/".concat(path));
    }
}
