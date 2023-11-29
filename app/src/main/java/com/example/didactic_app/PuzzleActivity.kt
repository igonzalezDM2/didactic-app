package com.example.didactic_app

import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.abs


class PuzzleActivity : AppCompatActivity() {

    private lateinit var pieces: List<PuzzlePiece>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_puzzle)

        var layout = findViewById<RelativeLayout>(R.id.puzzleLayout)
        var imageView = findViewById<ImageView>(R.id.ivPuzzle)

        // run image related code after the view was laid out
        // to have all dimensions calculated

        imageView.post {
            pieces = splitImage()
            var touchListener = TouchListener()
            for (piece in pieces) {
                piece.setOnTouchListener(touchListener)
                layout.addView(piece)
            }
        }


    }

    private fun splitImage(): List<PuzzlePiece> {
        var piecesNumber = 12
        var rows = 4
        var cols = 3
        var imageView = findViewById<ImageView>(R.id.ivPuzzle)
        var pieces = ArrayList<PuzzlePiece>(piecesNumber)

        // Get the scaled bitmap of the source image
        var drawable = imageView.drawable as BitmapDrawable
        var bitmap = drawable.bitmap


        var dimensions = getBitmapPositionInsideImageView(imageView)
        var scaledBitmapLeft = dimensions!![0]
        var scaledBitmapTop = dimensions[1]
        var scaledBitmapWidth = dimensions[2]
        var scaledBitmapHeight = dimensions[3]

        var croppedImageWidth: Int = scaledBitmapWidth - 2 * abs(scaledBitmapLeft)
        var croppedImageHeight: Int = scaledBitmapHeight - 2 * abs(scaledBitmapTop)

        var scaledBitmap =
            Bitmap.createScaledBitmap(bitmap, scaledBitmapWidth, scaledBitmapHeight, true)
        var croppedBitmap = Bitmap.createBitmap(
            scaledBitmap,
            abs(scaledBitmapLeft),
            abs(scaledBitmapTop),
            croppedImageWidth,
            croppedImageHeight
        )

        // Calculate the with and height of the pieces
        var pieceWidth = croppedImageWidth / cols
        var pieceHeight = croppedImageHeight / rows

        // Create each bitmap piece and add it to the resulting array
        var yCoord = 0
        for (row in 0 until rows) {
            var xCoord = 0
            for (col in 0 until cols) {
                var pieceBitmap =
                    Bitmap.createBitmap(croppedBitmap, xCoord, yCoord, pieceWidth, pieceHeight)
                var piece = PuzzlePiece(applicationContext)
                piece.setImageBitmap(pieceBitmap)
                piece.xCoord = imageView.left
                piece.yCoord = imageView.top
                piece.pieceWidth = pieceWidth
                piece.pieceHeight = pieceHeight
                pieces.add(piece)

                xCoord += pieceWidth
            }
            yCoord += pieceHeight
        }
        return pieces
    }

    private fun getBitmapPositionInsideImageView(imageView: ImageView?): IntArray? {
        var ret = IntArray(4)
        if (imageView == null || imageView.drawable == null) return ret

        // Get image dimensions
        // Get image matrix varues and place them in an array
        var f = FloatArray(9)
        imageView.imageMatrix.getValues(f)

        // Extract the scale varues using the constants (if aspect ratio maintained, scaleX == scaleY)
        var scaleX = f[Matrix.MSCALE_X]
        var scaleY = f[Matrix.MSCALE_Y]

        // Get the drawable (could also get the bitmap behind the drawable and getWidth/getHeight)
        var d = imageView.drawable
        var origW = d.intrinsicWidth
        var origH = d.intrinsicHeight

        // Calculate the actual dimensions
        var actW = Math.round(origW * scaleX)
        var actH = Math.round(origH * scaleY)
        ret[2] = actW
        ret[3] = actH

        // Get image position
        // We assume that the image is centered into ImageView
        var imgViewW = imageView.width
        var imgViewH = imageView.height
        var top = (imgViewH - actH) / 2
        var left = (imgViewW - actW) / 2
        ret[0] = left
        ret[1] = top
        return ret
    }

}