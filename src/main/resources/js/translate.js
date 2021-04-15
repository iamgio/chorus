/**
 * Used to allow string translation. See wiki for further information
 */
var translationMap = {};

/**
 * Translates a string based on translationMap. English value is returned if there is not any translation for current locale
 * @param key translation key present in translationMap
 * @return java.lang.String
 */
function translate(key) {
    var locale = chorus.getResourceBundle().getLocale().toLanguageTag();
    print(locale)
    var subTranslationMap = translationMap[key];
    if(!subTranslationMap) {
        print('Error: there is no translation key ' + key);
        return '[' + key + ']';
    }
    var translation = subTranslationMap[locale];
    return translation ? translation : subTranslationMap['en']
}

function translationKeyExists(key) {
    return !!translationMap[key];
}

/**
 * Translates text from a language to another using Reverso
 * @param from input language code (en, it...)
 * @param to output language code
 * @param text text to translate
 * @return java.lang.String
 */
function translateFromWeb(from, to, text) {
    var options = '{"origin": "reversodesktop", "sentenceSplitter": true, "contextResults": true, "languageDetection": false}';
    var doc = connect('https://api.reverso.net/translate/v1/translation')
        .requestBody('{"format": "text", "from": "' + from + '", "to": "' + to + '", input: "' + text + '", "options": ' + options + '}')
        .ignoreHttpErrors(true)
        .ignoreContentType(true)
        .userAgent('Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:66.0) Gecko/20100101 Firefox/66.0')
        .header('Content-Type', 'application/json')
        .referrer('http://www.reverso.net/')
        .post();
    return json(doc.body().text()).get("translation")[0];
}