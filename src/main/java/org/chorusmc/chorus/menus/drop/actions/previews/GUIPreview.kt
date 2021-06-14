package org.chorusmc.chorus.menus.drop.actions.previews

import eu.iamgio.libfx.timing.WaitingTimer
import javafx.application.Platform
import javafx.scene.control.Button
import javafx.scene.control.Spinner
import javafx.scene.control.TextField
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.MouseButton
import javafx.scene.layout.Region
import javafx.util.Duration
import org.chorusmc.chorus.Chorus
import org.chorusmc.chorus.editor.EditorArea
import org.chorusmc.chorus.menus.coloredtextpreview.ColoredTextPreviewMenu
import org.chorusmc.chorus.menus.coloredtextpreview.previews.ColoredTextPreviewImage
import org.chorusmc.chorus.menus.coloredtextpreview.previews.GUIPreviewImage
import org.chorusmc.chorus.menus.drop.actions.DropMenuAction
import org.chorusmc.chorus.menus.insert.InsertMenu
import org.chorusmc.chorus.minecraft.McClass
import org.chorusmc.chorus.minecraft.chat.ChatParser
import org.chorusmc.chorus.minecraft.item.Item
import org.chorusmc.chorus.minecraft.item.Items
import org.chorusmc.chorus.nodes.popup.LocalTextPopup
import org.chorusmc.chorus.notification.Notification
import org.chorusmc.chorus.notification.NotificationType
import org.chorusmc.chorus.util.colorPrefix
import org.chorusmc.chorus.util.makeFormal
import org.chorusmc.chorus.util.toFlowList
import org.chorusmc.chorus.util.translate
import org.graalvm.polyglot.PolyglotException
import org.yaml.snakeyaml.Yaml

/**
 * @author Giorgio Garofalo
 */
class GUIPreview : DropMenuAction() {

    private companion object {
        var grid: Grid? = null
    }

    override fun onAction(area: EditorArea, x: Double, y: Double) {
        val format = GUIFormats.format
        val useFormatData = format != null

        val selectedText = if(useFormatData) area.selectedText.let { if(it.isNotEmpty()) it else area.text } else selectedText

        // Parse YAML if using a format
        val map = if(useFormatData) {
            try {
                Yaml().load<Map<String, Any>>(selectedText)
            } catch(e: Exception) {
                null
            }
        } else null

        // Load UI
        val textfield = TextField(
                when {
                    useFormatData && map != null -> format!!.getName(map)
                    selectedText.isNotEmpty() -> selectedText
                    else -> translate("preview.gui.title_default")
                }
        )
        textfield.promptText = translate("preview.gui.title_prompt")
        val rows = Spinner<Int>(1, 6,
                if(useFormatData && map != null) format!!.getRows(map) else if(grid == null) 1 else grid!!.rows)
        val image = GUIPreviewImage(colorPrefix + "8" + textfield.text, rows.value)
        val button = Button(translate("preview.gui.clear"))

        // Clear button
        button.setOnAction {
            grid!!.members.forEach { it.clear() }
            grid = Grid(textfield)
            updateMembers(grid!!, rows.value, image)
            textfield.requestFocus()
            textfield.positionCaret(textfield.length)
        }

        // Load new empty grid if either there is not a previous one or if a format is being used
        if(grid == null || useFormatData) {
            grid = Grid(textfield)
        }

        // Load initial grid
        updateMembers(grid!!, rows.value, image)

        // Create menu
        val menu = ColoredTextPreviewMenu(translate("preview.gui"), image, listOf(textfield, rows, button))

        // Set listeners
        textfield.textProperty().addListener { _ ->
            menu.image.flows = listOf(ChatParser(colorPrefix + "8" + textfield.text, true).toTextFlow(false)).toFlowList()
            updateMembers(grid!!, rows.value, image)
        }
        rows.valueProperty().addListener { _ ->
            updateMembers(grid!!, rows.value, image)
            menu.image.background.image = Image(Chorus::class.java.getResourceAsStream("/assets/minecraft/previews/gui-${rows.value}.png"))
        }

        // Load format data
        if(useFormatData && map != null) {
            try {
                format!!.getItems(map).forEach {
                    if(it.position.slot < grid!!.members.size) {
                        it.item?.let { item ->
                            grid!!.members[it.position.slot].setItem(item, it.meta)
                        }
                    }
                }
            } catch(e: PolyglotException) {
                System.err.println("${format!!.name} GUI error: ${e.message} at line ${e.sourceLocation.startLine}")
                Notification(translate("preview.gui.error", format.name), NotificationType.ERROR).send()
            }
        }

        // Show menu
        menu.layoutX = x
        menu.layoutY = y
        menu.show()
    }
}

