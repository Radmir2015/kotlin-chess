package game.core.listeners

import game.core.IBoardPanel
import game.core.Square

/**
 * Слушатель для игр в которых фигуры ставятся на доску.
 * Слушатель PutPiecePromptListener определяет клетки на которые можно поставить фигуру.
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
class NoPromptListener(
        /**
         * Панель на которой отрисовывается доска.
         */
        private val boardPanel: IBoardPanel,
) : IMouseMoveListener {
    override fun mouseMove(squareUnderMouse: Square) {
        // Доска на которой расположены фигуры.
        val board = boardPanel.board

        // Получим фигуру НЕ стоящую на клетке.
        val moveColor = board.moveColor
        val piece = boardPanel.getPiece(squareUnderMouse, moveColor) ?: return
        piece.remove() // Уберем фигуру с доски.

        // Зададим изображение курсора такое как избражение у фигуры.
        boardPanel.pieceToCursor(piece)
    }
}