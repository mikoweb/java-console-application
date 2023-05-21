package app.module.core.infrastructure.dataset.reader;

import app.module.core.application.path.AppPathResolver;

import java.nio.file.Path;

public abstract class DatasetReader {
    private final AppPathResolver pathResolver;

    public DatasetReader(AppPathResolver pathResolver) {
        this.pathResolver = pathResolver;
    }

    protected abstract String getDatasetFolderName();
    protected abstract String getDatasetFileName();

    protected Path createDatasetPath() {
        String datasetFolderName = getDatasetFolderName();
        String datasetFileName = getDatasetFileName();

        return this.pathResolver.getAppDatasetPath(datasetFolderName.concat("/").concat(datasetFileName));
    }
}
