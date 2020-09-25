package chess.pieces

import game.core.Piece
import game.core.PieceColor
import game.core.Square

/**
 * Базовый класс для всех шахматных фигур.
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
abstract class ChessPiece(square: Square, color: PieceColor) : Piece(square, color) {
    override fun isCorrectMove(vararg squares: Square): Boolean {
        // Если идем на клетку, занятую фигурой
        // того же цвета, то ход не корректен.
        val target = squares[0]
        val piece = target.getPiece() ?: return true

        return color !== piece.color
    }
}