package reversi.players;

import game.core.IPieceProvider;
import game.core.Move;
import game.core.Square;
import game.core.moves.ICaptureMove;
import game.core.moves.IPutMove;

import java.util.Comparator;

/**
 * Сова - игрок в реверси.
 * Знает что фигуры в углах доски окружить невозможно.
 * Знает что фигуры на краях окружить сложнее чем в центре доски.
 * Выбирает ход с захватом максимального количества фигур врага.
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Owl extends ReversiPlayer {
    private final Comparator<? super Move> brain
            = (m1, m2) -> getMoveWeight(m2) - getMoveWeight(m1);

    /**
     * Сова - игрок в реверси. Знает что фигуры в углах доски окружить
     * невозможно. Знает что фигуры на краях окружить сложнее чем в центре
     * доски. Выбирает ход с захватом максимального количества фигур врага.
     */
    public Owl(IPieceProvider pieceProvider) {
        super(pieceProvider);
        this.pieceProvider = pieceProvider;
    }

    @Override
    public String getName() {
        return "Сова";
    }

    @Override
    public String getAuthorName() {
        return "Романов В.Ю.";
    }

    @Override
    public Comparator<? super Move> getComparator() {
        return brain;
    }

    private int getMoveWeight(Move move) {
        IPutMove putMove = (IPutMove) move;

        Square target = putMove.getTarget();

        if (isCorner(target))
            return 1000; // Встали в угол.

        if (isBorder(target))
            return 900; // Встали на край доски.

        if (move instanceof ICaptureMove) {
            // Ход - взятие фигур врага.
            ICaptureMove capture = (ICaptureMove) move;

            // Цена хода - сколько взяли фигур.
            return capture.getCaptured().size();
        }

        return 0;
    }
}