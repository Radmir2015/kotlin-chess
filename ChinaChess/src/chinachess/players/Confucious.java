package chinachess.players;

import chinachess.pieces.King;
import game.core.Move;
import game.core.Piece;
import game.core.Square;
import game.core.moves.ICaptureMove;
import game.core.moves.ITransferMove;

import java.util.Comparator;

/**
 * Сунь-Цзы - первый профессиональный педагог Поднебесной.<br>
 * <a href="https://ok.ru/tantra.yoga/topic/65490423540658"> Пять постоянств
 * праведного человека (Конфуций)</a>
 */
public class Confucious extends ChinaChessPlayer {
    private final Comparator<? super Move> brain = (m1, m2) -> getWeight(m2) - getWeight(m1);

    @Override
    public String getName() {
        return "Конфуций";
    }

    @Override
    public String getAuthorName() {
        return "Осинцев Александр";
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    Comparator<? super Move> getComparator() {
        return brain;
    }

    /**
     * Задать вес для хода.
     *
     * @param move - ход
     * @return оценка хода.
     */
    private int getWeight(Move move) {
        ITransferMove transfer = (ITransferMove) move;

        Square source = transfer.getSource();
        Square target = transfer.getTarget();
        Piece thePiece = source.getPiece();

        if (move instanceof ICaptureMove) {
            // Ход - взятие фигуры врага.
            ICaptureMove capture = (ICaptureMove) move;

            Square capturedSquare = capture.getCaptured().get(0);
            Piece capturedPiece = capturedSquare.getPiece();

            // У захвата короля врага наивысший приоритет.
            if (capturedPiece instanceof King)
                return 1000;

            // Пока берем любую фигуру.
            return 999;
        }

        // Из всех ходов без взятия фигуры врага лучший ход
        // который максимально приближает к королю врага.
        King enemyKing = getEnemyKing(thePiece);

        return MAX_DISTANCE - target.distance(enemyKing.square);
    }
}