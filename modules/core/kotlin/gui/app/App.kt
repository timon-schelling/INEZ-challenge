package gui.app

import gui.controllers.MainController
import gui.style.MainStylesheet
import gui.views.MainView
import javafx.stage.StageStyle
import tornadofx.*
import javafx.stage.Stage
import kotlin.concurrent.thread

class INEZApplication : App(MainView::class, MainStylesheet::class) {
    override fun start(stage: Stage) {
        stage.initStyle(StageStyle.TRANSPARENT)
        super.start(stage)
        MainController.load()
    }
}
