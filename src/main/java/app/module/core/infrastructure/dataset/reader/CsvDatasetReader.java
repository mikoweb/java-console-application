package app.module.core.infrastructure.dataset.reader;

import app.module.core.application.path.AppPathResolver;
import de.siegmar.fastcsv.reader.NamedCsvReader;

import java.io.IOException;

public abstract class CsvDatasetReader extends DatasetReader {
    public CsvDatasetReader(AppPathResolver pathResolver) {
        super(pathResolver);
    }

    protected NamedCsvReader createReader() throws IOException {
        return NamedCsvReader.builder().build(createDatasetPath());
    }
}
