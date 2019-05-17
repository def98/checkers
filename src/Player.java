public interface Player {
    public char[][] getMove(char[][] boardState, int row, int col);
    public char getColor();
    public char getKingColor();
    public boolean getIsBottom();
    public char[][] getRandMove(char[][] boardState, boolean moveType);
    public boolean getTurnComplete();
    public void setTurnComplete(boolean c);
}