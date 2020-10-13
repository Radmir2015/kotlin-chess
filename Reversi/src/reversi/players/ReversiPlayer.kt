package reversi.players

import game.core.*
import game.core.Board.Companion.getOpponentColor
import game.core.GameResult.Companion.win
import game.core.moves.PassMove
import game.core.players.PutPiecePlayer
import java.util.*

/**
 * Базовый класс для всех игроков в реверси.
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
abstract class ReversiPlayer(pieceProvider: IPieceProvider) : PutPiecePlayer(pieceProvider) {
    /**
     * @param s - проверяемая клетка.
     * @return Находится ли клетка на границе доски.
     */
    fun isBorder(s: Square): Boolean {
        val b = s.board
        return s.v == 0 ||
                s.h == 0 ||
                s.v == b.nV - 1 ||
                s.h == b.nH - 1
    }

    /**
     * @param s - проверяемая клетка.
     * @return Находится ли клетка в углу доски.
     */
    fun isCorner(s: Square): Boolean {
        val b = s.board
        if (s.v == 0 && s.h == 0) return true
        if (s.v == 0 && s.h == b.nH - 1) return true
        return if (s.v == b.nV - 1 && s.h == 0) true else s.v == b.nV - 1 && s.h == b.nH - 1
    }

    @Throws(GameOver::class)
    override fun doMove(board: Board, color: PieceColor) {
        val enemyColor = getOpponentColor(color)
        val enemies = board.getPieces(enemyColor)
        if (enemies.isEmpty()) {
            // Врагов уже нет. Мы выиграли.
            // Сохраняем в истории игры последний сделанный ход
            // и результат игры.
            board.history.result = win(color)

            // Просим обозревателей доски показать
            // положение на доске, сделанный ход и
            // результат игры.
            board.setBoardChanged()
            throw GameOver(win(color))
        }

        val correctMoves = getCorrectMoves(board)
        if (correctMoves.isEmpty()) {
            // Пропускаем ход.
            val bestMove: Move = PassMove()

            // Сохраняем ход в истории игры.
            board.history.addMove(bestMove)

            // Просим обозревателей доски показать
            // положение на доске, сделанный ход и
            // результат игры.
            board.setBoardChanged()
            return
        }
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
            throw GameOver(GameResult.DRAWN)
        }

        // Сохраняем ход в истории игры.
        board.history.addMove(bestMove)

        // Просим обозревателей доски показать
        // положение на доске, сделанный ход и
        // результат игры.
        board.setBoardChanged()

        // Для отладки ограничим количество ходов в игре.
        // После этого результат игры ничья.
        if (board.history.moves.size > 80) {
            // Сохраняем в истории игры последний сделанный ход
            // и результат игры.
            board.history.result = GameResult.DRAWN
            throw GameOver(GameResult.DRAWN)
        }
    }

    protected abstract val comparator: Comparator<in Move>
}