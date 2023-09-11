package app.module.song.infrastructure.query;

import app.module.core.domain.exception.FailedQueryException;
import app.module.song.domain.dataset.MusicInfoDataset;
import app.module.song.domain.dataset.UserListeningHistoryDataset;
import app.module.song.domain.dataset.UsersFavoriteSongsDataset;
import app.module.song.domain.dto.UserFavoriteSongDTO;
import app.module.song.domain.dto.UserListeningDTO;
import app.module.song.infrastructure.reader.MusicInfoReader;
import app.module.song.infrastructure.reader.UserListeningHistoryReader;
import de.siegmar.fastcsv.reader.NamedCsvRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public final class FindUsersFavoriteSongsQuery {
    private final MusicInfoReader musicInfoReader;
    private final UserListeningHistoryReader userListeningHistoryReader;

    private static final class ListeningMap extends LinkedHashMap<String, LinkedHashMap<String, Integer>> {}

    @Autowired
    public FindUsersFavoriteSongsQuery(
        MusicInfoReader musicInfoReader,
        UserListeningHistoryReader userListeningHistoryReader
    ) {
        this.musicInfoReader = musicInfoReader;
        this.userListeningHistoryReader = userListeningHistoryReader;
    }

    public UsersFavoriteSongsDataset find() throws FailedQueryException {
        UsersFavoriteSongsDataset favoriteSongs = new UsersFavoriteSongsDataset();

        UserListeningHistoryDataset userListeningHistory = loadUserListeningHistory();
        MusicInfoDataset musicInfo = loadMusicInfo();

        ListeningMap listeningMap = new ListeningMap();

        for (UserListeningDTO listeningDTO : userListeningHistory) {
            appendListeningToMap(listeningDTO, listeningMap);
        }

        listeningMap.forEach((userId, userSongsCountMap) -> {
            String favoriteSongId = Collections.max(
                userSongsCountMap.entrySet(),
                Map.Entry.comparingByValue()
            ).getKey();

            Integer favoriteSongCount = userSongsCountMap.get(favoriteSongId);
            NamedCsvRow songRow = musicInfo.get(favoriteSongId);

            favoriteSongs.add(createUserFavorite(userId, favoriteSongId, favoriteSongCount, songRow));
        });

        return favoriteSongs;
    }

    private void appendListeningToMap(UserListeningDTO listeningDTO, ListeningMap listeningMap) {
        String userId = listeningDTO.userId();
        String trackId = listeningDTO.trackId();
        LinkedHashMap<String, Integer> userSongsCountMap;

        if (listeningMap.containsKey(userId)) {
            userSongsCountMap = listeningMap.get(userId);
        } else {
            userSongsCountMap = new LinkedHashMap<>();
            listeningMap.put(userId, userSongsCountMap);
        }

        userSongsCountMap.put(
            trackId,
            userSongsCountMap.containsKey(trackId)
                ? userSongsCountMap.get(trackId) + listeningDTO.playCount()
                : listeningDTO.playCount()
        );
    }

    private UserFavoriteSongDTO createUserFavorite(
        String userId,
        String favoriteSongId,
        int favoriteSongCount,
        NamedCsvRow songRow
    ) {
        return new UserFavoriteSongDTO(
            favoriteSongId,
            userId,
            favoriteSongCount,
            songRow.getField("name"),
            songRow.getField("artist"),
            songRow.getField("genre"),
            songRow.getField("tags")
        );
    }

    private UserListeningHistoryDataset loadUserListeningHistory() throws FailedQueryException {
        try {
            return userListeningHistoryReader.loadDataset();
        } catch (Throwable exception) {
            throw new FailedQueryException("Cannot load User Listening History!", exception);
        }
    }

    private MusicInfoDataset loadMusicInfo() throws FailedQueryException {
        try {
            return musicInfoReader.loadDataset();
        } catch (Throwable exception) {
            throw new FailedQueryException("Cannot load Music Info Dataset!", exception);
        }
    }
}
