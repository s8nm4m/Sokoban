package figure

import Position
import pt.isel.canvas.Canvas

const val BOXSTARTW = 81
const val BOXSTARTH = 217

data class Box(val dim: Dimension, val pos: Position)

/**
 * Drawing the white box if it isn't on target and the brown one if it is
 */
fun Box.draw(canvas: Canvas, targets: List<Position>) {
    val startW = if (!inTarget(targets)) BOXSTARTW else BOXSTARTW + dim.width
    canvas.drawImage(
        "soko|$startW,$BOXSTARTH,${dim.width},${dim.height}",
        pos.col * dim.width,
        pos.line * dim.height,
        dim.width,
        dim.height
    )
}
/**
 * Function to verify if the box is in the target
 */
fun Box.inTarget(targets: List<Position>) = targets.contains(pos)

