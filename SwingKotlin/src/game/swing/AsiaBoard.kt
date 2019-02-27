package game.swing

import game.core.Board
import game.swing.images.GameImages
import java.awt.Color
import java.awt.Graphics

/**
 *
 */
abstract class AsiaBoard(board: Board) : GameBoard(board) {
    override fun drawBack(g: Graphics) {
        g.drawImage(GameImages.woodLight, 0, 0, width, height, null)
    }

    override fun drawSquare(g: Graphics, v: Int, h: Int, sw: Int, sh: Int) {
        val sx = v * sw
        val sy = h * sh

        val dv = sw / 2
        val dh = sh / 2
        val cx = sx + dv
        val cy = sy + dh

        g.color = Color.BLACK
        if (v != 0) g.drawLine(cx, cy, cx - dv, cy)
        if (v != board.nV - 1) g.drawLine(cx, cy, cx + dv, cy)

        if (h != 0) g.drawLine(cx, cy, cx, cy - dh)
        if (h != board.nH - 1) g.drawLine(cx, cy, cx, cy + dh)
    }
}