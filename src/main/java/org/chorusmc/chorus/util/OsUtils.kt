@file:JvmName("Os")
package org.chorusmc.chorus.util

// OS-related utilities

enum class OsType {
    WINDOWS, OSX, LINUX, UNKNOWN;
}

val os: OsType by lazy {
    val sys = System.getProperty("os.name").toLowerCase()
    when {
        "win" in sys -> OsType.WINDOWS
        "nix" in sys || "nux" in sys || "aix" in sys || "sunos" in sys -> OsType.LINUX
        "mac" in sys -> OsType.OSX
        else -> OsType.UNKNOWN
    }
}

val isWindows: Boolean
    get() = os == OsType.WINDOWS

val isMac: Boolean
    get() = os == OsType.OSX

val isLinux: Boolean
    get() = os == OsType.LINUX