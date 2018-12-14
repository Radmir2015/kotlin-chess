package game.core.listeners;

import game.core.*;

/**
 * Слушатель постановки перемещения фигуры на доске.
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class MovePieceListener implements IGameListner {
    /**
     * Выбранная для перемещения фигура.
     */
    private Piece selectedPiece;

    /**
     * Клетка на которой стоит выбранная для перемещений фигура.
     */
    private Square selectedSquare;

    /**
     * Доска на которой происходят изменения.
     */
    private Board board;

    /**
     * Панель на которой рисуется доска.
     */
    private IBoardPanel boardPanel;

    /**
     * Создать слушателя мыши для панели доски на которой перемещяются фигуры.
     */
    public MovePieceListener(IBoardPanel boardPanel) {
        this.board = boardPanel.getPanelBoard();
        this.boardPanel = boardPanel;
    }

    @Override
    public void mouseDown(Square mouseSquare, int button) {
        if (mouseSquare.isEmpty())
            return;

        PieceColor moveColor = board.getMoveColor();

        // Выберем для перемещения фигуру нужного цвета.
        selectedPiece = mouseSquare.getPiece();
        if (selectedPiece.getColor() != moveColor)
            return;

        // На время перемещения фигуры мышкой
        // снимем ее с доски.
        selectedSquare = mouseSquare;
        selectedSquare.removePiece();
        boardPanel.saveCursor(selectedPiece);

        // Перерисуем изображение доски с временно снятой фигурой.
        boardPanel.updateBoard();
        board.setBoardChanged();
    }

    @Override
    public void mouseUp(Square mouseSquare, int button) {
        if (selectedSquare == null)
            return;

        // Возвращаем фигуру на начальную клетку.
        // Теперь знаем куда эта фигура пойдет.
        // Все изменения на доске, связанные с этим ходом,
        // будут сделаны классом реализующим интерфейс Move.
        selectedSquare.setPiece(selectedPiece);

        if (selectedPiece.isCorrectMove(mouseSquare)) {
            // Ход на заданную клетку правильный.
            // Создадим экземпляр хода и выполним его.
            Move move = selectedPiece.makeMove(selectedSquare, mouseSquare);
            try {
                move.doMove();
            } catch (GameOver e) {
                // Сохраним экземпляр хода в истории партии.
                board.history.addMove(move);
                board.history.setResult(e.result);

                selectedPiece = null;
                selectedSquare = null;

                // Восстановим курсор (с изображением стрелки).
                boardPanel.restoreCursor();

                // Пусть слушатели изменений на доске
                // нарисуют новое состояние доски.
                board.setBoardChanged();
                return;
            }

            // Сохраним экземпляр кода и истории партии.
            board.history.addMove(move);

            // TODO Реализовать запрос фигуры для превращения пешки.

            // Теперь ходить должен противник.
            board.changeMoveColor();
            return;
        }

        selectedPiece = null;
        selectedSquare = null;

        // Восстановим курсор (с изображением стрелки).
        boardPanel.restoreCursor();

        // Пусть слушатели изменений на доске
        // нарисуют новое состояние доски.
        board.setBoardChanged();
    }
}