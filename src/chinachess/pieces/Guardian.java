package chinachess.pieces;

import chinachess.moves.Capture;
import chinachess.moves.SimpleMove;
import game.core.Move;
import game.core.PieceColor;
import game.core.Square;

/**
 * Телохранитель (советник) в игре <a href="https://ru.wikipedia.org/wiki/%D0%A1%D1%8F%D0%BD%D1%86%D0%B8">
 * Китайские шахматы</a>
 *
 * @author <a href="mailto:y.o.dmitriv@gmail.com">Dmitriv Y.</a>
 */
public class Guardian extends ChinaChessPiece {

    public Guardian(Square square, PieceColor color) {
        super(square, color);
    }

    @Override
    public boolean isCorrectMove(Square... squares) {
        // Пока используем только умалчиваемую проверку
        // выполняемую в базовом классе.
        if (!super.isCorrectMove(squares))
            return false;

        Square target = squares[0];

        // Ходы вне крепости для короля запрещены.
        if (!inCastle(getColor(), target))
            return false;

        int dv = Math.abs(target.v - square.v);
        int dh = Math.abs(target.h - square.h);

        // Допустимы только ходы на одну клетку
        // по диагонали.
        return (dh == 1) && (dv == 1);
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
        return "A";
    }
}
