package game.swing

import game.core.Board
import game.core.Piece
import java.awt.BorderLayout
import java.awt.Graphics
import java.awt.Image
import javax.swing.JPanel

/**
 *
 */
abstract class GameBoard(val board: Board) : JPanel(BorderLayout()) {
    override fun paint(gc: Graphics) {
        val sw = size.width / board.nV
        val sh = size.height / board.nH

        drawBack(gc)

        for (v in 0..board.nV - 1)
            for (h in 0..board.nH - 1)
                drawSquare(gc, v, h, sw, sh)

        for (v in 0..board.nV - 1)
            for (h in 0..board.nH - 1)
                drawPiece(gc, v, h, sw, sh)
    }

    private fun drawPiece(gc: Graphics, v: Int, h: Int, sw: Int, sh: Int) {
        val piece = board.getSquare(v, h).piece ?: return

        val sx = v * sw
        val sy = h * sh

        val image = getImage(piece)
        gc.drawImage(image, sx, sy, sw, sh, null)
    }

    protected abstract fun getImage(piece: Piece): Image?

    protected abstract fun drawBack(gc: Graphics)

    protected abstract fun drawSquare(gc: Graphics, v: Int, h: Int, sw: Int, sh: Int)
}