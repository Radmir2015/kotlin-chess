package renju.pieces

import game.core.Move
import game.core.Piece
import game.core.PieceColor
import game.core.Square
import renju.moves.RenjuMove

/**
 */
class RenjuPiece(square: Square, color: PieceColor) : Piece(square, color) {
    override fun isCorrectMove(vararg squares: Square): Boolean {
        val target = squares[0]
        return target.isEmpty
    }

    override fun makeMove(vararg squares: Square): Move = RenjuMove(this, squares[0])

    override fun toString(): String = "$square"
}