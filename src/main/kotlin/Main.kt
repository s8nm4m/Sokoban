import figure.Dimension
import figure.Direction
import figure.Man
import pt.isel.canvas.Canvas
import pt.isel.canvas.WHITE
import pt.isel.canvas.onFinish
import pt.isel.canvas.onStart

const val WIDTH = 40
const val HEIGHT = 55

//OBJETIVOS
//empurrar caixa
//mudar de cor em cima da bola
//nao deixar andar dps de acabar o jogo
//correção do bug
//DONE

fun main() {
    val maze = loadMap(level1)
    val manPos = maze.positionOfType(Type.MAN)
    val boxList = maze.positionsOfType(Type.BOX)
    val wallList = maze.positionsOfType(Type.WALL)
    val targetList = maze.positionsOfType(Type.TARGET)
    val man = Man(manPos, Direction.RIGHT, false)
    val board = Canvas(WIDTH * maze.width, HEIGHT * maze.height, WHITE)
    println(maze.width)
    println(maze.height)
    var game = Game(Dimension(WIDTH * maze.width, HEIGHT * maze.height), man, wallList, boxList, targetList)
    onStart {
        game.draw(board)
        board.onKeyPressed { k ->
            if (game.boxes != game.targets) {
                game = game.move(k.code)
                game.draw(board)
            }
        }
    }
    onFinish {
    }
}