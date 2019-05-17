
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.border.*;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;


public class CheckerBoard {

    private final JPanel gui = new JPanel(new BorderLayout(3, 3));
    private JButton[][] checkerBoardSquares = new JButton[8][8];
    private JPanel checkerBoard;
    private final JLabel message = new JLabel("");
    private Image redPiece, redKingPiece, blackKingPiece, blackPiece, noPiece, highlightedTile;
    private static GameManager gameManager;
    ActionListener listener;
    private JCheckBox hVh, hVcpu;


    CheckerBoard() {
        try {
            initializeActionListener();
        }
        catch (InterruptedException i) {
            System.out.println("INTERRUPT");
        }
        
        gameManager = new GameManager(1,0);

        initializeGui();
    }

    public final void initializeGui() {
        // set up the main GUI
        gui.setBorder(new EmptyBorder(5, 5, 5, 5));
        JToolBar tools = new JToolBar();
        tools.setFloatable(false);
        gui.add(tools, BorderLayout.PAGE_START);

        JButton newButton = new JButton("New");
        newButton.addActionListener(listener);
        JButton optionsButton = new JButton("Options");
        JButton leaderboardButton = new JButton("Leaderboard");
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(listener);
        
        JCheckBox humanVsAiCheckBox = new JCheckBox("Human vs. CPU");
        hVcpu = humanVsAiCheckBox;
        humanVsAiCheckBox.setSelected(true);
        humanVsAiCheckBox.addActionListener(listener);
        JCheckBox humanVsHumanCheckBox = new JCheckBox("Human vs. Human");
        hVh = humanVsHumanCheckBox;
        humanVsHumanCheckBox.addActionListener(listener);



        tools.add(newButton);
        tools.add(optionsButton); // TODO - add functionality!
        tools.add(leaderboardButton); // TODO - add functionality!
        tools.addSeparator();
        tools.add(exitButton);
        tools.addSeparator();
        tools.add(humanVsAiCheckBox);
        tools.add(humanVsHumanCheckBox);
        //tools.add(message);

        //gui.add(new JLabel("?"), BorderLayout.LINE_START);

        checkerBoard = new JPanel(new GridLayout(0, 8));
        checkerBoard.setBorder(new LineBorder(Color.BLACK));
        gui.add(checkerBoard);

        // Set piece images
        redPiece = null;
        blackPiece = null;
        noPiece = null;
        highlightedTile = null;
        try {
        		// .png files must be approx 60x60 pixels
            redPiece = ImageIO.read(getClass().getResource("RedPiece.png"));
            redKingPiece = ImageIO.read(getClass().getResource("RedKingPiece.png"));
            blackPiece = ImageIO.read(getClass().getResource("BlackPiece.png"));
            blackKingPiece = ImageIO.read(getClass().getResource("BlackKingPiece.png"));
            noPiece = ImageIO.read(getClass().getResource("Transparent.png"));
            highlightedTile = ImageIO.read(getClass().getResource("TileHighlight.png"));

        }
        catch (IOException e) {}


        // create the chess board squares
        Insets buttonMargin = new Insets(0,0,0,0);
        for (int i = 0; i < checkerBoardSquares.length; i++) {
            for (int j = 0; j < checkerBoardSquares[i].length; j++) {
                JButton b = new JButton();
                b.setSize(200,200);
                //b.setMargin(buttonMargin);
                // checkers pieces are 64x64 px in size
                // 'fill this in' using a transparent icon..
                ImageIcon icon = new ImageIcon(
                        new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB));
                //b.setIcon(icon);
                b.setOpaque(true);
                b.setBorderPainted(false);
                if ((j % 2 == 1 && i % 2 == 1)
                        //) {
                        || (j % 2 == 0 && i % 2 == 0)) {
                    b.setBackground(Color.gray);
                } else {
                    b.setBackground(Color.black);
                }

                // Set action command for each button's position -- (String) "xy"
                String buttonPos = Integer.toString(j)+Integer.toString(i);
                b.setActionCommand(buttonPos);

                // Set action listener to the button
                b.addActionListener(listener);

                checkerBoardSquares[i][j] = b;
            }
        }

        this.paintBoard();

