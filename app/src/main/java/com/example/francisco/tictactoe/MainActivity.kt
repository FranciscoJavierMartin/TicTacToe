package com.example.francisco.tictactoe

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() , View.OnClickListener{

    private var cells= mutableMapOf<Int,String>()
    private var isPlayer1=true;
    private var winner:String="";
    private val numCells=9;
    private lateinit var txtResult:TextView;
    private val player1="X";
    private val player2="O";
    private var btns= arrayOfNulls<Button>(numCells)
    private val combinations:Array<IntArray> = arrayOf(
            intArrayOf(0,1,2),
            intArrayOf(3,4,5),
            intArrayOf(6,7,8),
            intArrayOf(0,3,6),
            intArrayOf(1,4,7),
            intArrayOf(2,5,8),
            intArrayOf(0,4,8),
            intArrayOf(2,4,6)
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtResult=findViewById(R.id.txtResult)

        for(i in 1..numCells){
            var button=findViewById<Button>(resources.getIdentifier("button$i","id","packagename"))
            button.setOnClickListener(this)
            btns[i-1]=button
        }
    }

    private fun btnSelected(button:Button){
        var index=0;

        when(button.id){
            R.id.button1->index=0
            R.id.button2->index=1
            R.id.button3->index=2
            R.id.button4->index=3
            R.id.button5->index=4
            R.id.button6->index=5
            R.id.button7->index=6
            R.id.button8->index=7
            R.id.button9->index=8
        }

        playGame(index,button)
        checkWinner()
        update()
    }

    private fun checkWinner(){
        if(cells.isNotEmpty()){
            for(combination in combinations){
                var(a,b,c)=combination

                if(cells[a]!=null && cells[a]==cells[b] && cells[a]==cells[c]){
                    this.winner=cells[a].toString()
                }
            }
        }
    }

    private fun update(){

        when{
            winner.isNotEmpty()->{
                txtResult.text=resources.getString(R.string.winner,winner)
                txtResult.setTextColor(Color.GREEN)
            }

            cells.size==numCells->{
                txtResult.text=resources.getString(R.string.draw)
            }

            else->{
                txtResult.text=resources.getString(R.string.next_player,if(isPlayer1) player1 else player2)
            }
        }
    }

    private fun playGame(index:Int,button: Button){

        if(!winner.isNullOrEmpty()){
            Toast.makeText(this,R.string.finalized_game,Toast.LENGTH_LONG).show()
        }else{
            when{
                isPlayer1->cells[index]=player1
                else->cells[index]=player2
            }

            button.text=cells[index]
            button.isEnabled=false
            isPlayer1=!isPlayer1

        }
    }

    fun resertButton(){

        for(i in 1..numCells){
            var button=btns[i-1]
            button?.text=""
            button?.isEnabled=true
        }
    }

    fun newGame(){
        cells= mutableMapOf()
        isPlayer1=true
        winner=""
        txtResult.text=resources.getString(R.string.next_player,player1)
        txtResult.setTextColor(Color.BLACK)
    }

    fun reset(view: View){

    }

    override fun onClick(v: View?) {
        btnSelected(v as Button)
    }
}
