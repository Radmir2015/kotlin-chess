package chinachess.pieces;

import chinachess.moves.Capture;
import chinachess.moves.SimpleMove;
import game.core.Move;
import game.core.PieceColor;
import game.core.Square;

/**
 * Слон в игре <a href="https://ru.wikipedia.org/wiki/%D0%A1%D1%8F%D0%BD%D1%86%D0%B8">
 * Китайские шахматы</a>
 *
 * @author <a href="mailto:y.o.dmitriv@gmail.com">Dmitriv Y.</a>
 */
public class Bishop extends ChinaChessPiece {

    public Bishop(Square square, PieceColor color) {
        super(square, color);
    }

    @Override
    public boolean isCorrectMove(Square... squares) {
        // Пока используем только умалчиваемую проверку
        // выполняемую в базовом классе.
        if (!super.isCorrectMove(squares))
            return false;

        Square target = squares[0];

        int dh = Math.abs(target.h - square.h);
        int dv = Math.abs(target.v - square.v);

        if (dh != dv)
            return false; // Это не диагональ.

        // Слон не может пойти на вражескую территорию.
        if (isEnemyPart(getColor(), target))
            return false;

        //Если dv должно быть равно dh,
        //то справедливо что любое из них не должно быть больше 2
        return dh <= 2;
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
        return "E";
    }
}
