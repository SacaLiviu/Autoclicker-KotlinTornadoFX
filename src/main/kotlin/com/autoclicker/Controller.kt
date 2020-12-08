package com.autoclicker

import kotlinx.coroutines.delay
import tornadofx.Controller
import java.awt.event.InputEvent
import kotlin.time.ExperimentalTime

class BasicController : Controller()     {
    var checkMouseSwitch:Boolean=false
    var interval:Int =0
    var clickStanga:Boolean=false
    var clickDreapta:Boolean=false
    var clickCount:Int=1
    var clickInfinit:Boolean=false
    var xPos:Int=0
    var yPos:Int=0
    var checkKeyboardSwitch:Boolean=false

  @ExperimentalTime
  suspend fun start(){
        val robotMouse = java.awt.Robot()
        var count=clickCount
        //print(count)
        if(clickStanga) {
            while(count>0) {
                if(clickInfinit) count= 9
                delay(interval.toLong()/2)
                robotMouse.mouseMove(xPos, yPos)
                delay(interval.toLong()/4)
                robotMouse.mousePress(InputEvent.BUTTON1_DOWN_MASK)
                robotMouse.mouseRelease(InputEvent.BUTTON1_DOWN_MASK)
                count--
                delay(interval.toLong()/4)
                if(checkKeyboardSwitch) break
            }
            checkKeyboardSwitch=false
        }
        if(clickDreapta){
            while(count>0) {
                if(clickInfinit) count= 9
                delay(interval.toLong()/2)
                robotMouse.mouseMove(xPos, yPos)
                delay(interval.toLong()/4)
                robotMouse.mousePress(InputEvent.BUTTON2_DOWN_MASK)
                robotMouse.mouseRelease(InputEvent.BUTTON2_DOWN_MASK)
                count--
                delay(interval.toLong()/4)
                if(checkKeyboardSwitch) break
            }
            checkKeyboardSwitch=false
        }
        if(clickStanga && clickDreapta){
            while(count>0) {
                if(clickInfinit) count= 9
                delay(interval.toLong()/2)
                robotMouse.mouseMove(xPos, yPos)
                delay(interval.toLong()/4)
                robotMouse.mousePress(InputEvent.BUTTON1_DOWN_MASK)
                robotMouse.mouseRelease(InputEvent.BUTTON1_DOWN_MASK)
                delay(interval.toLong()/4)
                robotMouse.mousePress(InputEvent.BUTTON2_DOWN_MASK)
                robotMouse.mouseRelease(InputEvent.BUTTON2_DOWN_MASK)
                count--
                delay(interval.toLong()/4)
                if(checkKeyboardSwitch) break
            }
            checkKeyboardSwitch=false
        }
    }
}