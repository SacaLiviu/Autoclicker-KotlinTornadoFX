package com.autoclicker

import javafx.geometry.Pos
import javafx.scene.text.FontWeight
import tornadofx.*

class Styles : Stylesheet() {
    companion object {
        val labelInterval by cssclass()
        val textFieldInterval by cssclass()
        val labelCO by cssclass()
    }

    init {
        labelInterval {
            fontSize = 13.px
            fontWeight=FontWeight.BOLD
            padding = box(5.px, 10.px)
            maxWidth = infinity
            alignment=Pos.CENTER
        }
        textFieldInterval{
            alignment=Pos.CENTER
            tileAlignment=Pos.CENTER


        }
        labelCO{
            alignment=Pos.CENTER
            fontWeight=FontWeight.BOLD
        }
    }
}