private fun updateMembers(grid: Grid, rows: Int, image: ColoredTextPreviewImage) {
    image.children.removeAll(grid.members)
    grid.rows = rows
    grid.updateMembers()
    image.children.addAll(grid.members)
}

private class Grid(private val titleField: TextField) {

    var members = mutableListOf<GridMember>()
    private var positions = mutableListOf<Pair<Double, Double>>()

    var rows = 1
    private val columns = 9

    fun updateMembers() {
        var n = 0
        val pass = 36.0
        var y = 26.0
        for(i in 0 until rows) {
            var x = 8.0
            for(j in 0 until columns) {
                if(!positions.contains(x to y)) {
                    val member = GridMember(n, j, i, titleField)
                    member.layoutX = x
                    member.layoutY = y
                    members.add(member)
                    positions.add(x to y)
                    x += pass
                }
                n++
            }
            y += pass
        }
        if(members.size > rows * columns) {
            members = members.subList(0, rows * columns)
            positions = positions.subList(0, rows * columns)
        }
    }
}

private class GridMember(private val n: Int, private val x: Int, private val y: Int, private val titleField: TextField) : Region() {

    private val centerX: Double
        get() = layoutX + prefWidth / 2

    private val centerY: Double
        get() = layoutY + prefHeight / 2

    var item: Item? = null
    var meta = 0

    init {
        prefWidth = 34.0
        prefHeight = 34.0
        val popup = LocalTextPopup()
        popup.layoutX = centerX - prefWidth
        popup.layoutY = centerY - 35
        setOnMouseEntered {
            updatePopupText(popup)
            children += popup
            style = "-fx-background-color: rgba(255, 255, 255, .2)"
        }
        setOnMouseExited {
            children -= popup
            style = ""
        }
        setOnMouseClicked {
            showingMenu?.hide()
            when(it.button) {
                MouseButton.PRIMARY -> {
                    val menu = InsertMenu(McClass(Items).components)
                    menu.target = titleField
                    if(item != null) {
                        menu.textField.text = item!!.name.makeFormal()
                    }
                    menu.layoutX = layoutX + 40
                    menu.layoutY = layoutY
                    menu.setOnSelect {
                        item = McClass(Items).valueOf<Item>(menu.selected.toUpperCase().replace(" ", "_"))
                        meta = if(menu.meta > 0) menu.meta else 0
                        setItem(item!!, meta)
                    }
                    menu.show()
                    showingMenu = menu
                }
                MouseButton.SECONDARY -> {
                    item = null
                    removeImage()
                }
                MouseButton.MIDDLE -> {
                    when(it.isControlDown) {
                        true -> Clipboard.copied = this
                        false -> {
                            val copied = Clipboard.copied
                            if(copied != null) {
                                removeImage()
                                if(copied.image != null) {
                                    children += ImageView(copied.image!!.image)
                                }
                                item = copied.item
                                meta = copied.meta
                                updatePopupText(popup)
                            }
                        }
                    }
                }
                else -> {
                }
            }
        }
    }

    fun setItem(item: Item, meta: Int) {
        this.item = item
        removeImage()
        val icons = item.icons
        children += ImageView(if(icons.size > meta) icons[meta] else (McClass(Items).valueOf<Item>("BEDROCK"))!!.icons.first())
        WaitingTimer().start({
            Platform.runLater {
                titleField.requestFocus()
                titleField.positionCaret(titleField.length)
            }
        }, Duration(2.0))
    }

    private fun updatePopupText(popup: LocalTextPopup) {
        popup.text = "${translate("preview.gui.slot")}: $n, X: $x, Y: $y${if(item != null) ", ${translate("preview.gui.item")}: ${item!!.name}:$meta" else ""}"
    }

    val image: ImageView?
        get() = children.filterIsInstance<ImageView>().firstOrNull()

    fun removeImage() = children.remove(image)

    fun clear() {
        item = null
        meta = 0
        removeImage()
    }

    companion object {
        var showingMenu: InsertMenu? = null
    }
}

private object Clipboard {
    var copied: GridMember? = null
}