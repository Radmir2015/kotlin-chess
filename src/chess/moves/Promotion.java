package chess.moves;

import chess.pieces.Queen;
import game.core.Piece;
import game.core.Square;
import game.core.moves.ICaptureMove;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Ход европейских шахмат - преврашение пешки на последней горизонтали
 * в новую фигуру с возможным взятием фигуры противника.
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Promotion extends SimpleMove
        implements ICaptureMove {
    private Piece pawn;
    private Piece capturedPiece;
    private Queen promotedPiece;

    public Promotion(Square[] squares) {
        super(squares);

        pawn = source.getPiece();

        if (source.v != target.v)
            // Ход по диагонали со взятием фигуры.
            capturedPiece = target.getPiece();
    }

    @Override
    public List<Square> getCaptured() {
        return capturedPiece == null
                ? Collections.emptyList() : Arrays.asList(target);
    }

    /*
     * Удалить пешку, поставить фигуру.
     */
    @Override
    public void doMove() {
        if (capturedPiece != null)
            target.removePiece();

        source.removePiece();
        promotedPiece = new Queen(target, pawn.getColor());

        target.setPiece(promotedPiece);
    }

    /*
     * Удалить фигуру, поставить пешку.
     */
    @Override
    public void undoMove() {
        target.removePiece();
        source.setPiece(piece);

        if (capturedPiece != null)
            target.setPiece(capturedPiece);
    }

    @Override
    public String toString() {
        String moveKind = (capturedPiece == null) ? "-" : "x";
        String pieceKind = promotedPiece.toString();

        return "" + piece + source + moveKind + target + pieceKind;
    }
}
