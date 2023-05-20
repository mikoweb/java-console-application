package app.module.sample.domain.dto;

import java.util.HashMap;

public record SampleDTO (
    String firstName,
    String lastName,
    long age
) {
    public SampleDTO(HashMap<String, Object> data) {
        this(
            (String) data.get("firstName"),
            (String) data.get("lastName"),
            (long) data.get("age")
        );
    }
}
