package app.module.song.application.command;

import app.module.core.application.style.shell.DefaultShellMessageStyle;
import app.module.core.application.style.shell.ShellMessageStyle;
import app.module.song.infrastructure.dataset.SongDatasetDownloader;
import net.lingala.zip4j.exception.ZipException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.io.IOException;

@ShellComponent
@ShellCommandGroup("Song")
public class DownloadDatasetCommand {
    private final SongDatasetDownloader songDatasetDownloader;
    private final ShellMessageStyle messageStyle;

    @Autowired
    public DownloadDatasetCommand(SongDatasetDownloader songDatasetDownloader) {
        this.songDatasetDownloader = songDatasetDownloader;
        this.messageStyle = new DefaultShellMessageStyle();
    }

    @ShellMethod(value = "Download Song Dataset", key = "song:download-dataset")
    public void execute() {
        try {
            songDatasetDownloader.downloadDataset();
            messageStyle.success("Song Dataset downloaded!");
        } catch (ZipException exception) {
            catchCannotUnzipDataset(exception);
        } catch (IOException | SecurityException exception) {
            catchCannotDownloadDataset();
        }
    }

    private void catchCannotDownloadDataset() {
        messageStyle.error("Cannot download dataset!");
        System.exit(1);
    }

    private void catchCannotUnzipDataset(ZipException exception) {
        messageStyle.error("Cannot unzip dataset!");
        messageStyle.error(exception.getMessage());
        System.exit(1);
    }
}
