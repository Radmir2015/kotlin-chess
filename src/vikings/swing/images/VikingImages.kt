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
    val icoVikings9 = ImageIO.read(javaClass.getResource("icoVikings9.png"))
    val icoVikings11 = ImageIO.read(javaClass.getResource("icoVikings11.png"))

    val icoReversi = ImageIcon(icoVikings9.getScaledInstance(20, 20, Image.SCALE_DEFAULT))


    val imageCyningBlack = ImageIO.read(javaClass.getResource("bСyning.png"))
    val imageVikingBlack = ImageIO.read(javaClass.getResource("bViking.png"))

    val imageCyningWhite = ImageIO.read(javaClass.getResource("wСyning.png"))
    val imageVikingWhite = ImageIO.read(javaClass.getResource("wViking.png"))
}