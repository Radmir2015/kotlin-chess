package game.core.listeners

import game.core.*
import game.core.moves.CompositeMove
import game.core.moves.ITrackMove
import game.core.moves.ITransferMove

/**
 * Слушатель постановки перемещения фигуры на доске.
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
class TrackPieceListener<T : ITransferMove>(private val boardPanel: IBoardPanel) : IGameListener {
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

    private var track: CompositeMove<T>? = null

    override fun mouseDown(s: Square, button: Int) {
        if (s.isEmpty) return
        selectedPiece = s.getPiece()

        // Выберем для перемещения фигуру нужного цвета.
        val moveColor = board.moveColor
        if (selectedPiece!!.color !== moveColor) return

        // На время перемещения фигуры мышкой
        // снимем ее с доски.
        selectedSquare = s
        selectedSquare!!.removePiece()
        selectedPiece?.let { boardPanel.saveCursor(it) }

        // Перерисуем изображение доски с временно снятой фигурой.
        board.setBoardChanged()
        boardPanel.updateBoard()
    }

    override fun mouseUp(s: Square, button: Int) {
        if (selectedPiece == null) return
        if (selectedSquare == null) return

        // Возвращаем фигуру на начальную клетку.
        // Теперь знаем куда эта фигура пойдет.
        // Все изменения на доске, связанные с этим ходом,
        // будут сделаны классом реализующим интерфейс Move.
        selectedSquare!!.setPiece(selectedPiece!!)
        if (selectedPiece!!.isCorrectMove(s)) doMove(s)
        selectedPiece = null
        selectedSquare = null

        // Восстановим курсор (с изображением стрелки).
        boardPanel.restoreCursor()

        // Пусть слушатели изменений на доске
        // нарисуют новое состояние доски.
        board.setBoardChanged()
        boardPanel.updateBoard()
    }

    private fun doMove(mouseSquare: Square) {
        if (selectedPiece == null) return
        if (selectedSquare == null) return

        // Ход на заданную клетку допустим.
        // Создадим экземпляр хода и выполним его.
        val move = selectedPiece!!.makeMove(selectedSquare!!, mouseSquare) as T?
        if (move !is ITrackMove) {
            // Простой ход.
            try {
                move!!.doMove()
            } catch (e: GameOver) {
                // Сохраним экземпляр хода в истории игры.
                move?.let { board.history.addMove(it) }
                board.history.result = e.result
                selectedPiece = null
                selectedSquare = null

                // Восстановим курсор (с изображением стрелки).
                boardPanel.restoreCursor()

                // Пусть слушатели изменений на доске
                // нарисуют новое состояние доски.
                board.setBoardChanged()
                boardPanel.updateBoard()
            }

            // Сохраним ход в истории игры.
            move?.let { board.history.addMove(it) }

            // Теперь ходить должен противник.
            board.changeMoveColor()

            // Пусть слушатели изменений на доске
            // нарисуют новое состояние доски.
            board.setBoardChanged()
        } else {
            // Простой ход фигурой - часть составного хода фигурой
            // (последовательности простых ходов той же фигурой).
            if (track == null) {
                // Первый ход в серии ходов.
                track = CompositeMove(move)
            } else
                if (!track!!.isAcceptable(mouseSquare)) // На эту клетку уже ходили.
                // Избегаем хождения фигурой по кругу.
                    return
                else {
                    // Добавим простой ход в серию ходов.
                    track!!.undoMove()
                    track!!.addMove(move)
                }

            // Делаем последовательность простых ходов.
            try {
                track!!.doMove()
            } catch (e: GameOver) {
                // Конец игры.
                // Сохраним экземпляр хода в истории игры.
                board.history.addMove(track!!)
                board.history.result = e.result
                selectedPiece = null
                selectedSquare = null

                // Восстановим курсор (с изображением стрелки).
                boardPanel.restoreCursor()

                // Пусть слушатели изменений на доске
                // нарисуют новое состояние доски.
                board.setBoardChanged()
            }

            // Пусть слушатели изменений на доске
            // нарисуют новое состояние доски.
            board.setBoardChanged()
            if (move.hasNext()) return

            //
            // Последний простой ход в последовательности ходов.
            //
            // Сохраним экземпляр хода в истории игры.
            board.history.addMove(track!!)

            // Теперь ходить должен противник.
            board.changeMoveColor()
            track = null
        }
    }
}