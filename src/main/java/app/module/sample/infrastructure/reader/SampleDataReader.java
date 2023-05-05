package app.module.sample.infrastructure.reader;

import app.module.sample.domain.dto.SampleDTO;
import app.module.sample.infrastructure.reader.exception.CannotReadFileException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

final public class SampleDataReader
{
    public ArrayList<SampleDTO> readFromJson(String jsonFilePath) throws CannotReadFileException {
        ArrayList<SampleDTO> list = new ArrayList<>();

        try (FileReader reader = new FileReader(jsonFilePath)) {
            JSONParser jsonParser = new JSONParser();
            JSONArray sampleData = (JSONArray) jsonParser.parse(reader);

            for (Object item : sampleData) {
                list.add(mapItemToDTO((JSONObject) item));
            }
        }  catch (IOException | ParseException exception) {
            throwCannotRead(jsonFilePath);
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

    private void throwCannotRead(String jsonFilePath) throws CannotReadFileException{
        throw new CannotReadFileException("Cannot read file " + jsonFilePath);
    }
}
