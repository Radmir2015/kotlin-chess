package game.swing

import game.core.Board
import java.awt.Color
import java.awt.Graphics

/**
 * Зеленая доска c клетками в рамке.
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
abstract class GreenBoard(board: Board) : GameBoard(board) {
    override fun drawBack(g: Graphics) {
        g.color = FILL_COLOR
        g.fillRect(0, 0, width, height)
    }

    override fun drawSquare(g: Graphics, v: Int, h: Int, sw: Int, sh: Int) {
        g.color = LINE_COLOR
        g.drawRect(v * sw, h * sh, sw, sh)
    }

    companion object {
        private val LINE_COLOR = Color.black
        private val FILL_COLOR = Color.GREEN
    }
}