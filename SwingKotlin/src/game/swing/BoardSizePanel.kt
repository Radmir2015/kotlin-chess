package game.swing

import java.awt.Color
import java.awt.Insets
import javax.swing.BoxLayout
import javax.swing.ButtonGroup
import javax.swing.JPanel
import javax.swing.JRadioButton
import javax.swing.border.LineBorder
import javax.swing.border.TitledBorder


/**
 * Панель выбора размеров доски для игры.
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 *
 * @param gamePanel панель игры.
 * @param sizes допустимые размеры доски.
 */
class BoardSizePanel(gamePanel: GamePanel, sizes: Array<IntArray>)
    : JPanel(true) {

    init {
        layout = BoxLayout(this, BoxLayout.Y_AXIS)
        background = Color.GREEN
        isOpaque = false

        val board = gamePanel.game.board

        val titledBorder = TitledBorder(LineBorder(TITLE_COLOR), "Доска")
        titledBorder.titleColor = TITLE_COLOR
        border = titledBorder

        val group = ButtonGroup()
        for (size in sizes) {
            val nV: Int = size[0]
            val nH: Int = size[1]

            val b = JRadioButton("$nV x $nH")
            b.isOpaque = false
            b.isSelected = (nV == board.nV && nH == board.nH)
            b.addActionListener { if (b.isSelected) gamePanel.resizeBoard(nV, nH) }

            group.add(b)
            add(b)
        }
    }

    override fun getInsets(): Insets = Insets(10, 10, 5, 10)

    companion object {
        private val TITLE_COLOR = Color(255, 255, 255, 255)
    }
}