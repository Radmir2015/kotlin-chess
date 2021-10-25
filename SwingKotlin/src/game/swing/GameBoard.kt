package game.swing

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import game.core.*
import game.core.listeners.*
import game.core.moves.CompositeMove
import game.core.moves.ICaptureMove
import game.core.moves.IPutMove
import game.core.moves.ITransferMove
import java.awt.*
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.awt.event.MouseMotionListener
import java.util.*
import javax.imageio.ImageIO
import javax.swing.JPanel

/**
 * Панель для изображения доски настольной игры и расположенных на ней фигур.
 */
abstract class GameBoard(val game: Game)
    : JPanel(BorderLayout()), MouseListener, MouseMotionListener, IBoardPanel, Observer {
    /**
     * Изображаемая доска с фигурами.
     */
    final override val board: Board = game.board

    /**
     * Слушатель события нажатия кнопок мыши над клетками доски.
     */
    private var listener: IGameListener = IGameListener.EMPTY

    /**
     * Слушатель события перемещения мыши над клетками доски.
     */
    private var mouseMoveListener: IMouseMoveListener = MovePiecePromptListener(this)

    override fun setMouseClickListener(listener: IGameListener) {
        this.listener = listener
    }

    override fun setMouseMoveListener(listener: IMouseMoveListener) {
        mouseMoveListener = listener
    }

    /**
     * Изображения белых фигур.
     */
    private val whites = HashMap<Class<out Piece>, Image>()

    /**
     * Изображения черных фигур.
     */
    private val blacks = HashMap<Class<out Piece>, Image>()

    init {
        addMouseListener(this)
        addMouseMotionListener(this)

        val ref = FirebaseDatabase.getInstance().getReference("game")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                println(dataSnapshot.child("black").value)
                val startSquare = Square(board, 7, 4)
                val finishSquare = Square(board, 5, 4)
                val selectedPiece = startSquare.getPiece()
                val move = selectedPiece!!.makeMove(startSquare, finishSquare)
                println(startSquare)
                println(finishSquare)
                println(selectedPiece)
                println(move)
                try {
                    move.doMove()
                } catch (e: GameOver) {
                    board.history.addMove(move)
                    board.history.result = e.result

                    board.setBoardChanged()
                    return
                }

                board.history.addMove(move)
                board.changeMoveColor()
                board.setBoardChanged()
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })

        when (game.moveKind) {
            MoveKind.PIECE_MOVE -> {
                listener = MovePieceListener(this)
                mouseMoveListener = MovePiecePromptListener(this)
            }
            MoveKind.PIECE_PUT -> {
                listener = PutPieceListener(this)

                // Слушатель мыши выдающий подсказки для клеток -
                // можно ли ставить фигуру на клетку доски.
                mouseMoveListener = PutPiecePromptListener(this)
            }
//          MoveKind.PIECE_TRACK -> {
//              listener = TrackPieceListener<Capture>(this)
//          }
            else -> {
            }
        }

        val ww: MutableMap<Class<out Piece>, String> = game.getPieceImages(PieceColor.WHITE)

        for ((key, value) in ww) {
            val resource = game.javaClass.getResource("images/$value")
            whites[key] = ImageIO.read(resource)
        }

        val bb: MutableMap<Class<out Piece>, String> = game.getPieceImages(PieceColor.BLACK)

        for ((key, value) in bb)
            blacks[key] = ImageIO.read(game.javaClass.getResource("images/$value"))

        board.addObserver(this)

        addMouseWheelListener {
            val history = board.history

            if (it.wheelRotation < 0) {
                history.toPrevMove()
                board.setBoardChanged()
            }
            if (it.wheelRotation > 0) {
                history.toNextMove()
                board.setBoardChanged()
            }
        }
    }

    override fun updateBoard() {
        validate()
        repaint()
    }

    override fun update(o: Observable?, arg: Any?) {
        updateBoard()
    }

    override fun paint(g: Graphics) {
        val sw = getSquareWidth()
        val sh = getSquareHeight()

        drawBack(g)

        for (v in 0 until board.nV)
            for (h in 0 until board.nH)
                drawSquare(g, v, h, sw, sh)

        markLastTransferMove(g)

        for (v in 0 until board.nV)
            for (h in 0 until board.nH)
                drawPiece(g, v, h, sw, sh)

        markLastPutMove(g, sw, sh)

        drawSquaresPrompt(g, sw, sh)
    }

    private fun drawPiece(g: Graphics, v: Int, h: Int, sw: Int, sh: Int) {
        val piece = board.getSquare(v, h)?.piece ?: return

        val sx = v * sw
        val sy = h * sh

        val image = getImage(piece)
        g.drawImage(image, sx, sy, sw, sh, null)
    }

    /**
     * Выдать изображение фигуры заданного цвета.
     *
     * @param piece фигура для которой выдается изображение.
     * @param color цвет фигуры.
     * @return изображение фигуры.
     */
    open fun getPieceImage(piece: Piece, color: PieceColor): Image? = when (color) {
        PieceColor.WHITE -> whites[piece.javaClass]
        else -> blacks[piece.javaClass]
    }

    open fun getImage(piece: Piece): Image? = getPieceImage(piece, piece.color)

    protected abstract fun drawBack(g: Graphics)

    protected abstract fun drawSquare(g: Graphics, v: Int, h: Int, sw: Int, sh: Int)

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
    private var promptColor: Color = Color.GREEN

    /**
     * Нарисовать подсказку для клеток на которые фигура может сделать очередной ход.
     *
     * @param g графический контекст для отрисовки подсказки.
     * @param sw ширина клетки.
     * @param sh высота клетки.
     */
    private fun drawSquaresPrompt(g: Graphics, sw: Int, sh: Int) {
        if (prompted.isEmpty())
            return

        g.color = promptColor

        for (s in prompted)
            Markers.markSquare(g, s, sw, sh, promptColor)
    }

    /**
     * Пометить на доске маркером последний ход
     * для игр с перемещаемыми фигурами.
     *
     * @param g графический контекст для отрисовки маркера.
     */
    private fun markLastTransferMove(g: Graphics) {
        val moves = board.history.moves
        if (moves.isEmpty()) return

        val curMove = board.history.curMove ?: return

        if (curMove is CompositeMove<*>) {
            for (m in curMove.moves)
                markLastTransferMove(g, m)
        }

        if (curMove is ITransferMove)
            markLastTransferMove(g, curMove)
    }

    private fun markLastTransferMove(g: Graphics, m: ITransferMove) {
        val source = m.source
        val target = m.target

        val sw = getSquareWidth()
        val sh = getSquareHeight()
        Markers.markLine(g, source, target, sw, sh, lastMoveColor)

        if (m is ICaptureMove) {
            val capture = m as ICaptureMove

            for (s in capture.captured)
                Markers.markCross(g, s, sw, sh, lastMoveColor)
        }
    }

    /**
     * Пометить на доске маркером последний ход
     * для игр с фигурами которые ставятся на доску.
     *
     * @param g графический контекст для отрисовки маркера.
     */
    private fun markLastPutMove(g: Graphics, sw: Int, sh: Int) {
        val moves = board.history.moves
        if (moves.isEmpty()) return

        val curMove = board.history.curMove ?: return

        if (curMove is IPutMove) {
            val target = curMove.target

            Markers.markSquare(g, target, sw, sh, lastMoveColor)

            if (curMove is ICaptureMove) {
                val capture = curMove as ICaptureMove

                for (s in capture.captured)
                    Markers.markSquare(g, s, sw, sh, lastCaptureColor)
            }
        }
    }

    // ------------------------------------------------------
    // ------ Обработка событий нажатия на кнопки мыши ------
    // ------------------------------------------------------
    /**
     * Выдать клетку над которой было произошло событие мыши.
     * @param e событие от мыши.
     * @return клетка под мышкой или null
     */
    private fun getSquare(e: MouseEvent?): Square? {
        e ?: return null

        val squareW = getSquareWidth()
        val squareH = getSquareHeight()

        val selectedV = e.x / squareW
        val selectedH = e.y / squareH

        if (!board.onBoard(selectedV, selectedH))
            return null

        return board.getSquare(selectedV, selectedH)
    }

    // --------------------------------------
    // --------------- Cursor ---------------
    // --------------------------------------
    private var boardCursor = Cursor(Cursor.DEFAULT_CURSOR)

    /**
     * Сохраненный курсор.
     */
    private var savedCursor: Cursor? = null

    /**
     * Сохранить текущий курсор заменив его на курсор с изображением заданной фигуры.
     */
    override fun saveCursor(selectedPiece: Piece) {
        // Сохраним курсор для его восстановления
        // после перемещения фигуры мышкой.
        savedCursor = cursor

        // Зададим изображение курсора такое же
        // как избражение у перемещаемой фигуры.
        pieceToCursor(selectedPiece)
    }

    /**
     * Восстановить сохраненный курсор
     */
    override fun restoreCursor() {
        cursor = savedCursor
    }

    /**
     * Сделать заданное изображение изображением курсора.
     *
     * @param image новое изображение курсора.
     */
    private fun imageToCursor(image: Image) {
        val sw = getSquareWidth()
        val sh = getSquareHeight()

        val toolkit = Toolkit.getDefaultToolkit()
        val cursorImage: Image = image // image!!.getScaledInstance(sw, sh, Image.SCALE_DEFAULT)
        val hotSpot = Point(sw / 2, sh / 2)
        cursor = toolkit.createCustomCursor(cursorImage, hotSpot, "customCursor")
    }

    /**
     * Сделать изображение фигуры изображением курсора.
     *
     * @param piece фигура изображение которой "перемешается" в курсор.
     */
    override fun pieceToCursor(piece: Piece): Unit = getImage(piece)?.let { imageToCursor(it) }!!

    //
    // Реализация интерфейса MouseListener
    //

    override fun mouseClicked(e: MouseEvent?) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun mousePressed(e: MouseEvent?) {
        e ?: return

        val s = getSquare(e) ?: return

        listener.mouseDown(s, e.button)
    }

    override fun mouseReleased(e: MouseEvent?) {
        e ?: return

        val s = getSquare(e) ?: return

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
    override var prompted: MutableList<Square> = ArrayList()

    //
    // Реализация интерфейса MouseMotionListener
    //

    override fun mouseDragged(e: MouseEvent?) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun mouseMoved(e: MouseEvent?) {
        e ?: return

        val s = getSquare(e) ?: return

        mouseMoveListener.mouseMove(s)
    }
}