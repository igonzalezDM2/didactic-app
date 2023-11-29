package com.example.didactic_app

import android.content.Context

class PuzzlePiece(context: Context) : androidx.appcompat.widget.AppCompatImageView(context) {
    var xCoord = 0
    var yCoord = 0
    var pieceWidth = 0
    var pieceHeight = 0
    var canMove = true
}