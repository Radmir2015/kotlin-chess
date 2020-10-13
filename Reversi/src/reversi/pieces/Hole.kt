package reversi.pieces

import game.core.Move
import game.core.Piece
import game.core.PieceColor
import game.core.Square
import game.core.moves.PassMove

/**
 * Вспомогательная фигура представляющая отверстие на доске
 * для [Реверси](https://ru.wikipedia.org/wiki/%D0%A0%D0%B5%D0%B2%D0%B5%D1%80%D1%81%D0%B8)
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
class Hole(square: Square, color: PieceColor) : Piece(square, color) {
    override fun isCorrectMove(vararg squares: Square): Boolean = false

    override fun makeMove(vararg squares: Square): Move = PassMove()
}