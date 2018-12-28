package tamerlan.playres;

import game.core.Move;
import game.core.Piece;
import game.core.Square;
import game.core.moves.ICaptureMove;
import game.core.moves.ITransferMove;
import tamerlan.pieces.King;

import java.util.Comparator;

/**
 * Тохтамыш - хан Золотой Орды.
 */
public class Tuqtamish extends TamerlanChessPlayer {
    private final Comparator<? super Move> brain
            = (m1, m2) -> getMoveWeight(m2) - getMoveWeight(m1);

    @Override
    public String getName() {
        return "Тохтамыш (З.Орда)";
    }

    @Override
    public String getAuthorName() {
        return "Романов В.Ю.";
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
    private int getMoveWeight(Move move) {
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

        return MAX_DISTANCE - distance(target, enemyKing.square);
    }
}