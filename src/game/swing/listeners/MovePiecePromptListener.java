package game.swing.listeners;

import game.core.Board;
import game.core.Piece;
import game.core.Square;
import game.swing.GameBoard;

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
    private GameBoard boardPanel;

    /**
     * Слушатель MovePiecePromptListener определяет клетки, на которые может
     * пойти фигура находящаяся под мышкой.
     *
     * @param boardPanel - панель для отрисовки доски.
     */
    public MovePiecePromptListener(GameBoard boardPanel) {
        this.boardPanel = boardPanel;
    }

    @Override
    public void mouseMove(Square underMouse) {
        boardPanel.getPrompted().clear();

        Piece underMousePiece = underMouse.getPiece();
        if (underMousePiece == null) {
            // Под мышкой фигуры нет, клетка пустая.
            // Перерисуем панель доски без подсказок.
            boardPanel.updateUI();
//			.update();
//			boardPanel.redraw();
            return;
        }

        // Доска на которой расположены фигуры.
        Board board = boardPanel.getBoard();

        for (Square s : board.getSquares())
            if (underMousePiece.isCorrectMove(s))
                boardPanel.getPrompted().add(s);

        // Перерисуем панель доски c маркерами-подсказками
        // для клеток на которые допустим ход фигуры.
        boardPanel.updateUI();
//		boardPanel.update();
//		boardPanel.redraw();
    }
}