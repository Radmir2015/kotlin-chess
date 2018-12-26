package vikings.swing.images

import java.awt.Image
import javax.imageio.ImageIO
import javax.swing.ImageIcon

/**
 * Изображения для игры Викинги.
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
object VikingImages {
    private val icoVikings9: Image = ImageIO.read(javaClass.getResource("icoVikings9.png"))
    val icoVikings = ImageIcon(icoVikings9.getScaledInstance(20, 20, Image.SCALE_DEFAULT))

    val imageCyningBlack: Image = ImageIO.read(javaClass.getResource("bСyning.png"))
    val imageVikingBlack: Image = ImageIO.read(javaClass.getResource("bViking.png"))

    val imageCyningWhite: Image = ImageIO.read(javaClass.getResource("wСyning.png"))
    val imageVikingWhite: Image = ImageIO.read(javaClass.getResource("wViking.png"))
}