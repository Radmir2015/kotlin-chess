package game.swing

import game.core.Board
import game.swing.images.GameImages
import java.awt.Color
import java.awt.Graphics

/**
 *
 */
abstract class AsiaBoard(board: Board) : GameBoard(board) {
    init {
        board.reset(10, 10)
    }

    override fun drawBack(gc: Graphics) {
        gc.drawImage(GameImages.woodLight, 0, 0, width, height, null)
    }

    override fun drawSquare(gc: Graphics, v: Int, h: Int, sw: Int, sh: Int) {
        val sx = v * sw
        val sy = h * sh

        val dv = sw / 2
        val dh = sh / 2
        val cx = sx + dv
        val cy = sy + dh

        gc.color = Color.BLACK
        if (v != 0) gc.drawLine(cx, cy, cx - dv, cy)
        if (v != board.nV - 1) gc.drawLine(cx, cy, cx + dv, cy)

        if (h != 0) gc.drawLine(cx, cy, cx, cy - dh)
        if (h != board.nH - 1) gc.drawLine(cx, cy, cx, cy + dh)
    }
}