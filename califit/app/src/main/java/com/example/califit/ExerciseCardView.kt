package com.example.califit

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.califit.R

class ExerciseCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var titleTextView: TextView
    private var imageView: ImageView

    init {
        orientation = HORIZONTAL
        LayoutInflater.from(context).inflate(R.layout.exercise_card, this, true)

        titleTextView = findViewById(R.id.cardTitle)
        imageView = findViewById(R.id.cardImage)
    }

    fun setCardTitle(title: String) {
        titleTextView.text = title
    }

    fun setImageResource(imageRes: Int) {
        imageView.setImageResource(imageRes)
    }
}
