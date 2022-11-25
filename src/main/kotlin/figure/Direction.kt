package figure

import pt.isel.canvas.*

enum class Direction(x: Int, y: Int) {
    LEFT(-1, 0),
    RIGHT(1, 0),
    UP(0, -1),
    DOWN(0, 1)
}

fun Int.toDir(): Direction? = Direction.values().find {
    return when (this) {
        LEFT_CODE -> Direction.LEFT
        RIGHT_CODE -> Direction.RIGHT
        DOWN_CODE -> Direction.DOWN
        UP_CODE -> Direction.UP
        else -> null
    }
}