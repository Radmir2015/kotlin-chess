/**
 *
 */
package halma.swing

import game.core.Game
import game.core.Piece
import game.core.PieceColor
import game.core.Square
import game.core.listeners.MovePieceListener
import game.swing.AsiaBoard
import game.swing.GamePanel
import game.swing.images.GameImages
import halma.Halma
import java.awt.Image

/**
 * Панель для игры в уголки.
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
class HalmaGamePanel(boardSize: Int) : GamePanel(Halma(boardSize)) {
    constructor() : this(10)

    init {
//        insertSquares(HalmaBoardPanel(game))

//        val n8x8 = intArrayOf(8, 8)
//        val n10x10 = intArrayOf(10, 10)
//        val n16x16 = intArrayOf(16, 16)
//        val sizes: Array<IntArray> = arrayOf(n8x8, n10x10, n16x16)
//
//        val bsp = BoardSizePanel(this, sizes)
//        bsp.alignmentX = Component.CENTER_ALIGNMENT
//
//        control.add(Box.createRigidArea(Dimension(0, 5)))
//        control.add(bsp)

//
//        val scorePanel = ScorePanel(game)
//        scorePanel.alignmentX = Component.CENTER_ALIGNMENT
//
//        control.add(Box.createRigidArea(Dimension(0, 5)))
//        control.add(scorePanel)
    }
}

/**
 * Доска для игры [
 * Халма](https://ru.wikipedia.org/wiki/https://ru.wikipedia.org/wiki/%D0%A5%D0%B0%D0%BB%D0%BC%D0%B0)
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
internal class HalmaBoardPanel(game: Game) : AsiaBoard(game) {
    init {
        listener = MovePieceListener(this)
    }

    override fun getPiece(mouseSquare: Square?, moveColor: PieceColor?): Piece {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getImage(piece: Piece): Image? {
        return getPieceImage(piece, piece.color)
    }

    override fun getPieceImage(piece: Piece, color: PieceColor): Image {
        return if (color == PieceColor.WHITE)
            GameImages.stoneWhite
        else
            GameImages.stoneBlack
    }
}
