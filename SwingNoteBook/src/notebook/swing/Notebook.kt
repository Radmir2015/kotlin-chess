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
    val frame = JFrame("Games Notebook")
    frame.iconImage = ImageIcon("images/GamesNotebook.png").image
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    frame.size = Dimension(800, 600)
    frame.layout = BorderLayout()
    frame.isVisible = true
    frame.setLocationRelativeTo(null)

    val tabbedPane = JTabbedPane()
    frame.add(tabbedPane, BorderLayout.CENTER)

    tabbedPane.addGame(Reversi(0))
    tabbedPane.addGame(Chess())
    tabbedPane.addGame(Checkers())
    tabbedPane.addGame(TamerlanChess())
    tabbedPane.addGame(ChinaChess())
    tabbedPane.addGame(Vikings())
    tabbedPane.addGame(Renju())
    tabbedPane.addGame(Go(10))
    tabbedPane.addGame(Halma(10))

//    val halmaPanel = HalmaGamePanel()
//    tabbedPane.addTab("Halma", HalmaImages.icoHalma, halmaPanel)
}

private fun JTabbedPane.addGame(game: Game) {
    val url: URL = game.javaClass.getResource("images/${game.iconImageFile}")
    val iconImage = ImageIO.read(url)
    val icon = ImageIcon(iconImage.getScaledInstance(20, 20, Image.SCALE_DEFAULT))

    addTab(game.name, icon, GamePanel(game))
}