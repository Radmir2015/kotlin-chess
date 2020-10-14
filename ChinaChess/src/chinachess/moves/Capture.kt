package chinachess.moves

import chinachess.pieces.King
import game.core.GameOver
import game.core.GameResult.Companion.lost
import game.core.Piece
import game.core.Square
import game.core.moves.ICaptureMove

/**
 * Ход китайских шахматах - перемещение фигуры на клетку
 * с захватом фигуры противника.
 * Игра [
 * Китайские шахматы](https://ru.wikipedia.org/wiki/%D0%A1%D1%8F%D0%BD%D1%86%D0%B8)
 *
 * @author [Dmitriv Y.](mailto:y.o.dmitriv@gmail.com)
 */
class Capture(vararg squares: Square) : SimpleMove(*squares), ICaptureMove {
    private val capturedSquare: Square = squares[1]
    private val capturedPiece: Piece?

    @Throws(GameOver::class)
    override fun doMove() {
        capturedPiece!!.remove()
        super.doMove()
        if (capturedPiece is King) throw GameOver(lost(capturedPiece))
    }

    override fun undoMove() {
        super.undoMove()
        capturedSquare.setPiece(capturedPiece!!)
    }

    override fun toString() = "$piece${source}x$target"

    override val captured: List<Square> = listOf(capturedSquare)

    init {
        capturedPiece = capturedSquare.getPiece()
    }
}