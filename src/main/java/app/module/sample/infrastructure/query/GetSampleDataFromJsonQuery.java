package app.module.sample.infrastructure.query;

import app.module.core.domain.exception.FailedQueryException;
import app.module.sample.domain.dto.SampleDTO;
import app.module.sample.domain.query.SampleDataQuery;
import app.module.sample.infrastructure.reader.SampleDataReader;
import app.module.sample.infrastructure.reader.exception.CannotReadFileException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
final public class GetSampleDataFromJsonQuery implements SampleDataQuery {
    private final SampleDataReader reader;

    @Autowired
    public GetSampleDataFromJsonQuery(SampleDataReader reader) {
        this.reader = reader;
    }

    public ArrayList<SampleDTO> getData() throws FailedQueryException {
        try {
            return reader.readFromJson("json/sample/sampleDataForTable.json");
        } catch (CannotReadFileException exception) {
            throw new FailedQueryException(exception.getMessage(), exception);
        }
    }
}
