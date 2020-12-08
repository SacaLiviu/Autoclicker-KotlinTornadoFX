package com.autoclicker

import org.jnativehook.keyboard.NativeKeyEvent
import org.jnativehook.keyboard.NativeKeyListener
import kotlin.time.ExperimentalTime

class GlobalKeyListener(controller: BasicController):NativeKeyListener {
    private var localController=controller
    @ExperimentalTime
    override fun nativeKeyPressed(e: NativeKeyEvent) {
        if(e.keyCode == NativeKeyEvent.VC_F4)
            localController.checkKeyboardSwitch=true

    }

    override fun nativeKeyReleased(e: NativeKeyEvent) {
        if(e.keyCode == NativeKeyEvent.VC_F4){
            localController.checkKeyboardSwitch=true
        }
    }

    override fun nativeKeyTyped(e: NativeKeyEvent) {
        if(e.keyCode == NativeKeyEvent.VC_F4){
            localController.checkKeyboardSwitch=true
        }
    }
}