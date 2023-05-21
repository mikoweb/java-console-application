package app.module.song.application.command;

import app.module.core.application.style.shell.DefaultShellMessageStyle;
import app.module.core.application.style.shell.ShellMessageStyle;
import app.module.core.domain.exception.FailedQueryException;
import app.module.song.domain.dataset.UsersFavoriteSongsDataset;
import app.module.song.infrastructure.query.FindUsersFavoriteSongsQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@ShellCommandGroup("Song")
public class GenerateUsersFavoriteSongsReportCommand {
    private final FindUsersFavoriteSongsQuery findUsersFavoriteSongsQuery;
    private final ShellMessageStyle messageStyle;

    @Autowired
    public GenerateUsersFavoriteSongsReportCommand(FindUsersFavoriteSongsQuery findUsersFavoriteSongsQuery) {
        this.findUsersFavoriteSongsQuery = findUsersFavoriteSongsQuery;
        this.messageStyle = new DefaultShellMessageStyle();
    }

    @ShellMethod(value = "Generate users' favorite songs report", key = "song:generate-users-favorite-songs-report")
    public void execute() {
        try {
            UsersFavoriteSongsDataset favoriteSongs = findUsersFavoriteSongsQuery.find();

            System.out.println(favoriteSongs);
            // TODO write report to CSV

            messageStyle.success("Generated users' favorite songs report!");
        } catch (FailedQueryException exception) {
            catchFailedQuery();
        }
    }

    private void catchFailedQuery() {
        messageStyle.error("Failed to download favorites!");
        System.exit(1);
    }
}
