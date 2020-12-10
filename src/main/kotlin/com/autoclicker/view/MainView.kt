package com.autoclicker.view

import com.autoclicker.BasicController
import com.autoclicker.GlobalKeyListener
import com.autoclicker.GlobalMouseListener
import com.autoclicker.Styles
import javafx.scene.control.Button
import javafx.scene.control.CheckBox
import javafx.scene.control.TextField
import javafx.scene.image.Image
import javafx.scene.layout.Priority
import javafx.scene.text.FontWeight
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jnativehook.GlobalScreen
import org.jnativehook.NativeHookException
import tornadofx.*
import java.util.logging.Level
import java.util.logging.Logger
import kotlin.system.exitProcess
import kotlin.time.ExperimentalTime

class MainView : View("Autoclicker") {
    private val controller: BasicController by inject()
    private var intervalTextField: TextField by singleAssign()
    private var clicksTextField: TextField by singleAssign()
    private var xPosTextField: TextField by singleAssign()
    private var yPosTextField: TextField by singleAssign()
    private var clickStangaCheckbox: CheckBox by singleAssign()
    private var clickDreaptaCheckbox: CheckBox by singleAssign()
    private var infinitCheckbox: CheckBox by singleAssign()
    private var startButton: Button by singleAssign()
    private var pickButton: Button by singleAssign()

    @ExperimentalTime
    override val root = vbox {
        vbox {
            label("INTERVAL(ms)") {
                addClass(Styles.labelInterval)
            }
            intervalTextField = textfield {
                addClass(Styles.textFieldInterval)
                setPrefSize(80.0, 30.0)
                vboxConstraints {
                    marginLeft = 150.0
                    marginRight = 150.0
                    vgrow = Priority.NEVER
                }
                filterInput { it.controlNewText.isInt() }
                tooltip("Input the interval in miliseconds between clicks")
            }

        }
        label("CLICK OPTIONS") {
            vboxConstraints {
                marginLeft = 145.0
                marginTop = 25.0
            }
        }
        hbox {
            clickStangaCheckbox = checkbox("Left Click") {
                hboxConstraints {
                    marginLeft = 75.0
                    marginTop = 25.0
                }
                tooltip("Check this box if you want the left mouse button to be pressed")
            }
            clickDreaptaCheckbox = checkbox("Right Click") {
                hboxConstraints {
                    marginLeft = 75.0
                    marginTop = 25.0
                    marginBottom = 25.0
                }
                tooltip("Check this box if you want the right mouse button to be pressed")
            }
        }
        group {
            line {
                startX = 0.0
                startY = 0.0
                endX = 400.0
                endY = 0.0
            }
        }
        hbox {
            label("How many clicks?") {
                hboxConstraints {
                    marginTop = 28.0
                    marginLeft = 10.0
                }
                style {
                    fontWeight = FontWeight.BOLD
                    fontSize = 12.px
                }

            }
            clicksTextField = textfield {
                hboxConstraints {
                    marginTop = 25.0
                    marginLeft = 10.0
                    marginRight = 15.0
                    setPrefSize(80.0, 20.0)
                    prefWidth(10.0)
                }
                filterInput { it.controlNewText.isInt() }
                tooltip("Enter how many times should the mouse be pressed")
            }
            infinitCheckbox = checkbox("∞") {
                hboxConstraints {
                    marginTop = 25.0
                    marginLeft = 20.0
                    marginBottom = 20.0
                }
                action{
                    if(infinitCheckbox.isSelected) {
                        clicksTextField.disableProperty().set(true)
                        controller.clickInfinit=true
                    }
                    else
                        clicksTextField.disableProperty().set(false)

                }
                tooltip("Check this box if you want to run infinitely")
                style {
                    fontWeight = FontWeight.BOLD
                    fontSize = 16.px
                }
            }
        }

        group {
            line {
                startX = 0.0
                startY = 0.0
                endX = 400.0
                endY = 0.0
            }
        }

        hbox {

            pickButton=button("Pick") {
                hboxConstraints {
                    marginTop = 25.0
                    marginLeft = 25.0
                }
                tooltip("Press this button then press click where you want to get the X and Y coordinates")
                action {
                    GlobalScope.launch {
                        try {
                            disableButtons()
                            if(!clicksTextField.disableProperty().get())
                                clicksTextField.disableProperty().set(true)
                            disableLogger()
                            GlobalScreen.registerNativeHook()
                            val mouse = GlobalMouseListener(controller)
                            GlobalScreen.addNativeMouseListener(mouse)
                            while (true)
                                if (controller.checkMouseSwitch) break
                            enableButtons()
                            enableBuggedButton()
                            GlobalScreen.removeNativeMouseListener(mouse)
                            GlobalScreen.unregisterNativeHook()

                        } catch (ex: NativeHookException) {
                            System.err.println("There was a problem registering the native hook.")
                            System.err.println(ex.message)
                            exitProcess(1)
                        }
                        controller.checkMouseSwitch = false
                        try {
                            xPosTextField.textProperty().set(controller.xPos.toString())
                            yPosTextField.textProperty().set(controller.yPos.toString())
                        }
                        catch (err:NullPointerException){
                            print("There has been an error assigning the values")
                        }
                    }
                }
            }
            label("X= ") {
                hboxConstraints {
                    marginTop = 28.0
                    marginLeft = 38.5
                }
            }
            xPosTextField = textfield {
                hboxConstraints {
                    marginTop = 25.0
                }
                tooltip("Enter the X coordinate")
                setPrefSize(80.0, 20.0)
                filterInput { it.controlNewText.isInt() }
            }
            label("Y= ") {
                hboxConstraints {
                    marginTop = 28.0
                    marginLeft = 25.0
                }
            }
            yPosTextField = textfield {
                hboxConstraints {
                    marginTop = 25.0
                }
                tooltip("Enter the Y coordinate")
                setPrefSize(80.0, 20.0)
                filterInput { it.controlNewText.isInt() }
            }

        }
        startButton = button("START(F3)/STOP(F4)") {
            vboxConstraints {
                marginTop = 25.0
                marginLeft = 125.0
            }
            action {
                GlobalScreen.registerNativeHook()
                val keyboard=GlobalKeyListener(controller)
                GlobalScreen.addNativeKeyListener(keyboard)
                disableButtons()
                disableBuggedButton()
                disableLogger()
                setAll()
                GlobalScope.launch {
                    controller.start()
                    GlobalScreen.removeNativeKeyListener(keyboard)
                    GlobalScreen.unregisterNativeHook()
                    enableButtons()
                    enableBuggedButton()
                }
            }
            shortcut("F3")
        }
    }




