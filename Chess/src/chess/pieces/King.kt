package chess.pieces

import chess.moves.Capture
import chess.moves.Castling
import chess.moves.SimpleMove
import game.core.Move
import game.core.PieceColor
import game.core.Square
import kotlin.math.abs

/**
 * Класс представляет шахматного короля.
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
class King(square: Square, color: PieceColor) : ChessPiece(square, color) {
    override fun isCorrectMove(vararg squares: Square): Boolean {
        // Пока используем только умалчиваемую проверку
        // выполняемую в базовом классе.
        if (!super.isCorrectMove(*squares)) return false

        val target = squares[0]
        val kingV = square.v
        val kingH = square.h
        val dv = target.v - kingV
        val dh = target.h - kingH
        if (dv == 2) { // Ход вправо.
            //
            // Возможно короткая рокировка
            //
            if (outStartPosition()) return false // Ход не с начальной позиции.
            if (dh != 0) return false // Ход не по горизонтали.
            val board = square.board
            if (!board.isEmpty(kingV + 1, kingH)) return false // Между королем и ладьей фигура.
            if (!board.isEmpty(kingV + 2, kingH)) return false // Между королем и ладьей фигура.
            if (board.isEmpty(kingV + 3, kingH)) return false // Ладьи нет.
            val pieceH = board.getSquare(kingV + 3, kingH)!!.getPiece()
            return if (pieceH !is Rook) false else pieceH.color === color // На вертикали H не ладья.

            // На вертикали H ладья противника.
        }
        if (dv == -2) { // Ход влево.
            //
            // Возможно длинная рокировка
            //
            if (outStartPosition()) return false // Ход не с начальной позиции.
            if (dh != 0) return false // Ход не по горизонтали.
            val board = square.board
            if (!board.isEmpty(kingV - 1, kingH)) return false // Между королем и ладьей фигура.
            if (!board.isEmpty(kingV - 2, kingH)) return false // Между королем и ладьей фигура.
            if (!board.isEmpty(kingV - 3, kingH)) return false // Между королем и ладьей фигура.
            if (board.isEmpty(kingV - 4, kingH)) return false // Ладьи нет.
            val pieceA = board.getSquare(kingV - 4, kingH)!!.getPiece()
            return if (pieceA !is Rook) false else pieceA.color === color // На вертикали A не ладья.

            // На вертикали A ладья противника.
        }
        return square.isNear(target)
    }

    /**
     * Находится ли король в начальной позиции.
     *
     * @return начальная позиция?
     */
    private fun outStartPosition(): Boolean {
        if (square.v != 4) return true

        return when (color) {
            PieceColor.BLACK -> square.h != 0
            PieceColor.WHITE -> square.h != 7
            else -> false
        }
    }

    override fun makeMove(vararg squares: Square): Move {
        val source = squares[0]
        val target = squares[1]
        val dv = abs(target.v - source.v)
        if (dv > 1) return Castling(source, target)
        return if (!target.isEmpty) Capture(source, target) else SimpleMove(source, target)
    }

    override fun toString(): String = "K"
}