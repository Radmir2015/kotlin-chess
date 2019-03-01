package tamerlan.pieces;

import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;

/**
 * Базовый класс для всех фигур
 * <a href="https://ru.wikipedia.org/wiki/%D0%92%D0%B5%D0%BB%D0%B8%D0%BA%D0%B8%D0%B5_%D1%88%D0%B0%D1%85%D0%BC%D0%B0%D1%82%D1%8B">Шахмат Тамерлана</a>
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
abstract class TamerlanPiece extends Piece {
    TamerlanPiece(Square square, PieceColor color) {
        super(square, color);
    }

    @Override
    public boolean isCorrectMove(Square... squares) {
        Square target = squares[0];

        if (target.isEmpty())
            return true;

        // Если идем на клетку, занятую фигурой
        // того же цвета, то ход не корректен.
        return getColor() != target.getPiece().getColor();
    }
}