    private fun disableLogger() {
        val logger = Logger.getLogger(GlobalScreen::class.java.getPackage().name)
        logger.level = Level.OFF
        logger.useParentHandlers = false
    }

    private fun disableButtons(){
        startButton.disableProperty().set(true)
        pickButton.disableProperty().set(true)
        intervalTextField.disableProperty().set(true)
        clickDreaptaCheckbox.disableProperty().set(true)
        clickStangaCheckbox.disableProperty().set(true)
        infinitCheckbox.disableProperty().set(true)
        xPosTextField.disableProperty().set(true)
        yPosTextField.disableProperty().set(true)
    }

    private fun enableButtons(){
        startButton.disableProperty().set(false)
        pickButton.disableProperty().set(false)
        intervalTextField.disableProperty().set(false)
        clickDreaptaCheckbox.disableProperty().set(false)
        clickStangaCheckbox.disableProperty().set(false)
        infinitCheckbox.disableProperty().set(false)
        xPosTextField.disableProperty().set(false)
        yPosTextField.disableProperty().set(false)
    }

    private fun enableBuggedButton(){
        clicksTextField.disableProperty().set(false)
    }
    private fun disableBuggedButton(){
        clicksTextField.disableProperty().set(true)
    }

    private fun setAll(){
        if (intervalTextField.text != "")
            controller.interval = intervalTextField.text.toInt()
        if (clicksTextField.text != "")
            controller.clickCount = clicksTextField.text.toInt()
        if (xPosTextField.text != "")
            controller.xPos = xPosTextField.text.toInt()
        if (yPosTextField.text != "")
            controller.yPos = yPosTextField.text.toInt()
        controller.clickStanga = clickStangaCheckbox.isSelected
        controller.clickDreapta = clickDreaptaCheckbox.isSelected
        controller.clickInfinit = infinitCheckbox.isSelected
    }
    init {
        addStageIcon(Image("file:///AutoClickerIcon.png"))
    }
}


