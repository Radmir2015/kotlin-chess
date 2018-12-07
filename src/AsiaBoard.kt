package games.ui

import java.awt.Color
import java.awt.Graphics

class AsiaBoard(nV: Int, nH: Int) : AbstractBoardPanel(nV, nH) {
    private val yellow = Color(255, 255, 0)
    private val BLACK = Color(0, 0, 0)

    override fun drawBack(gc: Graphics) {
        gc.color = yellow
        gc.fillRect(0, 0, size.width - 1, size.height - 1)
    }

    override fun drawSquare(gc: Graphics, v: Int, h: Int) {
        val squareWidth = size.width / nV
        val squareHeight = size.height / nH
        val squareX = v * squareWidth
        val squareY = h * squareHeight

        val dv = squareWidth / 2
        val dh = squareHeight / 2
        val x = squareX + dv
        val y = squareY + dh

        gc.color = BLACK
        if (v != 0) gc.drawLine(x, y, x - dv, y)
        if (v != nV - 1) gc.drawLine(x, y, x + dv, y)

        if (h != 0) gc.drawLine(x, y, x, y - dh)
        if (h != nH - 1) gc.drawLine(x, y, x, y + dh)
    }
}