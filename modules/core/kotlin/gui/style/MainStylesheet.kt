package gui.style

import javafx.geometry.Pos
import javafx.scene.paint.Color
import tornadofx.*

class MainStylesheet : Stylesheet() {

    companion object {
        val bg = c("#242424")
        val bg100 = bg
        val bg90 = bg.brightness(0.9)
        val bg80 = bg.brightness(0.8)
        val bg70 = bg.brightness(0.7)
        val bg60 = bg.brightness(0.6)
        val bg50 = bg.brightness(0.5)
        val bg40 = bg.brightness(0.4)
        val bg30 = bg.brightness(0.3)
        val bg20 = bg.brightness(0.2)
        val bg10 = bg.brightness(0.1)


        val text = c("#a1a1a1")
        val text100 = text
        val text90 = text.brightness(0.9)
        val text80 = text.brightness(0.8)
        val text70 = text.brightness(0.7)
        val text60 = text.brightness(0.6)
        val text50 = text.brightness(0.5)
        val text40 = text.brightness(0.4)
        val text30 = text.brightness(0.3)
        val text20 = text.brightness(0.2)
        val text10 = text.brightness(0.1)


        val blue = c("#007bff")
        val indigo = c("#6610f2")
        val purple = c("#6f42c1")
        val pink = c("#e83e8c")
        val red = c("#dc3545")
        val orange = c("#fd7e14")
        val yellow = c("#ffc107")
        val green = c("#28a745")
        val teal = c("#20c997")
        val cyan = c("#17a2b8")
        val white = c("#fff")
        val gray = c("#6c757d")

        val controlBorderColor = box(text)
        val controlBorderWidth = box(0.0.em)
        val controlBorderRadius = box(0.2.em)

        val primaryColor = c("#ddd")
        val primaryColor100 = primaryColor
        val primaryColor90 = primaryColor.brightness(0.9)
        val primaryColor80 = primaryColor.brightness(0.8)
        val primaryColor70 = primaryColor.brightness(0.7)
        val primaryColor60 = primaryColor.brightness(0.6)
        val primaryColor50 = primaryColor.brightness(0.5)
        val primaryColor40 = primaryColor.brightness(0.4)
        val primaryColor30 = primaryColor.brightness(0.3)
        val primaryColor20 = primaryColor.brightness(0.2)


        val primaryColor10 = text.brightness(0.1)

        val standard by cssclass()
        val bgColor100 by cssclass()
        val bgColor90 by cssclass()
        val bgColor80 by cssclass()
        val bgColor70 by cssclass()
        val bgColor60 by cssclass()
        val bgColor50 by cssclass()
        val bgColor40 by cssclass()
        val bgColor30 by cssclass()
        val bgColor20 by cssclass()

        val bgColor10 by cssclass()
        val textColor100 by cssclass()
        val textColor90 by cssclass()
        val textColor80 by cssclass()
        val textColor70 by cssclass()
        val textColor60 by cssclass()
        val textColor50 by cssclass()
        val textColor40 by cssclass()
        val textColor30 by cssclass()
        val textColor20 by cssclass()


        val textColor10 by cssclass()
        val iconButton by cssclass()


        val dotButton by cssclass()

        val window by cssid()

        val titleBar by cssid(snakeCase = true)

        val resetIcon by cssclass()
        val strikethrough by cssclass()
        val itemRoot by cssclass()
        val contentLabel by cssid()

        val addItemRoot by cssclass()
        val header by cssclass()

        val footer by cssclass()
    }


