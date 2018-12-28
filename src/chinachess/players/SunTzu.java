package chinachess.players;

import chinachess.pieces.King;
import game.core.Board;
import game.core.Move;
import game.core.Piece;
import game.core.Square;
import game.core.moves.ICaptureMove;
import game.core.moves.ITransferMove;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Сунь-Цзы - автор знаменитого трактата о военной стратегии «Искусство войны».<br>
 * <a href="http://militera.lib.ru/science/sun-tszy/01.html">Сунь-Цзы. Искусство войны</a>
 */
public class SunTzu extends ChinaChessPlayer {
    private final Comparator<? super Piece> pieceComparator
            = (p1, p2) -> getWeight(p2) - getWeight(p1);

    private Comparator<? super Move> brain
            = (m1, m2) -> getWeight(m2) - getWeight(m1);

    @Override
    public String getName() {
        return "Сунь-Цзы";
    }

    @Override
    public String getAuthorName() {
        return "Дмитрив Ярослав";
    }

    Comparator<? super Move> getComparator() {
        return brain;
    }

    @Override
    public String toString() {
        return getName();
    }

    /**
     * Задать вес для хода.
     *
     * @param move - ход фигурой.
     * @return вес хода.
     */
    private int getWeight(Move move) {
        ITransferMove transfer = (ITransferMove) move;

        Square source = transfer.getSource();
        Square target = transfer.getTarget();

        Board board = source.getBoard();
        Piece piece = source.getPiece();

        final int maxDistance = board.maxDistance();

        if (move instanceof ICaptureMove) {
            // Ход - взятие фигуры врага.
            ICaptureMove capture = (ICaptureMove) move;

            Square capturedSquare = capture.getCaptured().get(0);
            Piece capturedPiece = capturedSquare.getPiece();

            // У захвата короля врага наивысший приоритет.
            if (capturedPiece instanceof King)
                return 1000;

            // Берем фигуру только если это выгодно.
            if (!isSafeCapture(piece, target))
                return -1;

            return 999;
        }

        // Идем на пустую клетку только если это безопасно.
        if (!isSafeMove(piece, target))
            return -1;

        // Из всех ходов без взятия фигуры врага лучший ход
        // который максимально приближает к королю врага.
        King enemyKing = getEnemyKing(piece);
        int stepWeight = maxDistance - target.distance(enemyKing.square);

        // Приоритет у тех фигур, которые стоят дальше от короля врага.
        // Это для того, что бы отстающие фигуры шли первыми.
        // Тогда наши фигуры наступают вместе и нет "зарвавшихся" фигур.
        stepWeight += source.distance(enemyKing.square);

        return stepWeight;
    }


    /**
     * @param piece  - какая фигура идет.
     * @param target - куда фигура идет.
     * @return Это безопасный ход?
     */
    private boolean isSafeMove(Piece piece, Square target) {
        // Собираем врагов атакующих клетку target
        // и сортируем их по весу. Менее ценные в начале списка.
        List<Piece> enemies = piece.getEnemies()
                .stream()
                .filter(enemy -> enemy.isCorrectMove(target))
                .sorted(pieceComparator.reversed())
                .collect(Collectors.toList());

        if (enemies.isEmpty())
            return true; // Враги поле target не атакуют.

        // Собираем друзей атакующих клетку target
        // и сортируем их по весу. Менее ценные в начале списка.
        List<Piece> friends = piece.getFriends()
                .stream()
                .filter(friend -> friend.isCorrectMove(target))
                .sorted(pieceComparator.reversed())
                .collect(Collectors.toList());

        // Не бьет ли клетку фигура меньшей ценности.
        Piece lowerEnemy = enemies.get(0);
        if (getWeight(piece) > getWeight(lowerEnemy))
            return false; // Не подставляем фигуру.

        // Если друзей атакующих клетку target больше, они защитят.
        // Себя среди атакующих клетку target не учитываем.
        return (friends.size() - 1) > enemies.size();
    }

    /**
     * @param piece  - какая фигура идет.
     * @param target - куда фигура идет.
     * @return Выгодно ли взятие фигурой на клетке target?
     */
    private boolean isSafeCapture(Piece piece, Square target) {
        // TODO Дмитрив
        // По аналогии с ходом isSafeMove определить
        // выгодно ли взятие.
        return true;
    }
}