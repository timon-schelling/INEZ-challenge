package gui.app

import gui.controllers.MainController
import gui.style.MainStylesheet
import gui.views.MainView
import javafx.scene.image.Image
import javafx.stage.StageStyle
import tornadofx.*
import javafx.stage.Stage
import java.io.File

/**
 * TornadoFX Main App
 */
class INEZApplication : App(MainView::class, MainStylesheet::class) {

    /**
     * configures [stage] (JavaFX Main Stage), Model and Controller
     * @param stage
     */
    override fun start(stage: Stage) {
        configureView(stage)
        super.start(stage)
        configureModel()
        configureController()
    }

    /**
     * configures [stage] (JavaFX Main Stage)
     * @param stage
     */
    private fun configureView(stage: Stage) {
        stage.initStyle(StageStyle.TRANSPARENT)
        stage.icons.add(Image(File("icon.png").inputStream()))
    }

    /**
     * configures [MainModel]
     */
    private fun configureModel() {

    }

    /**
     * configures [MainController]
     */
    private fun configureController() {
        MainController.load()
    }
}
