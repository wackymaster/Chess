package main

import javafx.application.Application
import javafx.stage.Stage
import view.*

class Main : Application() {
    private lateinit var controller: GUIController

    override fun start(primaryStage: Stage) {
        controller = GUIController(primaryStage)
        primaryStage.show()
    }

    override fun stop() {
        super.stop()
    }

    fun main() {
        launch()
    }

}