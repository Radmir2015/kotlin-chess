package chess.moves;

import game.core.Piece;
import game.core.Square;
import game.core.moves.ITransferMove;

/**
 * Простой ход европейских шахмат - перемещение фигуры на пустую клетку.
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class SimpleMove implements ITransferMove {
    /**
     * Какая фигура перемещается.
     */
    final Piece piece;

    /**
     * Откуда перемещается.
     */
    final Square source;

    /**
     * Куда перемещается.
     */
    final Square target;

    public SimpleMove(Square[] squares) {
        source = squares[0];
        target = squares[1];

        piece = source.getPiece();
    }

    @Override
    public Square getTarget() {
        return target;
    }

    @Override
    public Square getSource() {
        return source;
    }

    @Override
    public void doMove() {
        piece.moveTo(target);
    }

    @Override
    public void undoMove() {
        piece.moveTo(source);
    }

    @Override
    public String toString() {
        return "" + piece + source + "-" + target;
    }

    @Override
    public Piece getPiece() {
        return piece;
    }
}
