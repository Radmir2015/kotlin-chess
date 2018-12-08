package game.swing

import java.awt.Color
import java.awt.Graphics

class EuropeBoard(nV: Int, nH: Int) : AbstractBoardPanel(nV, nH) {
    private val GREEN = Color(0, 192, 0)
    private val WHITE = Color(255, 255, 255)
    private val BLACK = Color(0, 0, 0)


    override fun drawBack(gc: Graphics) {
//        val url = EuropeBoard::class.java!!.getResource("images/wood_light.png)
        gc.drawImage(GameImages.woodMedium,
                0, 0, width, height,
                0, 0, size.width - 1, size.height - 1,
                null)
    }

    override fun drawSquare(gc: Graphics, v: Int, h: Int, sw: Int, sh: Int) {
        val sx = v * sw
        val sy = h * sh

        val isWhiteSquare = (v + h) % 2 == 0
        val image = if (isWhiteSquare) GameImages.woodMedium else GameImages.woodDark
        gc.drawImage(image, sx, sy, sw, sh, null)
    }
}