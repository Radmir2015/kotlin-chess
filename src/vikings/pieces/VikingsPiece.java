package vikings.pieces;

import game.core.Board;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;

import java.util.Arrays;
import java.util.List;

/**
 * Базовый класс для фигур игры Викинги.
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
abstract
public class VikingsPiece extends Piece {
    VikingsPiece(Square square, PieceColor color) {
        super(square, color);
    }

    /**
     * Трон - клетка в центре доски. На троне в начальнй позиции расположен
     * король белых. Проверить это клетка трон или нет?
     *
     * @param square - проверяемая клетка.
     * @return трон или нет?
     */
    static
    public boolean isTron(Square square) {
        return (square.h == square.getBoard().nH / 2) &&
                (square.v == square.getBoard().nV / 2);
    }

    /**
     * Выход - клетка в углу доски. Король белых должен прорваться к выходу.
     * Проверить это клетка выход для короля или нет?
     *
     * @param square - проверяемая клетка.
     * @return выход или нет?
     */
    static
    public boolean isExit(Square square) {
        int nv = square.getBoard().nV - 1;
        int nh = square.getBoard().nH - 1;

        if ((square.v == 0) && (square.h == 0))
            return true;

        if ((square.v == 0) && (square.h == nh))
            return true;

        if ((square.v == nv) && (square.h == 0))
            return true;

        return (square.v == nv) && (square.h == nh);
    }

    public static List<Square> getExits(Board board) {
        int nv = board.nV - 1;
        int nh = board.nH - 1;

        return Arrays.asList(
                board.getSquare(0, 0),
                board.getSquare(nv, 0),
                board.getSquare(0, nv),
                board.getSquare(nv, nh)
        );
    }

    @Override
    public boolean isCorrectMove(Square... squares) {
        Square target = squares[0];

        // Допустим ход только на пустую клетку.
        return target.isEmpty();
    }
}
