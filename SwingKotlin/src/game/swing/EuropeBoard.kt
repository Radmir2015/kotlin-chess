package game.swing

import game.core.Game
import game.swing.images.GameImages
import java.awt.Color
import java.awt.Graphics

abstract class EuropeBoard(game: Game) : GameBoard(game) {
    override fun drawBack(g: Graphics) {
        g.drawImage(GameImages.woodLight, 0, 0, width, height, null)
    }

    override fun drawSquare(g: Graphics, v: Int, h: Int, sw: Int, sh: Int) {
        val sx = v * sw
        val sy = h * sh

        val isWhiteSquare = (v + h) % 2 == 0
        val image = if (isWhiteSquare) GameImages.woodMedium else GameImages.woodDark
        g.drawImage(image, sx, sy, sw, sh, null)

        g.color = Color.BLACK
        g.drawRect(sx, sy, sw, sh)
    }
}