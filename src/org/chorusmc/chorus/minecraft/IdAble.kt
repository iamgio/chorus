package org.chorusmc.chorus.minecraft

/**
 * @author Gio
 */
interface IdAble {

    val id: Short

    @Suppress("UNCHECKED_CAST")
    companion object {
        fun <T> byId(idAble: Class<out McComponent>, id: Short): T? where T: IdAble =
                idAble.enumConstants.firstOrNull {(it as IdAble).id == id} as T?

        inline fun <reified T> byId(id: Short): T? where T: IdAble = byId(T::class.java as Class<out McComponent>, id)
    }
}