    init {

        fun CssSelectionBlock.standard() {
            backgroundColor += bg
            textFill = text
            highlightFill = text
            highlightTextFill = bg
            borderWidth += box(0.px)
        }

        fun CssSelectionBlock.standardColors() {
            backgroundColor += bg
            textFill = text
        }

        bgColor100 {
            backgroundColor -= bg100
        }
        bgColor90 {
            backgroundColor -= bg90
        }
        bgColor80 {
            backgroundColor -= bg80
        }
        bgColor70 {
            backgroundColor -= bg70
        }
        bgColor60 {
            backgroundColor -= bg60
        }
        bgColor50 {
            backgroundColor -= bg50
        }
        bgColor40 {
            backgroundColor -= bg40
        }
        bgColor30 {
            backgroundColor -= bg30
        }
        bgColor20 {
            backgroundColor -= bg20
        }
        bgColor10 {
            backgroundColor -= bg10
        }


        textColor100 {
            textFill = text100
        }
        textColor90 {
            textFill = text90
        }
        textColor80 {
            textFill = text80
        }
        textColor70 {
            textFill = text70
        }
        textColor60 {
            textFill = text60
        }
        textColor50 {
            textFill = text50
        }
        textColor40 {
            textFill = text40
        }
        textColor30 {
            textFill = text30
        }
        textColor20 {
            textFill = text20
        }
        textColor10 {
            textFill = text10
        }




        standard {
            standardColors()
        }




        default {
            standardColors()
        }




        label {
            standardColors()
            textFill = text
            backgroundColor -= bg
            highlightFill = text
            highlightTextFill = bg
        }




        textArea {
            standardColors()
            backgroundColor -= bg
            textFill = text
            highlightFill = text
            highlightTextFill = bg
        }




        textField {
            standardColors()
            backgroundColor -= bg
            textFill = text
            highlightFill = text
            highlightTextFill = bg
            borderColor -= controlBorderColor
            borderWidth -= controlBorderWidth
            borderRadius -= controlBorderRadius
            backgroundRadius -= controlBorderRadius
        }




        button {
            standardColors()
            backgroundColor -= bg
            textFill = text
            highlightFill = text
            highlightTextFill = bg
            borderColor -= controlBorderColor
            borderWidth -= controlBorderWidth
            borderRadius -= controlBorderRadius
            backgroundRadius -= controlBorderRadius

            and(hover) {
                backgroundColor -= bg80
                textFill = text80
            }
        }

        iconButton {
            backgroundColor -= Color.TRANSPARENT
            borderWidth -= box(0.px)
            padding = box(0.px)
            and(hover) {
                backgroundColor -= Color.TRANSPARENT
            }
        }




        splitPane {
            standardColors()
            backgroundColor -= bg
            padding = box(0.px)
        }

        splitPaneDivider {
            standardColors()
            backgroundColor -= bg
            padding = box(8.px)
        }




        scrollBar {

            val thumbWidth = 0.8.em

            standardColors()
            backgroundColor -= bg
            borderWidth -= box(0.px)
            fontSize = 1.em

            and(horizontal) {
                incrementButton {
                    padding = box(0.px, 0.px, thumbWidth, 0.px)
                }

                decrementButton {
                    padding = box(0.px, 0.px, thumbWidth, 0.px)
                }
            }

            and(vertical) {
                incrementButton {
                    padding = box(0.px, thumbWidth, 0.px, 0.px)
                }

                decrementButton {
                    padding = box(0.px, thumbWidth, 0.px, 0.px)
                }
            }

            thumb {
                backgroundColor -= bg90
                backgroundRadius -= box(0.5.em)
                padding = box(thumbWidth / 2)
            }

            track {
                backgroundColor -= bg
                backgroundRadius -= controlBorderRadius
                borderWidth -= box(0.px)
                borderColor -= controlBorderColor
                borderRadius -= controlBorderRadius
            }

            incrementButton {
                backgroundColor -= Color.TRANSPARENT
                backgroundRadius -= box(0.px)
            }

            decrementButton {
                backgroundColor -= Color.TRANSPARENT
                backgroundRadius -= box(0.px)
            }

            incrementArrow {
                shape = ""
                padding = box(0.px)
            }

            decrementArrow {
                shape = ""
                padding = box(0.px)
            }
        }




        scrollPane {
            standardColors()
            backgroundColor -= bg
            viewport {
                standardColors()
                backgroundColor -= bg
            }
        }

        corner {
            standardColors()
            backgroundColor -= bg
            maxHeight = 0.px
        }




        contextMenu {
            standardColors()
            backgroundColor -= bg
            textFill = text
        }

        menuItem {
            standardColors()
            textFill = text

            label {
                textFill = text
            }

            and(focused) {

                backgroundColor -= bg80
                textFill = text80

                label {
                    backgroundColor -= bg80
                    textFill = text80
                }
            }
        }

        separator {
            standardColors()
            backgroundColor -= text
        }




        listView{
            standard()

            cell {
                standard()
            }
        }




        checkBox {

            textFill = text

            minHeight = 1.3.em
            minWidth = 1.33.em

            box {
                backgroundColor -= bg
                borderColor -= box(text)
                borderRadius -= box(0.15.em)
                borderWidth -= box(0.1.em)

                prefHeight = 1.3.em
                prefWidth = 1.33.em
            }

            and(selected) {
                mark {
                    backgroundColor -= bg
                    shape = "M20.285 2l-11.285 11.567-5.286-5.011-3.714 3.716 9 8.728 15-15.285z"
                    translateX = -0.7.px
                    translateY = 1.px
                }

                box {
                    backgroundColor -= text
                }
            }
        }




        window {
            standardColors()
            minWidth = 400.px
            backgroundRadius -= box(8.px)
            borderRadius -= box(8.px)
        }

        titleBar {
            padding = box(8.px)
            spacing = 8.px
        }


        dotButton {
            standardColors()
            maxHeight = 16.px
            maxWidth = 16.px
            minHeight = 16.px
            minWidth = 16.px
            backgroundRadius -= box(16.px)
        }

        strikethrough {
            text {
                strikethrough = true
            }
        }

        itemRoot {
            standardColors()
            padding = box(0.em, 0.px, 0.em, 3.px)
            button {
                standardColors()
                backgroundColor -= c("transparent")
                padding = box(0.px, 0.3.em)
            }
            label {
                standardColors()
                padding = box(1.px)
            }
            checkBox {
                standardColors()
                padding = box(0.px)
                prefHeight = 1.3.em
                prefWidth = 1.33.em
                and(selected) {
                    box {
                        backgroundColor -= green
                        borderColor -= box(green)
                    }
                }
            }
            alignment = Pos.CENTER_LEFT
        }

        contentLabel {
            standardColors()
            fontSize = 1.2.em
        }

        addItemRoot {
            standardColors()
            padding = box(0.5.em, 12.0.px)
            spacing = 12.px
            textField {
                backgroundColor -= bg80
            }
        }

        header {
            standardColors()
            alignment = Pos.CENTER
            star {
                alignment = Pos.CENTER_LEFT
            }
        }

        footer {
            standardColors()
            backgroundColor -= Color.TRANSPARENT
            padding = box(10.px, 12.px)
            alignment = Pos.CENTER
            spacing = 15.px
            star {
                spacing = 10.px
            }
        }
    }
}
