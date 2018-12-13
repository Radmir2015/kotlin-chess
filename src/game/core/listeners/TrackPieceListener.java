package game.core.listeners;

import game.core.*;
import game.core.moves.CompositeMove;
import game.core.moves.ITrackMove;
import game.core.moves.ITransferMove;

/**
 * Слушатель постановки перемещения фигуры на доске.
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class TrackPieceListener<T extends ITransferMove> implements IGameListner {
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

    private CompositeMove<T> track = null;

    /**
     * Создать слушателя мыши для панели доски на которой перемещяются фигуры.
     */
    public TrackPieceListener(IBoardPanel boardPanel) {
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
        board.setBoardChanged();
        boardPanel.updateBoard();
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

        if (selectedPiece.isCorrectMove(mouseSquare))
            doMove(mouseSquare);

        selectedPiece = null;
        selectedSquare = null;

        // Восстановим курсор (с изображением стрелки).
        boardPanel.restoreCursor();

        // Пусть слушатели изменений на доске
        // нарисуют новое состояние доски.
        board.setBoardChanged();
        boardPanel.updateBoard();
    }

    private void doMove(Square mouseSquare) {
        // Ход на заданную клетку допустим.
        // Создадим экземпляр хода и выполним его.
        @SuppressWarnings("unchecked")
        T move = (T) selectedPiece.makeMove(selectedSquare, mouseSquare);

        if (!(move instanceof ITrackMove)) {
            // Простой ход.
            try {
                move.doMove();
            } catch (GameOver e) {
                // Сохраним экземпляр хода в истории игры.
                board.history.addMove(move);
                board.history.setResult(e.result);

                selectedPiece = null;
                selectedSquare = null;

                // Восстановим курсор (с изображением стрелки).
                boardPanel.restoreCursor();

                // Пусть слушатели изменений на доске
                // нарисуют новое состояние доски.
                board.setBoardChanged();
                boardPanel.updateBoard();
            }

            // Сохраним ход в истории игры.
            board.history.addMove(move);

            // Пусть слушатели изменений на доске
            // нарисуют новое состояние доски.
            board.setBoardChanged();
            //	boardPanel.redraw();
            boardPanel.updateBoard();

            // Теперь ходить должен противник.
            board.changeMoveColor();
        } else {
            // Простой ход фигурой - часть составного хода фигурой
            // (последовательности простых ходов той же фигурой).
            ITrackMove trackMove = (ITrackMove) move;
            if (track == null) {
                // Первый ход в серии ходов.
                track = new CompositeMove<>(move);
            } else if (!track.isAcceptable(mouseSquare))
                // На эту клетку уже ходили.
                // Избегаем хождения фигурой по кругу.
                return;
            else {
                // Добавим простой ход в серию ходов.
                track.undoMove();
                track.addMove(move);
            }

            // Делаем последовательность простых ходов.
            try {
                track.doMove();
            } catch (GameOver e) {
                // Конец игры.
                // Сохраним экземпляр хода в истории игры.
                board.history.addMove(track);
                board.history.setResult(e.result);

                selectedPiece = null;
                selectedSquare = null;

                // Восстановим курсор (с изображением стрелки).
                boardPanel.restoreCursor();

                // Пусть слушатели изменений на доске
                // нарисуют новое состояние доски.
                board.setBoardChanged();
                boardPanel.updateBoard();

            }

            // Пусть слушатели изменений на доске
            // нарисуют новое состояние доски.
            board.setBoardChanged();
            boardPanel.updateBoard();


            if (trackMove.hasNext())
                return;

            //
            // Последний простой ход в последовательности ходов.
            //
            // Сохраним экземпляр хода в истории игры.
            board.history.addMove(track);

            track = null;

            // Теперь ходить должен противник.
            board.changeMoveColor();
        }
    }
}
