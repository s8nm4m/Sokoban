package figure

import pt.isel.canvas.*

enum class Direction() {
    LEFT,
    RIGHT,
    UP,
    DOWN;
}

/**
 * Selecting the proper direction dependent on the key pressed
 */
fun Int.toDir(): Direction? = Direction.values().find {
    return when (this) {
        LEFT_CODE -> Direction.LEFT
        RIGHT_CODE -> Direction.RIGHT
        DOWN_CODE -> Direction.DOWN
        UP_CODE -> Direction.UP
        else -> null
    }
}