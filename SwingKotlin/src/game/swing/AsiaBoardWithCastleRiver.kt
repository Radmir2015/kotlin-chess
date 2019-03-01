package game.swing

import game.core.Game
import java.awt.Graphics

abstract class AsiaBoardWithCastleRiver(game: Game) : AsiaBoardWithCastle(game) {

    override fun drawSquare(g: Graphics, v: Int, h: Int, sw: Int, sh: Int) {
        val dv = sw / 2
        val dh = sh / 2

        val x = v * sw + dv
        val y = h * sh + dh

        val isBlackMargin = h == 4
        val isWhiteMargin = h == board.nH - 5

        if (isBlackMargin || isWhiteMargin) {
            // Рисуем "реку" между территориями противников.
            if (v != 0) g.drawLine(x, y, x - dv, y)
            if (v != board.nV - 1) g.drawLine(x, y, x + dv, y)

            if (!isWhiteMargin) g.drawLine(x, y, x, y - dh)
            if (!isBlackMargin) g.drawLine(x, y, x, y + dh)
        } else
        // Обычные клетки азиатской доски с двумя крепостями.
            super.drawSquare(g, v, h, sw, sh)
    }

}