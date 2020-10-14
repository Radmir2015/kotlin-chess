package chinachess.players

import chinachess.pieces.King
import game.core.Move
import game.core.Piece
import game.core.Square
import game.core.moves.ICaptureMove
import game.core.moves.ITransferMove
import java.util.*
import java.util.stream.Collectors

/**
 * Сунь-Цзы - автор знаменитого трактата о военной стратегии «Искусство войны».<br></br>
 * [Сунь-Цзы. Искусство войны](http://militera.lib.ru/science/sun-tszy/01.html)
 */
class SunTzu : ChinaChessPlayer() {
    private val pieceComparator: Comparator<in Piece> = Comparator { p1: Piece, p2: Piece -> getWeight(p2) - getWeight(p1) }

    override val comparator: Comparator<in Move> = Comparator { m1: Move, m2: Move -> getWeight(m2) - getWeight(m1) }

    override val name = "Сунь-Цзы"
    override val authorName = "Дмитрив Ярослав"
    override fun toString() = name

    /**
     * Задать вес для хода.
     *
     * @param move - ход фигурой.
     * @return вес хода.
     */
    private fun getWeight(move: Move): Int {
        val transfer = move as ITransferMove
        val source = transfer.source
        val target = transfer.target
        val board = source.board
        val piece = source.getPiece() ?: return 0
        val maxDistance = board.maxDistance()

        if (move is ICaptureMove) {
            // Ход - взятие фигуры врага.
            val capture = move as ICaptureMove
            val capturedSquare = capture.captured[0]
            val capturedPiece = capturedSquare.getPiece()

            // У захвата короля врага наивысший приоритет.
            if (capturedPiece is King) return 1000

            // Берем фигуру только если это выгодно.
            return if (!isSafeCapture(piece, target)) -1 else 999
        }

        // Идем на пустую клетку только если это безопасно.
        if (!isSafeMove(piece, target)) return -1

        // Из всех ходов без взятия фигуры врага лучший ход
        // который максимально приближает к королю врага.
        val enemyKing = getEnemyKing(piece) ?: return 0
        var stepWeight = maxDistance - target.distance(enemyKing.square)

        // Приоритет у тех фигур, которые стоят дальше от короля врага.
        // Это для того, что бы отстающие фигуры шли первыми.
        // Тогда наши фигуры наступают вместе и нет "зарвавшихся" фигур.
        stepWeight += source.distance(enemyKing.square)

        return stepWeight
    }

    /**
     * @param piece  - какая фигура идет.
     * @param target - куда фигура идет.
     * @return Это безопасный ход?
     */
    private fun isSafeMove(piece: Piece?, target: Square): Boolean {
        // Собираем врагов атакующих клетку target
        // и сортируем их по весу. Менее ценные в начале списка.
        val enemies = piece!!.enemies
                .stream()
                .filter { enemy: Piece -> enemy.isCorrectMove(target) }
                .sorted(pieceComparator.reversed())
                .collect(Collectors.toList())
        if (enemies.isEmpty()) return true // Враги поле target не атакуют.

        // Собираем друзей атакующих клетку target
        // и сортируем их по весу. Менее ценные в начале списка.
        val friends = piece.friends
                .stream()
                .filter { friend: Piece -> friend.isCorrectMove(target) }
                .sorted(pieceComparator.reversed())
                .collect(Collectors.toList())

        // Не бьет ли клетку фигура меньшей ценности.
        val lowerEnemy = enemies[0]
        return if (getWeight(piece) > getWeight(lowerEnemy)) false
        else friends.size - 1 > enemies.size // Не подставляем фигуру.

        // Если друзей атакующих клетку target больше, они защитят.
        // Себя среди атакующих клетку target не учитываем.
    }

    /**
     * @param piece  - какая фигура идет.
     * @param target - куда фигура идет.
     * @return Выгодно ли взятие фигурой на клетке target?
     */
    private fun isSafeCapture(piece: Piece?, target: Square): Boolean {
        // TODO Дмитрив
        // По аналогии с ходом isSafeMove определить
        // выгодно ли взятие.
        return true
    }
}