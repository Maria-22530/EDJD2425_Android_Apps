package ipca.examples.spacefighter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier


class GameView : SurfaceView, Runnable {

    var playing = false
    var gameThread : Thread? = null
    lateinit var surfaceHolder : SurfaceHolder
    lateinit var canvas : Canvas

    lateinit var paint :Paint
    var stars = arrayListOf<Star>()

    var enemies = arrayListOf<Enemy>()
    lateinit var player : Player
    lateinit var boom : Boom

    var impactCount = 0

    // Variável para armazenar o número de vidas do jogador
    var lives = 3
    lateinit var lifeBitmap: Bitmap


    var gameOver = false
    var score = 0

    // Adiciona uma variável para o bitmap de fundo redimensionado
    lateinit var backgroundBitmap: Bitmap

    private fun init(context: Context, width: Int, height: Int){

        surfaceHolder = holder
        paint = Paint()

        for (i in 0..100){
            stars.add(Star(width, height))
        }
        for (i in 0..2){
            enemies.add(Enemy(context,width, height))
        }

        player = Player(context, width, height)
        boom = Boom(context, width, height)


        // Imagem que representa uma vida
        lifeBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.life)

        // Carregar e redimensionar a imagem de fundo para caber na tela
        val originalBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.bg)
        backgroundBitmap = Bitmap.createScaledBitmap(originalBitmap, width, height, false)

    }

    constructor(context: Context?, width: Int, height: Int) : super(context) {
        init(context!!, width, height)
    }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs){
        init(context!!, 0, 0)
    }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ){
        init(context!!, 0, 0)
    }

    fun resume() {
        playing = true
        gameThread = Thread(this)
        gameThread?.start()
    }

    fun pause() {
        playing = false
        gameThread?.join()
    }

    override fun run() {
        while (playing){
            update()
            draw()
            control()
        }
    }


    fun update(){

        if (gameOver) return // Não atualizar o jogo se for "Game Over"

        score++ // Aumentar a pontuação a cada atualização
        boom.x = -300
        boom.y = -300

        for (s in stars){
            s.update(30)
        }
        for (e in enemies){
            e.update(30)
            if (Rect.intersects(player.detectCollision, e.detectCollision)) {


                boom.x = e.x
                boom.y = e.y

                e.x = -300

                // Incrementa o contador de impactos e chama a função de impacto
                impact()
            }

        }
        player.update()
    }

    fun draw(){

        val backgroundBitmap: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.bg)

        val screenWidth: Int = width
        val screenHeight: Int = height

        if (surfaceHolder.surface.isValid){
            canvas = surfaceHolder.lockCanvas()

            canvas.drawColor(Color.BLACK)

            // Definir o Rect de destino para cobrir toda a tela

            val destRect = Rect(0, 0, screenWidth, screenHeight)

            // Desenhar a imagem de fundo escalada
            canvas.drawBitmap(backgroundBitmap, null, destRect, paint)

            paint.color = Color.WHITE
            for (star in stars) {
                paint.strokeWidth = star.starWidth.toFloat()
                canvas.drawPoint(star.x.toFloat(), star.y.toFloat(), paint)
            }


            canvas.drawBitmap(player.bitmap, player.x.toFloat(), player.y.toFloat(), paint)

            for (e in enemies) {
                canvas.drawBitmap(e.bitmap, e.x.toFloat(), e.y.toFloat(), paint)
            }
            canvas.drawBitmap(boom.bitmap, boom.x.toFloat(), boom.y.toFloat(), paint)

            // Desenhar as vidas restantes na tela
            drawLives(canvas)

            // Desenhar a pontuação na tela
            drawScore(canvas)

            //Texto de "Game Over" se o jogo estiver no estado "Game Over"
            if (gameOver) {
                paint.color = Color.RED
                paint.textSize = width/10f
                paint.textAlign = Paint.Align.CENTER
                canvas.drawText("GAME OVER", (width / 2).toFloat(), (height / 2).toFloat(), paint)
            }

            surfaceHolder.unlockCanvasAndPost(canvas)
        }
    }

    fun drawLives(canvas: Canvas) {
        val paddingRight = 120f // Ajuste o valor conforme necessário
        val paddingBottom = 90f  // Ajuste o valor conforme necessário

        val x = canvas.width - paint.measureText("Score: $score") - paddingRight
        val y = paddingBottom

        for (i in 0 until lives) {
            canvas.drawBitmap(lifeBitmap, (paddingRight + i * 80).toFloat(), paddingBottom, paint)

        }
    }

    fun control(){
        Thread.sleep(17)
    }

    // Função para desenhar a pontuação na tela
    fun drawScore(canvas: Canvas) {
        val paddingRight = 120f // Ajuste o valor conforme necessário
        val paddingBottom = 150f  // Ajuste o valor conforme necessário

        val x = canvas.width - paint.measureText("Score: $score") - paddingRight
        val y = paddingBottom

        paint.color = Color.WHITE
        paint.textSize = 90f
        canvas.drawText("Score: $score", x, y, paint)
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {

        if (gameOver) return true // Ignorar toques se o jogo estiver no estado "Game Over"

        when(event?.action){
            MotionEvent.ACTION_DOWN -> {
               // player.boosting = true
                player.x = event.x.toInt()
            }
            MotionEvent.ACTION_UP -> {
               // player.boosting = false
            }
            MotionEvent.ACTION_MOVE -> {
                player.x = event.x.toInt()
            }
        }
        return true
    }

    fun impact(){

        impactCount++
        if (impactCount >= 3) {
            // Reduz o número de vidas e reseta o contador de impactos
            lives--
            impactCount = 0

            if (lives <= 0){

                //Jogador morre após perder todas as vidas
                playing = false
                gameOver = true
            }
        }
    }



}


