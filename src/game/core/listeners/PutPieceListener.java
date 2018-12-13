package game.core.listeners;

import game.core.*;

/**
 * Слушатель событий о нажатии кнопок мыши используемых
 * для постановки новой фигуры на доску.
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class PutPieceListener implements IGameListner {
    /**
     * Доска на которой присходят изменения.
     */
    private Board board;

    /**
     * Панель для отрисовки доски.
     */
    private IBoardPanel boardPanel;

    /**
     * Создать слушателя событий от нажатий кнопок мыши
     * используемых для постановки новой фигуры на доску.
     *
     * @param boardPanel - панель доски на которую ставятся фигуры.
     */
    public PutPieceListener(IBoardPanel boardPanel) {
        this.board = boardPanel.getPanelBoard();
        this.boardPanel = boardPanel;
    }

    @Override
    public void mouseUp(Square s, int button) {
    }

    @Override
    public void mouseDown(Square mouseSquare, int button) {
        if (!mouseSquare.isEmpty())
            return;

        // Получим фигуру НЕ стоящую на клетке.
        PieceColor moveColor = board.getMoveColor();
        Piece piece = boardPanel.getPiece(mouseSquare, moveColor);
        if (piece == null)
            return;

        piece.remove();

        if (piece.isCorrectMove(mouseSquare)) return; // На эту клетку ставить нельзя.

        // Постановка фигуры на заданную клетку правильная.
        // Создадим экземпляр хода и выполним его.
        Move move = piece.makeMove(mouseSquare);
        board.history.addMove(move);
        try {
            move.doMove();
        } catch (GameOver e) {
            // Сохраним экземпляр кода и истории партии.
            board.history.addMove(move);
            board.history.setResult(e.result);

            // Пусть слушатели изменений на доске
            // нарисуют новое состояние доски.
            board.setBoardChanged();
            boardPanel.updateBoard();
            return;
        }

        // Зададим изображение курсора такое как избражение у фигуры.
        boardPanel.pieceToCursor(piece);

        // Пусть слушатели изменений на доске
        // нарисуют новое состояние доски.
        board.setBoardChanged();
        boardPanel.updateBoard();

        // Теперь ходить должен противник.
        board.changeMoveColor();
    }
}
