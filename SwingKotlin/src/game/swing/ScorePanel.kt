package game.swing

import game.core.Game
import game.core.PieceColor
import java.awt.Color
import java.awt.Component
import java.util.*
import javax.swing.BoxLayout
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.border.LineBorder
import javax.swing.border.TitledBorder

/**
 * Панель для демонстрации текущего счета в игре.
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
class ScorePanel(private val game: Game) : JPanel(true), Observer {
    private val whiteScore: JLabel
    private val blackScore: JLabel

    private var wScore = 0
    private var bScore = 0

    init {
        wScore = game.getScore(PieceColor.WHITE)
        bScore = game.getScore(PieceColor.BLACK)

        layout = BoxLayout(this, BoxLayout.Y_AXIS)
        background = Color.GREEN
        isOpaque = false

        val titledBorder = TitledBorder(LineBorder(TITLE_COLOR), "Счет")
        titledBorder.titleColor = TITLE_COLOR
        border = titledBorder

        whiteScore = JLabel("Белые:	$wScore")
        whiteScore.isOpaque = false
        whiteScore.background = COLOR_WHITE
        whiteScore.foreground = COLOR_TEXT
        whiteScore.alignmentX = Component.LEFT_ALIGNMENT
        add(whiteScore)

        blackScore = JLabel("Черные:	$bScore")
        blackScore.isOpaque = false
        blackScore.background = COLOR_GRAY
        blackScore.foreground = COLOR_TEXT
        blackScore.alignmentX = Component.LEFT_ALIGNMENT
        add(blackScore)

        game.board.addObserver(this)
    }

    override fun update(arg0: Observable, arg1: Any?) {
        wScore = game.getScore(PieceColor.WHITE)
        bScore = game.getScore(PieceColor.BLACK)

        whiteScore.text = "Белые:   $wScore"
        blackScore.text = "Черные:  $bScore"
    }

    companion object {
        private val COLOR_WHITE = Color.WHITE
        private val COLOR_GRAY = Color.GRAY
        private val COLOR_TEXT = Color.BLACK
        private val TITLE_COLOR = Color.WHITE
    }
}