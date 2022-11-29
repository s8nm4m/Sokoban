package figure

import Position
import pt.isel.canvas.Canvas

const val PUSHIMG = 4
const val STILLIMG = 1
const val LEFTIMG = 3
const val RIGHTIMG = 1
const val UPIMG = 0
const val DOWNIMG = 2
const val STEPWIDTH = 40
const val STEPHEIGHT = 54

data class Man(val pos: Position, val dir: Direction, val push: Boolean = false)

fun Man.draw(canvas: Canvas, boxes: List<Position>) {
    val standing = when (dir) {
        Direction.LEFT -> LEFTIMG
        Direction.RIGHT -> RIGHTIMG
        Direction.DOWN -> DOWNIMG
        else -> UPIMG
    }
    val pushing = isPushing(boxes)
    canvas.drawImage(
        "soko|${pushing * STEPWIDTH},${standing * STEPHEIGHT+1},${STEPWIDTH},${STEPHEIGHT}",
        pos.col * STEPWIDTH,
        pos.line * STEPHEIGHT,
        STEPWIDTH,
        STEPHEIGHT
    )

}

fun Man.isPushing(boxes: List<Position>): Int {
    val newPos = pos.newPos(dir)
    return if (boxes.contains(newPos)) PUSHIMG else STILLIMG
}

fun Position.newPos(dir: Direction?): Position {
    return when (dir) {
        Direction.LEFT -> Position(col - 1, line)
        Direction.RIGHT -> Position(col + 1, line)
        Direction.UP -> Position(col, line - 1)
        Direction.DOWN -> Position(col, line + 1)
        else -> this
    }
}

fun Man.move(key: Int, walls: List<Position>, boxes: List<Position>): Man {
    val direction = key.toDir()
    val newPos = pos.newPos(direction)
    val newDir = when (direction) {
        Direction.LEFT -> direction
        Direction.RIGHT -> direction
        Direction.UP -> direction
        Direction.DOWN -> direction
        else -> dir
    }
    val aux = newPos.newPos(newDir)
    if (walls.contains(aux) && boxes.contains(newPos)) return copy(dir = newDir)
    return if (walls.contains(newPos)) {
        copy(dir = newDir)
    } else copy(pos = newPos, dir = newDir)
}