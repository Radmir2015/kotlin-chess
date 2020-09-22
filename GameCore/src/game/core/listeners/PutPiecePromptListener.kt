package game.core.listeners

import game.core.IBoardPanel
import game.core.Square

/**
 * Слушатель для игр в которых фигуры ставятся на доску.
 * Слушатель PutPiecePromptListener определяет клетки на которые можно поставить фигуру.
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
class PutPiecePromptListener(
        /**
         * Панель на которой отрисовывается доска.
         */
        private val boardPanel: IBoardPanel,
) : IMouseMoveListener {
    override fun mouseMove(squareUnderMouse: Square) {
        boardPanel.prompted.clear()
        val underMousePiece = squareUnderMouse.getPiece()
        if (underMousePiece != null) {
            // Под мышкой уже есть фигура. Клетка не пустая.
            // Для игр у которых фигуры ставяться на доску,
            // ход на занятую клетку невозможен.
            // Перерисуем панель доски без подсказок.
            boardPanel.updateBoard()
            return
        }

        // Доска на которой расположены фигуры.
        val board = boardPanel.board

        // Получим фигуру НЕ стоящую на клетке.
        val moveColor = board.moveColor
        val piece = boardPanel.getPiece(squareUnderMouse, moveColor) ?: return
        piece.remove() // Уберем фигуру с доски.

        // Зададим изображение курсора такое как избражение у фигуры.
        boardPanel.pieceToCursor(piece)

        // Соберем клетки, на которые можно поставить новую фигуру.
        // На этих клетках будут нарисованы маркеры- подсказки игроку.
        val prompted: MutableList<Square> = boardPanel.prompted
        for (s in board.getSquares())
            if (piece.isCorrectMove(s))
                prompted.add(s)

        // Перерисуем панель доски c подсказками для тех
        // клеток на которые допустима постановка фигуры.
        board.setBoardChanged()
    }
}