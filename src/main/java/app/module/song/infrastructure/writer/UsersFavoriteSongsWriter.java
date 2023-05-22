package app.module.song.infrastructure.writer;

import app.module.core.application.path.AppPathResolver;
import app.module.song.domain.dataset.UsersFavoriteSongsDataset;
import app.module.song.domain.dto.UserFavoriteSongDTO;
import de.siegmar.fastcsv.writer.CsvWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public final class UsersFavoriteSongsWriter {
    private final AppPathResolver pathResolver;

    @Autowired
    public UsersFavoriteSongsWriter(AppPathResolver pathResolver) {
        this.pathResolver = pathResolver;
    }

    public Path writeDatasetToCsc(UsersFavoriteSongsDataset dataset) throws IOException {
        Path targetPath = createTargetPath();

        try (CsvWriter csv = CsvWriter.builder().build(targetPath)) {
            csv.writeRow("track_id", "user_id", "play_count", "name", "artist", "genre", "tags");

            for (UserFavoriteSongDTO favoriteSong : dataset) {
                csv.writeRow(
                    favoriteSong.trackId(),
                    favoriteSong.userId(),
                    Integer.toString(favoriteSong.playCount()),
                    favoriteSong.name(),
                    favoriteSong.artist(),
                    favoriteSong.genre(),
                    favoriteSong.tags()
                );
            }
        }

        return targetPath;
    }

    private Path createTargetPath() throws IOException {
        Path reportDir = this.pathResolver.getStoragePath("users_favorite_songs_report");
        Files.createDirectories(reportDir);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
        LocalDateTime now = LocalDateTime.now();

        return reportDir.resolve(dtf.format(now).concat("-users_favorite_songs.csv"));
    }
}
