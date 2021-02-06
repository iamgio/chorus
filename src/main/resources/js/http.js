/**
 * Override this to set a user-agent for connections
 */
var userAgent;

/**
 * Establishes an HTTP connection
 * @param url target url
 * @param data (optional) parameters as map
 * @return org.jsoup.Connection
 */
function connect(url, data) {
    var connection = org.jsoup.Jsoup.connect(url);
    if(data) {
        for(param in data) {
            connection = connection.data(param, data[param]);
        }
    }
    return userAgent ? connection.userAgent(userAgent) : connection;
}

/**
 * Parses JSON
 * @param json either JSON string or URL to JSON
 * @return org.json.simple.JSONObject
 */
function json(json) {
    var Parser = chorus_type('json.JSONParser');
    var parser = new Parser();
    if(json.startsWith('http://') || json.startsWith('https://')) {
        return parser.parse(new java.net.URL(json));
    }
    return parser.parse(json);
}