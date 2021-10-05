package game.swing

import game.core.Game
import game.swing.images.GameImages
import java.awt.Graphics

abstract class NineMansBoard(game: Game) : GameBoard(game) {
    override fun drawBack(g: Graphics) {
        g.drawImage(GameImages.woodLight, 0, 0, width, height, null)
    }

    override fun drawSquare(g: Graphics, v: Int, h: Int, sw: Int, sh: Int) {
//        TODO("Not yet implemented")


    }
}
