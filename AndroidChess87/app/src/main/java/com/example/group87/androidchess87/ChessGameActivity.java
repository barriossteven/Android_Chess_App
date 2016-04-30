package com.example.group87.androidchess87;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

public class ChessGameActivity extends AppCompatActivity {

    ChessGame currGame;
    private ImageButton boardButtons[][]; /* [x][y] */
    private String currPlayer;
    private Button undoButton;
    private Button aiButton;
    private Button drawButton;
    private Button resignButton;
    private Button recordButton;
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
    private char promotionChar = 'q';
    Context context;
    private Boolean wasJustPromoted = false;
    String tmpFile = "temp.txt";
    String FILENAME = "temp.txt";
    Boolean firstTime = true;



    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chess_main);

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

        undoButton = (Button) findViewById(R.id.undo);
        aiButton = (Button) findViewById(R.id.ai);
        drawButton = (Button) findViewById(R.id.draw);
        resignButton = (Button) findViewById(R.id.resign);
        recordButton = (Button) findViewById(R.id.RecordButton);

        information = (TextView) findViewById(R.id.information);
        currPlayerText = (TextView) findViewById(R.id.currentPlayerText);
        drawRequestText = (TextView) findViewById(R.id.drawRequestText);

        currGame = new ChessGame();
        moveValidator = new MoveValidator();
        currPlayer = "white";


        undoButton.setEnabled(true);
        undoButton.setOnClickListener(new UndoButtonClickListener());
        aiButton.setOnClickListener(new AiButtonClickListener());
        drawButton.setOnClickListener(new DrawButtonClickListener());
        resignButton.setOnClickListener(new ResignButtonClickListener());
        recordButton.setEnabled(false);

        printBoard();
