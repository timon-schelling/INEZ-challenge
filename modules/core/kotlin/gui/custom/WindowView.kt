package gui.custom

import javafx.event.EventHandler
import javafx.event.EventTarget
import javafx.geometry.Point2D
import javafx.scene.Cursor
import javafx.scene.Node
import javafx.scene.Parent
import javafx.scene.input.MouseEvent
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.scene.paint.Color
import javafx.stage.Screen
import javafx.stage.Stage
import javafx.stage.StageStyle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tornadofx.*

/**
 * a TornadoFX view that imitates a resizeable and draggable window
 * should be used on a [Stage] with style set to [StageStyle.TRANSPARENT]
 */
open class WindowView(): View() {

    private val window by cssid()

    private val titleBar by cssid(snakeCase = true)

    private lateinit var windowTitleBar: HBox
    private lateinit var windowBody: BorderPane

    override val root = stackpane {
        setId(window)
        borderpane {
            top {
                hbox {
                    setId(titleBar)
                    windowTitleBar = this
                    GlobalScope.launch {
                        withContext(Dispatchers.Main) {
                            currentStage?.hide()
                            currentStage?.scene?.fill = Color.TRANSPARENT
                            currentStage?.addResizeListener(400.0, 500.0)
                            currentStage?.addDragZone(this@hbox)
                            currentStage?.show()
                        }
                    }
                }
            }
            windowBody = this
        }
    }

    /**
     * TornadoFX dsl function used to configure [windowTitleBar]
     * @param op TornadoFX dsl block used to configure [windowTitleBar]
     */
    fun titleBar(op: HBox.() -> Unit) = windowTitleBar.apply {
        apply(op)
    }

    /**
     * TornadoFX dsl function used to configure [windowBody]
     * @param op TornadoFX dsl block used to configure  [windowBody]
     */
    fun body(op: BorderPane.() -> Unit) = windowBody.apply {
        center {
            apply(op)
        }
    }

    private fun Stage.addDragZone(zone: Parent) = allowDrag(zone, this)
    private fun allowDrag(root: Parent, stage: Stage, dragZonePadding: Double = 7.0) {
        val SCREEN_BOUNDS = Screen.getPrimary()
                .visualBounds
        var offset: Point2D? = null
        var pressPosition: Point2D? = null
        var isDragAllowed = false
        root.setOnMousePressed {
            offset = Point2D(it.sceneX, it.sceneY)
            pressPosition = Point2D(it.x, it.y)
            isDragAllowed = (pressPosition!!.y > dragZonePadding &&
                             pressPosition!!.y < root.boundsInParent.height - dragZonePadding &&
                             pressPosition!!.x > dragZonePadding &&
                             pressPosition!!.x < root.boundsInParent.width - dragZonePadding)
        }

        root.setOnMouseDragged {
            if (isDragAllowed) {
                if (it.screenY < SCREEN_BOUNDS.maxY - 20)
                    stage.y = it.screenY - offset!!.y
                stage.x = it.screenX - offset!!.x
            }
        }

        root.setOnMouseReleased {
            if (stage.y < 0.0) stage.y = 0.0
        }
    }

    fun Stage.addResizeListener(
            minWidth: Double = 0.0,
            minHeight: Double = 0.0,
            maxWidth: Double = java.lang.Double.MAX_VALUE,
            maxHeight: Double = java.lang.Double.MAX_VALUE
    ) {
        val resizeListener = ResizeListener(this)
        this.scene.addEventHandler(MouseEvent.MOUSE_MOVED, resizeListener)
        this.scene.addEventHandler(MouseEvent.MOUSE_PRESSED, resizeListener)
        this.scene.addEventHandler(MouseEvent.MOUSE_DRAGGED, resizeListener)
        this.scene.addEventHandler(MouseEvent.MOUSE_EXITED, resizeListener)
        this.scene.addEventHandler(MouseEvent.MOUSE_EXITED_TARGET, resizeListener)

        resizeListener.setMinWidth(minWidth)
        resizeListener.setMinHeight(minHeight)
        resizeListener.setMaxWidth(maxWidth)
        resizeListener.setMaxHeight(maxHeight)

        val children = this.scene.root.childrenUnmodifiable
        for (child in children) {
            addListenerDeeply(child, resizeListener)
        }
    }

    private fun addListenerDeeply(node: Node, listener: EventHandler<MouseEvent>) {
        node.addEventHandler(MouseEvent.MOUSE_MOVED, listener)
        node.addEventHandler(MouseEvent.MOUSE_PRESSED, listener)
        node.addEventHandler(MouseEvent.MOUSE_DRAGGED, listener)
        node.addEventHandler(MouseEvent.MOUSE_EXITED, listener)
        node.addEventHandler(MouseEvent.MOUSE_EXITED_TARGET, listener)
        if (node is Parent) {
            val children = node.childrenUnmodifiable
            for (child in children) {
                addListenerDeeply(child, listener)
            }
        }
    }

