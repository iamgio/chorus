package org.chorusmc.chorus.menus.coloredtextpreview.previews

import javafx.application.Platform
import javafx.scene.layout.Pane
import javafx.scene.text.TextFlow
import org.chorusmc.chorus.menus.coloredtextpreview.FlowList
import org.chorusmc.chorus.minecraft.chat.ChatParser

/**
 * @author Giorgio Garofalo
 */
abstract class ColoredTextPreviewImage @JvmOverloads constructor(
        val background: ColoredTextBackground,
        flowsAmount: Int = 1,
        flows: FlowList = with(ChatParser("", true)) {
            val list = FlowList()
            repeat(flowsAmount) {
                list += toTextFlow()
            }
            list
        },
        private val reversed: Boolean = false
) : Pane(background.pane) {

    var flows = flows
        set(value) {
            children.removeAll(children.filterIsInstance<TextFlow>())
            value.image = this
            (if(reversed) value.reversed() else value).forEachIndexed { index, flow ->
                initFlow(flow, index)
                children += flow
            }
            field = value
        }

    init {
        @Suppress("LEAKINGTHIS")
        flows.image = this
        prefWidth = background.width
        background.onWidthChange = Runnable {
            prefWidth = background.width
        }
        Platform.runLater {
            (if(reversed) flows.reversed() else flows).forEachIndexed { index, flow ->
                initFlow(flow, index)
                children += flow
            }
        }
    }

    abstract fun initFlow(flow: TextFlow, index: Int)
}