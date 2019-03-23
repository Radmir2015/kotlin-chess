package notebook.swing

import checkers.Checkers
import chess.Chess
import chinachess.ChinaChess
import game.core.Game
import game.swing.GamePanel
import go.Go
import halma.Halma
import renju.Renju
import reversi.Reversi
import tamerlan.TamerlanChess
import vikings.Vikings
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.Image
import java.net.URL
import javax.imageio.ImageIO
import javax.swing.ImageIcon
import javax.swing.JFrame
import javax.swing.JTabbedPane

fun main(args: Array<String>) {
    val tabbedPane = JTabbedPane()

    val frame = JFrame("Games Notebook")
    with(frame) {
        iconImage = ImageIcon("images/GamesNotebook.png").image
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        size = Dimension(800, 600)
        layout = BorderLayout()
        setLocationRelativeTo(null)
        add(tabbedPane, BorderLayout.CENTER)
        isVisible = true
    }

    tabbedPane.addGame(Reversi())
    tabbedPane.addGame(Chess())
    tabbedPane.addGame(Checkers())
    tabbedPane.addGame(TamerlanChess())
    tabbedPane.addGame(ChinaChess())
    tabbedPane.addGame(Renju())
    tabbedPane.addGame(Go())
    tabbedPane.addGame(Vikings())
    tabbedPane.addGame(Halma())
}

private fun JTabbedPane.addGame(game: Game) {
    val url: URL = game.javaClass.getResource("images/${game.iconImageFile}")
    val iconImage = ImageIO.read(url)
    val icon = ImageIcon(iconImage.getScaledInstance(20, 20, Image.SCALE_DEFAULT))

    addTab(game.name, icon, GamePanel(game))
}