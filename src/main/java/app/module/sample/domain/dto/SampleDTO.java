package app.module.sample.domain.dto;

import org.json.simple.JSONObject;

import java.util.Objects;

public record SampleDTO (
    String firstName,
    String lastName,
    long age
) {
    public SampleDTO {
        Objects.requireNonNull(firstName);
        Objects.requireNonNull(lastName);
    }

    public SampleDTO(JSONObject jsonData) {
        this(
            (String) jsonData.get("firstName"),
            (String) jsonData.get("lastName"),
            (long) jsonData.get("age")
        );
    }
}
