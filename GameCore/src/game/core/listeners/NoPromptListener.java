package game.core.listeners;


import game.core.*;

/**
 * Слушатель для игр в которых фигуры ставятся на доску.
 * Слушатель PutPiecePromptListener определяет клетки на которые можно поставить фигуру.
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class NoPromptListener implements IMouseMoveListener {
    /**
     * Панель на которой отрисовывается доска.
     */
    private final IBoardPanel boardPanel;

    public NoPromptListener(IBoardPanel boardPanel) {
        this.boardPanel = boardPanel;
    }

    @Override
    public void mouseMove(Square mouseSquare) {
        // Доска на которой расположены фигуры.
        Board board = boardPanel.getPanelBoard();

        // Получим фигуру НЕ стоящую на клетке.
        PieceColor moveColor = board.getMoveColor();
        Piece piece = boardPanel.getPiece(mouseSquare, moveColor);

        if (piece == null)
            return;

        piece.remove(); // Уберем фигуру с доски.

        // Зададим изображение курсора такое как избражение у фигуры.
        boardPanel.pieceToCursor(piece);
    }
}