package game.swing

import game.core.Board
import game.core.History
import game.core.Move
import java.awt.BorderLayout
import java.awt.Color
import java.awt.Cursor
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.util.*
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JScrollPane

/**
 * Журнал для хранения истории ходов игры.
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
class MovesJornal
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

    internal inner class MovesPanel : JPanel(BorderLayout()) {
        fun clear() {
            val length = components.size
            for (i in length - 1 downTo 0) {
//                components[i].dispose()
            }
        }
    }

    /**
     * Класс для представлени текста хода в истории игры.
     *
     * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
     */
    private inner class MoveLabel

    /**
     * Управляющий элемент представляющий ход и сам ход в игре.
     *
     * @param kMove - номер хода.
     * @param move - ход в игре.
     */
    (kMove: Int, private val move: Move) {
        private val label: JLabel = JLabel()

        private var oldCursor: Cursor = Cursor(Cursor.DEFAULT_CURSOR)

        init {
            val mListener = object : MouseListener {
                override fun mouseReleased(e: MouseEvent?) {}
                override fun mouseClicked(e: MouseEvent?) {}

                override fun mouseEntered(e: MouseEvent?) {
                    oldCursor = label.cursor
                    label.cursor = hand
                }

                override fun mouseExited(e: MouseEvent?) {
                    label.cursor = oldCursor
                }

                override fun mousePressed(e: MouseEvent?) {
                    val n = history.getMoveNumber(this@MoveLabel.move)
                    history.toMove(n)
                    history.board.setBoardChanged()
                }
            }
            label.addMouseListener(mListener)

            val isOdd = kMove % 2 == 0
            val number = if (!isOdd) "" else "" + (1 + kMove / 2) + ". "

            label.font = font
            label.text = "" + number + move
            label.repaint()
        }
    }

    init {
        foreground = BLACK_COLOR
        background = PAPER_COLOR

        //
        // Панель для показа игроков партии и авторов программ.
        //
        headerPanel = JLabel("")
        headerPanel.background = HEADER_COLOR
        headerPanel.foreground = BLACK_COLOR
        headerPanel.font = font

        val board = history.board
        setHeaderText(board)
        add(headerPanel, BorderLayout.NORTH)

        //
        // Панель для показа ходов в партии.
        //
        val sc = JScrollPane()
//        sc.setBackgroundImage(GameImages.papiro)
        movesPanel = MovesPanel()
        sc.add(movesPanel)
        add(sc, BorderLayout.CENTER)

        //
        // Панель для выдачи результата игры.
        //
        resultPanel = JLabel("" + history.result)
        resultPanel.font = font
        resultPanel.background = HEADER_COLOR
        resultPanel.foreground = BLACK_COLOR
        add(resultPanel, BorderLayout.SOUTH)

        // Добавляем к доске еще одного обозревателя - панель ходов.
        // При изменении положения фигур на доске или результата партии
        // панель ходов уведомят, что нужно перерисовать историю партии
        // (список ходов партии).
        board.addObserver(this)
    }

    private fun setHeaderText(board: Board) {
        val white = board.whitePlayer.name
        val black = board.blackPlayer.name
        val title = String.format("%s - %s", white, black)

        val aWhite = board.whitePlayer.authorName
        val aBlack = board.blackPlayer.authorName
        val aTitle = String.format("%s против %s", aWhite, aBlack)

        headerPanel.text = title
        headerPanel.toolTipText = aTitle
    }

    override fun update(board: Observable, arg: Any) {
        var nChilds = movesPanel.components.size

        val moves = history.moves

        if (nChilds > moves.size) {
            movesPanel.clear()
            nChilds = 0
        }

        // Добавим новые ходы в отображаемый список ходов.
        for (k in nChilds until moves.size)
            MoveLabel(k, moves[k])

        // Отрисуем в списке темным фоном текущий ход.
        //
        val curMove = history.curMoveNumber
        val children = movesPanel.components

        for (k in children.indices) {
            val color = if (k == curMove) SELECT_COLOR else PAPER_COLOR
            val control = children[k]
            control.background = color
        }
        resultPanel.text = "" + history.result
        setHeaderText(history.board)
    }

    companion object {
        private val hand = Cursor(Cursor.HAND_CURSOR)

        private val BLACK_COLOR = Color.BLACK
        private val PAPER_COLOR = Color(255, 210, 0, 1)
        private val SELECT_COLOR = Color(217, 173, 124, 1)
        private val HEADER_COLOR = Color(217, 173, 124, 1)
    }
}