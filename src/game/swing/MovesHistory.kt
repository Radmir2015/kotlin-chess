package game.swing

import game.core.Board
import game.core.History
import game.core.Move
import game.swing.images.GameImages
import java.awt.*
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.util.*
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.border.LineBorder

/**
 * Журнал для хранения истории ходов игры.
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
class MovesHistory
/**
 * @param history - история игры.
 */
(
        /**
         * История игры.
         */
        private val history: History) : JPanel(BorderLayout()), Observer {
    /**
     * Панель для имен игроков
     */
    private val headerPanel: JLabel
    private val movesPanel: MovesPanel
    private val resultPanel: JLabel

    internal inner class MovesPanel : JPanel(FlowLayout(FlowLayout.LEFT)) {
        override fun paintComponent(g: Graphics) {
            super.paintComponent(g)
            g.drawImage(GameImages.papiro, 0, 0, this)
        }
    }

    /**
     * Класс для представлени текста хода в истории игры.
     *
     * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
     */
    private inner class MoveLabel
    /**
     * Управляющий элемент представляющий
     * ход в игре и номер этого хода.
     *
     * @param kMove - номер хода.
     * @param move - ход в игре.
     */
    (kMove: Int, private val move: Move) : JLabel() {
        private var oldCursor: Cursor = cursor

        init {
            val mListener = object : MouseListener {
                override fun mouseReleased(e: MouseEvent?) {}
                override fun mouseClicked(e: MouseEvent?) {}

                override fun mouseEntered(e: MouseEvent?) {
                    oldCursor = cursor
                    cursor = hand
                }

                override fun mouseExited(e: MouseEvent?) {
                    cursor = oldCursor
                }

                override fun mousePressed(e: MouseEvent?) {
                    val n = history.getMoveNumber(this@MoveLabel.move)
                    history.toMove(n)
                    history.board.setBoardChanged()
                }
            }
            addMouseListener(mListener)

            val isOdd = kMove % 2 == 0
            val number = if (!isOdd) "" else """${1 + kMove / 2}. """

            horizontalAlignment = JLabel.LEFT
            text = """$number$move"""
            repaint()
        }
    }

    init {
        val board = history.board

        border = LineBorder(Color.BLACK)

        //
        // Панель для показа игроков партии и авторов программ.
        //
        headerPanel = JLabel()
        setHeaderText(board)

        //
        // Панель для показа ходов в партии.
        //
        movesPanel = MovesPanel()
        movesPanel.background = PAPER_COLOR
        add(JScrollPane(movesPanel), BorderLayout.CENTER)

        //
        // Панель для выдачи результата игры.
        //
        resultPanel = JLabel()
        setResultText(board)

        add(headerPanel, BorderLayout.NORTH)
        add(resultPanel, BorderLayout.SOUTH)
        add(movesPanel, BorderLayout.CENTER)

        // Добавляем к доске обозревателя - панель ходов.
        // При изменении положения фигур на доске или результата партии
        // панель ходов уведомят, что нужно перерисовать историю партии
        // (список ходов партии).
        board.addObserver(this)
    }

    /**
     * Покажен имена игроков (программы или человека)
     * и имена авторов программы как всплывающей подсказки.
     */
    private fun setHeaderText(board: Board) {
        val white = board.whitePlayer.name
        val black = board.blackPlayer.name
        val title = " $white - $black "

        val aWhite = board.whitePlayer.authorName
        val aBlack = board.blackPlayer.authorName
        val aTitle = "$aWhite против $aBlack"

        headerPanel.horizontalAlignment = JLabel.CENTER
        headerPanel.text = title
        headerPanel.toolTipText = aTitle
        headerPanel.foreground = HEADER_COLOR
        headerPanel.isOpaque = false
    }

    private fun setResultText(board: Board) {
        val white = board.whitePlayer.name
        val black = board.blackPlayer.name
        val text = """${history.result}"""
        val title = " $white - $black $text"

        resultPanel.text = text
        resultPanel.toolTipText = title
        resultPanel.isOpaque = false
        resultPanel.foreground = RESULT_COLOR
        resultPanel.horizontalAlignment = JLabel.CENTER
    }

    override fun update(board: Observable, arg: Any?) {
        var nComponent = movesPanel.components.size

        val moves = history.moves

        if (nComponent > moves.size) {
            movesPanel.removeAll()
            nComponent = 0
        }

        // Добавим новые ходы в отображаемый список ходов.
        for (k in nComponent until moves.size)
            movesPanel.add(MoveLabel(k, moves[k]))

        paintSelection()

        // Возможно игра закончилась. Покажем результат.
        resultPanel.text = """${history.result}"""

        validate()
        repaint()
    }

    /**
     * Отрисуем в списке ходов темным фоном текущий ход.
     */
    private fun paintSelection() {
        //
        val curMove = history.curMoveNumber
        val children = movesPanel.components

        for (k in children.indices) {
            val color = if (k == curMove) SELECT_COLOR else MOVE_COLOR
            children[k].foreground = color
        }
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        g.drawImage(GameImages.woodDark, 0, 0, width, height, this)
    }

    companion object {
        private val hand = Cursor(Cursor.HAND_CURSOR)

        private val PAPER_COLOR = Color(192, 192, 0, 255)
        private val MOVE_COLOR = Color(0, 0, 255, 255)
        private val SELECT_COLOR = Color(255, 0, 0, 255)
        private val HEADER_COLOR = Color(0, 255, 0, 255)
        private val RESULT_COLOR = Color(255, 0, 0, 255)
    }
}