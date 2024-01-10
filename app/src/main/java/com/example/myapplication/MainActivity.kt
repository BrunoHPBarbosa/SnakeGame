package com.example.myapplication

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SnakeGameActivity : AppCompatActivity(), ScoreListener {
    private lateinit var scoreTextView: TextView
    private lateinit var snakeGameView: SnakeGameView
    private lateinit var btnUp: Button
    private lateinit var btnDown: Button
    private lateinit var btnLeft: Button
    private lateinit var btnRight: Button
    private lateinit var select: Button
    private lateinit var telacobra: ImageView
    private lateinit var startimg: ImageView
    private lateinit var comandoBotoes: ComandoBotoes
    private lateinit var blinkHandler: Handler
    private var isStartImageVisible = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        blinkHandler = Handler()
        startBlinking()

        telacobra = findViewById(R.id.telacobra)
        startimg = findViewById(R.id.startimg)
        scoreTextView = findViewById(R.id.scoreTextView)
        snakeGameView = findViewById(R.id.telagame)
        btnUp = findViewById(R.id.botaoup)
        btnDown = findViewById(R.id.btndown)
        btnLeft = findViewById(R.id.btnleft)
        btnRight = findViewById(R.id.btnright)
        select = findViewById(R.id.select)

        snakeGameView.setScoreListener(this)

        comandoBotoes = ComandoBotoes(snakeGameView, telacobra, startimg)

        btnUp.setOnClickListener { comandoBotoes.confUpButton() }
        btnDown.setOnClickListener { comandoBotoes.confDownButton() }
        btnLeft.setOnClickListener { comandoBotoes.confLeftButton() }
        btnRight.setOnClickListener { comandoBotoes.confRightButton() }
        select.setOnClickListener { comandoBotoes.confSelectButton() }
    }
    private fun startBlinking() {
        blinkHandler.post(object : Runnable {
            override fun run() {
                isStartImageVisible = !isStartImageVisible
                startimg.visibility = if (isStartImageVisible) View.VISIBLE else View.INVISIBLE
                blinkHandler.postDelayed(this, 500) // Altere o intervalo conforme necessário (aqui é 500ms)
            }
        })
    }
    override fun onScoreUpdated(score: Int) {
        scoreTextView.text = score.toString()
    }
}
