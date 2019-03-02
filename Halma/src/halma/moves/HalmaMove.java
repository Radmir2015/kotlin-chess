package halma.moves;

import game.core.Piece;
import game.core.Square;
import game.core.moves.ITransferMove;

/**
 * Ход для игры <a href=
 * "https://ru.wikipedia.org/wiki/https://ru.wikipedia.org/wiki/%D0%A5%D0%B0%D0%BB%D0%BC%D0%B0">
 * Халма</a>
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
abstract
public class HalmaMove implements ITransferMove {
    /**
     * Откуда перемещается.
     */
    Square source;
    /**
     * Куда перемещается.
     */
    Square target;
    /**
     * Какая фигура перемещается.
     */
    private Piece piece;

    /**
     * Перемешение фигуры через заданные клетки.
     *
     * @param squares Последовательность клеток
     */
    HalmaMove(Square... squares) {
        source = squares[0];
        target = squares[1];

        piece = source.getPiece();
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
    public Square getTarget() {
        return target;
    }

    @Override
    public Square getSource() {
        return source;
    }

    @Override
    public Piece getPiece() {
        return piece;
    }
}
