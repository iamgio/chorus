package eu.iamgio.chorus.json;

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

    private URL url;

    public JSONParser(URL url) {
        this.url = url;
    }

    @SuppressWarnings("deprecation")
    public JSONObject parse() {
        try {
            return (JSONObject) JSONValue.parseWithException(IOUtils.toString(url));
        } catch(IOException | ParseException e) {
            return null;
        }
    }
}
