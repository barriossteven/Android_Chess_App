package com.example.group87.androidchess87;

import android.app.Dialog;

import java.io.IOException;
import java.util.Scanner;

//import controller.MoveValidator;
//import model.Bishop;
//import model.Board;
//import model.Knight;
//import model.Pawn;
//import model.Piece;
//import model.Queen;
//import model.Rook;
/**
 * Created by dannychoi on 4/24/2016.
 */
public class ChessGame {

    /**
     * current input is valid
     */
    static Boolean validInput = true;
    /**
     * default promotion character is q for queen
     */
    static char promotionChar = 'q';
    /**
     * current game is active
     */
    static Boolean gameIsActive = true;
    /**
     * white's turn
     */
    static Boolean whiteTurn = true;
    /**
     * asking for draw
     */
    static Boolean askingDraw= false;
    /**
     * creates new Board instance
     */
    static Board board = new Board();
    /**
     * currently asking for draw
     * currently asking for draw
     */
    static Boolean askforDraw = false;

    static Boolean checkFlag = false;
    static Boolean checkmateFlag = false;
    static String winner;
    static Boolean gameOver;
    static int promoteRank;
    static int promoteFile;
    static Boolean promoteFlag = false;


    public ChessGame(){

    }
    /**
     * Parses the input and checks if it is valid input/move
     *
     * @param s String that is inputed
     */
    public static void readInput(String s){
        s=s.toLowerCase();
        s= s.trim();
        if(!gameIsActive){
            return;
        }
        if(s.length()== 0){
            validInput = false;
            return;
        }
        if(s.length() > 11){
            System.out.println("invalid input");
            validInput = false;
            return;
        }
        if(s.equals("resign")){
            resign();
            validInput = true;
            return;
        }
        if(s.equals("draw") && askforDraw == true){
            System.out.println("Draw");
            System.exit(0);
            askforDraw = false;
        }

        //checks if move is between a-h and 1-8 .....letter is rank number is file
        if( ( s.charAt(0) >= 'a' && s.charAt(0)<= 'h') &&
                (s.charAt(3) >= 'a' && s.charAt(3)<= 'h')&&
                (s.charAt(1) >= '1' && s.charAt(1)<= '8')&&
                (s.charAt(4) >= '1' && s.charAt(4)<= '8')&&
                (s.charAt(2) == ' ')
                ){
            int oldcol = Character.getNumericValue(s.charAt(0))-10;
            int oldrow = Math.abs(Character.getNumericValue(s.charAt(1))-8);
            int newcol = Character.getNumericValue(s.charAt(3))-10;
            int newrow = Math.abs(Character.getNumericValue(s.charAt(4))-8);

            if(s.length() == 5){
                //if input is e4 e4 then invalid because cant have new position same as old position
                if((s.charAt(0) ==s.charAt(3))&& (s.charAt(1)==s.charAt(4)) ){
                    System.out.println("invalid input");
                    validInput = false;
                    askforDraw = false;
                }
                // since input is length 5 we know it is in form of e2 e4
                //send input to movepiece to see if input is a valid move
                if(movePiece(oldrow, oldcol, newrow, newcol, promotionChar)){
                    validInput = true;
                    askforDraw = false;
                }else{
                    System.out.println("Illegal move, try again");
                    validInput = false;
                    askforDraw = false;
                }
            }else if((s.length()== 7)&&
                    s.charAt(5)== ' ' &&
                    (s.charAt(6)== 'n' ||
                            s.charAt(6)== 'q' ||
                            s.charAt(6)== 'b' ||
                            s.charAt(6)=='r'))
            {
                //input in form of e2 e4 N
                System.out.println("input with promotion");
                if(movePiece(oldrow, oldcol, newrow, newcol, s.charAt(6))){
                    validInput = true;
                    askforDraw = false;
                }else{
                    System.out.println("Illegal move, try again");
                    validInput = false;
                    askforDraw = false;
                }
            }else if(s.length()==11 && s.indexOf("draw?")!=-1){
                //input in for of e4 e3 draw?
                if(movePiece(oldrow, oldcol, newrow, newcol, promotionChar)){
                    validInput = true;
                    System.out.println("asking for draw");
                    askforDraw = true;
                    //ask for draw
                }else{
                    System.out.println("Illegal move, try again");
                    validInput = false;
                    askforDraw = false;
                }
            }
            if(validInput){
                if(whiteTurn){
                    whiteTurn = false;
                }else{
                    whiteTurn = true;
                }
                if(checkFlag) {
                    System.out.println("Check");
                    checkFlag = false;
                }
//                return;
            }
            if(!gameIsActive){
                System.out.println("GAME IS OVER!!!!");
            }
            return;
        }
        System.out.println("invalid input");
        validInput = false;
        askforDraw = false;

    }
    /**
     * If white player entered "resign" then black wins,
     *  if black player entered "resign" then white wins.
     *
     */
    public static void resign(){
        gameIsActive = false;
        if(whiteTurn){
            System.out.println("Black wins");
//            System.exit(0);
        }else{
            System.out.println("White wins");
//            System.exit(0);
        }

    }
    /**
     * Takes in current position and new position and check if it is a valid move. Path must be clear, move must obey piece's rule set, and can not move into check for move to be valid.
     *
     * @param oldRank Piece's current rank
     * @param oldFile Piece's current file
     * @param newRank Piece's new rank
     * @param newFile Piece's new file
     * @param promotionChar if Piece is a pawn and promotion is valid then this character describes the piece you promote pawn to, Q by default
     * @return true if valid move, false if invalid move
     */
    public static Boolean movePiece(int oldRank, int oldFile, int newRank, int newFile, char promotionChar ) {
        if(board.board[oldRank][oldFile]== null){
            return false;
        }

        String defendingColor;
        String attackingColor;

        if(!whiteTurn) {
            defendingColor = "white";
            attackingColor = "black";
        } else {
            defendingColor = "black";
            attackingColor = "white";
        }

        Piece yourPiece = board.board[oldRank][oldFile];
        if((yourPiece.color.equals("white")&& whiteTurn)||(yourPiece.color.equals("black")&&!whiteTurn)) {
            Boolean movevalidity = yourPiece.validMove(oldRank, oldFile, newRank, newFile, board);
            if(movevalidity && ((yourPiece instanceof Pawn)&&((oldRank==6&&newRank==7)||(oldRank==1 && newRank==0))) ){
                System.out.println("pawn to be promoted to: " + promotionChar);
                board.board[newRank][newFile] =  pawnPromotion(promotionChar);
                promoteRank = newRank;
                promoteFile = newFile;
                board.board[oldRank][oldFile] = null;
                removeEnPassant();
                promoteFlag = true;
                return true;
            }else if(movevalidity){
                board.board[newRank][newFile] =  board.board[oldRank][oldFile];
                board.board[newRank][newFile].hasMoved = true;
                board.board[oldRank][oldFile] = null;

				/* check for check and checkmate */
                if(MoveValidator.checkChecker(board, attackingColor)) {
                    //change this for checkmate
                    //System.out.println("Check");

                    if(MoveValidator.checkmateChecker(board, defendingColor)) {
                        System.out.println("CheckMate");
                        gameIsActive = false;
                        if(whiteTurn){
                            System.out.println("White wins");
                            winner = "white";
//                            System.exit(0);
                        }else{
                            System.out.println("Black wins");
                            winner = "black";
//                            System.exit(0);
                        }
                    }
//					System.out.println("Check");
                    checkFlag = true;
                }

                else if(MoveValidator.checkmateChecker(board, defendingColor)) {
                    System.out.println("Stalemate");
                    gameIsActive = false;
//                    System.exit(0);
                }
                removeEnPassant();
                return true;
            }
        }
        return false;
    }

