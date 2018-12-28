package chinachess.pieces;

import chinachess.moves.Capture;
import chinachess.moves.SimpleMove;
import game.core.Move;
import game.core.PieceColor;
import game.core.Square;

/**
 * Пешка в игре <a href="https://ru.wikipedia.org/wiki/%D0%A1%D1%8F%D0%BD%D1%86%D0%B8">
 * Китайские шахматы</a>
 *
 * @author <a href="mailto:y.o.dmitriv@gmail.com">Dmitriv Y.</a>
 */
public class Pawn extends ChinaChessPiece {
    public Pawn(Square square, PieceColor color) {
        super(square, color);
    }

    @Override
    public boolean isCorrectMove(Square... squares) {
        // Пока используем только умалчиваемую проверку
        // выполняемую в базовом классе.
        if (!super.isCorrectMove(squares))
            return false;

        Square source = square;
        Square target = squares[0];

        boolean isBlack = getColor() == PieceColor.BLACK;
        int dv = Math.abs(target.v - source.v);
        int dh = isBlack
                ? target.h - source.h  // Черная фигура идет вниз.
                : source.h - target.h; // Белая фигура идет вверх.

        // Пешка назад не ходит.
        if (dh < 0)
            return false;

        if ((dh == 1) && (dv == 0))
            return true; // Пешка пошла на один ход вперед.

        // Если пешка стоит на вражской территории,
        // то может пойти еще влево и вправо.
        if (isEnemyPart(getColor(), square))
            return (dh == 0) && (dv == 1);

        return false;
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
        return "P";
    }
}
