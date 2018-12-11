package notebook.swing.images

import java.awt.Image
import javax.imageio.ImageIO

object NotebookImages {
    val gamesNotebook: Image = ImageIO.read(javaClass.getResource("GamesNotebook.png"))
    val open: Image = ImageIO.read(javaClass.getResource("Open.png"))
}