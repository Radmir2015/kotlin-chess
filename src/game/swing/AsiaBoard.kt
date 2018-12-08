package game.swing

import java.awt.Color
import java.awt.Graphics

/**
 *
 */
class AsiaBoard(nV: Int, nH: Int) : AbstractBoardPanel(nV, nH) {
    private val black = Color(0, 0, 0)

    override fun drawBack(gc: Graphics) {
        gc.drawImage(GameImages.woodLight,
                0, 0, width, height,
                0, 0, size.width - 1, size.height - 1,
                null)
    }

    override fun drawSquare(gc: Graphics, v: Int, h: Int, sw: Int, sh: Int) {
        val sx = v * sw
        val sy = h * sh

        val dv = sw / 2
        val dh = sh / 2
        val cx = sx + dv
        val cy = sy + dh

        gc.color = black
        if (v != 0) gc.drawLine(cx, cy, cx - dv, cy)
        if (v != nV - 1) gc.drawLine(cx, cy, cx + dv, cy)

        if (h != 0) gc.drawLine(cx, cy, cx, cy - dh)
        if (h != nH - 1) gc.drawLine(cx, cy, cx, cy + dh)
    }
}