package com.example.myapplication
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.View

interface ScoreListener {
    fun onScoreUpdated(score: Int)
}

class SnakeGameView : View {

    private lateinit var scoreListener: ScoreListener
    private lateinit var gameLoop: Runnable
    private val snakeSize = 20
    private var gridSize = 55
    private val snake = ArrayList<Pair<Int, Int>>()
    private var food: Pair<Int, Int> = Pair(0, 0)
    private var direction = Direction.RIGHT
    private var score = 0

    enum class Direction { UP, DOWN, LEFT, RIGHT }

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    fun init() {
        snake.add(Pair(0, 0))
        snake.add(Pair(0, 1))
        snake.add(Pair(0, 2))


        spawnFood()

        gameLoop = object : Runnable {
            override fun run() {
                moveSnake()
                checkCollision()
                invalidate()
                Handler(Looper.myLooper()!!).postDelayed(this, 100)
            }
        }

        val handler = Handler(Looper.myLooper()!!)
        handler.postDelayed(gameLoop, 100)
    }

    fun setScoreListener(listener: ScoreListener) {
        this.scoreListener = listener
    }

    private fun moveSnake() {
        val head = snake.first()
        var newHead: Pair<Int, Int> = head

        when (direction) {
            Direction.UP -> newHead = Pair(head.first, (head.second - 1 + gridSize) % gridSize)
            Direction.DOWN -> newHead = Pair(head.first, (head.second + 1) % gridSize)
            Direction.LEFT -> newHead = Pair((head.first - 1 + gridSize) % gridSize, head.second)
            Direction.RIGHT -> newHead = Pair((head.first + 1) % gridSize, head.second)
        }

        snake.add(0, newHead)

        if (newHead == food) {
            score += 1
            spawnFood()
            updateScoreTextView()
        } else {
            snake.removeAt(snake.size - 1)
        }
    }

    private fun updateScoreTextView() {
        scoreListener.onScoreUpdated(score)
    }

    private fun checkCollision() {
        val head = snake.first()
        for (i in 1 until snake.size) {
            if (head == snake[i]) {
                resetGame()
            }
        }
    }

    private fun spawnFood() {
        food = Pair((0 until gridSize).random(), (0 until gridSize).random())
        while (snake.contains(food)) {
            food = Pair((0 until gridSize).random(), (0 until gridSize).random())
        }
    }

    fun resetGame() {
        snake.clear()
        snake.add(Pair(0, 0))
        snake.add(Pair(0, 1))
        snake.add(Pair(0, 2))
        direction = Direction.RIGHT
        spawnFood()
        score = 0
        updateScoreTextView()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val paint = Paint()
        paint.color = Color.WHITE
        for (segment in snake) {
            canvas.drawRect(
                segment.first * snakeSize.toFloat(),
                segment.second * snakeSize.toFloat(),
                (segment.first + 1) * snakeSize.toFloat(),
                (segment.second + 1) * snakeSize.toFloat(),
                paint
            )
        }

        paint.color = Color.RED
        canvas.drawRect(
            food.first * snakeSize.toFloat(),
            food.second * snakeSize.toFloat(),
            (food.first + 1) * snakeSize.toFloat(),
            (food.second + 1) * snakeSize.toFloat(),
            paint
        )
    }


    fun changeDirection(newDirection: Direction) {
        if (direction == Direction.UP && newDirection != Direction.DOWN ||
            direction == Direction.DOWN && newDirection != Direction.UP ||
            direction == Direction.LEFT && newDirection != Direction.RIGHT ||
            direction == Direction.RIGHT && newDirection != Direction.LEFT
        ) {
            direction = newDirection
        }
    }
}
