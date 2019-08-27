package gui.views

import gui.style.MainStylesheet
import gui.style.minusAssign
import gui.custom.windowView
import gui.view
import tornadofx.*

/**
 * TornadoFX main view off [INEZApplication]
 */
class MainView: View("INEZ") {

    override val root = windowView {
        titleBar {
            hbox {
                setId(MainStylesheet.titleBar)
                button {
                    addClass(MainStylesheet.dotButton)
                    style {
                        backgroundColor -= MainStylesheet.red
                    }
                    action {
                        currentStage?.close()
                    }
                }
                button {
                    addClass(MainStylesheet.dotButton)
                    style {
                        backgroundColor -= MainStylesheet.yellow
                    }
                    action {
                        currentStage?.isIconified = true
                        currentStage?.isAlwaysOnTop = false
                    }
                }
                button {
                    addClass(MainStylesheet.dotButton)
                    style {
                        backgroundColor -= MainStylesheet.blue
                    }
                    action {
                        currentStage?.isAlwaysOnTop = !(currentStage?.isAlwaysOnTop ?: true)
                    }
                }
            }
        }
        body {
            borderpane {
                top = view(Header())
                center = view(ListView())
                bottom = view(Footer())
            }
        }
    }
}
