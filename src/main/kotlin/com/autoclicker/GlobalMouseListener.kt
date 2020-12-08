package com.autoclicker

import org.jnativehook.mouse.NativeMouseEvent
import org.jnativehook.mouse.NativeMouseInputListener


class GlobalMouseListener(controller: BasicController) : NativeMouseInputListener {
    private var localController=controller
    override fun nativeMouseClicked(e: NativeMouseEvent) {
        localController.xPos = e.x
        localController.yPos=e.y
        localController.checkMouseSwitch=true
    }

    override fun nativeMousePressed(e: NativeMouseEvent) {

    }

    override fun nativeMouseReleased(e: NativeMouseEvent) {

    }

    override fun nativeMouseMoved(e: NativeMouseEvent) {

    }

    override fun nativeMouseDragged(e: NativeMouseEvent) {

    }
}