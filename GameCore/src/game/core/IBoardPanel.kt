package game.core

import game.core.listeners.IGameListener
import game.core.listeners.IMouseMoveListener

/**
 * Интерфейс панели изображающей доску для настольной игры.
 * Скрывает особености реализации такой панели с помощью
 * различных пакетов: Swing, SWT, JavaFX, ...
 */
interface IBoardPanel {
    /**
     * Выдать изображаемую доску для игры.
     *
     * @return доска игры изображаемая на этой панели.
     */
    val board: Board

    /**
     * Выдать список клеток для подсказки клеток
     * на которые может пойти выбранная фигура.
     *
     * @return список клеток
     */
    val prompted: MutableList<Square>

    /**
     * Обновить изображение для сделаных изменений на доске.
     */
    fun updateBoard()

    /**
     * Сохранить текущий курсор заменив его на курсор с изображением заданной фигуры.
     *
     * @param selectedPiece заданная фигура
     */
    fun saveCursor(selectedPiece: Piece)

    /**
     * Восстановить сохраненный курсор.
     */
    fun restoreCursor()

    /**
     * Задать изображение курсора как у заданной фигуры.
     *
     * @param piece заданная фигура.
     */
    fun pieceToCursor(piece: Piece)

    /**
     * Получить фигуру заданного цвета стоящую на заданной клетке.
     *
     * @param mouseSquare заданая клетка
     * @param moveColor   заданный цвет
     * @return фигура
     */
    fun getPiece(mouseSquare: Square, moveColor: PieceColor): Piece

    /**
     * Задать слушателя нажатий кнопок мыши.
     *
     * @param listener слушатель
     */
    fun setMouseClickListener(listener: IGameListener)

    /**
     * Задать слушателя перемещения мыши.
     *
     * @param listener слушатель
     */
    fun setMouseMoveListener(listener: IMouseMoveListener)
}