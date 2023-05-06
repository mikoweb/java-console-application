package app.module.sample.infrastructure.reader;

import app.module.sample.domain.dto.SampleDTO;
import app.module.sample.infrastructure.reader.exception.CannotReadFileException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

@Service
final public class SampleDataReader
{
    private final ResourceLoader resourceLoader;

    @Autowired
    public SampleDataReader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public ArrayList<SampleDTO> readFromJson(String jsonResourceFilePath) throws CannotReadFileException {
        Resource jsonResource = resourceLoader.getResource("classpath:" + jsonResourceFilePath);
        ArrayList<SampleDTO> list = new ArrayList<>();

        try (InputStream jsonStream = jsonResource.getInputStream()) {
            InputStreamReader reader = new InputStreamReader(jsonStream);
            JSONParser jsonParser = new JSONParser();
            JSONArray sampleData = (JSONArray) jsonParser.parse(reader);

            for (Object item : sampleData) {
                list.add(mapItemToDTO((JSONObject) item));
            }
        } catch (IOException | ParseException exception) {
            throwCannotRead(jsonResourceFilePath);
        }

        return list;
    }

    private SampleDTO mapItemToDTO(JSONObject jsonItem) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("firstName", jsonItem.get("firstName"));
        data.put("lastName", jsonItem.get("lastName"));
        data.put("age", jsonItem.get("age"));

        return new SampleDTO(data);
    }

    private void throwCannotRead(String jsonFilePath) throws CannotReadFileException {
        throw new CannotReadFileException("Cannot read file " + jsonFilePath);
    }
}
