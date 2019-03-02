package go.moves;

import game.core.GameOver;
import game.core.Piece;
import game.core.Square;
import game.core.moves.IPutMove;

/**
 * Ход без захвата фигуры противника
 * для <a href="https://ru.wikipedia.org/wiki/%D0%93%D0%BE">Го</a>.
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class SimpleMove extends GoMove implements IPutMove {
    /**
     * Куда поставят фигуру..
     */
    private Square target;


    /**
     * Простой ход без взятия фигур противника.
     *
     * @param piece   - какая фигура ставится.
     * @param squares - клетки.
     *                Клетка squares[0] - куда ставится фигура.
     */
    public SimpleMove(Piece piece, Square... squares) {
        target = squares[0];
        this.piece = piece;
        piece.square = target;
    }

    @Override
    public void doMove() throws GameOver {
        target.setPiece(piece);

        checkGameEnd(piece);
    }

    @Override
    public void undoMove() {
        if (piece.square == null)
            System.out.println();
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
}
