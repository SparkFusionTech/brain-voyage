package com.sparkfusion.quiz.brainvoyage.window

object StatusBarHeightOwner {

    private var _hasCutout = false
    var hasCutout
        get() = _hasCutout
        set(value) {
            _hasCutout = value
        }

    private var _height = 0
    val height get() = _height

    fun setHeightIfNotAlreadyInstalled(newHeight: Int) {
        if (newHeight <= 0 || height != 0) return
        _height = newHeight
    }
}
