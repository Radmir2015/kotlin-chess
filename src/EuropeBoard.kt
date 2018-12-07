package games.ui

import java.awt.Color
import java.awt.Graphics

class EuropeBoard(nV: Int, nH: Int) : AbstractBoardPanel(nV, nH) {
    private val WHITE = Color(255, 255, 255)
    private val GREEN = Color(0, 192, 0)
    private val BLACK = Color(0, 0, 0)

    override fun drawBack(gc: Graphics) {
        gc.drawRect(0, 0, size.width - 1, size.height - 1)
    }

    override fun drawSquare(gc: Graphics, v: Int, h: Int) {
        val squareWidth = size.width / nV
        val squareHeight = size.height / nH
        val squareX = v * squareWidth
        val squareY = h * squareHeight

        val isWhiteSquare = (v + h) % 2 == 0
        val squareColor = if (isWhiteSquare) WHITE else GREEN

        gc.color = squareColor
        gc.fillRect(squareX, squareY, squareWidth, squareHeight)

        gc.color = BLACK
        gc.drawRect(squareX, squareY, squareWidth, squareHeight)
    }
}