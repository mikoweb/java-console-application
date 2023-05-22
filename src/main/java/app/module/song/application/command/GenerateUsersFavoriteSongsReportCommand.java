package app.module.song.application.command;

import app.module.core.application.style.shell.DefaultShellMessageStyle;
import app.module.core.application.style.shell.ShellMessageStyle;
import app.module.core.domain.exception.FailedQueryException;
import app.module.song.domain.dataset.UsersFavoriteSongsDataset;
import app.module.song.infrastructure.query.FindUsersFavoriteSongsQuery;
import app.module.song.infrastructure.writer.UsersFavoriteSongsWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.io.IOException;
import java.nio.file.Path;

@ShellComponent
@ShellCommandGroup("Song")
public class GenerateUsersFavoriteSongsReportCommand {
    private final FindUsersFavoriteSongsQuery findUsersFavoriteSongsQuery;
    private final UsersFavoriteSongsWriter usersFavoriteSongsWriter;
    private final ShellMessageStyle messageStyle;

    @Autowired
    public GenerateUsersFavoriteSongsReportCommand(
        FindUsersFavoriteSongsQuery findUsersFavoriteSongsQuery,
        UsersFavoriteSongsWriter usersFavoriteSongsWriter
    ) {
        this.findUsersFavoriteSongsQuery = findUsersFavoriteSongsQuery;
        this.usersFavoriteSongsWriter = usersFavoriteSongsWriter;
        this.messageStyle = new DefaultShellMessageStyle();
    }

    @ShellMethod(value = "Generate user's favorite songs report", key = "song:generate-users-favorite-songs-report")
    public void execute() {
        UsersFavoriteSongsDataset favoriteSongs = null;

        try {
            favoriteSongs = findUsersFavoriteSongsQuery.find();
        } catch (FailedQueryException exception) {
            catchFailedQuery();
        }

        try {
            Path reportPath = usersFavoriteSongsWriter.writeDatasetToCsc(favoriteSongs);

            messageStyle.success("Generated user's favorite songs report!");
            messageStyle.info(reportPath.toString());
        } catch (IOException exception) {
            catchFailedWrite(exception);
        }
    }

    private void catchFailedQuery() {
        messageStyle.error("Failed to download songs data!");
        System.exit(1);
    }

    private void catchFailedWrite(Throwable exception) {
        messageStyle.error("Failed write report!");
        messageStyle.error(exception.getMessage());
        System.exit(1);
    }
}
