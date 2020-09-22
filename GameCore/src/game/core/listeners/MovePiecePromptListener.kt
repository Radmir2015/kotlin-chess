package game.core.listeners

import game.core.IBoardPanel
import game.core.Square

/**
 * Слушатель MovePiecePromptListener определяет клетки, на которые может пойти
 * фигура находящаяся под мышкой.
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
class MovePiecePromptListener(
        /**
         * Панель, на которой рисуется доска.
         */
        private val boardPanel: IBoardPanel,
)
    : IMouseMoveListener {
    override fun mouseMove(squareUnderMouse: Square) {
        boardPanel.prompted.clear()
        val underMousePiece = squareUnderMouse.getPiece()
        if (underMousePiece == null) {
            // Под мышкой фигуры нет, клетка пустая.
            // Перерисуем панель доски без подсказок.
            boardPanel.updateBoard()
            return
        }

        // Доска на которой расположены фигуры.
        val board = boardPanel.board
        for (s in board.getSquares())
            if (underMousePiece.isCorrectMove(s))
                boardPanel.prompted.add(s)

        // Перерисуем панель доски c маркерами-подсказками
        // для клеток на которые допустим ход фигуры.
        if (boardPanel.prompted.isNotEmpty()) boardPanel.updateBoard()
    }
}