package chess.moves

import game.core.Piece
import game.core.Square
import game.core.moves.ICaptureMove

/**
 * Ход европейских шахмат - перемещение фигуры на клетку
 * с захватом фигуры противника.
 *
 * @author [Romanov V.Y.](mailto:vla	dimir.romanov@gmail.com)
 */
open class Capture(source: Square, target: Square) : SimpleMove(source, target), ICaptureMove {
    var capturedSquare: Square = target
    var capturedPiece: Piece = target.piece!!

    override val captured: List<Square>
        get() = listOf(capturedSquare)

    override fun doMove() {
        capturedPiece.remove()
        super.doMove()
    }

    override fun undoMove() {
        super.undoMove()
        capturedSquare.setPiece(capturedPiece)
    }

    override fun toString(): String = "$piece${source}x$target"
}