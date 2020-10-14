package renju.moves

import game.core.Dirs
import game.core.Piece
import game.core.Square
import game.core.moves.IPutMove

/**
 */
class RenjuMove(piece: Piece, vararg squares: Square) : IPutMove {
    /**
     * Куда поставят фигуру..
     */
    override val target: Square = squares[0]
    override val piece: Piece = piece

    override fun doMove() {
        target.setPiece(piece)
        checkGameEnd(piece)
    }

    private fun checkGameEnd(piece: Piece) {
        // TODO Проверить не стоят ли пять фигур в ряд.
        for (dirs in Dirs.PAIRS) {
            val count = 0

            // Пара направлений.
            for (dir in dirs) {
            }
        }
    }

    override fun undoMove() {
        piece.remove()
    }

    override fun toString(): String = "$target"

    /**
     */
    init {
        piece.square = target
    }
}