package game.core.listeners;

import game.core.Board;
import game.core.IBoardPanel;
import game.core.Piece;
import game.core.Square;

/**
 * Слушатель MovePiecePromptListener определяет клетки, на которые может пойти
 * фигура находящаяся под мышкой.
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class MovePiecePromptListener implements IMouseMoveListener {
    /**
     * Панель, на которой рисуется доска.
     */
    private final IBoardPanel boardPanel;

    /**
     * Слушатель MovePiecePromptListener определяет клетки, на которые может
     * пойти фигура находящаяся под мышкой.
     *
     * @param boardPanel - панель для отрисовки доски.
     */
    public MovePiecePromptListener(IBoardPanel boardPanel) {
        this.boardPanel = boardPanel;
    }

    @Override
    public void mouseMove(Square underMouse) {
        boardPanel.getPrompted().clear();

        Piece underMousePiece = underMouse.getPiece();
        if (underMousePiece == null) {
            // Под мышкой фигуры нет, клетка пустая.
            // Перерисуем панель доски без подсказок.
            boardPanel.updateBoard();
            return;
        }

        // Доска на которой расположены фигуры.
        Board board = boardPanel.getPanelBoard();

        for (Square s : board.getSquares())
            if (underMousePiece.isCorrectMove(s))
                boardPanel.getPrompted().add(s);

        // Перерисуем панель доски c маркерами-подсказками
        // для клеток на которые допустим ход фигуры.
        if (!boardPanel.getPrompted().isEmpty())
            boardPanel.updateBoard();
    }
}