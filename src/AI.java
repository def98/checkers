import java.util.Random;
import java.util.concurrent.TimeUnit;

public class AI implements Player{

    public AI(char color, boolean positionFlag){
        myColor = color;
        myKingColor = Character.toUpperCase(color);
        isBottom = positionFlag;
    }

    public char[][] getRandMove(char[][] boardState, boolean select){
        System.out.println("Inside of AI's getRandMove()");
        board = boardState;
        boolean complete;
        int row = 0;
        int col = 0;
        int count = 0;
        Random rand = new Random();

        do{
            do {
                row = rand.nextInt(8);
                col = rand.nextInt(8);
                count++;
                if(count > 40){
                    return getMove(board, 0, 0);
                }
            }while((board[row][col] != myColor)&&(board[row][col] != myKingColor));
            selected[0] = row;
            selected[1] = col;
            if(board[row][col] == myKingColor){
                complete = attemptMove(true);
            }
            else{
                complete = attemptMove(false);
            }


        }while(!complete);



        return board;
    }

    public char[][] getMove(char[][] boardState, int row, int col){
        board = boardState;
        movePiece();
        return board;
    }

    private void movePiece(){
        boolean complete = false;
        if(isBottom){
            for(int i = 0; (i < 8)&&(!complete); i++){
                for(int j = (i+1)%2; (j < 8)&&(!complete); j+=2){
                    if(board[i][j] == myKingColor){
                        selected[0] = i;
                        selected[1] = j;
                        complete = attemptMove(true);
                    }
                    else if(board[i][j] == myColor){
                        selected[0] = i;
                        selected[1] = j;
                        complete = attemptMove(false);
                    }
                }
            }
        }
        else{
            for(int i = 7; (i >= 0)&&(!complete); i--){
                for(int j = 7; (j >= 0)&&(!complete); j--){
                    if(board[i][j] == myKingColor){
                        selected[0] = i;
                        selected[1] = j;
                        complete = attemptMove(true);
                    }
                    else if(board[i][j] == myColor){
                        selected[0] = i;
                        selected[1] = j;
                        complete = attemptMove(false);
                    }
                }
            }
        }
    }

    private boolean attemptMove(boolean isKing){
        boolean capture = false;
        boolean hasCaptured = false;
        boolean moveDone = false;
        boolean wait = false;
        int i = selected[0];
        int j = selected[1];

        do{
            capture = false;
            wait = false;
            i = selected[0];
            j = selected[1];

            if(isKing){
                //Move Up and Left
                if(((i-1) >= 0)&&((j-1) >= 0)&&(board[i-1][j-1] != myColor)){
                    if(((i-2) >= 0) && ((j-2) >= 0) && (board[i-2][j-2] == ' ') && (board[i-1][j-1] != ' ')){
                        board = captureUL(myKingColor);
                        capture = true;
                        hasCaptured = true;
                        wait = true;
                        moveDone = true;
                    }
                    else if(board[i-1][j-1] == ' '){
                        canMoveUL = true;
                    }
                }

                //Move Up and Right
                if((!wait)&&((i-1) >= 0)&&((j+1) < 8)&&(board[i-1][j+1] != myColor)){
                    if(((i-2) >= 0) && ((j+2) < 8) && (board[i-2][j+2] == ' ') && (board[i-1][j+1] != ' ')){
                        board = captureUR(myKingColor);
                        hasCaptured = true;
                        capture = true;
                        wait = true;
                        moveDone = true;
                    }
                    else if(board[i-1][j+1] == ' '){
                        canMoveUR = true;
                    }
                }

                //Move Down and Right
                if((!wait)&&((i+1) < 8)&&((j+1) < 8)&&(board[i+1][j+1] != myColor)){
                    if(((i+2) < 8) && ((j+2) < 8) && (board[i+2][j+2] == ' ') && (board[i+1][j+1] != ' ')){
                        board = captureDR(myKingColor);
                        capture = true;
                        hasCaptured = true;
                        wait = true;
                        moveDone = true;
                    }
                    else if(board[i+1][j+1] == ' '){
                        canMoveDR = true;
                    }
                }

                //Move Down and Left
                if((!wait) && ((i+1) < 8) && ((j-1) >= 0) && (board[i+1][j-1] != myColor)){
                    if(((i+2) < 8) && ((j-2) >= 0) && (board[i+2][j-2] == ' ') && (board[i+1][j-1] != ' ')){
                        board = captureDL(myKingColor);
                        capture = true;
                        hasCaptured = true;
                        wait = true;
                        moveDone = true;
                    }
                    else if(board[i+1][j-1] == ' '){
                        canMoveDL = true;
                    }
                }

                if((!hasCaptured)&&(!wait)&&canMoveUL){
                    board = moveUL(myKingColor);
                    wait = true;
                    moveDone = true;
                }
                else if((!hasCaptured)&&(!wait)&&canMoveUR){
                    board = moveUR(myKingColor);
                    wait = true;
                    moveDone = true;
                }
                else if((!hasCaptured)&&(!wait)&&canMoveDR){
                    board = moveDR(myKingColor);
                    wait = true;
                    moveDone = true;
                }
                else if((!hasCaptured)&&(!wait)&&canMoveDL){
                    board = moveDL(myKingColor);
                    wait = true;
                    moveDone = true;
                }
                canMoveUL = false;
                canMoveUR = false;
                canMoveDL = false;
                canMoveDR = false;

            }
            else if(isBottom){
                //Move Up and Left
                if(((i-1) >= 0)&&((j-1) >= 0)&&(board[i-1][j-1] != myColor)){
                    if(((i-2) >= 0) && ((j-2) >= 0) && (board[i-2][j-2] == ' ') && (board[i-1][j-1] != ' ')){
                        board = captureUL(myColor);
                        capture = true;
                        hasCaptured = true;
                        wait = true;
                        moveDone = true;
                    }
                    else if(board[i-1][j-1] == ' '){
                        canMoveUL = true;
                    }
                }

                //Move Up and Right
                if((!wait)&&((i-1) >= 0)&&((j+1) < 8)&&(board[i-1][j+1] != myColor)){
                    if(((i-2) >= 0) && ((j+2) < 8) && (board[i-2][j+2] == ' ') && (board[i-1][j+1] != ' ')){
                        board = captureUR(myColor);
                        capture = true;
                        hasCaptured = true;
                        wait = true;
                        moveDone = true;
                    }
                    else if(board[i-1][j+1] == ' '){
                        canMoveUR = true;
                    }
                }

                if((!hasCaptured)&&(!wait)&&canMoveUL){
                    board = moveUL(myColor);
                    wait = true;
                    moveDone = true;
                }
                else if((!hasCaptured)&&(!wait)&&canMoveUR){
                    board = moveUR(myColor);
                    wait = true;
                    moveDone = true;
                }
                canMoveUL = false;
                canMoveUR = false;
            }
            else{
                //Move Down and Right
                if(((i+1) < 8)&&((j+1) < 8)&&(board[i+1][j+1] != myColor)){
                    if(((i+2) < 8) && ((j+2) < 8) && (board[i+2][j+2] == ' ') && (board[i+1][j+1] != ' ')){
                        board = captureDR(myColor);
                        capture = true;
                        hasCaptured = true;
                        wait = true;
                        moveDone = true;
                    }
                    else if(board[i+1][j+1] == ' '){
                        canMoveDR = true;
                    }
                }

                //Move Down and Left
                if((!wait) && ((i+1) < 8) && ((j-1) >= 0) && (board[i+1][j-1] != myColor)){
                    if(((i+2) < 8) && ((j-2) >= 0) && (board[i+2][j-2] == ' ') && (board[i+1][j-1] != ' ')){
                        board = captureDL(myColor);
                        capture = true;
                        hasCaptured = true;
                        wait = true;
                        moveDone = true;
                    }
                    else if(board[i+1][j-1] == ' '){
                        canMoveDL = true;
                    }
                }

                if((!hasCaptured)&&(!wait)&&canMoveDR){
                    board = moveDR(myColor);
                    wait = true;
                    moveDone = true;
                }
                else if((!hasCaptured)&&(!wait)&&canMoveDL){
                    board = moveDL(myColor);
                    wait = true;
                    moveDone = true;
                }
                canMoveDL = false;
                canMoveDR = false;
            }
        }while(capture);

        return moveDone;
    }

