package app.module.sample.domain.dto;

import java.util.HashMap;

final public class SampleDTO {
    public final String fistName;
    public final String lastName;
    public final long age;

    public SampleDTO(HashMap<String, Object> data) {
        this.fistName = (String) data.get("fistName");
        this.lastName = (String) data.get("lastName");
        this.age = (long) data.get("age");
    }
}
