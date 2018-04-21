package eu.iamgio.chorus.minecraft

/**
 * @author Gio
 */
interface IdAble {

    val id: Short

    companion object {
        fun byId(idAble: Class<out IdAble>, id: Short): Enum<*>? {
            @Suppress("UNCHECKED_CAST")
            return (idAble as Class<out Enum<*>>).enumConstants.firstOrNull {(it as IdAble).id == id}
        }
    }
}