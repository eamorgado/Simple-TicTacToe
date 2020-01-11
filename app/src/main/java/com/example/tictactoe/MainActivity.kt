package com.example.tictactoe

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun butClick(view: View){
        val button_selected = view as Button
        var cell_id = 0
        when(button_selected.id){
            R.id.button1 -> cell_id = 1
            R.id.button2 -> cell_id = 2
            R.id.button3 -> cell_id = 3
            R.id.button4 -> cell_id = 4
            R.id.button5 -> cell_id = 5
            R.id.button6 -> cell_id = 6
            R.id.button7 -> cell_id = 7
            R.id.button8 -> cell_id = 8
            R.id.button9 -> cell_id = 9
        }
        Toast.makeText(this,"" +cell_id, Toast.LENGTH_LONG).show()
        playGame(cell_id,button_selected)
    }

    var played = ArrayList<Int>() //Will store played cells
    var current = if(Math.random() >= 0) 1 else 2 //find initial player randomly
    var grid = Array(3) {Array(3){-1}} //3 by 3 array all at -1 initially
    var r1 = arrayOf(1,2,3)
    var winner = -1
    var game_ended = false

    fun popUp(message: String){
        var alert = AlertDialog.Builder(this)
        alert.setTitle("Alert")
             .setMessage(message)
             .setCancelable(false)
             .setPositiveButton("Ok",{ _, _ -> Toast.makeText(this, "Ok", Toast.LENGTH_LONG).show()})
             .show()
    }


    private fun playGame(cell_id: Int, button_selected: Button){
        //Check to see if game is running
            if(game_ended){
                popUp("Game has ended")
                return
            }
        //Check to see if cell is occupied
            if(played.contains(cell_id)){
                popUp("Cell Occupied")
                return
            }
        //add cell to played
            played.add(cell_id)
        //update app cell
            button_selected.text = if(current == 1) "X" else "O"
            val color_but = if(current == 1) "#3D48C8" else "#E2372B"
            button_selected.setBackgroundColor(Color.parseColor(color_but))


        var rows = Array(3, {i -> ArrayList<Int>(3)})
        var cols = Array(3, {i -> ArrayList<Int>(3)})
        var i = 1
        for(x in 0 until 3){
            rows[x].addAll(listOf(i, i + 1, i + 2))
            i += 3
        }
        i = 1
        for(x in 0 until 3) cols[x].addAll(listOf(x + 1, x + 1 + 3, x + 1 + 6))

        //popUp("R0: "+ rows[0] + "\nR1: "+rows[1]+"\nR2: "+rows[2])
        //popUp("C0: "+ cols[0] + "\nC1: "+cols[1]+"\nC2: "+cols[2])


        //find col
            val col = if(cell_id in cols[0]) 0 else if(cell_id in cols[1]) 1 else 2
        //find row
            val row = if(cell_id in rows[0]) 0 else if(cell_id in rows[1]) 1 else 2

        //update matrix
            grid[row][col] = current
            //Toast.makeText(this,"Row: " + row +" Col: " + col + " Val: "+grid[row][col],Toast.LENGTH_LONG).show()
        //Update current
            current = if(current == 1) 2 else 1

        //Check to see if game is over
            if(isGameOver()){
                val game_winner = if(winner == 1) "X" else "O"
                popUp(game_winner + " has won the game")
                game_ended = true
            }

        //disable button
            button_selected.isEnabled = false

        var message = ""
        for(i in 0 until 3) for(j in 0 until 3)message += "" + grid[i][j] + " "
        popUp(message)

    }

    private fun isGameOver(): Boolean{
        //horizontal test
            for(i in 0 until 3)
                if(grid[i][0] != -1 && grid[i][0] == grid[i][1] && grid[i][1] == grid[i][2]){winner = grid[i][0]; return true;}

        //vertical test
            for(i in 0 until 3)
                if(grid[0][i] != -1 && grid[0][i] == grid[1][i] && grid[1][i] == grid[2][i]){winner = grid[0][i]; return true;}

        //main diagonal \
            if(grid[0][0] != -1 && grid[0][0] == grid[1][1] && grid[1][1] == grid[2][2]){winner = grid[0][0]; return true;}

        //second diagonal /
            if(grid[2][0] != -1 && grid[2][0] == grid[1][1] && grid[1][1] == grid[2][2]){winner = grid[1][1]; return true;}

        return false;
    }
}
