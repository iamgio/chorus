package org.chorusmc.chorus.minecraft

/**
 * Represents a game component with an identifier
 * @author Giorgio Garofalo
 */
interface IdAble {

    val id: Short

    @Suppress("UNCHECKED_CAST")
    companion object {

        /**
         * @param idAble class of the component
         * @param id i
         * @return Element with matching ID, <tt>null<tt> otherwise
         */
        fun <T> byId(idAble: Class<out McComponent>, id: Short): T? where T: IdAble =
                idAble.enumConstants.firstOrNull {(it as IdAble).id == id} as T?

        /**
         * @see byId
         */
        inline fun <reified T> byId(id: Short): T? where T: IdAble = byId(T::class.java as Class<out McComponent>, id)
    }
}