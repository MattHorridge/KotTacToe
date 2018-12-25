import java.awt.GridLayout
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.*

val buttonArray = Array(3) { Array(3) { GameButton() } }
var currentplayer: Char = 'X'
var PlayerOScore = 0
var PlayerXScore = 0
var GamePanel = JPanel()
var TurnPanel = JPanel()
var SettingsPanel = JPanel()
var PlayerXScoreLabel = JLabel()
var PlayerOScoreLabel = JLabel()
var PlayerXLabel = JLabel()
var PlayerOLabel = JLabel()
var TurnLabel = JLabel()
var NewGameButton = JButton()
var GameOver = false

class GameButton : JButton(){
    var playerType: Char = ' '

    fun resetPlayerType(){
        playerType = ' '
    }

}

class GridButtonListener : ActionListener{

    override fun actionPerformed(e: ActionEvent) {
        var button = e.source as GameButton

        if(button.text.equals(" ") && !GameOver) {
            button.text = currentplayer.toString()
            button.playerType = currentplayer

            if(IsWinner(buttonArray)){
                GameOver = true
                NewGameButton.isVisible = true
                TurnLabel.text = "Player " + currentplayer + " Wins!"
                if(currentplayer == 'X'){
                    PlayerXScore++
                }
                else {
                    PlayerOScore++
                }

            }
            else{
            ChangePlayer()
            }
        }

        if(isBoardFull() && !IsWinner(buttonArray)){
            GameOver = true
            NewGameButton.isVisible = true
            TurnLabel.text = "Draw!"
        }

        UpdateScores()
    }
}


class NewGameButtonListener : ActionListener{
    override fun actionPerformed(e: ActionEvent?) {
        resetBoard()
    }
}

class UI(title: String) : JFrame() {

            init {
                initUI(title)
            }

            private fun initUI(title: String){
                setTitle(title);
                defaultCloseOperation = JFrame.EXIT_ON_CLOSE
                setSize(600, 600)
                layout = GridLayout(3,1)
                isResizable = false
                setLocationRelativeTo(null)
            }
            //Board size 500x500
            fun initBoard() {
                TurnLabel.text = "Player " + currentplayer + " Turn"


                PlayerOLabel.text = "Player O Score: "
                PlayerXLabel.text = "Player X Score: "

                TurnPanel.layout = GridLayout(2, 1)
                TurnPanel.add(TurnLabel)
                TurnPanel.add(NewGameButton)

                SettingsPanel.layout = GridLayout(2,2)
                SettingsPanel.add(PlayerOLabel)
                SettingsPanel.add(PlayerOScoreLabel)
                SettingsPanel.add(PlayerXLabel)
                SettingsPanel.add(PlayerXScoreLabel)

                GamePanel.layout = GridLayout(3,3)
                GamePanel.setSize(500,500)

                NewGameButton.text = "New Game"
                NewGameButton.isVisible = false
                NewGameButton.addActionListener(NewGameButtonListener())

                UpdateScores()
                for (i in 0..2) {
                    for (j in 0..2) {
                         GamePanel.add(buttonArray[i][j])
                         buttonArray[i][j].addActionListener(GridButtonListener())
                       }
                }

                resetBoard()

                this.add(TurnPanel)
                this.add(SettingsPanel)
                this.add(GamePanel)

                pack()
            }
}


fun main(args: Array<String>) {

    //CreateBoard
    val GUIboard = UI("KotTacToe")
    GUIboard.isVisible = true
    GUIboard.initBoard();

 }

fun UpdateScores(){
    PlayerOScoreLabel.text = PlayerOScore.toString()
    PlayerXScoreLabel.text = PlayerXScore.toString()
}


fun resetBoard(){
    for (i in 0..2) {
        for (j in 0..2) {
            buttonArray[i][j].text = " "
            buttonArray[i][j].resetPlayerType()
        }
    }
    NewGameButton.isVisible = false
    GameOver = false
    ChangePlayer()
}



fun isBoardFull(): Boolean{
    var count = 0
    for (i in 0..2) {
        for (j in 0..2) {
            if(!buttonArray[i][j].text.equals(" ")){
               count++
            }
        }
    }
    return count == 9
 }


 fun IsWinner(b: Array<Array<GameButton>>): Boolean {
     return CheckRows(b) || CheckColumns(b) || CheckDiagonals(b)
 }


 fun CheckRows(b: Array<Array<GameButton>>): Boolean {

     for(i  in 0..2){
        if(CheckRowCol(b[i][0], b[i][1], b[i][2])){
            return true
        }
     }
     return false
 }


 fun CheckDiagonals(b: Array<Array<GameButton>>): Boolean {
  return (CheckRowCol(b[0][0], b[1][1], b[2][2] ) || CheckRowCol(b[0][2], b[1][1], b[2][0]))
 }


 fun CheckRowCol(c1: GameButton, c2: GameButton, c3: GameButton ): Boolean {
     return (!c1.playerType.equals(' ') && c1.playerType == c2.playerType && c2.playerType == c3.playerType)
 }

 fun CheckColumns(b: Array<Array<GameButton>>): Boolean {
     for(i in 0..2){
        if(CheckRowCol(b[0][i], b[1][i], b[2][i])){
            return true
        }
     }
     return false
 }


 fun ChangePlayer() {
     currentplayer = if (currentplayer == 'X') {
         'O'
     } else {
         'X'
     }
     TurnLabel.text = "Player " + currentplayer + " Turn"
 }


