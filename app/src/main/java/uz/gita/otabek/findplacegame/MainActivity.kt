package uz.gita.otabek.findplacegame

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.abs
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {
    private var success: Int = 0

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val iv1 = findViewById<ImageView>(R.id.iv1)
        val iv2 = findViewById<ImageView>(R.id.iv2)
        val iv3 = findViewById<ImageView>(R.id.iv3)
        val iv4 = findViewById<ImageView>(R.id.iv4)
        val iv5 = findViewById<ImageView>(R.id.iv5)
        val iv6 = findViewById<ImageView>(R.id.iv6)
        val iv7 = findViewById<ImageView>(R.id.iv7)
        val iv8 = findViewById<ImageView>(R.id.iv8)
        val iv9 = findViewById<ImageView>(R.id.iv9)

        val holder1 = findViewById<ImageView>(R.id.iv_holder1)
        val holder2 = findViewById<ImageView>(R.id.iv_holder2)
        val holder3 = findViewById<ImageView>(R.id.iv_holder3)
        val holder4 = findViewById<ImageView>(R.id.iv_holder4)
        val holder5 = findViewById<ImageView>(R.id.iv_holder5)
        val holder6 = findViewById<ImageView>(R.id.iv_holder6)
        val holder7 = findViewById<ImageView>(R.id.iv_holder7)
        val holder8 = findViewById<ImageView>(R.id.iv_holder8)
        val holder9 = findViewById<ImageView>(R.id.iv_holder9)

        val text1 = findViewById<TextView>(R.id.tv1)
        val text2 = findViewById<TextView>(R.id.tv2)
        val text3 = findViewById<TextView>(R.id.tv3)
        val text4 = findViewById<TextView>(R.id.tv4)
        val text5 = findViewById<TextView>(R.id.tv5)
        val text6 = findViewById<TextView>(R.id.tv6)
        val text7 = findViewById<TextView>(R.id.tv7)
        val text8 = findViewById<TextView>(R.id.tv8)
        val text9 = findViewById<TextView>(R.id.tv9)

        val middleText = findViewById<TextView>(R.id.tvMiddle)

        val listOfImages = arrayListOf(iv1, iv2, iv3, iv4, iv5, iv6, iv7, iv8, iv9)
        val listOfHolders = arrayListOf(holder5, holder4, holder3, holder2, holder1, holder6, holder7, holder9, holder8)
        val listOfTexts = arrayListOf(text5, text4, text3, text2, text1, text6, text7, text9, text8)

        for (i in 0 until 9) {
            action(listOfImages[i], listOfHolders[i], listOfTexts[i], listOfTexts.size, middleText)
//            scale(listOfImages[i])
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun action(iv: ImageView, holder: ImageView, text: TextView, count: Int, middleText: TextView) {
        var initX = 0f
        var initY = 0f

        var x0 = 0f
        var y0 = 0f

        iv.isClickable = true
        iv.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    initX = event.rawX
                    initY = event.rawY
                    x0 = iv.x
                    y0 = iv.y
                }

                MotionEvent.ACTION_MOVE -> {
                    val dx = event.rawX - initX
                    val dy = event.rawY - initY
                    iv.x = x0 + dx
                    iv.y = y0 + dy

                    val ax = abs(holder.x - iv.x)
                    val ay = abs(holder.y - iv.y)

                    if (ax < holder.width / 2 && ay < holder.height / 2) {
                        iv.x = holder.x
                        iv.y = holder.y
                        text.visibility = View.INVISIBLE
                        iv.isClickable = false
                        holder.setBackgroundResource(R.drawable.bg2)
                    } else {
                        holder.setBackgroundResource(R.drawable.bg1)
                        text.visibility = View.VISIBLE
                        iv.isClickable = true
                    }
                }

                MotionEvent.ACTION_UP -> {
                    if (iv.x == holder.x && iv.y == holder.y) {
                        success++
                        if (success == count) {
                            middleText.text = "Siz buni uddaladingiz!"
                            middleText.setTextColor(Color.GREEN)
                        } else {
                            middleText.text = "Pastdan tepaga mos hayvonlarni joylashtiring"
                            middleText.setTextColor(Color.BLACK)
                        }
                    }
                }
            }
            false
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun scale(iv: ImageView) {
        var startDistance = 0f
        var scaleFactor = 1.0f
        iv.setOnTouchListener { v, event ->
            when (event.actionMasked) {
                MotionEvent.ACTION_POINTER_DOWN -> {
                    if (event.pointerCount == 2) {
                        startDistance = getDistance(event)
                    }
                }

                MotionEvent.ACTION_MOVE -> {
                    if (event.pointerCount == 2) {
                        val currentDistance = getDistance(event)
                        if (startDistance != 0f) {
                            scaleFactor = currentDistance / startDistance
                            iv.scaleX = scaleFactor
                            iv.scaleY = scaleFactor

                        }
                    }
                }

                MotionEvent.ACTION_UP -> {
                    startDistance = 0f
                }
            }
            true
        }
    }

    private fun getDistance(event: MotionEvent): Float {
        val x = event.getX(0) - event.getX(1)
        val y = event.getY(0) - event.getY(1)
        return sqrt(x * x + y * y)
    }
}