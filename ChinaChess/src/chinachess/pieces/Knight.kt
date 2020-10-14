package chinachess.pieces;

import chinachess.moves.Capture;
import chinachess.moves.SimpleMove;
import game.core.Board;
import game.core.Move;
import game.core.PieceColor;
import game.core.Square;

/**
 * Конь в игре <a href="https://ru.wikipedia.org/wiki/%D0%A1%D1%8F%D0%BD%D1%86%D0%B8">
 * Китайские шахматы</a>
 *
 * @author <a href="mailto:y.o.dmitriv@gmail.com">Dmitriv Y.</a>
 */
public class Knight extends ChinaChessPiece {

    public Knight(Square square, PieceColor color) {
        super(square, color);
    }

    @Override
    public boolean isCorrectMove(Square... squares) {
        // Пока используем только умалчиваемую проверку
        // выполняемую в базовом классе.
        if (!super.isCorrectMove(squares))
            return false;

        Square knight = square;
        Square target = squares[0];

        int dh = Math.abs(target.h - square.h);
        int dv = Math.abs(target.v - square.v);

        boolean isKnightMove = (dh == 1 && dv == 2) ||
                (dh == 2 && dv == 1);
        if (!isKnightMove)
            return false;

        // Китайские лошади не могут перепрыгнуть через фигуру.
        // Они маленькие, наверное их плохо кормят.
        // Посмотрим есть ли у лошади на пути фигура-барьер.
        int hBarier = 0;
        int vBarier = 0;

        if (dh > dv) {
            // Прыжок по горизонтали и ход в вертикали.
            hBarier = (knight.h + target.h) / 2;
            vBarier = knight.v;
        } else if (dh < dv) {
            // Прыжок по вертикали и ход в горизонтали.
            vBarier = (knight.v + target.v) / 2;
            hBarier = knight.h;
        }

        // Клетка на пути не пустая?
        Board board = target.getBoard();
        return board.isEmpty(vBarier, hBarier);
    }

    @Override
    public Move makeMove(Square... squares) {
        Square target = squares[1];

        if (!target.isEmpty())
            return new Capture(squares);

        return new SimpleMove(squares);
    }

    @Override
    public String toString() {
        return "H";
    }
}
