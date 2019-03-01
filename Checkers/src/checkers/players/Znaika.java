package checkers.players;

import game.core.Move;
import game.core.Piece;
import game.core.moves.ICaptureMove;

import java.util.Comparator;

/**
 * Знайка - не пускает фигуры противника в дамки.
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Znaika extends CheckersPlayer {
    private Comparator<? super Move> movesSorter
            = (m1, m2) -> getWeight(m2) - getWeight(m1);

    @Override
    public String getName() {
        return "Знайка";
    }

    @Override
    public String getAuthorName() {
        return "Романов В.Ю.";
    }

    protected Comparator<? super Move> getComparator() {
        return movesSorter;
    }

    @Override
    public String toString() {
        return getName();
    }

    /**
     * Задать вес для хода.
     *
     * @param move - ход шашек
     * @return оценка хода.
     */
    private int getWeight(Move move) {
        if (move instanceof ICaptureMove)
            return 1;

        Piece p = move.getPiece();

        // Ход фигурой с первой линии плох, поскольку
        // освобожает клетку для создания вражеской дамки.
        int firstLine = (p.isBlack() ? 0 : 7);
        return (p.square.h == firstLine) ? -1 : 1;
    }
}
