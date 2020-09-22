package game.core.players

import game.core.Board
import game.core.GameOver
import game.core.GameResult
import game.core.PieceColor

/**
 * Незнайка - простой игрок для игр в которых передвигают фигуры.
 * Он случайным образом выбирает ход из всех допустимых ходов.
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
class Neznaika @JvmOverloads constructor(private val maxMoves: Int = 80) : MovePiecePlayer() {
    override val name: String
        get() = "Незнайка"
    override val authorName: String
        get() = "Романов В.Ю."

    @Throws(GameOver::class)
    override fun doMove(board: Board, color: PieceColor) {
        val correctMoves = getCorrectMoves(board, color)
        if (correctMoves.isEmpty()) return

        // Незнайка делает случайный ход.
        correctMoves.shuffle()
        val randomMove = correctMoves[0]

        try {
            randomMove.doMove()
        } catch (e: GameOver) {
            // Сохраняем в истории игры последний сделанный ход
            // и результат игры.
            board.history.addMove(randomMove)
            board.history.result = e.result

            // Просим обозревателей доски показать
            // положение на доске, сделанный ход и
            // результат игры.
            board.setBoardChanged()
            throw GameOver(e.result)
        }

        // Сохраняем ход в истории игры.
        board.history.addMove(randomMove)

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

    override fun toString(): String = name
}