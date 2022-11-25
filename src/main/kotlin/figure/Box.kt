package figure

import Position
import pt.isel.canvas.Canvas

const val BOXWIDTH = 40
const val BOXHEIGHT = 54
const val BOXSTARTW = 82
const val BOXSTARTH = 217


data class Box(val pos: Position, val canMove: Boolean, val inTarget: Boolean)

fun Box.draw(canvas: Canvas) {
    if (!inTarget) {
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