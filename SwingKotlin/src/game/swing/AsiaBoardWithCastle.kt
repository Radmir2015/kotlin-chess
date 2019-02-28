package game.swing

import game.core.Game
import java.awt.Graphics

/**
 * Азиатская доска в крепостью у каждого из противников.
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
abstract class AsiaBoardWithCastle(game: Game) : AsiaBoard(game) {

    override fun drawSquare(g: Graphics, v: Int, h: Int, sw: Int, sh: Int) {
        super.drawSquare(g, v, h, sw, sh)

        val vCastle = 4
        val hCastleBlack = 1
        val hCastleWhite = board.nH - 2

        val isCastleV = v == vCastle
        val isCastleH = h == hCastleBlack || h == hCastleWhite

        if (isCastleV && isCastleH) {
            val dv = sw / 2
            val dh = sh / 2

            val x = v * sw + dv
            val y = h * sh + dh

            g.drawLine(x, y, x - sw, y - sh)
            g.drawLine(x, y, x + sw, y + sh)
            g.drawLine(x, y, x - sw, y + sh)
            g.drawLine(x, y, x + sw, y - sh)
        }
    }
}