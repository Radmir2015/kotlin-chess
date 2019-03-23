package game.core.players;

import game.core.*;

import java.util.Collections;
import java.util.List;

/**
 * Незнайка - простой игрок для игр в которых передвигают фигуры.
 * Он случайным образом выбирает ход из всех допустимых ходов.
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Neznaika extends MovePiecePlayer {
    private final int maxMoves;

    public Neznaika() {
        this(80);
    }

    public Neznaika(int maxMoves) {
        this.maxMoves = maxMoves;
    }

    @Override
    public String getName() {
        return "Незнайка";
    }

    @Override
    public String getAuthorName() {
        return "Романов В.Ю.";
    }

    @Override
    public void doMove(Board board, PieceColor color) throws GameOver {
        List<Move> correctMoves = getCorrectMoves(board, color);

        if (correctMoves.isEmpty())
            return;

        // Незнайка делает случайный ход.
        Collections.shuffle(correctMoves);
        Move randomMove = correctMoves.get(0);

        try {
            randomMove.doMove();
        } catch (GameOver e) {
            // Сохраняем в истории игры последний сделанный ход
            // и результат игры.
            board.history.addMove(randomMove);
            board.history.setResult(e.result);

            // Просим обозревателей доски показать
            // положение на доске, сделанный ход и
            // результат игры.
            board.setBoardChanged();

            throw new GameOver(e.result);
        }

        // Сохраняем ход в истории игры.
        board.history.addMove(randomMove);

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

    @Override
    public String toString() {
        return getName();
    }
}