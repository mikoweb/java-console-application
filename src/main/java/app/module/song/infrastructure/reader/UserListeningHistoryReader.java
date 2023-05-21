package app.module.song.infrastructure.reader;

import app.module.core.application.path.AppPathResolver;
import app.module.core.infrastructure.dataset.reader.CsvDatasetReader;
import app.module.song.domain.dataset.UserListeningHistoryDataset;
import app.module.song.domain.dto.UserListeningDTO;
import app.module.song.infrastructure.dataset.SongDatasetLocationConstant;
import de.siegmar.fastcsv.reader.NamedCsvReader;
import de.siegmar.fastcsv.reader.NamedCsvRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public final class UserListeningHistoryReader extends CsvDatasetReader {
    @Autowired
    public UserListeningHistoryReader(AppPathResolver pathResolver) {
        super(pathResolver);
    }

    public UserListeningHistoryDataset loadDataset() throws IOException {
        NamedCsvReader reader = createReader();
        UserListeningHistoryDataset dataset = new UserListeningHistoryDataset();

        for (NamedCsvRow row : reader) {
            dataset.add(new UserListeningDTO(row));
        }

        return dataset;
    }

    @Override
    protected String getDatasetFolderName() {
        return SongDatasetLocationConstant.FOLDER_NAME;
    }

    @Override
    protected String getDatasetFileName() {
        return SongDatasetLocationConstant.USER_LISTENING_HISTORY_FILE_NAME;
    }
}