    /**
     * Removes en Passant flags in each pawn after 1 turn indicating those pieces are no longer in en Passant.
     */
    public static void removeEnPassant(){
        String color = null;
        if(whiteTurn){
            color = "black";
        }else {
            color = "white";
        }

        for(int i =0; i<8;i++){
            for(int k=0; k<8 ;k++){
                if(board.board[i][k] != null){
                    if(board.board[i][k].color.equals(color)){
                        board.board[i][k].inEnPassant= false;
                    }
                }
            }
        }
    }

    /**
     * PawnPromotion checks what the promotion character is and will return the piece corresponding to the character.
     * r = Rook, q = Queen, n = Knight , b = Bishop
     *
     * @param promotionChar indicates what the piece the player wants their Pawn to be promoted to.
     *
     * @return the piece that will replace the promoted Pawn
     */
    public static Piece pawnPromotion(char promotionChar){
        String color;
        if(whiteTurn){
            color = "black";
        } else{
            color = "white";
        }
        if(promotionChar == 'q'){
            return new Queen(color);
        }else if(promotionChar == 'n'){
            return new Knight(color);
        }else if(promotionChar == 'b'){
            return new Bishop(color);
        }else if(promotionChar == 'r'){
            return new Rook(color);
        }

        return null;
    }
    public void promote(char c){
        board.board[promoteRank][promoteFile] =  pawnPromotion(c);
        System.out.print(board.board[promoteRank][promoteFile].pieceSymbol);
    }


}
