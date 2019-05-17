import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

public class GameManager{
    public CheckerBoard board;
    GameManager(int p1, int p2){
        if(p1 == 0){
            this.p1 = new AI(black, true);
        }
        else{
            this.p1 = new Human(black, true);
        }
        if(p2 == 0){
            this.p2 = new AI(red, false);
        }
        else{
            this.p2 = new Human(red, false);
        }

        initBoard();
        drawBoard(); //Only used for debugging in Terminal
        loserColor = ' ';
        gameOn = true;
        ap = this.p1;

    }

    /*
    * Calculates first move if p1 is an AI.
    */
    public char[][] firstMove() {
        if(ap instanceof AI){
            processAIMove();
        }
        return boardState;
    }

    private boolean boardHighlighted(){
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(boardState[i][j] == 'H'){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean same(char[][] a1, char[][] a2){
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(a1[i][j] != a2[i][j]){
                    return false;
                }
            }
        }
        return true;
    }

    public void assign(char[][] a1, char[][] a2){
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                a1[i][j] = a2[i][j];
            }
        }
    }

    public char[][] getBoardState() {
        return boardState;
    }

    private boolean playerHasPieces(Player p){
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(boardState[i][j] == p.getColor()){
                    return true;
                }
            }
        }
        return false;
    }

    public void drawBoard(){
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                System.out.print("|"+boardState[i][j]);
            }
            System.out.print("|\n");
        }
        System.out.print("\n");
    }

    public void initBoard(){
        clearBoard(); // Added to make sure a new game does not have any lingering pieces
        for(int i = 0; i < 3; i++){
            for(int j = ((i%2)+1)%2; j < 8; j += 2){
                boardState[i][j] = red;
            }
        }

        for(int i = 5; i < 8; i++){
            for(int j = ((i%2)+1)%2; j < 8; j += 2){
                boardState[i][j] = black;
            }
        }

    }

    private void crownKings(){
        for(int i = 0; i < 2; i++){
            int k = i*7;
            for(int j = 0; j < 8; j++){
                if(boardState[k][j] == ap.getColor()){
                    if((k == 0)&&(ap.getIsBottom())){
                        boardState[k][j] = ap.getKingColor();
                    }
                    else if((k == 7)&&(!ap.getIsBottom())){
                        boardState[k][j] = ap.getKingColor();
                    }
                }
            }
        }
    }

    public boolean isGameOver(){
        return gameOn;
    }

    public boolean playerCanMove() {
        if (ap instanceof Human) { return true; }
        else { return false; }
    }

    public char[][] clickEvent(int row, int col) {
        if (ap instanceof Human) {
            boardState = ap.getMove(boardState, row, col);
            if(ap.getTurnComplete()){
                if(ap == p1){
                    ap = p2;
                }
                else{
                    ap = p1;
                }
                crownKings();
            }
        }
        return boardState;
    }

    public void playerMove(){
        processAIMove();
        crownKings();
    }

    private void processAIMove(){
        if(ap instanceof AI){

            assign(saveState, boardState);

            if (ap instanceof AI){
                boardState = ap.getRandMove(boardState, false);

                if(same(saveState, boardState)&&(ap instanceof AI)){
                    // Sets loser color
                    if (ap.getColor() == 'r') {
                    		int input = JOptionPane.showConfirmDialog(null, "Red loses. Would you like to start a new game?",
                    				"Game Over", JOptionPane.YES_NO_OPTION);
                    		if (input == 0) {
                    			initBoard();
                    			drawBoard(); //Only used for debugging in Terminal
                    	        loserColor = ' ';
                    	        gameOn = true;
                    	        ap = this.p1;
                    		}
                    		
                    }
                    else {
                    		JOptionPane.showMessageDialog(null, "Black loses");
                    }
                }
                else{
                    crownKings();
                    drawBoard();

                    // Switch active player
                    if(ap == p1){
                        ap = p2;

                    }
                    else{
                        ap = p1;

                    }
                    ap.setTurnComplete(false);
                }

            }
        }
    }

    /*
     * Clears board with blanks
     */
    private void clearBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                boardState[i][j] = ' ';
            }
        }
    }

    public static char color = 'O';
    public char[][] saveState = {{' ',' ',' ',' ',' ',' ',' ',' '},{' ',' ',' ',' ',' ',' ',' ',' '},
            {' ',' ',' ',' ',' ',' ',' ',' '},{' ',' ',' ',' ',' ',' ',' ',' '},{' ',' ',' ',' ',' ',' ',' ',' '},
            {' ',' ',' ',' ',' ',' ',' ',' '},{' ',' ',' ',' ',' ',' ',' ',' '},{' ',' ',' ',' ',' ',' ',' ',' '}};
    public char[][] boardState = {{' ',' ',' ',' ',' ',' ',' ',' '},{' ',' ',' ',' ',' ',' ',' ',' '},
            {' ',' ',' ',' ',' ',' ',' ',' '},{' ',' ',' ',' ',' ',' ',' ',' '},{' ',' ',' ',' ',' ',' ',' ',' '},
            {' ',' ',' ',' ',' ',' ',' ',' '},{' ',' ',' ',' ',' ',' ',' ',' '},{' ',' ',' ',' ',' ',' ',' ',' '}};
    private Player p1;
    private Player p2;
    private Player ap; //Active Player
    private boolean gameOn;
    private char loserColor;
    private static char red = 'r';
    private static char black = 'b';
    private boolean enabled = true;

    //Not Used Currently
    /*
    public void runGame() throws InterruptedException {
        Random rand = new Random();
        while(playerHasPieces(ap)&&(loserColor == ' ')){
            int row = 0;
            int col = 0 ;
            assign(saveState, boardState);
            //Human moves
            // Simulate Human
            // if(ap instanceof Human){
            // TimeUnit.MILLISECONDS.sleep(500);
            // boardState = ap.getRandMove(boardState,true);
            // drawBoard();
            // TimeUnit.MILLISECONDS.sleep(2000);
            // boardState = ap.getRandMove(boardState,false);
            // while(boardHighlighted()){
            // TimeUnit.MILLISECONDS.sleep(500);
            // boardState = ap.getRandMove(boardState,false);
            // }
            // }
            //AI moves
            if (ap instanceof AI){
                System.out.println("HEY");
                boardState = ap.getRandMove(boardState,false);
                //boardState = ap.getMove(boardState, row, col);
                if(same(saveState, boardState)&&(ap instanceof AI)){
                    // Sets loser color
                    loserColor = ap.getColor();
                    System.out.println("The loser is " + loserColor);
                }
                else{
                    crownKings();
                    drawBoard();
                    // Switch active player
                    if(ap == p1){
                        ap = p2;
                    }
                    else{
                        ap = p1;
                    }
                    ap.setTurnComplete(false);
                }
            }
            else if (ap instanceof Human) {
                if (ap.getTurnComplete()) {
                    // Switch active player
                    if (ap == p1) { ap = p2; }
                    else { ap = p1; }
                }
            }
            //Check if a move was completed
            // No move has happened and active player is an AI
            if(same(saveState, boardState)&&(ap instanceof AI)){
                // Sets loser color
                loserColor = ap.getColor();
                System.out.println("The loser is " + loserColor);
            }
            else{
                crownKings();
                drawBoard();
                if(ap == p1){
                    ap = p2;
                }
                else{
                    ap = p1;
                }
            }
            //TimeUnit.SECONDS.sleep(2);
        }
    }
    */
}