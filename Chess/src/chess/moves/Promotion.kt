package chess.moves

import chess.pieces.Queen
import game.core.Piece
import game.core.Square
import game.core.moves.ICaptureMove

/**
 * Ход европейских шахмат - преврашение пешки на последней горизонтали
 * в новую фигуру с возможным взятием фигуры противника.
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
class Promotion(source: Square, target: Square) : SimpleMove(source, target), ICaptureMove {
    private val pawn: Piece = source.piece!!
    private var capturedPiece: Piece? = null
    private var promotedPiece: Piece? = null

    override val captured: List<Square>
        get() = if (capturedPiece == null) emptyList() else listOf(target)

    /*
     * Удалить пешку, поставить фигуру.
     */
    override fun doMove() {
        source.removePiece()
        if (capturedPiece != null) target.removePiece()

        promotedPiece = Queen(target, pawn.color)
        target.setPiece(promotedPiece!!)
    }

    /*
     * Удалить фигуру, поставить пешку.
     */
    override fun undoMove() {
        target.removePiece()
        piece?.let { source.setPiece(it) }
        if (capturedPiece != null) target.setPiece(capturedPiece!!)
    }

    override fun toString(): String {
        val moveKind = if (capturedPiece == null) "-" else "x"
        val pieceKind = promotedPiece.toString()
        return "$piece$source$moveKind$target$pieceKind"
    }

    init {
        if (source.v != target.v) // Ход по диагонали со взятием фигуры.
            capturedPiece = target.getPiece()
    }
}