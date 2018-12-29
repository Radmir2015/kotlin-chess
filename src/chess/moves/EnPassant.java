package chess.moves;

import game.core.Square;

/**
 * Ход европейских шахмат - взятие пешки на проходе.
 * TODO Zhdanov
 * https://ru.wikipedia.org/wiki/Взятие_на_проходе
 *
 * @author <a href="mailto:ramzes.zhdanov@mail.ru">Zhdanov R.A.</a>
 */
public class EnPassant extends Capture implements ICapture {

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

    @Override
    public void removePiece() {
        // TODO Auto-generated method stub
    }

    @Override
    public void restorePiece() {
        // TODO Auto-generated method stub
    }
}