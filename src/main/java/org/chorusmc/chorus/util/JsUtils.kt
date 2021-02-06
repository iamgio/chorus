@file:JvmName("JsUtils")
package org.chorusmc.chorus.util

import javafx.animation.KeyFrame
import javafx.animation.Timeline
import javafx.beans.property.Property
import javafx.util.Duration
import java.util.concurrent.CompletableFuture

// Top-level add-ons utility functions

fun listen(property: Property<*>, action: () -> Unit) {
    property.addListener { _ -> action()}
}

fun wait(action: () -> Unit, millis: Double, count: Int) {
    with(Timeline(KeyFrame(Duration(millis), { _ -> action() }))) {
        cycleCount = count
        playFromStart()
    }
}

fun runAsync(action: () -> Unit) = CompletableFuture.runAsync(action)!!