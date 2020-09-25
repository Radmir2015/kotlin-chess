package chess.pieces

import chess.moves.Capture
import chess.moves.SimpleMove
import game.core.Move
import game.core.PieceColor
import game.core.Square

/**
 * Класс представляет шахматного слона.
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
class Bishop(square: Square, color: PieceColor) : ChessPiece(square, color) {
    override fun isCorrectMove(vararg squares: Square): Boolean {
        // Пока используем только умалчиваемую проверку
        // выполняемую в базовом классе.
        if (!super.isCorrectMove(*squares)) return false

        val target = squares[0]
        return square.isEmptyDiagonal(target)
    }

    override fun makeMove(vararg squares: Square): Move {
        val target = squares[1]
        return if (!target.isEmpty) Capture(square, target) else SimpleMove(square, target)
    }

    override fun toString(): String = "B"
}