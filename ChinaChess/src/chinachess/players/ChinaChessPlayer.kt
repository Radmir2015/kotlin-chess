package chinachess.players

import chinachess.pieces.*
import game.core.*
import game.core.players.MovePiecePlayer
import java.security.Guard
import kotlin.math.abs

//import java.util.*

/**
 * Базовый класс для всех игроков в китайские шахматы.
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
abstract class ChinaChessPlayer : MovePiecePlayer() {
    @Throws(GameOver::class)
    override fun doMove(board: Board, color: PieceColor) {
        val correctMoves = getCorrectMoves(board, color)
        if (correctMoves.isEmpty()) return
        correctMoves.shuffle()
        correctMoves.sortWith(comparator)
        val bestMove = correctMoves[0]
        try {
            bestMove.doMove()
        } catch (e: GameOver) {
            // Сохраняем в истории игры последний сделанный ход
            // и результат игры.
            board.history.addMove(bestMove)
            board.history.result = e.result

            // Просим обозревателей доски показать
            // положение на доске, сделанный ход и
            // результат игры.
            board.setBoardChanged()
            throw GameOver(e.result)
        }

        // Сохраняем ход в истории игры.
        board.history.addMove(bestMove)

        // Просим обозревателей доски показать
        // положение на доске, сделанный ход и
        // результат игры.
        board.setBoardChanged()

        // Для отладки ограничим количество ходов в игре.
        // После этого результат игры ничья.
        if (board.history.moves.size > maxMoves) {
            // Сохраняем в истории игры последний сделанный ход
            // и результат игры.
            board.history.result = GameResult.DRAWN
            throw GameOver(GameResult.DRAWN)
        }
    }

    /**
     * Алгоритм сравнения ходов для выбора лучшего хода
     * будет реализован в классах - потомках.
     *
     * @return - алгоритм сравнения ходов.
     */
    abstract val comparator: Comparator<in Move>

    /**
     * Найти короля у фигур - врагов для фигуры piece.
     *
     * @param piece - фигура для которой ищем врага-короля.
     * @return вражеский король.
     */
    fun getEnemyKing(piece: Piece): King? {
        return piece.enemies
                .stream()
                .filter { enemy: Piece -> enemy is King }
                .map { enemy: Piece -> enemy as King }
                .findFirst()
                .orElse(null)
    }

    /**
     * Дать вес фигуры
     *
     * @param p - измеряемая фигура.
     * @return ценность фигуры.
     */
    fun getWeight(p: Piece): Int {
        if (p is King) return 1000
        if (p is Guard) return 900
        if (p is Gun) return 800
        if (p is Rook) return 700
        if (p is Bishop) return 600
        if (p is Knight) return 500
        return if (p is Pawn) 400 else 0
    }

    companion object {
        /**
         * Максимальное расстояние между клетками доски:
         * ширина доски + длина доски.
         */
        const val MAX_DISTANCE = 9 + 10
        private const val maxMoves = 180

        /**
         * Ценность (вес) поля на доске.
         * Чем ближе поле к центру, тем оно лучше.
         *
         * @param s - поле
         * @return вес поля.
         */
        fun getSquareWeight(s: Square): Int {
            val board = s.board
            val tv = s.v
            val th = s.h
            val dv = abs(tv - 0.5 * (board.nV - 1))
            val dh = abs(th - 0.5 * (board.nH - 1))
            val distance = (dv + dh).toInt()
            return MAX_DISTANCE - distance
        }
    }
}