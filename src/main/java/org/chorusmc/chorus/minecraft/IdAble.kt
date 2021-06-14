package org.chorusmc.chorus.minecraft

/**
 * Represents a game component with an identifier
 * @author Giorgio Garofalo
 */
interface IdAble {

    val id: Short

    companion object {

        /**
         * @param idAble class of the component
         * @param id i
         * @return Element with matching ID, <tt>null<tt> otherwise
         */
        fun <T> byId(idAble: McComponents<T>, id: Short): T? where T : IdAble, T : McComponent =
                idAble.components.firstOrNull { (it as IdAble).id == id }
    }
}