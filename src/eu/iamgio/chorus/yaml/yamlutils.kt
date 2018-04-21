package eu.iamgio.chorus.yaml

import eu.iamgio.chorus.util.area

fun charToWord(index: Int, styleClass: String): String {
    val area = area!!

    var word = ""
    var min = index
    var plus = index
    val toReverse = ArrayList<Char>()

    while(min > 0 && area.text[min] != '\n' && area.getStyleOfChar(min - 1).contains(styleClass)) {
        min--
        toReverse += area.text[min]
    }
    toReverse.reversed().forEach {word += it}
    while(plus < area.text.length && area.text[plus] != '\n' && area.getStyleOfChar(plus).contains(styleClass)) {
        word += area.text[plus]
        plus++
    }
    return removeInit(word)
}

fun removeInit(string: String): String {
    var s = ""
    var b = !(string.startsWith(" ") || string.startsWith("\t") || string.startsWith("\n"))
    string.toCharArray().forEach {
        if(b || !(it == ' ' || it == '\t' || it == '\n')) {
            b = true
            s += it
        }
    }
    return s
}