    internal class ResizeListener(private val stage: Stage) : EventHandler<MouseEvent> {
        private var cursorEvent = Cursor.DEFAULT
        private val border = 7
        private var startX = 0.0
        private var startY = 0.0

        private var minWidth: Double = 0.toDouble()
        private var maxWidth: Double = 0.toDouble()
        private var minHeight: Double = 0.toDouble()
        private var maxHeight: Double = 0.toDouble()

        fun setMinWidth(minWidth: Double) {
            this.minWidth = minWidth
        }

        fun setMaxWidth(maxWidth: Double) {
            this.maxWidth = maxWidth
        }

        fun setMinHeight(minHeight: Double) {
            this.minHeight = minHeight
        }

        fun setMaxHeight(maxHeight: Double) {
            this.maxHeight = maxHeight
        }

        override fun handle(mouseEvent: MouseEvent) {
            val mouseEventType = mouseEvent.eventType
            val scene = stage.scene

            val mouseEventX = mouseEvent.sceneX
            val mouseEventY = mouseEvent.sceneY
            val sceneWidth = scene.width
            val sceneHeight = scene.height

            if (MouseEvent.MOUSE_MOVED == mouseEventType) {
                if (mouseEventX < border && mouseEventY < border) {
                    cursorEvent = Cursor.NW_RESIZE
                }
                else if (mouseEventX < border && mouseEventY > sceneHeight - border) {
                    cursorEvent = Cursor.SW_RESIZE
                }
                else if (mouseEventX > sceneWidth - border && mouseEventY < border) {
                    cursorEvent = Cursor.NE_RESIZE
                }
                else if (mouseEventX > sceneWidth - border && mouseEventY > sceneHeight - border) {
                    cursorEvent = Cursor.SE_RESIZE
                }
                else if (mouseEventX < border) {
                    cursorEvent = Cursor.W_RESIZE
                }
                else if (mouseEventX > sceneWidth - border) {
                    cursorEvent = Cursor.E_RESIZE
                }
                else if (mouseEventY < border) {
                    cursorEvent = Cursor.N_RESIZE
                }
                else if (mouseEventY > sceneHeight - border) {
                    cursorEvent = Cursor.S_RESIZE
                }
                else {
                    cursorEvent = Cursor.DEFAULT
                }
                scene.cursor = cursorEvent
            }
            else if (MouseEvent.MOUSE_EXITED == mouseEventType || MouseEvent.MOUSE_EXITED_TARGET == mouseEventType) {
                scene.cursor = Cursor.DEFAULT
            }
            else if (MouseEvent.MOUSE_PRESSED == mouseEventType) {
                startX = stage.width - mouseEventX
                startY = stage.height - mouseEventY
            }
            else if (MouseEvent.MOUSE_DRAGGED == mouseEventType) {
                if (Cursor.DEFAULT != cursorEvent) {
                    if (Cursor.W_RESIZE != cursorEvent && Cursor.E_RESIZE != cursorEvent) {
                        val minHeight = if (stage.minHeight > border * 2) stage.minHeight else border * 2
                        if (Cursor.NW_RESIZE == cursorEvent || Cursor.N_RESIZE == cursorEvent
                            || Cursor.NE_RESIZE == cursorEvent) {
                            if (stage.height > this.minHeight || mouseEventY < 0) {
                                setStageHeight(stage.y - mouseEvent.screenY + stage.height)
                                stage.y = mouseEvent.screenY
                            }
                        }
                        else {
                            if (stage.height > this.minHeight || mouseEventY + startY - stage.height > 0) {
                                setStageHeight(mouseEventY + startY)
                            }
                        }
                    }

                    if (Cursor.N_RESIZE != cursorEvent && Cursor.S_RESIZE != cursorEvent) {
                        val minWidth = if (stage.minWidth > border * 2) stage.minWidth else border * 2
                        if (Cursor.NW_RESIZE == cursorEvent || Cursor.W_RESIZE == cursorEvent
                            || Cursor.SW_RESIZE == cursorEvent) {
                            if (stage.width > this.minWidth || mouseEventX < 0) {
                                setStageWidth(stage.x - mouseEvent.screenX + stage.width)
                                stage.x = mouseEvent.screenX
                            }
                        }
                        else {
                            if (stage.width > this.minWidth || mouseEventX + startX - stage.width > 0) {
                                setStageWidth(mouseEventX + startX)
                            }
                        }
                    }
                }

            }
        }

        private fun setStageWidth(width: Double) {
            var width = width
            width = Math.min(width, maxWidth)
            width = Math.max(width, minWidth)
            stage.width = width
        }

        private fun setStageHeight(height: Double) {
            var height = height
            height = Math.min(height, maxHeight)
            height = Math.max(height, minHeight)
            stage.height = height
        }

    }

}

fun EventTarget.windowView(op: WindowView.() -> Unit = {}) = WindowView().apply(op).root


