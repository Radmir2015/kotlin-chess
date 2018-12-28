package chinachess.pieces;

import chinachess.moves.Capture;
import chinachess.moves.SimpleMove;
import game.core.Board;
import game.core.Move;
import game.core.PieceColor;
import game.core.Square;

/**
 * Пушка в игре <a href="https://ru.wikipedia.org/wiki/%D0%A1%D1%8F%D0%BD%D1%86%D0%B8">
 * Китайские шахматы</a>
 *
 * @author <a href="mailto:y.o.dmitriv@gmail.com">Dmitriv Y.</a>
 */
public class Gun extends ChinaChessPiece {
    public Gun(Square square, PieceColor color) {
        super(square, color);
    }

    @Override
    public boolean isCorrectMove(Square... squares) {
        // Пока используем только умалчиваемую проверку
        // выполняемую в базовом классе.
        if (!super.isCorrectMove(squares))
            return false;

        Square gun = square;
        Square target = squares[0];

        // По пустым вертикалям и горизонталям
        // ходит как ладья не захватывая фигур.
        if (gun.isEmptyHorizontal(target))
            return target.isEmpty();

        if (gun.isEmptyVertical(target))
            return target.isEmpty();

        // Ни по кому не стреляем?
        if (target.isEmpty())
            return false;

        // По НЕ пустым вертикалям и горизонталям можем стрелять,
        // если:
        // 		- между пушкой и целью есть только одна фигура-барьер.
        //  	- этот барьер рядом с пушкой.
        Board board = square.getBoard();
        int v = target.v;
        int h = target.h;
        int nBarier = 0;
        Square barier = null;

        // Стреляем по горизонтали?
        if (gun.isHorizontal(target)) {
            // Двинемся в сторону цели.
            int dv = (target.v > gun.v) ? +1 : -1;

            for (v = gun.v; v != target.v; v += dv) {
                Square s = board.getSquare(v, h);

                // Пропустим пустую клетку.
                if (s.isEmpty()) continue;

                nBarier++;
                barier = s;
            }

            // Барьеров слишом много.
            if (nBarier != 1) return false;

            // Барьер далеко.
            return barier.isNear(square);
        }

        // Стреляем по вертикали?
        if (gun.isVertical(target)) {
            // Двинемся в сторону цели.
            int dh = (target.h > gun.h) ? +1 : -1;

            for (h = gun.h + dh; h != target.h; h += dh) {
                Square s = board.getSquare(v, h);

                // Пропустим пустую клетку.
                if (s.isEmpty()) continue;

                nBarier++;
                barier = s;
            }

            if (nBarier != 1) // Барьеров слишом много.
                return false;

            // Барьер далеко.
            return barier.isNear(square);
        }

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
        return "C";
    }
}
