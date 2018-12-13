package game.swing.listeners;

import game.core.*;

import java.util.List;

/**
 * Слушатель для игр в которых фигуры ставятся на доску.
 * Слушатель PutPiecePromptListener определяет клетки на которые можно поставить фигуру.
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class PutPiecePromptListener implements IMouseMoveListener {
    /**
     * Панель на которой отрисовывается доска.
     */
    private IBoardPanel boardPanel;

    /**
     * Создать слушатель событий от перемешения мыши
     * для выдачи подсказки допустимых для хода клеток
     * в играх с постановкой фигуры на доску.
     */
    public PutPiecePromptListener(IBoardPanel boardPanel) {
        this.boardPanel = boardPanel;
    }

    @Override
    public void mouseMove(Square mouseSquare) {
        boardPanel.getPrompted().clear();

        Piece underMousePiece = mouseSquare.getPiece();
        if (underMousePiece != null) {
            // Под мышкой уже есть фигура. Клетка не пустая.
            // Для игр у которых фигуры ставяться на доску,
            // ход на занятую клетку невозможен.
            // Перерисуем панель доски без подсказок.
            boardPanel.updateBoard();
            return;
        }

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

        // Соберем клетки, на которые можно поставить новую фигуру.
        // На этих клетках будут нарисованы маркеры- подсказки игроку.
        List<Square> prompted = boardPanel.getPrompted();

        for (Square s : board.getSquares())
            if (piece.isCorrectMove(s))
                prompted.add(s);

        // Перерисуем панель доски c подсказками для тех
        // клеток на которые допустима постановка фигуры.
        boardPanel.updateBoard();
    }
}