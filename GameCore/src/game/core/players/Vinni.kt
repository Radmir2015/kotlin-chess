package game.core.players;

import game.core.*;

import java.util.Collections;
import java.util.List;

/**
 * Винни - простой игрок для игр в которых ставятся фигуры на доску.
 * Он случайным образом выбирает ход из всех допустимых ходов.
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Vinni extends PutPiecePlayer {
    private int maxMoves = 80;

    /**
     * Винни - простой игрок для игр в которых ставятся фигуры на доску.
     * Он случайным образом выбирает клетку на которую можно поставить фигуру.
     */
    public Vinni(IPieceProvider pieceProvider, int maxMoves) {
        super(pieceProvider);
        this.pieceProvider = pieceProvider;
        this.maxMoves = maxMoves;
    }

    /**
     * Винни - простой игрок для игр в которых ставятся фигуры на доску.
     * Он случайным образом выбирает клетку на которую можно поставить фигуру.
     */
    public Vinni(IPieceProvider pieceProvider) {
        super(pieceProvider);
        this.pieceProvider = pieceProvider;
    }

    @Override
    public String getName() {
        return "Винни";
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

        // Винни делает случайный ход.
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

            throw new GameOver(GameResult.DRAWN);
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
}