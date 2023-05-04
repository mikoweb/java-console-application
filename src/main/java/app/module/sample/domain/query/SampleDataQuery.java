package app.module.sample.domain.query;

import app.module.core.domain.exception.FailedQueryException;
import app.module.sample.domain.dto.SampleDTO;

import java.util.ArrayList;

public interface SampleDataQuery {
    ArrayList<SampleDTO> getData() throws FailedQueryException;
}
