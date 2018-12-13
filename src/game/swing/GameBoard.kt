package game.swing

import game.core.*
import game.core.moves.CompositeMove
import game.core.moves.ICaptureMove
import game.core.moves.IPutMove
import game.core.moves.ITransferMove
import game.swing.listeners.IGameListner
import game.swing.listeners.IMouseMoveListener
import game.swing.listeners.MovePiecePromptListener
import java.awt.*
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.awt.event.MouseMotionListener
import java.util.*
import javax.swing.JPanel

/**
 *
 */
abstract class GameBoard(val board: Board) : JPanel(BorderLayout()),
        MouseListener, MouseMotionListener, IBoardPanel {
    init {
        this.addMouseListener(this)
        this.addMouseMotionListener(this)
    }

    override fun getPanelBoard(): Board {
        return board
    }

    override fun updateBoard() {
        validate()
        repaint()
    }

    override fun paint(gc: Graphics) {
        val sw = width / board.nV
        val sh = height / board.nH

        drawBack(gc)

        for (v in 0..board.nV - 1)
            for (h in 0..board.nH - 1)
                drawSquare(gc, v, h, sw, sh)

        markLastTransferMove(gc)

        for (v in 0..board.nV - 1)
            for (h in 0..board.nH - 1)
                drawPiece(gc, v, h, sw, sh)

        markLastPutMove(gc)

        drawSquaresPrompt(gc, getSquareHeight(), getSquareHeight())
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

    /**
     * Цвет для отрисовки последнего хода.
     */
    private val lastMoveColor = Color.RED

    /**
     * Цвет для отрисовки захвата фигур.
     */
    private val lastCaptureColor = Color.BLUE

    /**
     * Цвет для подсказок правильных ходов.
     */
    private val promptColor = Color.GREEN

    /**
     * Нарисовать подсказку для клеток на которые фигура может сделать очередной ход.
     *
     * @param gc
     * - графический контекст для отрисовки подсказки.
     * @param sw
     * - ширина клетки.
     * @param sh
     * - высота клетки.
     */
    fun drawSquaresPrompt(gc: Graphics, sw: Int, sh: Int) {
        if (prompted.isEmpty())
            return

//        gc.setLineWidth(3) TODO ширина линии
        gc.color = promptColor

        for (s in prompted)
            markSquare(gc, s, promptColor)
    }

    /**
     * Пометить клетку цветным маркером.
     *
     * @param gc
     * - графический контекст.
     * @param square
     * - помечаемая клетка.
     * @param markColor
     * - цвет маркера.
     */
    fun markSquare(gc: Graphics, square: Square, markColor: Color) {
        val v = square.v
        val h = square.h
        val sw = getSquareWidth()
        val sh = getSquareHeight()

        val d = 10
        gc.color = markColor
        gc.fillOval(v * sw + (sw - d) / 2, h * sh + (sh - d) / 2, d, d)
    }

    /**
     * Соединить линией центры двух клеток.
     *
     * @param gc
     * - графический контекст.
     * @param source
     * - откуда линия.
     * @param target
     * - куда линия.
     * @param color
     * - цвет линии.
     */
    fun markLine(gc: Graphics, source: Square, target: Square, color: Color) {
        val sw = getSquareWidth()
        val sh = getSquareHeight()

        val v1 = sw * source.v + sw / 2
        val h1 = sh * source.h + sh / 2

        val v2 = sw * target.v + sw / 2
        val h2 = sh * target.h + sh / 2

        gc.color = color
        gc.drawLine(v1, h1, v2, h2)
    }

    /**
     * Нарисовать на клетке перекрестье.
     *
     * @param gc
     * - графический контекст.
     * @param where
     * - откуда линия.
     * @param color
     * - цвет линии.
     */
    fun markCross(gc: Graphics, where: Square, color: Color) {
        val sw = getSquareWidth()
        val sh = getSquareHeight()

        val v1 = sw * where.v
        val h1 = sh * where.h

        val v2 = sw * where.v + sw
        val h2 = sh * where.h + sh

        gc.color = color
        gc.drawLine(v1, h1, v2, h2)
        gc.drawLine(v2, h1, v1, h2)
    }

    /**
     * Пометить две клетки рамками заданного цвета.
     *
     * @param gc
     * - графический конеткст для рисования.
     * @param source
     * - клетка откуда идет фигура.
     * @param target
     * - клетка куда идет фигура.
     * @param color
     * - цвет рамки.
     */
    fun markSquares(gc: Graphics, source: Square, target: Square, color: Color) {
        val sw = getSquareWidth()
        val sh = getSquareHeight()

        val v1 = sw * source.v + sw / 2
        val h1 = sh * source.h + sh / 2

        val v2 = sw * target.v + sw / 2
        val h2 = sh * target.h + sh / 2

        gc.color = color
        gc.drawRect(v1 * sw, h1 * sh, sw, sh)
        gc.drawRect(v2 * sw, h2 * sh, sw, sh)
    }

    /**
     * Пометить на доске маркером последний ход для игр с перемещаемыми
     * фигурами.
     *
     * @param gc
     * - графический контекст для отрисовки маркера.
     */
    protected fun markLastTransferMove(gc: Graphics) {
        val moves = board.history.moves
        if (moves.isEmpty()) return

        val move = board.history.curMove as Move

        if (move is CompositeMove<*>) {
            val cm = move
            for (m in cm.moves)
                markLastTransferMove(gc, m)
        }

        if (move is ITransferMove) {
            markLastTransferMove(gc, move)
        }
    }

    private fun markLastTransferMove(gc: Graphics, m: ITransferMove) {
        val source = m.source
        val target = m.target

//        gc.l.setLineWidth(3) TODO Line Width
        markLine(gc, source, target, lastMoveColor)

        if (m is ICaptureMove) {
            val capture = m as ICaptureMove

            for (s in capture.captured)
                markCross(gc, s, lastMoveColor)
        }
    }

    /**
     * Пометить на доске маркером последний ход для игр с фигурами которые
     * ставятся на доску.
     *
     * @param gc
     * - графический контекст для отрисовки маркера.
     */
    private fun markLastPutMove(gc: Graphics) {
        val moves = board.history.moves
        if (moves.isEmpty()) return

        val move = board.history.curMove as Move

        if (move is IPutMove) {
            val target = move.target

//            gc.setLineWidth(3) TODO Line Width
            markSquare(gc, target, lastMoveColor)

            if (move is ICaptureMove) {
                val capture = move as ICaptureMove

                for (s in capture.captured)
                    markSquare(gc, s, lastCaptureColor)
            }
        }
    }

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
    private fun getSquare(e: MouseEvent?): Square? {
        if (e == null) return null

        val squareW = getSquareWidth()
        val squareH = getSquareHeight()

        val selectedV = e.x / squareW
        val selectedH = e.y / squareH

        return if (!board.onBoard(selectedV, selectedH)) null else board.getSquare(selectedV, selectedH)
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
     * Сохраненный курсор.
     */
    private var savedCursor: Cursor? = null

    /**
     * Сохранить текущий курсор заменив его на курсор с изображением заданной фигуры.
     */
    override fun saveCursor(piece: Piece) {
        // Сохраним курсор для его восстановления
        // после перемещения фигуры мышкой.
        savedCursor = getCursor()

        // Зададим изображение курсора такое как избражение у фигуры.
        pieceToCursor(piece)
    }

    /**
     * Восстановить сохраненный курсор
     */
    override fun restoreCursor() {
        setCursor(savedCursor)
    }

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
    override fun pieceToCursor(piece: Piece) {
        val image = getImage(piece)
        imageToCursor(image)
    }

    /**
     * Слушатель нажатий кнопок мыши над клетками доски.
     */
    protected var listener: IGameListner = IGameListner.EMPTY

    //
    // Реализация интерфейса MouseListener
    //

    override fun mouseClicked(e: MouseEvent?) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun mousePressed(e: MouseEvent?) {
        if (e == null) return

        val s = getSquare(e)

        if (s != null)
            listener.mouseDown(s, e.button)
    }

    override fun mouseReleased(e: MouseEvent?) {
        if (e == null) return

        val s = getSquare(e)

        if (s != null)
            listener.mouseUp(s, e.button)
    }

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

    override fun mouseEntered(e: MouseEvent?) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun mouseExited(e: MouseEvent?) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    // ------------------------------------------------
    // ------ Обработка событий перемещения мыши ------
    // ------------------------------------------------

    /**
     * Клетки на которые допустим очередной ход фигурой.
     * Используются для отрисовки на доске подсказок
     * для всех допустимых ходов этой фигуры.
     */
    private var prompted: List<Square> = ArrayList()

    override fun getPrompted(): List<Square> = prompted

    /**
     * Слушатель события перемещения мыши.
     */
    protected var mouseMoveListener: IMouseMoveListener = MovePiecePromptListener(this)

    //
    // Реализация интерфейса MouseMotionListener
    //

    override fun mouseDragged(e: MouseEvent?) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun mouseMoved(e: MouseEvent?) {
        val s = getSquare(e)

        if (s != null) mouseMoveListener.mouseMove(s)
    }
}