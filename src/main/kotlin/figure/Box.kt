package figure

import Position
import pt.isel.canvas.Canvas

const val BOXWIDTH = 40
const val BOXHEIGHT = 54
const val BOXSTARTW = 81
const val BOXSTARTH = 217


data class Box(val pos: Position)

fun Box.draw(canvas: Canvas, targets:List<Position>) {
    if (!inTarget(targets)) {
        canvas.drawImage(
            "soko|$BOXSTARTW,$BOXSTARTH,$BOXWIDTH,$BOXHEIGHT",
            pos.col * BOXWIDTH, pos.line * BOXHEIGHT, BOXWIDTH,
            BOXHEIGHT
        )
    } else {
        canvas.drawImage(
            "soko|${BOXSTARTW + BOXWIDTH},$BOXSTARTH,$BOXWIDTH,$BOXHEIGHT",
            pos.col * BOXWIDTH, pos.line * BOXHEIGHT, BOXWIDTH,
            BOXHEIGHT
        )
    }
}

fun Box.inTarget(targets: List<Position>): Boolean {
    return targets.contains(this.pos)
}
