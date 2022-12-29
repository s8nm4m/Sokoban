package figure

import CELL
import Position
import pt.isel.canvas.Canvas

const val BOX_START_W = 81
const val BOX_START_H = 217

data class Box(val dim: Dimension, val pos: Position)

/**
 * Drawing the white box if it isn't on target and the brown one if it is
 */
fun Box.draw(canvas: Canvas, targets: List<Position>, idx: Int) {
    val startW = if (!inTarget(targets)) BOX_START_W else BOX_START_W + CELL.width
    canvas.drawImage(
        "soko|$startW,$BOX_START_H,${CELL.width},${CELL.height}",
        (pos.col - 1 + dim.width / 2) * CELL.width + (idx + 1) / 3 * CELL.width,
        (pos.line - 1 + dim.height / 2) * CELL.height + (idx + 1) / 3 * CELL.height,
        CELL.width,
        CELL.height
    )
}

/**
 * Function to verify if the box is in the target
 */
fun Box.inTarget(targets: List<Position>) = targets.contains(pos)