package halma.players;

import game.core.Board;
import game.core.Move;
import game.core.PieceColor;
import game.core.Square;
import game.core.moves.ITransferMove;
import halma.Halma;

import java.util.Comparator;
import java.util.List;

/**
 * Ants - алгоритм "Муравьи".
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Ants extends HalmaPlayer {
    /**
     * Приоритет у хода делающего больший шаг к цели -
     * противоположному углу доски.
     */
    private final Comparator<ITransferMove> maxStep =
            (move1, move2) -> {
                // Направление шага.
                int dir = move1.getPiece().isWhite() ? 1 : -1;

                int step1 = move1.getSource().shift(move1.getTarget());
                int step2 = move2.getSource().shift(move2.getTarget());

                return dir * (step2 - step1);
            };
    /**
     * Приоритет у хода с более дальней позиции (отстающими фигурами).</br>
     * Тогда будет больше фигур пригодных для "перепрыгивания".
     */
    private final Comparator<ITransferMove> fromBack =
            (move1, move2) -> {
                Square goal = Halma.getPieceGoal(move1.getPiece());

                // Расстояние до клетки - цели.
                int distance1 = goal.distance(move1.getSource());
                int distance2 = goal.distance(move2.getSource());

                return Math.abs(distance2) - Math.abs(distance1);
            };

    public Ants() {
        super();
    }

    @Override
    public String getName() {
        return "Муравьи";
    }

    @Override
    public String getAuthorName() {
        return "Романов В.Ю.";
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public List<Move> getCorrectMoves(Board board, PieceColor color) {
        // Пока используем метод базового класса.
        return super.getCorrectMoves(board, color);

        // TODO реализовать алгоритм сбора составных ходов.
        // Может быть множество составных ходов выходящих
        // из одной клетки.
    }

    protected Comparator<ITransferMove> getComparator() {
        return maxStep.thenComparing(fromBack);
    }
}