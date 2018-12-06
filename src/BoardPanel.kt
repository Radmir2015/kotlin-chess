package games.ui

import java.awt.BorderLayout
import java.awt.Color
import java.awt.Graphics
import javax.swing.JPanel

class BoardPanel(private var nV: Int, var nH: Int) : JPanel(BorderLayout()) {
    override fun paint(gc: Graphics) {
        for (v in 0 until nV)
            for (h in 0 until nH) {
                val squareWidth = size.width / nV
                val squareHeight = size.height / nH

                val isWhiteSquare = (v + h) % 2 == 0
                val squareColor = if (isWhiteSquare) Color(255, 255, 255) else Color(0, 192, 0)

                gc.color = squareColor
                gc.fillRect(v * squareWidth, h * squareHeight, squareWidth, squareHeight)

                gc.color = Color(0, 0, 0)
                gc.drawRect(v * squareWidth, h * squareHeight, squareWidth, squareHeight)
            }
        gc.drawRect(0, 0, size.width - 1, size.height - 1)
    }
}