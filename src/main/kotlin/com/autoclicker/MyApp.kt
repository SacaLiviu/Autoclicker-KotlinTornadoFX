package com.autoclicker


import com.autoclicker.view.MainView
import javafx.scene.image.Image
import javafx.stage.Stage
import tornadofx.App
import tornadofx.addStageIcon


class MyApp: App(MainView::class,Styles::class){
    override fun start(stage: Stage) {
        addStageIcon(Image("file:///AutoClickerIcon.png"))
        super.start(stage)
        stage.width = 400.0
        stage.height = 400.0
        stage.isResizable=false
    }
}