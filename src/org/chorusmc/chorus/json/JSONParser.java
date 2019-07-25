package org.chorusmc.chorus.json;

import org.apache.commons.io.IOUtils;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URL;

/**
 * @author Gio
 */
public class JSONParser {

    @SuppressWarnings("deprecation")
    public JSONObject parse(URL url) {
        try {
            return parseWithException(IOUtils.toString(url));
        } catch(IOException | ParseException e) {
            return null;
        }
    }

    public JSONObject parse(String json) {
        try {
            return parseWithException(json);
        } catch(ParseException e) {
            return null;
        }
    }

    private JSONObject parseWithException(String json) throws ParseException {
        return (JSONObject) JSONValue.parseWithException(json);
    }
}
