package chess.moves;

import game.core.Square;
import game.core.moves.ICaptureMove;

/**
 * Ход европейских шахмат - взятие пешки на проходе.
 * https://ru.wikipedia.org/wiki/Взятие_на_проходе
 */
public class EnPassant extends Capture implements ICaptureMove {

    public EnPassant(Square[] squares, Square enemy_square) {
        super(squares);
        setCapturedSquare(enemy_square);
        setCapturedPiece(enemy_square.getPiece());
    }

    @Override
    public void doMove() {
        super.doMove();
    }

    @Override
    public void undoMove() {
        super.undoMove();
    }
}