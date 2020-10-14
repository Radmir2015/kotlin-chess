package chinachess.pieces

import chinachess.moves.Capture
import chinachess.moves.SimpleMove
import game.core.Board.Companion.getOpponentColor
import game.core.Move
import game.core.Piece
import game.core.PieceColor
import game.core.Square
import java.util.*
import kotlin.math.abs

/**
 * Император в игре [
 * Китайские шахматы](https://ru.wikipedia.org/wiki/%D0%A1%D1%8F%D0%BD%D1%86%D0%B8)
 *
 * @author [Dmitriv Y.](mailto:y.o.dmitriv@gmail.com)
 */
class King(square: Square, color: PieceColor) : ChinaChessPiece(square, color) {
    override fun isCorrectMove(vararg squares: Square): Boolean {
        // Пока используем только умалчиваемую проверку
        // выполняемую в базовом классе.
        if (!super.isCorrectMove(*squares)) return false
        val source = square
        val target = squares[0]

        // Особый случай - ход вне крепости.
        // Король может захватить вражеского короля,
        // если между ними пустая вертикаль.
        val board = target.board
        val color = color
        val opponentColor = getOpponentColor(color)
        val opponentKingOpt: Optional<King?> = board.getPieces(opponentColor)
                .stream()
                .filter { piece: Piece -> piece.javaClass == King::class.java }
                .map { piece: Piece? -> piece as King? }
                .findAny()
        if (opponentKingOpt.isPresent) {
            val opponentKing = opponentKingOpt.get()
            val opponentSquare = opponentKing.square

            // Пытаемся ли захватить?
            val isAttempt = target == opponentSquare

            // Возможно ли захватить?
            val isPossible = source.isEmptyVertical(opponentSquare)
            if (isAttempt && isPossible) return true // Это захват короля противника.
        }

        // Другие ходы вне крепости для короля запрещены.
        if (outCastle(color, target)) return false
        val dv = abs(target.v - source.v)
        val dh = abs(target.h - source.h)

        // Допустимы только ходы на одну клетку
        // по вертикали и горизонтали.
        return (dh == 1 && dv == 0) || (dh == 0 && dv == 1)
    }

    override fun makeMove(vararg squares: Square): Move {
        val target = squares[1]
        return if (target.isEmpty) SimpleMove(*squares) else Capture(*squares)
    }

    override fun toString() = "K"
}