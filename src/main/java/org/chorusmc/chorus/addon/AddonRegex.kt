package org.chorusmc.chorus.addon

/**
 * @author Giorgio Garofalo
 */
class AddonRegex(regex: String) {

    private val regex = regex.toRegex(setOf(RegexOption.MULTILINE, RegexOption.DOT_MATCHES_ALL))

    fun matches(string: String) = regex.matches(string)
    fun find(string: String)    = regex.find(string)
    fun findAll(string: String) = regex.findAll(string)
}