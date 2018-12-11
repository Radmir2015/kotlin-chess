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

    public Square m_EnpassantCapturedSquare;

    public EnPassant(Square[] squares, Square enemy_square) {
        super(squares);
        setCapturedSquare(enemy_square);
        setCapturedPiece(enemy_square.getPiece());
    }

    @Override
    //Сделать ход
    public void doMove() {
        super.doMove();
    }

    @Override
    //Отменить ход
    public void undoMove() {
        super.undoMove();
    }

    @Override
    //Удалить фигуру
    public void removePiece() {
        // TODO Auto-generated method stub


    }

    @Override
    //Восстановить фигуру
    public void restorePiece() {
        // TODO Auto-generated method stub

    }
}
