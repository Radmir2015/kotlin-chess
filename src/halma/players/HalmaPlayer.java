package halma.players;

import game.core.*;
import game.core.moves.ITransferMove;
import game.players.MovePiecePlayer;
import halma.Halma;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Базовый класс для всех программ-игроков в уголки.
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
abstract
public class HalmaPlayer extends MovePiecePlayer {

    HalmaPlayer() {
        super();
    }

    @Override
    public void doMove(Board board, PieceColor color) throws GameOver {
        List<Move> correctMoves = getCorrectMoves(board, color);

        if (correctMoves.isEmpty())
            return;

        // Случайным образом переставим ходы в списке
        // что бы не играть всегда одну и ту же игру.
        Collections.shuffle(correctMoves);

        // Сначала приоритет ходам с более длинным шагом
        // (на большее число клеток).
        // Затем среди выбранных ходов с длинным шагом -
        // выбирается ход отстающими фигурами.
        Comparator<ITransferMove> comparator =
                getComparator();

        Move bestMove = correctMoves
                .stream()
                .map(m -> (ITransferMove) m)
                .sorted(comparator)
                .findFirst()
                .get();

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

        PieceColor moveColor = bestMove.getPiece().getColor();
        if (Halma.getScore(board, moveColor) <= 40) {
            // Сохраняем в истории игры последний сделанный ход
            // и результат игры.

            GameResult result = GameResult.win(moveColor);

            board.history.setResult(result);

            // Просим обозревателей доски показать
            // положение на доске, сделанный ход и
            // результат игры.
            board.setBoardChanged();

            throw new GameOver(result);
        }


        // Для отладки ограничим количество ходов в игре.
        // После этого результат игры ничья.
        if (board.history.getMoves().size() > 300) {
            // Сохраняем в истории игры последний сделанный ход
            // и результат игры.
            board.history.setResult(GameResult.DRAWN);

            // Сообщаем что игра закончилась ничьей.
            throw new GameOver(GameResult.DRAWN);
        }
    }

    abstract
    protected Comparator<ITransferMove> getComparator();
}