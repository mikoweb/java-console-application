package app.module.song.domain.dto;

import de.siegmar.fastcsv.reader.NamedCsvRow;

import java.util.Objects;

public record UserListeningDTO(
    String trackId,
    String userId,
    int playCount
) {
    public UserListeningDTO {
        Objects.requireNonNull(trackId);
        Objects.requireNonNull(userId);
    }

    public UserListeningDTO(NamedCsvRow row) {
        this(
            row.getField("\uFEFFtrack_id"),
            row.getField("user_id"),
            Integer.parseInt(row.getField("playcount"))
        );
    }
}
