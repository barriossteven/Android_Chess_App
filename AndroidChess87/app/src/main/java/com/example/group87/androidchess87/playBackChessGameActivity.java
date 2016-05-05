package com.example.group87.androidchess87;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Random;

public class playBackChessGameActivity extends AppCompatActivity {
    private String gameName;
    playBackChessGame currGame;
    private ImageButton boardButtons[][]; /* [x][y] */
    private String currPlayer;
    private Button undoButton;
    private Button aiButton;
    private Button drawButton;
    private Button nextMoveButton;
    private TextView information;
    private TextView currPlayerText;
    private TextView drawRequestText;
    private Boolean clickActive = false;
    private String prevClick;
    private Boolean canUndo = false;
    private Boolean drawRequest = false;
    private Board undoMove = new Board();
    private Board undoMoveTemp = new Board();
    private Piece killedPiece;
    private MoveValidator moveValidator;
    private String gameLog = "";
    private char promotionChar = 'q';
    private String FILENAME = "tempplacement.txt";
    FileInputStream fis;
    BufferedReader br;
    private Boolean wasJustPromoted = false;
    private Boolean firstTime = true;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_back_chess_game);
        Bundle bundle = getIntent().getExtras();
        gameName = bundle.getString(PlayBackActivity.game_name);
        TextView gameNameView = (TextView)findViewById(R.id.textView2);

        gameNameView.setText(gameName);



        boardButtons = new ImageButton[8][8];
        boardButtons[0][0] = (ImageButton) findViewById(R.id.a8);
        boardButtons[1][0] = (ImageButton) findViewById(R.id.b8);
        boardButtons[2][0] = (ImageButton) findViewById(R.id.c8);
        boardButtons[3][0] = (ImageButton) findViewById(R.id.d8);
        boardButtons[4][0] = (ImageButton) findViewById(R.id.e8);
        boardButtons[5][0] = (ImageButton) findViewById(R.id.f8);
        boardButtons[6][0] = (ImageButton) findViewById(R.id.g8);
        boardButtons[7][0] = (ImageButton) findViewById(R.id.h8);

        boardButtons[0][1] = (ImageButton) findViewById(R.id.a7);
        boardButtons[1][1] = (ImageButton) findViewById(R.id.b7);
        boardButtons[2][1] = (ImageButton) findViewById(R.id.c7);
        boardButtons[3][1] = (ImageButton) findViewById(R.id.d7);
        boardButtons[4][1] = (ImageButton) findViewById(R.id.e7);
        boardButtons[5][1] = (ImageButton) findViewById(R.id.f7);
        boardButtons[6][1] = (ImageButton) findViewById(R.id.g7);
        boardButtons[7][1] = (ImageButton) findViewById(R.id.h7);

        boardButtons[0][2] = (ImageButton) findViewById(R.id.a6);
        boardButtons[1][2] = (ImageButton) findViewById(R.id.b6);
        boardButtons[2][2] = (ImageButton) findViewById(R.id.c6);
        boardButtons[3][2] = (ImageButton) findViewById(R.id.d6);
        boardButtons[4][2] = (ImageButton) findViewById(R.id.e6);
        boardButtons[5][2] = (ImageButton) findViewById(R.id.f6);
        boardButtons[6][2] = (ImageButton) findViewById(R.id.g6);
        boardButtons[7][2] = (ImageButton) findViewById(R.id.h6);

        boardButtons[0][3] = (ImageButton) findViewById(R.id.a5);
        boardButtons[1][3] = (ImageButton) findViewById(R.id.b5);
        boardButtons[2][3] = (ImageButton) findViewById(R.id.c5);
        boardButtons[3][3] = (ImageButton) findViewById(R.id.d5);
        boardButtons[4][3] = (ImageButton) findViewById(R.id.e5);
        boardButtons[5][3] = (ImageButton) findViewById(R.id.f5);
        boardButtons[6][3] = (ImageButton) findViewById(R.id.g5);
        boardButtons[7][3] = (ImageButton) findViewById(R.id.h5);

        boardButtons[0][4] = (ImageButton) findViewById(R.id.a4);
        boardButtons[1][4] = (ImageButton) findViewById(R.id.b4);
        boardButtons[2][4] = (ImageButton) findViewById(R.id.c4);
        boardButtons[3][4] = (ImageButton) findViewById(R.id.d4);
        boardButtons[4][4] = (ImageButton) findViewById(R.id.e4);
        boardButtons[5][4] = (ImageButton) findViewById(R.id.f4);
        boardButtons[6][4] = (ImageButton) findViewById(R.id.g4);
        boardButtons[7][4] = (ImageButton) findViewById(R.id.h4);

        boardButtons[0][5] = (ImageButton) findViewById(R.id.a3);
        boardButtons[1][5] = (ImageButton) findViewById(R.id.b3);
        boardButtons[2][5] = (ImageButton) findViewById(R.id.c3);
        boardButtons[3][5] = (ImageButton) findViewById(R.id.d3);
        boardButtons[4][5] = (ImageButton) findViewById(R.id.e3);
        boardButtons[5][5] = (ImageButton) findViewById(R.id.f3);
        boardButtons[6][5] = (ImageButton) findViewById(R.id.g3);
        boardButtons[7][5] = (ImageButton) findViewById(R.id.h3);

        boardButtons[0][6] = (ImageButton) findViewById(R.id.a2);
        boardButtons[1][6] = (ImageButton) findViewById(R.id.b2);
        boardButtons[2][6] = (ImageButton) findViewById(R.id.c2);
        boardButtons[3][6] = (ImageButton) findViewById(R.id.d2);
        boardButtons[4][6] = (ImageButton) findViewById(R.id.e2);
        boardButtons[5][6] = (ImageButton) findViewById(R.id.f2);
        boardButtons[6][6] = (ImageButton) findViewById(R.id.g2);
        boardButtons[7][6] = (ImageButton) findViewById(R.id.h2);

        boardButtons[0][7] = (ImageButton) findViewById(R.id.a1);
        boardButtons[1][7] = (ImageButton) findViewById(R.id.b1);
        boardButtons[2][7] = (ImageButton) findViewById(R.id.c1);
        boardButtons[3][7] = (ImageButton) findViewById(R.id.d1);
        boardButtons[4][7] = (ImageButton) findViewById(R.id.e1);
        boardButtons[5][7] = (ImageButton) findViewById(R.id.f1);
        boardButtons[6][7] = (ImageButton) findViewById(R.id.g1);
        boardButtons[7][7] = (ImageButton) findViewById(R.id.h1);


        currGame = new playBackChessGame();
        moveValidator = new MoveValidator();
        currPlayer = "white";


        information = (TextView) findViewById(R.id.information);
        nextMoveButton = (Button) findViewById(R.id.nextButton);
        nextMoveButton.setOnClickListener(new nextMoveButtonClickListener());
        try {
            createScanner();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        boardButtons[0][0].setBackgroundResource(R.drawable.rookblack);
        startNewGame();
        printBoard();

    }

    public void recreateGame(){

        currGame.restartGame();
        clickActive = false;
        canUndo = false;
        drawRequest = false;
        undoMove = new Board();
        undoMoveTemp = new Board();
        promotionChar = 'q';
        wasJustPromoted = false;
        firstTime = true;
        information.setText("");
    }
        private void startNewGame()
        {
            recreateGame();
//        currGame.clearBoard();

            for (int i = 0; i < 8; i++)
            {
                for (int j = 0; j < 8; j++)
                {
                    String location = "";
                    switch (i) {
                        case 0:
                            location = "a" + (8-j);
                            break;
                        case 1:
                            location = "b" + (8-j);
                            break;
                        case 2:
                            location = "c" + (8-j);
                            break;
                        case 3:
                            location = "d" + (8-j);
                            break;
                        case 4:
                            location = "e" + (8-j);
                            break;
                        case 5:
                            location = "f" + (8-j);
                            break;
                        case 6:
                            location = "g" + (8-j);
                            break;
                        case 7:
                            location = "h" + (8-j);
                            break;
                    }
//                boardButtons[i][j].setText("");
                    boardButtons[i][j].setEnabled(true);
                    //boardButtons[i][j].setOnClickListener(new ButtonClickListener(location));
                }
            }

//            currPlayerText.setText("White");
        }

        private class ButtonClickListener implements View.OnClickListener
        {
            String location;
            public ButtonClickListener(String location)
            {
                this.location = location;
            }
            public void onClick(View view) {
//            view.getId();
                if(clickActive)
                {
                    storeUndo();
                    currGame.readInput(prevClick+ " " +location);
                    //check if move is legal
                    //deselect all clicks
                    clickActive = false;
                    prevClick = null;
                    printBoard();
                    if(currGame.validInput)
                    {
                        if(currGame.askforDraw){
                            if(currGame.whiteTurn){
                                information.setText("Black asked for draw");
                            }else {
                                information.setText("White asked for draw");
                            }
                        }
                        else if(currGame.acceptDraw){
                            information.setText("Accepted Draw");
                        }
                        for (int i = 0; i < 8; i++) {
                            for (int k = 0; k < 8; k++) {
                                undoMove.board[i][k] = undoMoveTemp.board[i][k];
                            }
                        }
                        canUndo = true;
                        information.setText("");
                    }
                    if(!currGame.gameIsActive) {
                        information.setText("Checkmate, winner is "+currGame.winner);
                    }
                } else {
                    //highlight the click
                    clickActive = true;
                    prevClick = location;
                    view.setBackgroundColor(444444);
                    //set prevClick string
                }
            }
        }

        private void printBoard(){
            if(currGame.checkFlag){
                //print check
            }
            if(currGame.whiteTurn) {
                information.setText("Current Move: White");
            } else {
                information.setText("Current Move: Black");
            }
            for (int i = 0; i < 8; i++) {
                for (int k = 0; k < 8; k++) {
                    if ((i + k) % 2 == 0) {
                        boardButtons[i][k].setBackgroundColor(Color.parseColor("#EEE8AA"));
                    } else {
                        boardButtons[i][k].setBackgroundColor(Color.parseColor("#BDB76B"));
                    }
                    if(currGame.board.board[i][k] != null){
                        switch (currGame.board.board[i][k].pieceSymbol) {
                            case "wK":
                                boardButtons[k][i].setImageResource(R.drawable.kingwhite);
                                break;
                            case "wQ":
                                boardButtons[k][i].setImageResource(R.drawable.queenwhite);
                                break;
                            case "wR":
                                boardButtons[k][i].setImageResource(R.drawable.rookwhite);
                                break;
                            case "wB":
                                boardButtons[k][i].setImageResource(R.drawable.bishopwhite);
                                break;
                            case "wN":
                                boardButtons[k][i].setImageResource(R.drawable.knightwhite);
                                break;
                            case "wp":
                                boardButtons[k][i].setImageResource(R.drawable.pawnwhite);
                                break;
                            case "bK":
                                boardButtons[k][i].setImageResource(R.drawable.kingblack);
                                break;
                            case "bQ":
                                boardButtons[k][i].setImageResource(R.drawable.queenblack);
                                break;
                            case "bR":
                                boardButtons[k][i].setImageResource(R.drawable.rookblack);
                                break;
                            case "bB":
                                boardButtons[k][i].setImageResource(R.drawable.bishopblack);
                                break;
                            case "bN":
                                boardButtons[k][i].setImageResource(R.drawable.knightblack);
                                break;
                            case "bp":
                                boardButtons[k][i].setImageResource(R.drawable.pawnblack);
                                break;
                        }
                    }
                    else {
                        boardButtons[k][i].setImageResource(0);
                    }
                }
            }
        }
        private void storeUndo(){
            for (int i = 0; i < 8; i++) {
                for (int k = 0; k < 8; k++) {
                    undoMoveTemp.board[i][k] = currGame.board.board[i][k];
                }
            }

        }

    public Boolean createScanner() throws IOException {
        //doesnt check for duplicate file yet
        fis = openFileInput(gameName);
        br = new BufferedReader(new InputStreamReader(fis));
        return true;

    }

    private class nextMoveButtonClickListener implements View.OnClickListener {
        public void onClick(View view) {
            try {
                recordClick();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void recordClick() throws  IOException{
        String move;
        if((move = br.readLine()) != null ) {
            currGame.readInput(move);
            printBoard();
            if(!currGame.gameIsActive) {
                information.setText("Checkmate, winner is "+currGame.winner);
            }
        }
        else {
            fis.close();
        }
    }




}
