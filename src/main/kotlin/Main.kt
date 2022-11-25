import figure.*
import pt.isel.canvas.*

const val WIDTH = 40 * 10
const val HEIGHT = 52 * 10

//OBJETIVOS
//empurrar caixa
//mudar de cor em cima da bola
//nao deixar andar dps de acabar o jogo
//correção do bug
//DONE

fun main() {
    onStart {
        val maze = loadMap(level1)
        val manPos = maze.positionOfType(Type.MAN)
        val boxList = maze.positionsOfType(Type.BOX)
        val wallList = maze.positionsOfType(Type.WALL)
        val targetList = maze.positionsOfType(Type.TARGET)
        val man = Man(manPos, Direction.RIGHT, false)
        val board = Canvas(WIDTH, HEIGHT, WHITE)
        var game = Game(Dimension(WIDTH, HEIGHT), man, wallList, boxList, targetList)
        game.draw(board)
        board.onKeyPressed { k ->
            game = game.move(k.code)
            game.draw(board)
        }
    }
    onFinish { }
}