    private char[][] moveUL(char color){
        int i = selected[0];
        int j = selected[1];
        board[i][j] = ' ';
        board[i-1][j-1] = color;
        return board;
    }

    private char[][] moveUR(char color){
        int i = selected[0];
        int j = selected[1];
        board[i][j] = ' ';
        board[i-1][j+1] = color;
        return board;
    }

    private char[][] captureUL(char color){
        int i = selected[0];
        int j = selected[1];
        board[i][j] = ' ';
        board[i-1][j-1] = ' ';
        board[i-2][j-2] = color;
        selected[0] = i-2;
        selected[1] = j-2;

        return board;
    }

    private char[][] captureUR(char color){
        int i = selected[0];
        int j = selected[1];
        board[i][j] = ' ';
        board[i-1][j+1] = ' ';
        board[i-2][j+2] = color;
        selected[0] = i-2;
        selected[1] = j+2;

        return board;
    }

    private char[][] moveDL(char color){
        int i = selected[0];
        int j = selected[1];
        board[i][j] = ' ';
        board[i+1][j-1] = color;
        return board;
    }

    private char[][] moveDR(char color){
        int i = selected[0];
        int j = selected[1];
        board[i][j] = ' ';
        board[i+1][j+1] = color;
        return board;
    }

    private char[][] captureDL(char color){
        int i = selected[0];
        int j = selected[1];
        board[i][j] = ' ';
        board[i+1][j-1] = ' ';
        board[i+2][j-2] = color;
        selected[0] = i+2;
        selected[1] = j-2;
        return board;
    }

    private char[][] captureDR(char color){
        int i = selected[0];
        int j = selected[1];
        board[i][j] = ' ';
        board[i+1][j+1] = ' ';
        board[i+2][j+2] = color;
        selected[0] = i+2;
        selected[1] = j+2;
        return board;
    }

    public char getColor(){
        return myColor;
    }

    public char getKingColor(){
        return myKingColor;
    }

    public boolean getIsBottom(){
        return isBottom;
    }

    public boolean getTurnComplete() { return turnComplete; }
    public void setTurnComplete(boolean c) { turnComplete = c; }

    private boolean canMoveUL = false;
    private boolean canMoveUR = false;
    private boolean canMoveDL = false;
    private boolean canMoveDR = false;
    private boolean turnComplete = false;
    private boolean isBottom;
    private char myColor;
    private char myKingColor;
    private char[][] board;
    private int capI = -1;
    private int capJ = -1;
    private int[] selected = new int[2];
}