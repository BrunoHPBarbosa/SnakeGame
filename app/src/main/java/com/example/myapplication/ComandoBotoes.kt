package com.example.myapplication

import android.os.Handler
import android.view.View
import android.widget.ImageView

class ComandoBotoes(
    private val snakeGameView: SnakeGameView,
    private val telacobra: ImageView,
    private val startimg: ImageView
) {
    private var isMenuButtonPressed = false
    private val handler = Handler()

    fun confUpButton() {
        snakeGameView.changeDirection(SnakeGameView.Direction.UP)
    }

    fun confDownButton() {
        snakeGameView.changeDirection(SnakeGameView.Direction.DOWN)
    }

    fun confLeftButton() {
        snakeGameView.changeDirection(SnakeGameView.Direction.LEFT)
    }

    fun confRightButton() {
        snakeGameView.changeDirection(SnakeGameView.Direction.RIGHT)
    }

    fun confSelectButton() {
        telacobra.visibility = View.GONE
        stopBlinking()
        startimg.visibility = View.VISIBLE
        snakeGameView.startGame()
    }

    private fun stopBlinking() {
        handler.removeCallbacksAndMessages(null)
    }

    fun confMenuPauseButton() {
        if (isMenuButtonPressed) {
            isMenuButtonPressed = false
            handler.removeCallbacksAndMessages(null)
            snakeGameView.resumeGame()
        } else {
            isMenuButtonPressed = true
            handler.postDelayed({ onMenuButtonLongPress() }, 2000)
        }
    }

    private fun onMenuButtonLongPress() {
        snakeGameView.resetGame()
        isMenuButtonPressed = false
    }
}
