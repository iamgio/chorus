/**
 * Translates text from a language to another using Reverso
 * @param from input language code (en, it...)
 * @param to output language code
 * @param text text to translate
 * @return java.lang.String
 */
function translateFromWeb(from, to, text) {
    var doc = connect('https://async.reverso.net/WebReferences/WSAJAXInterface.asmx/TranslateCorrWS')
        .requestBody("{'searchText': '" + text.replaceAll("\\bi\\b", "I").replace("'", "\\'") + "', 'direction': '" + from + "-" + to + "-7', 'maxTranslationChars':'-1', 'usecorr':'true'}")
        .ignoreHttpErrors(true)
        .ignoreContentType(true)
        .userAgent('Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:66.0) Gecko/20100101 Firefox/66.0')
        .header('Content-Type', 'application/json')
        .referrer('http://www.reverso.net/')
        .post();
    print(doc.body())
    return json(doc.body().text()).get("d").get("result");
}