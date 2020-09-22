package game.core.listeners

import game.core.*

/**
 * Слушатель перемещения фигуры на доске.
 * И, возможно, при этом постановки новой фигуры на доску.
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
class MovePieceListener(private val boardPanel: IBoardPanel) : IGameListener {
    /**
     * Выбранная для перемещения фигура.
     */
    private var selectedPiece: Piece? = null

    /**
     * Клетка на которой стоит выбранная для перемещений фигура.
     */
    private var selectedSquare: Square? = null

    /**
     * Доска на которой происходят изменения.
     */
    private val board: Board = boardPanel.board

    override fun mouseDown(s: Square, button: Int) {
        if (s.isEmpty) return

        val moveColor = board.moveColor

        // Выберем для перемещения фигуру нужного цвета.
        selectedPiece = s.getPiece()
        if (selectedPiece == null) return

        if (selectedPiece!!.color !== moveColor) return

        // На время перемещения фигуры мышкой снимем ее с доски.
        s.removePiece()
        selectedSquare = s
        selectedPiece?.let { boardPanel.saveCursor(it) }

        // Перерисуем изображение доски с временно снятой с доски фигурой.
        boardPanel.updateBoard()
        board.setBoardChanged()
    }

    override fun mouseUp(s: Square, button: Int) {
        if (selectedSquare == null) return

        // Возвращаем фигуру на начальную клетку.
        // Теперь знаем куда эта фигура пойдет.
        // Все изменения на доске, связанные с этим ходом,
        // будут сделаны классом реализующим интерфейс Move.
        selectedSquare!!.setPiece(selectedPiece!!)
        if (selectedPiece!!.isCorrectMove(s)) {
            // Ход на заданную клетку правильный.
            // Создадим экземпляр хода и выполним его.
            val move = selectedPiece!!.makeMove(selectedSquare, s)
            try {
                move!!.doMove()
            } catch (e: GameOver) {
                // Сохраним экземпляр хода в истории партии.
                board.history.addMove(move!!)
                board.history.result = e.result
                selectedPiece = null
                selectedSquare = null

                // Восстановим курсор (с изображением-стрелкой).
                boardPanel.restoreCursor()

                // Пусть слушатели изменений на доске
                // нарисуют новое состояние доски.
                board.setBoardChanged()
                return
            }
            boardPanel.restoreCursor()

            // Сохраним экземпляр хода в истории партии.
            board.history.addMove(move)

            // TODO Реализовать запрос у пользователя фигуры для превращения пешки в эту фигуру.

            // Теперь ходить должен противник.
            board.changeMoveColor()
            return
        }
        selectedPiece = null
        selectedSquare = null

        // Восстановим курсор (с изображением-стрелкой).
        boardPanel.restoreCursor()

        // Пусть слушатели изменений на доске
        // нарисуют новое состояние доски.
        board.setBoardChanged()
    }
}