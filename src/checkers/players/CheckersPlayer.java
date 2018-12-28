package checkers.players;

import checkers.moves.Capture;
import checkers.moves.CompositeMove;
import checkers.pieces.CheckersPiece;
import game.core.*;
import game.players.MovePiecePlayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Базовый класс для всех программ-игроков в шашки.
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
abstract
public class CheckersPlayer extends MovePiecePlayer {

    @Override
    public List<Move> getCorrectMoves(Board board, PieceColor color) {
        List<Move> correctMoves = new ArrayList<>();

        for (Piece p : board.getPieces(color)) {
            CheckersPiece piece = (CheckersPiece) p;

            // Собрали все клетки-цели на которые допустим ход фигуры р.
            List<Square> targets = board.getSquares()
                    .stream()
                    .filter(piece::isCorrectMove)
                    .collect(Collectors.toList());

            if (targets.isEmpty())
                continue;

            Square source = piece.square;

            // Соберем клетки на которые делаются ходы со взятием фигур.
            List<Square> captureSquares = targets
                    .stream()
                    .filter(target -> target.distance(source) == 4)
                    .collect(Collectors.toList());

            if (captureSquares.isEmpty())
                // Ходов со взятием фигур нет.
                for (Square target : targets) {
                    Move correctMove = piece.makeMove(source, target);
                    correctMoves.add(correctMove);
                }
            else {
                // Длинные ходы фигуры piece со взятием фигур.
                List<Move> allTracks = new ArrayList<>();

                // Основа для всех длинных ходов фигуры piece.
                CompositeMove trackBase = new CompositeMove(piece);

                collectTracks(piece, source, trackBase, allTracks);
                correctMoves.addAll(allTracks);
            }
        }

        return correctMoves;
    }

    /**
     * Собрать все длинные ходы простой фигуры.
     *
     * @param piece     - какая фигура идет.
     * @param square    - откуда фигура идет.
     * @param track     - предшествующие хода длинного хода.
     * @param allTracks - список всех возможных длинных ходов.
     */
    private void collectTracks(CheckersPiece piece, Square square, game.core.moves.CompositeMove<Capture> track, List<Move> allTracks) {
        List<Square> targets = new ArrayList<>();

        // Смотрим по всем диагоналям возможность захвата фигуры.
        for (Dirs dir : Dirs.DIAGONAL) {
            if (!square.hasNext(dir))
                continue;

            Square nextSquare = square.next(dir);

            // Нет вражеской фигуры для перепрыгивания.
            if (!piece.hasEnemy(nextSquare))
                continue;

            // Вражеская фигура на краю доски.
            if (!nextSquare.hasNext(dir))
                continue;

            // Клетка куда прыгаем пуста.
            Square jumpSquare = nextSquare.next(dir);

            if (jumpSquare.isEmpty() && track.isAcceptable(jumpSquare))
                targets.add(jumpSquare);
        }

        if (targets.isEmpty() && !track.isEmpty())
            // По всем диагоналям из клетки square больше ходов-взятий нет.
            // Клетка square - лист в дереве возможных длинных ходов.
            // Добавляем ход track в список допустимых длинных ходов.
            allTracks.add(track);
        else {
            // У длинного хода track фигуры есть еще ходы-взятия.
            for (Square target : targets) {
                Capture capture = (Capture) piece.createMove(piece, square, target);

                game.core.moves.CompositeMove<Capture> newBranch = track.getClone();
                newBranch.addMove(capture);

                collectTracks(piece, target, newBranch, allTracks);
            }
        }
    }

    @Override
    public void doMove(Board board, PieceColor color) throws GameOver {
        List<Move> correctMoves = getCorrectMoves(board, color);

        if (correctMoves.isEmpty()) {
            PieceColor enemyColor = Board.getOponentColor(color);

            // Все враги наши убиты. Мы выиграли.
            if (board.getPieces(enemyColor).isEmpty())
                throw new GameOver(GameResult.win(color));

            // Все наши фигуры убиты. Мы проиграли.
            if (board.getPieces(color).isEmpty())
                throw new GameOver(GameResult.lost(color));

            // Все наши фигуры заперты. Мы проиграли.
            throw new GameOver(GameResult.lost(color));
        }

        // Что бы ходы в играх не повторялись.
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
        if (board.history.getMoves().size() > 80) {
            // Сохраняем в истории игры последний сделанный ход
            // и результат игры.
            board.history.setResult(GameResult.DRAWN);

            // Просим обозревателей доски показать
            // положение на доске, сделанный ход и
            // результат игры.
            board.setBoardChanged();

            // Сообщаем что игра закончилась ничьей.
            throw new GameOver(GameResult.DRAWN);
        }
    }

    /**
     * Метод возвращяющий алгоритм для сравнения ходов
     * и выбора лучшего хода.
     *
     * @return алгоритм сравнения ходов.
     */
    abstract
    protected Comparator<? super Move> getComparator();
}