//        boardButtons[0][0].setBackgroundResource(R.drawable.rookblack);
        startNewGame();

    }



    private void startNewGame()
    {
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
                boardButtons[i][j].setOnClickListener(new ButtonClickListener(location));
            }
        }

        currPlayerText.setText("White");
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
                //System.out.println("this is input: " + prevClick+ " " +location);
                String move = prevClick+ " " +location;
                currGame.readInput(prevClick+ " " +location);
                //check if move is legal
                //deselect all clicks
                clickActive = false;
                prevClick = null;
                printBoard();
                if(!currGame.validInput){
                    information.setText("Illegal move, try again");
                }
                if(currGame.validInput)
                {
                    if(currGame.promoteFlag){
                        currGame.promoteFlag = false;
                        promotionDialog();
                        wasJustPromoted = true;

                    }
                    try {
                        storeLine(move);
                    } catch (IOException e) {
                        e.printStackTrace();
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
                    information.setText("Checkmate, winner is " + currGame.winner);
                    recordButton.setEnabled(true);
                    printBoard();

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

    private class UndoButtonClickListener implements View.OnClickListener {
        public void onClick(View view) {
            System.out.println("undo");
            if(canUndo) {
                //delete previous line in the logfile
                if(currGame.whiteTurn){
                    currGame.whiteTurn = false;
                }else{
                    currGame.whiteTurn = true;
                }
                for (int i = 0; i < 8; i++) {
                    for (int k = 0; k < 8; k++) {
                        currGame.board.board[i][k] = undoMove.board[i][k];
                        printBoard();
                    }
                }
                canUndo = false;
                information.setText("Undo'd");
            } else {
                information.setText("Cannot undo more than once");
            }
        }
    }
    private class AiButtonClickListener implements View.OnClickListener {
        public void onClick(View view) {
            Random rand = new Random();
            do{
                int n = rand.nextInt(8);
                String curr = "";
                switch (n) {
                    case 0:
                        curr = "a" + rand.nextInt(8);
                        break;
                    case 1:
                        curr = "b" + rand.nextInt(8);
                        break;
                    case 2:
                        curr = "c" + rand.nextInt(8);
                        break;
                    case 3:
                        curr = "d" + rand.nextInt(8);
                        break;
                    case 4:
                        curr = "e" + rand.nextInt(8);
                        break;
                    case 5:
                        curr = "f" + rand.nextInt(8);
                        break;
                    case 6:
                        curr = "g" + rand.nextInt(8);
                        break;
                    case 7:
                        curr = "h" + rand.nextInt(8);
                        break;
                }

                n = rand.nextInt(8);
                String dest = "";
                switch (n) {
                    case 0:
                        dest = "a" + rand.nextInt(8);
                        break;
                    case 1:
                        dest = "b" + rand.nextInt(8);
                        break;
                    case 2:
                        dest = "c" + rand.nextInt(8);
                        break;
                    case 3:
                        dest = "d" + rand.nextInt(8);
                        break;
                    case 4:
                        dest = "e" + rand.nextInt(8);
                        break;
                    case 5:
                        dest = "f" + rand.nextInt(8);
                        break;
                    case 6:
                        dest = "g" + rand.nextInt(8);
                        break;
                    case 7:
                        dest = "h" + rand.nextInt(8);
                        break;
                }
                String move = curr + " " + dest;
                storeUndo();
                currGame.readInput(move);
            }while(!currGame.validInput);
            for (int i = 0; i < 8; i++) {
                for (int k = 0; k < 8; k++) {
                    undoMove.board[i][k] = undoMoveTemp.board[i][k];
                }
            }
            canUndo = true;
            information.setText("AI move");

            printBoard();
        }
    }
    private class DrawButtonClickListener implements View.OnClickListener {
        public void onClick(View view) {
            if(!drawRequest) {
                if(currGame.whiteTurn) {
                    information.setText("White offers draw");
                } else {
                    information.setText("Black offers draw");
                }
                drawRequest = true;
            }
            else {

                if(currGame.whiteTurn) {
                    information.setText("White accepts draw");
                } else {
                    information.setText("Black accepts draw");
                }
                drawRequest = false;
                currGame.gameIsActive = false;
            }
        }
    }
    private class ResignButtonClickListener implements View.OnClickListener {
        public void onClick(View view) {
            currGame.readInput("resign");
            if(currGame.whiteTurn) {
                information.setText("White resigned, Black wins");
            } else {
                information.setText("Black resigned, White wins");
            }
            recordButton.setEnabled(true);
        }
    }
    private void printBoard(){
        if(currGame.checkFlag){
            //print check
        }
        if(currGame.whiteTurn) {
            currPlayerText.setText(" White");
        } else {
            currPlayerText.setText(" Black");
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

    public void recordClick(View v)
    {
        final Dialog dialog = new Dialog(ChessGameActivity.this);
        dialog.setTitle("Enter Game Name");
        dialog.setContentView(R.layout.recordgame_dialog_layout);
        dialog.show();
        final EditText gameName = (EditText)dialog.findViewById(R.id.gameName);
        Button submitButton = (Button)dialog.findViewById(R.id.confirmButton);
        Button cancelButton = (Button)dialog.findViewById(R.id.cancelButton);

        submitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //text is the name that the person gave to this game
                String text = gameName.getText().toString();
                text= text.trim() + ".txt";


                try {
                    if(createRecord(text)){
                        Toast.makeText(getApplicationContext(),text + " has been recorded", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(),text + " has NOT been recorded", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),text + " has NOT been recorded", Toast.LENGTH_SHORT).show();
                }

                dialog.cancel();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();


            }
        });
    }


    public Boolean createRecord(String record) throws IOException {
        //doesnt check for duplicate file yet
        FileOutputStream fos = openFileOutput(record, Context.MODE_PRIVATE);
        FileInputStream fis = openFileInput(FILENAME);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        PrintWriter pw = new PrintWriter(fos);
        String move;

        while( (move = br.readLine())!= null ){
            pw.println(move);
        }
        pw.close();
        fos.close();
        fis.close();
        return true;

    }


    public void promotionDialog() {
        Dialog dialog = onCreateDialogSingleChoice();
        dialog.setCancelable(false);
        dialog.show();

    }

    public Dialog onCreateDialogSingleChoice() {
        promotionChar = 'q';
        currGame.promote(promotionChar);
        printBoard();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            CharSequence[] array = {"Queen", "Knight", "Rook","Bishop"};
            builder.setTitle("Select Promotion Piece").setSingleChoiceItems(array, 0, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if (which == 0) {
                        promotionChar = 'q';
                        currGame.promote(promotionChar);
                        printBoard();
                    } else if (which == 1) {
                        promotionChar = 'n';
                        currGame.promote(promotionChar);
                        printBoard();
                    } else if (which ==2) {
                        promotionChar = 'r';
                        currGame.promote(promotionChar);
                        printBoard();
                    }else if(which ==3){
                        promotionChar = 'b';
                        currGame.promote(promotionChar);
                        printBoard();
                    }

                }
            })

                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });

            return builder.create();
        }



    public void unwriteUndo(){}
    public void storeLine(String move) throws IOException {
        if(wasJustPromoted){
            move = move + " " + promotionChar;
            wasJustPromoted = false;
        }
        FileOutputStream fos = null;
        if(firstTime){
            fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            firstTime = false;
        }else{
            fos = openFileOutput(FILENAME, Context.MODE_APPEND);
        }
        PrintWriter pw = new PrintWriter(fos);
        pw.println(move);
        pw.close();
        fos.close();
    }




}
