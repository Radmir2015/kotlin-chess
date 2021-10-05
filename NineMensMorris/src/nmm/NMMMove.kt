package nmm

import game.core.Piece
import game.core.Square
import game.core.moves.IPutMove

/**
 */
class NMMMove(piece: Piece, vararg squares: Square) : IPutMove {
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
        // TODO Проверить есть ли свободное место.
    }

    override fun undoMove() {
        piece.remove()
    }

    override fun toString() = "$target"

    /**
     */
    init {
        piece.square = target
    }
}