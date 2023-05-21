package app.module.song.infrastructure.reader;

import app.module.core.application.path.AppPathResolver;
import app.module.core.infrastructure.dataset.reader.CsvDatasetReader;
import app.module.song.domain.dataset.MusicInfoDataset;
import app.module.song.infrastructure.dataset.SongDatasetLocationConstant;
import de.siegmar.fastcsv.reader.NamedCsvReader;
import de.siegmar.fastcsv.reader.NamedCsvRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public final class MusicInfoReader extends CsvDatasetReader {
    @Autowired
    public MusicInfoReader(AppPathResolver pathResolver) {
        super(pathResolver);
    }

    public MusicInfoDataset loadDataset() throws IOException {
        NamedCsvReader reader = createReader();
        MusicInfoDataset dataset = new MusicInfoDataset();

        for (NamedCsvRow row : reader) {
            dataset.put(row.getField("\uFEFFtrack_id"), row);
        }

        return dataset;
    }

    @Override
    protected String getDatasetFolderName() {
        return SongDatasetLocationConstant.FOLDER_NAME;
    }

    @Override
    protected String getDatasetFileName() {
        return SongDatasetLocationConstant.MUSIC_INFO_FILE_NAME;
    }
}
