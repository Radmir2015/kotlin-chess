package vikings.players;

import game.core.*;
import game.core.players.MovePiecePlayer;
import vikings.moves.Capture;
import vikings.pieces.Cyning;
import vikings.pieces.VikingsPiece;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Баховый класс для всех программ-игроков ишры Викинги.
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
abstract
public class VikingsPlayer extends MovePiecePlayer {
    private int maxMoves;

    VikingsPlayer() {
        this(180);
    }

    private VikingsPlayer(int maxMoves) {
        this.maxMoves = maxMoves;
    }

    @Override
    public void doMove(Board board, PieceColor color) throws GameOver {
        List<Move> correctMoves = getCorrectMoves(board, color);

        if (correctMoves.isEmpty())
            return;

        Collections.shuffle(correctMoves);

        correctMoves.sort(getComparator());
        Move bestMove = correctMoves.get(0);

        try {
            bestMove.doMove();
        } catch (GameOver e) {
            // Сохраняем в истории игры последний сделанный ход
            // и результат игры.
            board.history.addMove(bestMove);
            board.history.setResult(e.result);

            // Просим обозревателей доски показать
            // положение на доске, сделанный ход и
            // результат игры.
            board.setBoardChanged();

            // Распространяем инфрмацию об окончании игры.
            throw new GameOver(e.result);
        }

        // Сохраняем ход в истории игры.
        board.history.addMove(bestMove);

        // Просим обозревателей доски показать
        // положение на доске, сделанный ход и
        // результат игры.
        board.setBoardChanged();

        // Для отладки ограничим количество ходов в игре.
        // После этого результат игры ничья.
        if (board.history.getMoves().size() > maxMoves) {
            // Сохраняем в истории игры последний сделанный ход
            // и результат игры.
            board.history.setResult(GameResult.DRAWN);

            // Сообщаем что игра закончилась ничьей.
            throw new GameOver(GameResult.DRAWN);
        }
    }

    /**
     * Алгоритм выбора лучшего хода реализуется в клессах - потомках.
     */
    abstract protected Comparator<? super Move> getComparator();

    /**
     * @param capture - ход-захват фигур.
     * @return Есть ли среди захваченых фигур белый король?
     */
    boolean isKingCapture(Capture capture) {
        return capture.getCapturedPieces()
                .stream()
                .anyMatch(p -> p instanceof Cyning);
    }

    /**
     * Перекрывает ли ход черной фигурой на клетку <b>target</b>
     * ход королем в одну из клеток выхода.
     *
     * @param target     - куда идет черная фигура
     * @param kingSquare - где стоит белый король.
     * @return есть перекрытие или нет.
     */
    boolean isOverlapMove(Square target, Square kingSquare) {
        Board board = target.getBoard();

        boolean isHorizontalOverlap = VikingsPiece.getExits(board)
                .stream()

                // Между выходом и королем пустая горизонталь.
                .filter(exit -> exit.isEmptyHorizontal(kingSquare))

                // Между выходом и новой клеткой фигуры пустая горизонталь.
                .filter(exit -> exit.isEmptyHorizontal(target))

                // Между новой клеткой фигуры и королем пустая горизонталь.
                .anyMatch(exit -> exit.isEmptyHorizontal(target));

        boolean isVerticalOverlap = VikingsPiece.getExits(board)
                .stream()

                // Между выходом и королем пустая горизонталь.
                .filter(exit -> exit.isEmptyVertical(kingSquare))

                // Между выходом и новой клеткой фигуры пустая горизонталь.
                .filter(exit -> exit.isEmptyVertical(target))

                // Между новой клеткой фигуры и королем пустая горизонталь.
                .anyMatch(exit -> exit.isEmptyVertical(target));

        return isHorizontalOverlap || isVerticalOverlap;
    }

    /**
     * Найти ближайшую для заданной клетки клетку-выход.
     *
     * @param square - заданная клетка.
     * @param exits  - клетки-выходы.
     * @return ближайшая клетка выход.
     */
    Square getNearestExit(Square square, List<Square> exits) {
        return exits
                .stream()
                .min(Comparator.comparingInt(square::distance))
                .get();
    }

    /**
     * @return Преследуем короля, как основную фигуру для захвата
     */
    Cyning getKing(Board board) {
        return board.getPieces(PieceColor.WHITE)
                .stream()
                .filter(p -> p instanceof Cyning)
                .map(p -> (Cyning) p)
                .findAny()
                .get();
    }
}