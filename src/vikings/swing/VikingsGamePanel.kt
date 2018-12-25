package vikings.swing

import game.core.Game
import game.core.Piece
import game.core.PieceColor
import game.core.Square
import game.core.listeners.MovePieceListener
import game.swing.BoardSizePanel
import game.swing.GamePanel
import game.swing.GreenBoard
import vikings.Vikings
import vikings.pieces.Cyning
import vikings.pieces.Viking
import vikings.swing.images.VikingImages
import java.awt.*
import javax.swing.Box

/**
 * Панель для игры в Викинги.
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
class VikingsGamePanel : GamePanel(Vikings()) {
    init {
        insertSquares(VikingsBoardPanel(game))

        val n9x9 = intArrayOf(9, 9)
        val n11x11 = intArrayOf(11, 11)
        val sizes = arrayOf(n9x9, n11x11)

        val bsp = BoardSizePanel(this, sizes)
        bsp.alignmentX = Component.CENTER_ALIGNMENT

        control.add(Box.createRigidArea(Dimension(0, 5)))
        control.add(bsp)
    }

//    override fun resizeBoard(nV: Int, nH: Int) {
//        super.resizeBoard(nV, nH)
//
//        // Новые размеры доски и расстановка фигур.
//        game.initBoard(nV, nH)
//
//        adorned.resizeBoard(nV, nH)
//    }
}

/**
 * Доска для игры
 * [Викинги (Хнефатафл, Тавлеи) ](https://ru.wikipedia.org/wiki/%D0%A5%D0%BD%D0%B5%D1%84%D0%B0%D1%82%D0%B0%D1%84%D0%BB).
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
internal class VikingsBoardPanel(game: Game) : GreenBoard(game.board) {

    init {
        listener = MovePieceListener(this)
    }

    override fun getPiece(mouseSquare: Square?, moveColor: PieceColor?): Piece {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getImage(piece: Piece): Image? {
        return getPieceImage(piece, piece.color)
    }

    override fun getPieceImage(piece: Piece, color: PieceColor): Image? {
        if (piece is Viking)
            return if (color == PieceColor.WHITE)
                VikingImages.imageVikingWhite
            else
                VikingImages.imageVikingBlack

        return if (piece is Cyning) if (color == PieceColor.WHITE)
            VikingImages.imageCyningWhite
        else
            VikingImages.imageCyningBlack else null
    }

    override fun drawSquare(g: Graphics, v: Int, h: Int, sw: Int, sh: Int) {
        super.drawSquare(g, v, h, sw, sh)

        val h1 = 0
        val v1 = 0
        val hc = board.nH / 2
        val vc = board.nV / 2
        val h2 = board.nH - 1
        val v2 = board.nV - 1

        g.color = COLOR
        drawMark(g, v1, h1, sw, sh)
        drawMark(g, v1, h2, sw, sh)
        drawMark(g, v2, h1, sw, sh)
        drawMark(g, v2, h2, sw, sh)

        drawMark(g, vc, hc, sw, sh)
    }

    private fun drawMark(g: Graphics, v: Int, h: Int, sw: Int, sh: Int) {
        g.fillRect(1 + v * sw, 1 + h * sh, sw - 1, sh - 1)
    }

    companion object {
        private val COLOR = Color(0, 255, 0, 255)
    }
}