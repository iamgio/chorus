package org.chorusmc.chorus.minecraft

/**
 * Minecraft versions
 * @author Giorgio Garofalo
 */
enum class McVersion {
    V1_12, V1_13, V1_14, V1_15, V1_16, V1_20;

    val commonName: String // V1_12 -> 1.12
        get() = name.substring(1).replace("_", ".")

    val packageName: String // V1_12 -> v112
        get() = name.replace("_", "").toLowerCase()

    override fun toString() = commonName

    companion object {
        @JvmStatic fun fromCommonName(commonName: String): McVersion {
            return valueOf("V" + commonName.replace(".", "_"))
        }

        @JvmStatic fun generateConfigPlaceholder(): String {
            return values().joinToString("|") { it.commonName }
        }
    }
}