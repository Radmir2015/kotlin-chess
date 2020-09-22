package game.core.listeners

import game.core.Board
import game.core.GameOver
import game.core.IBoardPanel
import game.core.Square

/**
 * Слушатель событий о нажатии кнопок мыши используемых
 * для постановки новой фигуры на доску.
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
class PutPieceListener(private val boardPanel: IBoardPanel) : IGameListener {
    /**
     * Доска на которой присходят изменения.
     */
    private val board: Board = boardPanel.board

    /**
     * Панель для отрисовки доски.
     */
    override fun mouseUp(s: Square, button: Int) {}

    override fun mouseDown(s: Square, button: Int) {
        if (!s.isEmpty) return

        // Получим фигуру НЕ стоящую на клетке.
        val moveColor = board.moveColor
        val piece = boardPanel.getPiece(s, moveColor) ?: return
        piece.remove()
        if (!piece.isCorrectMove(s)) return  // На эту клетку ставить нельзя.

        // Постановка фигуры на заданную клетку правильная.
        // Создадим экземпляр хода и выполним его.
        val move = piece.makeMove(s)
        board.history.addMove(move!!)
        try {
            move.doMove()
        } catch (e: GameOver) {
            // Сохраним экземпляр кода и истории партии.
            board.history.addMove(move)
            board.history.result = e.result

            // Теперь ходить должен противник.
            board.changeMoveColor()

            // Получим новую фигуру НЕ стоящую на клетке.
            val p = boardPanel.getPiece(s, board.moveColor) ?: return
            p.remove()

            // Зададим изображение курсора такое как избражение у новой фигуры.
            boardPanel.pieceToCursor(p)

            // Пусть слушатели изменений на доске
            // нарисуют новое состояние доски.
            board.setBoardChanged()
            return
        }

        // Теперь ходить должен противник.
        board.changeMoveColor()

        // Зададим изображение курсора такое как избражение у фигуры.
        boardPanel.pieceToCursor(piece)

        // Пусть слушатели изменений на доске
        // нарисуют новое состояние доски.
        board.setBoardChanged()
    }
}