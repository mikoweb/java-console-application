package app.module.song.domain.dto;

import de.siegmar.fastcsv.reader.NamedCsvRow;

import java.util.Objects;

public record UserFavoriteSongDTO(
    String trackId,
    String userId,
    int playCount,
    String name,
    String artist,
    String genre,
    String tags
) {
    public UserFavoriteSongDTO {
        Objects.requireNonNull(trackId);
        Objects.requireNonNull(userId);
        Objects.requireNonNull(name);
        Objects.requireNonNull(artist);
    }
}
