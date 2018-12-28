package chinachess.pieces;

import chinachess.moves.Capture;
import chinachess.moves.SimpleMove;
import game.core.Move;
import game.core.PieceColor;
import game.core.Square;

/**
 * Ладья (колесница) в игре <a href="https://ru.wikipedia.org/wiki/%D0%A1%D1%8F%D0%BD%D1%86%D0%B8">
 * Китайские шахматы</a>
 *
 * @author <a href="mailto:y.o.dmitriv@gmail.com">Dmitriv Y.</a>
 */
public class Rook extends ChinaChessPiece {
    public Rook(Square square, PieceColor color) {
        super(square, color);
    }

    @Override
    public boolean isCorrectMove(Square... squares) {
        // Пока используем только умалчиваемую проверку
        // выполняемую в базовом классе.
        if (!super.isCorrectMove(squares))
            return false;

        Square rook = square;
        Square target = squares[0];

        // По пустым вертикалям и горизонталям ходит как ладья.
        if (rook.isEmptyHorizontal(target))
            return true;

        return rook.isEmptyVertical(target);
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
        return "R";
    }
}
