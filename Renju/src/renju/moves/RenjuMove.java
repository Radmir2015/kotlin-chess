package renju.moves;

import game.core.Dirs;
import game.core.Piece;
import game.core.Square;
import game.core.moves.IPutMove;

/**
 */
public class RenjuMove implements IPutMove {
    /**
     * Куда поставят фигуру..
     */
    private final Square target;

    private final Piece piece;

    /**
     */
    public RenjuMove(Piece piece, Square... squares) {
        target = squares[0];
        this.piece = piece;
        piece.square = target;
    }

    @Override
    public void doMove() {
        target.setPiece(piece);

        checkGameEnd(piece);
    }

    private void checkGameEnd(Piece piece) {
        // TODO Проверить не стоят ли пять фигур в ряд.
        for (Dirs[] dirs : Dirs.PAIRS) {
            int count = 0;

            // Пара направлений.
            for (Dirs dir : dirs) {

            }
        }
    }

    @Override
    public void undoMove() {
        piece.remove();
    }

    @Override
    public String toString() {
        return "" + target;
    }

    @Override
    public Square getTarget() {
        return target;
    }

    @Override
    public Piece getPiece() {
        return piece;
    }
}
