package game.swing

import java.awt.BorderLayout
import java.awt.GridLayout
import javax.swing.JLabel
import javax.swing.JPanel

/**
 * Доска с обозначениями для горизонталей и вертикалей (номер или буква).
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
open class AdornedBoard : JPanel(GridLayout(3, 3)) {
    private var nV: Int = 8
    private var nH: Int = 8

    private var top: BoardAdorns = BoardAdorns(nV, false, false, false)
    private var left: BoardAdorns = BoardAdorns(nH, true, false, true)
    private var right: BoardAdorns = BoardAdorns(nH, true, false, true)
    private var bottom: BoardAdorns = BoardAdorns(nV, false, false, false)

    /**
     * Нумерация вертикалей и горизонталей доски.
     *
     * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
     */
    private inner class BoardAdorns
    /**
     * Поле с обозначениями (номер или буква)
     * для горизонталей и вертикалей доски
     *
     * @param n
     * сколько колонок или строк
     * @param isVertical
     * вертикально или горизотельно расположены надписи?
     * @param isInverted
     * в порядке убывания или возрастания идут обозначения?
     * @param isNumbers
     * обозначения цифры или буквы?
     */
    (n: Int, private val isVertical: Boolean,
     private val isInverted: Boolean, private val isNumbers: Boolean) : JPanel(true) {

        init {
            resize(n)
        }

        /**
         * Добавить к доске с новыми размерами номера горизонталей
         * или имена вертикалей (буквы).
         *
         * @param n
         * - новые размеры доски.
         */
        fun resize(n: Int) {
            removeAll()

            val layout = if (isVertical)
                GridLayout(1, n)
            else
                GridLayout(n, 1)

            setLayout(layout)

            for (k in 1..n) {
                val start = if (this.isInverted) n + 1 else 0
                val delta = if (this.isInverted) -1 else 1

                val i = start + delta * k
                val text = "" + if (this.isNumbers) i else alphabet.substring(i - 1, i)

                val adorn = JLabel(text)
                adorn.font = font
                adorn.text = text
                adorn.background = null
            }
        }
    }

    /**
     * Пустое прозрачное поле.
     *
     * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
     */
    private inner class EmptyAdorn : JPanel(BorderLayout()) {
        init {
//            setLayout(FillLayout(SWT.HORIZONTAL))
        }
    }

    /**
     * Встроить доску с клетками в доску с нумераций
     * вертикалей и горизонталей доски.
     *
     * @param boardPanel
     * - встраиваемая доска.
     */
    fun insertSquares(boardPanel: GameBoard) {
        nV = boardPanel.board.nV
        nH = boardPanel.board.nH

        val layout = GridLayout(2, 2)
        layout.hgap = 0
        layout.vgap = 0

        removeAll()
        initMainPanel(boardPanel)
    }

    /**
     * @param adornedControl
     */
    private fun initMainPanel(adornedControl: GameBoard) {
        // Сетевая планировка доски 3х3 ячейки.
        //
        // 1-я строка сетки.
        //
        add(EmptyAdorn())
        add(BoardAdorns(nV, false, false, false))
        add(EmptyAdorn())

        //
        // 2-я строка сетки.
        //
        add(BoardAdorns(nH, true, false, true))
        add(adornedControl)
        add(BoardAdorns(nH, true, false, true))

        //
        // 3-я строка сетки.
        //
        add(EmptyAdorn())
        add(BoardAdorns(nV, false, false, false))
        add(EmptyAdorn())
    }

    /**
     * Изменить надписи на доске на нового размера доски: <br></br>
     * номера горизонталей и имена вертикалей.
     *
     * @param nV
     * - количество вертикалей.
     * @param nH
     * - количество горизонталей.
     */
    fun resizeBoard(nV: Int, nH: Int) {
        left.resize(nH)
        right.resize(nH)

        top.resize(nV)
        bottom.resize(nV)
    }

    companion object {
//        private val fontSmall = Font(Display.getCurrent(), "mono", 10, SWT.BOLD)
//        private val fontLarge = Font(Display.getCurrent(), "mono", 12, SWT.BOLD)

//        private var font = fontSmall

        private const val alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    }
}