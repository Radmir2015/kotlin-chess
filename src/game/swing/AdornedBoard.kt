package game.swing

import game.swing.images.GameImages
import java.awt.Graphics
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.GridLayout
import javax.swing.JLabel
import javax.swing.JPanel


/**
 * Доска с обозначениями для горизонталей и вертикалей (номер или буква).
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
open class AdornedBoard : JPanel(GridBagLayout()) {
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
            isOpaque = false
            background = null
            resize(n)
        }

        override fun paintComponent(g: Graphics) {
            super.paintComponent(g)
            g.drawImage(GameImages.woodLight, 0, 0, this)
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
                GridLayout(n, 1)
            else
                GridLayout(1, n)

            setLayout(layout)

            for (k in 1..n) {
                val start = if (isInverted) n + 1 else 0
                val delta = if (isInverted) -1 else 1

                val i = start + delta * k
                val text = """ """ + (if (isNumbers) i else alphabet.substring(i - 1, i)) + """ """

                val adorn = JLabel(text)
                adorn.isOpaque = false
                adorn.font = font
                adorn.text = text
                adorn.background = null
                adorn.alignmentX = 0.5f
                adorn.alignmentY = 0.5f
                add(adorn)
            }
        }
    }

    /**
     * Пустое прозрачное поле.
     *
     * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
     */
    private inner class EmptyAdorn : JLabel(" ") {
        init {
            isOpaque = false
            background = null
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
//        removeAll()

        nV = boardPanel.board.nV
        nH = boardPanel.board.nH
        initMainPanel(boardPanel)
    }

    /**
     * @param adornedControl
     */
    private fun initMainPanel(adornedControl: GameBoard) {
//        // Сетевая планировка доски 3х3 ячейки.

        var c: GridBagConstraints?

        //
        // 1-я строка сетки.
        //
        c = GridBagConstraints()
        c.gridx = 0
        c.gridy = 0
        c.anchor = GridBagConstraints.NORTH
        c.fill = GridBagConstraints.NONE
        c.weightx = 0.0
        c.weighty = 0.0
        add(EmptyAdorn(), c)

        c = GridBagConstraints()
        c.gridx = 1
        c.gridy = 0
        c.anchor = GridBagConstraints.NORTH
        c.fill = GridBagConstraints.HORIZONTAL
        c.weightx = 0.0
        c.weighty = 0.0

        val topAdorns = BoardAdorns(8, false, false, false)
        topAdorns.isOpaque = false
        add(topAdorns, c)

        //
        // 2-я строка сетки.
        //
        c = GridBagConstraints()
        c.gridx = 0
        c.gridy = 1
        c.anchor = GridBagConstraints.EAST
        c.fill = GridBagConstraints.VERTICAL
        c.weightx = 0.0
        c.weighty = 0.0

        val leftAdorns = BoardAdorns(8, true, false, true)
        add(leftAdorns, c)

        c = GridBagConstraints()
        c.gridx = 1
        c.gridy = 1
        c.anchor = GridBagConstraints.CENTER
        c.fill = GridBagConstraints.BOTH
        c.weightx = 1.0
        c.weighty = 1.0

        adornedControl.isOpaque = false
        add(adornedControl, c)

        c = GridBagConstraints()
        c.gridx = 2
        c.gridy = 1
        c.anchor = GridBagConstraints.WEST
        c.fill = GridBagConstraints.VERTICAL
        c.weightx = 0.0
        c.weighty = 0.0

        val rightAdorns = BoardAdorns(8, true, false, true)
        add(rightAdorns, c)

        //
        // 3-я строка сетки.
        //
        c = GridBagConstraints()
        c.gridx = 1
        c.gridy = 2
        c.anchor = GridBagConstraints.SOUTH
        c.fill = GridBagConstraints.HORIZONTAL
        c.weightx = 1.0
        c.weighty = 0.0

        val bottomAdorns = BoardAdorns(8, false, false, false)
        bottomAdorns.isOpaque = false
        add(bottomAdorns, c)
//        add(EmptyAdorn())
//        add(BoardAdorns(nV, false, false, false))
//        add(EmptyAdorn())
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