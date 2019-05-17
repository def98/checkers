import java.util.Random;

public class Human implements Player{

    public Human(char color, boolean positionFlag){
        myColor = color;
        myKingColor = Character.toUpperCase(color);
        isBottom = positionFlag;
        turnComplete = false;
    }

    public char getColor(){
        return myColor;
    }

    public char getKingColor(){
        return myKingColor;
    }

    public char[][] getRandMove(char[][] boardState, boolean select){
        board = boardState;
        int row = 0;
        int col = 0;
        Random rand = new Random();
        if(select){
            do{
                row = rand.nextInt(8);
                col = rand.nextInt(8);
                if(row != col){
                    if(row != col-2){
                        if(row-2 != col){
                            board = getMove(board, row, col);
                        }
                    }
                }
            }while(!boardHighlighted());
        }
        else{
            do {
                row = rand.nextInt(8);
                col = rand.nextInt(8);
            }while(boardState[row][col] != 'H');
            board = getMove(board, row, col);
        }
        return board;
    }

    private boolean boardHighlighted(){
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(board[i][j] == 'H'){
                    return true;
                }
            }
        }
        return false;
    }

    public char[][] getMove(char[][] boardState, int row, int col){
        board = boardState;

        if(board[row][col] == 'H'){
            dest[0] = row;
            dest[1] = col;
            move();
            // If board is not highlighted turn complete
            if (!boardHighlighted()) {
                turnComplete = true;
            }
        }
        else if((board[row][col] == myColor)||(board[row][col] == myKingColor)){

            selected[0] = row;
            selected[1] = col;
            hlMoves();
        }

        return board;
    }

    private void move(){
        if(isBottom){
            if(selected[0] == (dest[0]+1)){
                moveU();
            }
            else if(selected[0] == (dest[0]+2)){
                captureU();
            }
            else if(board[selected[0]][selected[1]] == myKingColor){
                if(selected[0] == (dest[0]-1)){
                    moveD();
                }
                else if(selected[0] == (dest[0]-2)){
                    captureD();
                }
            }
        }
        else{
            if(selected[0] == (dest[0]-1)){
                moveD();
            }
            else if(selected[0] == (dest[0]-2)){
                captureD();
            }
            else if(board[selected[0]][selected[1]] == myKingColor){
                if(selected[0] == (dest[0]+1)){
                    moveU();
                }
                else if(selected[0] == (dest[0]+2)){
                    captureU();
                }
            }
        }
    }

    public boolean getTurnComplete() { return turnComplete; }
    public void setTurnComplete(boolean c) { turnComplete = c; }

    private void unHighlight(){
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(board[i][j] == 'H'){
                    board[i][j] = ' ';
                }
            }
        }
    }

    private void hlCaptures(){
        unHighlight();

        char p = board[selected[0]][selected[1]]; //selectedpiece
        char c1 = 'N'; //Corner tiles
        char c2 = 'N'; //
        char c3 = 'N'; //
        char c4 = 'N'; //

        if(((selected[0]-1) >= 0)&&((selected[1]-1) >= 0)){
            c1 = board[selected[0]-1][selected[1]-1];
        }
        if(((selected[0]-1) >= 0)&&((selected[1]+1) < 8)){
            c2 = board[selected[0]-1][selected[1]+1];
        }
        if(((selected[0]+1) < 8)&&((selected[1]-1) >= 0)){
            c3 = board[selected[0]+1][selected[1]-1];
        }
        if(((selected[0]+1) < 8)&&((selected[1]+1) < 8)){
            c4 = board[selected[0]+1][selected[1]+1];
        }

        if(p == myKingColor){
            //highlight UL
            if(c1 == 'N'){
                //Do Nothing
            }
            else if((c1 != myColor)&&(c1 != myKingColor)&&(c1 != ' ')){
                if(((selected[0]-2) >= 0)&&((selected[1]-2)) >= 0){
                    if(board[selected[0]-2][selected[1]-2] == ' '){
                        board[selected[0]-2][selected[1]-2] = 'H';
                    }
                }
            }

            //highlight UR
            if(c2 == 'N'){
                //Do Nothing
            }
            else if((c2 != myColor)&&(c2 != myKingColor)&&(c2 != ' ')){
                if(((selected[0]-2) >= 0)&&((selected[1]+2)) < 8){
                    if(board[selected[0]-2][selected[1]+2] == ' '){
                        board[selected[0]-2][selected[1]+2] = 'H';
                    }
                }
            }

            //highlight DL
            if(c3 == 'N'){
                //Do Nothing
            }
            else if((c3 != myColor)&&(c3 != myKingColor)&&(c3 != ' ')){
                if(((selected[0]+2) < 8)&&((selected[1]-2)) >= 0){
                    if(board[selected[0]+2][selected[1]-2] == ' '){
                        board[selected[0]+2][selected[1]-2] = 'H';
                    }
                }
            }

            //highlight DR
            if(c4 == 'N'){
                //Do Nothing
            }
            else if((c4 != myColor)&&(c4 != myKingColor)&&(c4 != ' ')){
                if(((selected[0]+2) < 8)&&((selected[1]+2)) < 8){
                    if(board[selected[0]+2][selected[1]+2] == ' '){
                        board[selected[0]+2][selected[1]+2] = 'H';
                    }
                }
            }
        }
        else if(isBottom){
            //highlight UL
            if(c1 == 'N'){
                //Do Nothing
            }
            else if((c1 != myColor)&&(c1 != myKingColor)&&(c1 != ' ')){
                if(((selected[0]-2) >= 0)&&((selected[1]-2)) >= 0){
                    if(board[selected[0]-2][selected[1]-2] == ' '){
                        board[selected[0]-2][selected[1]-2] = 'H';
                    }
                }
            }

            //highlight UR
            if(c2 == 'N'){
                //Do Nothing
            }
            else if((c2 != myColor)&&(c2 != myKingColor)&&(c2 != ' ')){
                if(((selected[0]-2) >= 0)&&((selected[1]+2)) < 8){
                    if(board[selected[0]-2][selected[1]+2] == ' '){
                        board[selected[0]-2][selected[1]+2] = 'H';
                    }
                }
            }
        }
        else{
            //highlight DL
            if(c3 == 'N'){
                //Do Nothing
            }
            else if((c3 != myColor)&&(c3 != myKingColor)&&(c3 != ' ')){
                if(((selected[0]+2) < 8)&&((selected[1]-2)) >= 0){
                    if(board[selected[0]+2][selected[1]-2] == ' '){
                        board[selected[0]+2][selected[1]-2] = 'H';
                    }
                }
            }

            //highlight DR
            if(c4 == 'N'){
                //Do Nothing
            }
            else if((c4 != myColor)&&(c4 != myKingColor)&&(c4 != ' ')){
                if(((selected[0]+2) < 8)&&((selected[1]+2)) < 8){
                    if(board[selected[0]+2][selected[1]+2] == ' '){
                        board[selected[0]+2][selected[1]+2] = 'H';
                    }
                }
            }
        }
    }

    private void hlMoves(){
        unHighlight();

        char p = board[selected[0]][selected[1]]; //selected piece
        char c1 = 'N'; //Corner tiles
        char c2 = 'N'; //
        char c3 = 'N'; //
        char c4 = 'N'; //


        if(((selected[0]-1) >= 0)&&((selected[1]-1) >= 0)){
            c1 = board[selected[0]-1][selected[1]-1];
        }
        if(((selected[0]-1) >= 0)&&((selected[1]+1) < 8)){
            c2 = board[selected[0]-1][selected[1]+1];
        }
        if(((selected[0]+1) < 8)&&((selected[1]-1) >= 0)){
            c3 = board[selected[0]+1][selected[1]-1];
        }
        if(((selected[0]+1) < 8)&&((selected[1]+1) < 8)){
            c4 = board[selected[0]+1][selected[1]+1];
        }

        if(p == myKingColor){
            //highlight UL
            if(c1 == ' '){
                board[selected[0]-1][selected[1]-1] = 'H';
            }
            else if(c1 == 'N'){
                //Do Nothing
            }
            else if((c1 != myColor)&&(c1 != myKingColor)){
                if(((selected[0]-2) >= 0)&&((selected[1]-2)) >= 0){
                    if(board[selected[0]-2][selected[1]-2] == ' '){
                        board[selected[0]-2][selected[1]-2] = 'H';
                    }
                }
            }

            //highlight UR
            if(c2 == ' '){
                board[selected[0]-1][selected[1]+1] = 'H';
            }
            else if(c2 == 'N'){
                //Do Nothing
            }
            else if((c2 != myColor)&&(c2 != myKingColor)){
                if(((selected[0]-2) >= 0)&&((selected[1]+2)) < 8){
                    if(board[selected[0]-2][selected[1]+2] == ' '){
                        board[selected[0]-2][selected[1]+2] = 'H';
                    }
                }
            }

            //highlight DL
            if(c3 == ' '){
                board[selected[0]+1][selected[1]-1] = 'H';
            }
            else if(c3 == 'N'){
                //Do Nothing
            }
            else if((c3 != myColor)&&(c3 != myKingColor)){
                if(((selected[0]+2) < 8)&&((selected[1]-2)) >= 0){
                    if(board[selected[0]+2][selected[1]-2] == ' '){
                        board[selected[0]+2][selected[1]-2] = 'H';
                    }
                }
            }

            //highlight DR
            if(c4 == ' '){
                board[selected[0]+1][selected[1]+1] = 'H';
            }
            else if(c4 == 'N'){
                //Do Nothing
            }
            else if((c4 != myColor)&&(c4 != myKingColor)){
                if(((selected[0]+2) < 8)&&((selected[1]+2)) < 8){
                    if(board[selected[0]+2][selected[1]+2] == ' '){
                        board[selected[0]+2][selected[1]+2] = 'H';
                    }
                }
            }
        }
        else if(isBottom){
            //highlight UL
            if(c1 == ' '){
                board[selected[0]-1][selected[1]-1] = 'H';
            }
            else if(c1 == 'N'){
                //Do Nothing
            }
            else if((c1 != myColor)&&(c1 != myKingColor)){
                if(((selected[0]-2) >= 0)&&((selected[1]-2)) >= 0){
                    if(board[selected[0]-2][selected[1]-2] == ' '){
                        board[selected[0]-2][selected[1]-2] = 'H';
                    }
                }
            }

            //highlight UR
            if(c2 == ' '){
                board[selected[0]-1][selected[1]+1] = 'H';
            }
            else if(c2 == 'N'){
                //Do Nothing
            }
            else if((c2 != myColor)&&(c2 != myKingColor)){
                if(((selected[0]-2) >= 0)&&((selected[1]+2)) < 8){
                    if(board[selected[0]-2][selected[1]+2] == ' '){
                        board[selected[0]-2][selected[1]+2] = 'H';
                    }
                }
            }
        }
        else{
            //highlight DL
            if(c3 == ' '){
                board[selected[0]+1][selected[1]-1] = 'H';
            }
            else if(c3 == 'N'){
                //Do Nothing
            }
            else if((c3 != myColor)&&(c3 != myKingColor)){
                if(((selected[0]+2) < 8)&&((selected[1]-2)) >= 0){
                    if(board[selected[0]+2][selected[1]-2] == ' '){
                        board[selected[0]+2][selected[1]-2] = 'H';
                    }
                }
            }

            //highlight DR
            if(c4 == ' '){
                board[selected[0]+1][selected[1]+1] = 'H';
            }
            else if(c4 == 'N'){
                //Do Nothing
            }
            else if((c4 != myColor)&&(c4 != myKingColor)){
                if(((selected[0]+2) < 8)&&((selected[1]+2)) < 8){
                    if(board[selected[0]+2][selected[1]+2] == ' '){
                        board[selected[0]+2][selected[1]+2] = 'H';
                    }
                }
            }
        }

    }

    private void setSelected(){
        selected[0] = dest[0];
        selected[1] = dest[1];
    }

    private void moveU(){
        board[dest[0]][dest[1]] = board[selected[0]][selected[1]];
        board[selected[0]][selected[1]] = ' ';
        setSelected();
        unHighlight();
    }

    private void captureU(){
        board[dest[0]][dest[1]] = board[selected[0]][selected[1]];
        board[selected[0]][selected[1]] = ' ';
        if(selected[1] > dest[1]){
            board[selected[0]-1][selected[1]-1] = ' ';
        }
        else{
            board[selected[0]-1][selected[1]+1] = ' ';
        }
        setSelected();
        hlCaptures();
    }

    private void moveD(){
        board[dest[0]][dest[1]] = board[selected[0]][selected[1]];
        board[selected[0]][selected[1]] = ' ';
        setSelected();
        unHighlight();
    }

    private void captureD(){
        board[dest[0]][dest[1]] = board[selected[0]][selected[1]];
        board[selected[0]][selected[1]] = ' ';
        if(selected[1] > dest[1]){
            board[selected[0]+1][selected[1]-1] = ' ';
        }
        else{
            board[selected[0]+1][selected[1]+1] = ' ';
        }
        setSelected();
        hlCaptures();
    }

    public boolean getIsBottom(){
        return isBottom;
    }

    private char myColor;
    private char myKingColor;
    private char[][] board;
    private boolean isBottom;
    private boolean turnComplete;
    private static int[] dest = new int[2];
    private static int[] selected = new int[2];
}