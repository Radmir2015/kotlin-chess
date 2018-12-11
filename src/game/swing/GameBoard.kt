package game.swing

import game.core.Board
import game.core.Piece
import game.core.PieceColor
import game.core.Square
import game.swing.listeners.IGameListner
import game.swing.listeners.IMouseMoveListener
import game.swing.listeners.MovePiecePromptListener
import java.awt.*
import java.awt.event.MouseEvent
import java.util.*
import javax.swing.JPanel


/**
 *
 */
abstract class GameBoard(var board: Board) : JPanel(BorderLayout()) {
    override fun paint(gc: Graphics) {
        val sw = size.width / board.nV
        val sh = size.height / board.nH

        drawBack(gc)

        for (v in 0..board.nV - 1)
            for (h in 0..board.nH - 1)
                drawSquare(gc, v, h, sw, sh)

        for (v in 0..board.nV - 1)
            for (h in 0..board.nH - 1)
                drawPiece(gc, v, h, sw, sh)
    }

    private fun drawPiece(gc: Graphics, v: Int, h: Int, sw: Int, sh: Int) {
        val piece = board.getSquare(v, h).piece ?: return

        val sx = v * sw
        val sy = h * sh

        val image = getImage(piece)
        gc.drawImage(image, sx, sy, sw, sh, null)
    }

    protected abstract fun getImage(piece: Piece): Image?

    protected abstract fun drawBack(gc: Graphics)

    protected abstract fun drawSquare(gc: Graphics, v: Int, h: Int, sw: Int, sh: Int)

    /**
     * @return высота клетки.
     */
    private fun getSquareHeight(): Int = height / board.nH

    /**
     * @return ширина клетки.
     */
    private fun getSquareWidth(): Int = width / board.nV

    // ------------------------------------------------------
    // ------ Обработка событий нажатия на кнопки мыши ------
    // ------------------------------------------------------
    /**
     * Выдать клетку над которой было произошло событие мыши.
     *
     * @param e
     * - событие от мыши.
     * @return клетка под мышкой
     */
    private fun getSquare(e: MouseEvent): Square? {
        val squareW = getSquareWidth()
        val squareH = getSquareHeight()

        val selectedV = e.x / squareW
        val selectedH = e.y / squareH

        return if (!board.onBoard(selectedV, selectedH)) null else board.getSquare(selectedV, selectedH)

    }


    /**
     * Выдать изображение для заданной фигуры клетке доски.<br></br>
     * ** !!! Этот метод должен быть переопределен для игр<br></br>
     * !!! в которых фигуры ставятся на доску. **
     *
     * @param color
     * - цвет фигуры.
     * @return - изображение фигуры.
     */
    fun getPiece(square: Square, color: PieceColor): Piece? {
        return null
    }

    /**
     * Выдать изображение фигуры заданного цвета.
     *
     * @param piece
     * - фигура для которой выдается изображение.
     * @param color
     * - цвет фигуры.
     * @return изображение фигуры.
     */
    abstract fun getPieceImage(piece: Piece, color: PieceColor): Image?


    //
    // Cursor
    //
    var boardCursor = Cursor(Cursor.DEFAULT_CURSOR)

    /**
     * Сделать заданное изображение изображением курсора.
     *
     * @param image
     * - новое изображение курсора.
     */
    fun imageToCursor(image: Image?) {
        val sw = getSquareWidth()
        val sh = getSquareHeight()

        val pw = sw - sw / 8 // Ширина фигуры в клетке.
        val ph = sh - sh / 8 // Высота фигуры в клетке.

//        boardCursor.dispose()

        val toolkit = Toolkit.getDefaultToolkit()
        val point = Point(pw, ph)

        boardCursor = toolkit.createCustomCursor(image, point, "CopyVisualCursor")

//        val imageDate = image.getImageData().scaledTo(pw, ph)


//        val display = Display.getCurrent()

//        boardCursor = Cursor(display, imageDate, sw / 2, sh / 2)
        cursor = boardCursor
    }

    /**
     * Сделать изображение фигуры изображением курсора.
     *
     * @param piece
     * - фигура изображение которой "перемешается" в курсор.
     */
    fun pieceToCursor(piece: Piece) {
        val image = getImage(piece)
        imageToCursor(image)
    }


    /**
     * Слушатель нажатий кнопок мыши над клетками доски.
     */
    protected var listener = IGameListner.EMPTY

    fun mouseDown(e: MouseEvent) {
        val s = getSquare(e)

        if (s != null)
            listener.mouseDown(s, e.button)
    }

    fun mouseUp(e: MouseEvent) {
        val s = getSquare(e)

        if (s != null)
            listener.mouseUp(s, e.button)
    }

    fun mouseDoubleClick(e: MouseEvent) {}

    // ------------------------------------------------
    // ------ Обработка событий перемещения мыши ------
    // ------------------------------------------------

    /**
     * Клетки на которые допустим очередной ход фигурой.
     * Используются для отрисовки на доске подсказок
     * для всех допустимых ходов этой фигуры.
     */
    var prompted: List<Square> = ArrayList()


    /**
     * Слушатель события перемещения мыши.
     */
    protected var mouseMoveListener: IMouseMoveListener = MovePiecePromptListener(this)

    fun mouseMove(e: MouseEvent) {
        val s = getSquare(e)

        if (s != null)
            mouseMoveListener.mouseMove(s)
    }

}