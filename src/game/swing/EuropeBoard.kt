package game.swing

import game.core.Board
import game.swing.images.GameImages
import java.awt.Color
import java.awt.Graphics

abstract class EuropeBoard(board: Board) : GameBoard(board) {
    override fun drawBack(gc: Graphics) {
        gc.drawImage(GameImages.woodLight, 0, 0, width, height, null)
    }

    override fun drawSquare(gc: Graphics, v: Int, h: Int, sw: Int, sh: Int) {
        val sx = v * sw
        val sy = h * sh

        val isWhiteSquare = (v + h) % 2 == 0
        val image = if (isWhiteSquare) GameImages.woodMedium else GameImages.woodDark
        gc.drawImage(image, sx, sy, sw, sh, null)

        gc.color = Color.BLACK
        gc.drawRect(sx, sy, sw, sh)
    }
}