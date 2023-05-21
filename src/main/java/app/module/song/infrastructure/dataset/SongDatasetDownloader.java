package app.module.song.infrastructure.dataset;

import app.module.core.application.path.AppPathResolver;
import net.lingala.zip4j.ZipFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public final class SongDatasetDownloader {
    private final AppPathResolver pathResolver;

    @Autowired
    public SongDatasetDownloader(AppPathResolver pathResolver) {
        this.pathResolver = pathResolver;
    }

    public void downloadDataset() throws IOException, SecurityException {
        String folderName = SongDatasetLocationConstant.FOLDER_NAME;
        Path datasetDir = this.pathResolver.getAppDatasetPath(folderName);
        Path dataSetZipFile = this.pathResolver.getAppDatasetPath(
            folderName.concat("/").concat(SongDatasetLocationConstant.FILE_NAME)
        );

        Files.createDirectories(datasetDir);

        ReadableByteChannel readableByteChannel = Channels.newChannel(
            new URL(SongDatasetLocationConstant.DOWNLOAD_URL).openStream()
        );

        FileOutputStream fileOutputStream = new FileOutputStream(dataSetZipFile.toFile());
        fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);

        new ZipFile(dataSetZipFile.toString()).extractAll(datasetDir.toString());
        dataSetZipFile.toFile().delete();
    }
}
