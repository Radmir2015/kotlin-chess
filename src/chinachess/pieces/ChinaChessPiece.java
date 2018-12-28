package chinachess.pieces;

import game.core.Board;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;

/**
 * Фигура в игре <a href="https://ru.wikipedia.org/wiki/%D0%A1%D1%8F%D0%BD%D1%86%D0%B8">
 * Китайские шахматы</a>
 *
 * @author <a href="mailto:y.o.dmitriv@gmail.com">Dmitriv Y.</a>
 */
abstract
public class ChinaChessPiece extends Piece {
    ChinaChessPiece(Square square, PieceColor color) {
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

    /**
     * Находится ли клетка <b>square</b> внутри крепости для фигур цвета
     * <b>color</b>?
     *
     * @param color  - цвет крепости.
     * @param square - проверяемая клетка.
     * @return внутри крепости или нет.
     */
    boolean inCastle(PieceColor color, Square square) {
        int minV = 3;
        int maxV = minV + 2;

        int minH = (color == PieceColor.BLACK ? 0 : 6);
        int maxH = (color == PieceColor.BLACK ? 2 : 8);

        return (minV <= square.v) && (square.v <= maxV)
                &&
                (minH <= square.h) && (square.h <= maxH);
    }

    /**
     * Пошла ли фигура цвета <b>color</b> на клетку <b>target</b> расположенную
     * на вражеской территории.
     *
     * @param color  - цвет фигуры которая пошла на поле <b>target</b>.
     * @param square - проверяемая клетка.
     * @return клетка <b>target</b> на вражеской территории или нет
     */
    boolean isEnemyPart(PieceColor color, Square square) {
        Board board = square.getBoard();

        return color == PieceColor.BLACK
                ? (square.h >= board.nH / 2)
                : (square.h < board.nH / 2);
    }
}