        //fill the chess board
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                checkerBoard.add(checkerBoardSquares[i][j]);
            }
        }

        gameManager.firstMove();
        this.paintBoard();

    }

    public void nextTurn(){
        if(!gameManager.playerCanMove()){

            System.out.println("About to sleep in \"nextTurn()\"");
            //SLEEP
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            gameManager.playerMove();
            char[][] d = gameManager.getBoardState();
            System.out.println("ABout to update board after AI's Turn.");
            updateBoard(d);
        }
    }

    public void paintBoard() {
        // Set piece icons (red/black)
        char[][] c = gameManager.getBoardState();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                if (c[i][j] == 'r') {
                    checkerBoardSquares[i][j].setIcon(new ImageIcon(redPiece));
                }
                else if (c[i][j] == 'b') {
                    checkerBoardSquares[i][j].setIcon(new ImageIcon(blackPiece));
                }
                else {
                    checkerBoardSquares[i][j].setIcon(new ImageIcon(noPiece));
                }
            }
        }
    }

    public void updateBoard(char[][] newBoard) {
        System.out.println("Inside of \"updateBoard()\"");
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (newBoard[i][j] == 'H')
                    checkerBoardSquares[i][j].setIcon(new ImageIcon(highlightedTile));
                else if (newBoard[i][j] == 'b')
                    checkerBoardSquares[i][j].setIcon(new ImageIcon(blackPiece));
                else if (newBoard[i][j] == 'B')
                    checkerBoardSquares[i][j].setIcon(new ImageIcon(blackKingPiece));
                else if (newBoard[i][j] == 'r')
                    checkerBoardSquares[i][j].setIcon(new ImageIcon(redPiece));
                else if (newBoard[i][j] == 'R')
                    checkerBoardSquares[i][j].setIcon(new ImageIcon(redKingPiece));
                else {
                    checkerBoardSquares[i][j].setIcon(new ImageIcon(noPiece));
                }
            }
        }
    }

    public final JComponent getChessBoard() {
        return checkerBoard;
    }

    public final JComponent getGui() {
        return gui;
    }

    public final void initializeActionListener() throws InterruptedException {
        listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() instanceof JButton) {
                    JButton b = (JButton) e.getSource();
                    if (b.getText().equals("New")) {

                        gameManager.initBoard();
                        updateBoard(gameManager.getBoardState());
                        nextTurn();


                    }
                    else if (b.getText().equals("Options")) {
                        // Implementation for Options button
                    }
                    else if (b.getText().equals("Leaderboard")) {
                        // Implementation for Leaderboard button
                    }
                    else if (b.getText().equals("Exit")) {
                        // Implementation for Exit button
                        //System.out.println("EXIT PROGRAM");
                        System.exit(1);
                    }                    
                    else {
                        String tempButtonAction = b.getActionCommand();
                        //System.out.println(b.getActionCommand());
                        // grab Icon of button pressed and check if it was highlighted
                        // Cast into an ImageIcon so can use getImage()
                        //ImageIcon i = (ImageIcon) b.getIcon();
                        // Checks if current player is a Human
                        if (gameManager.playerCanMove()) {
                            // Parse position of button into row and columns
                            int tempRow = Character.getNumericValue(tempButtonAction.charAt(1));
                            int tempCol = Character.getNumericValue(tempButtonAction.charAt(0));
                            char[][] c = gameManager.clickEvent(tempRow, tempCol);
                            System.out.println("ABout to update board after Player's Turn.");
                            updateBoard(c);


                        }
                        //Now Do AI Turn if the AP has changed.
                        if(!gameManager.playerCanMove()){
                            gameManager.playerMove();
                            char[][] d = gameManager.getBoardState();
                            System.out.println("ABout to update board after AI's Turn.");
                            updateBoard(d);
                        }
                    }
                    // if (i.getImage() != highlightedTile)
                    // b.setIcon(new ImageIcon(highlightedTile));
                    // else
                    // b.setIcon(new ImageIcon(blackPiece));
                }
                else if(e.getSource() instanceof JCheckBox){
                    JCheckBox b = (JCheckBox) e.getSource();
                    if (b.getText().equals("Human vs. CPU")) {
                        if (hVh.isSelected())
                            hVh.setSelected(false);
                        gameManager = new GameManager(1,0);
                        gameManager.initBoard();
                        updateBoard(gameManager.getBoardState());
                        nextTurn();
                    }
                    else if (b.getText().equals("Human vs. Human")) {
                        if (hVcpu.isSelected())
                            hVcpu.setSelected(false);
                        gameManager = new GameManager(1,1);
                        gameManager.initBoard();
                        updateBoard(gameManager.getBoardState());
                        nextTurn();
                    }
                }
            }

        };
    }

    public static void main(String[] args) {

        Runnable r = new Runnable() {

            @Override
            public void run() {
                CheckerBoard chb =
                        new CheckerBoard();

                JFrame f = new JFrame("Rabbit Checkers");
                f.add(chb.getGui());
                f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                f.setLocationByPlatform(true);

                // ensures the frame is the minimum size it needs to be
                // in order display the components within it
                f.pack();
                // ensures the minimum size is enforced.
                f.setMinimumSize(f.getSize());
                f.setVisible(true);
            }

        };
        SwingUtilities.invokeLater(r);
    }
}
