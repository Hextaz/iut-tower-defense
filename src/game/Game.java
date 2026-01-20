package game;

import game.effects.Effect;
import game.enemies.Enemy;
import game.entities.Player;
import game.enums.Difficulty;
import game.enums.GameState;
import game.managers.TowerManager;
import game.managers.WaveManager;
import game.towers.Tower;
import game.view.GameRenderer;

import java.awt.*;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.List;
import javax.swing.*;

/**
 * This class represents the playable game.  If you create an

 * object from this class, you have created an instance of the
 * game, complete with JFrame, panel, etc.
 * 
 * The class also has a main method so that the class can
 * be run as an application.
 * 
 * @author basilvetas
 */
public class Game implements Runnable
{
    /* Static methods */
    
    /**
     * The entry point for the game.
     * 
     * @param args not used
     */
    public static void main (String[] args)
    {
        // Just create a game object.  The game constructor
        //   will do the rest of the work.
        
        new Game ();
          
        // Main exits, but our other thread of execution
        //   will keep going.  We could do other work here if
        //   needed.
    }
    
    /* Object fields and methods */
    private PathPoints line;			// path coordinates

    private GamePanel gamePanel;		// gamePanel object
    private GameState state;	   		// The current game state

    private long lastTime;				// keeps track of time
    private double elapsedTime;			// time trackers

    private boolean gameIsOver;			// indicates if game is lost
    private boolean gameIsWon;			// indicates if game is won
    
    /* Managers */
    private Player player;
    private WaveManager waveManager;
    private GameRenderer renderer;
    private TowerManager towerManager;

    /* create enemies */
    private List<Enemy> enemies;				// list of enemy objects

    /* create towers */
    private List<Tower> towers;					// list of tower objects

    /* create effects */
    private List<Effect> effects;				// list of effect objects

    // You will declare other variables here.  These variables will last for
    //   the lifetime of the game, so don't store temporary values or loop counters
    //   here.

    /**
     * Constructor:  Builds a thread of execution, then starts it
     * on 'this' object.  This extra thread of execution will be
     * responsible for doing all the work of creating, running,
     * and playing the game.
     * 
     * (Note:  Drawing the screen happens inside of -another-
     * thread of execution controlled by Java.  Fortunately, we
     * don't care, but we are aware that some other threads
     * do exist.)
     */
    public Game ()
    {
        // The game starts in the SETUP state.
        
        state = GameState.SETUP;
        gamePanel = new GamePanel(this);
        // Create a thread of execution and run it.
        
        Thread t = new Thread(this);
        t.start();  // Our run method is now executing!!!
    }
    
    /**
     * The entry point for the second thread of execution.  Our
     * game loop is entirely within this method.
     */
    public void run ()
    {
        // Loop forever, or until the user closes the game window,
        //   whichever comes first.  ;)
        
        while (true)
        {
            // Test our game state, and do the appropriate action.
            
            if (state == GameState.SETUP)
            {
                doSetupStuff();
            }
            
            else if (state == GameState.UPDATE)
            {
                doUpdateTasks();
            }
            
            else if (state == GameState.DRAW)
            {
                // We don't actually force the drawing to happen.
                //   Instead, we 'request' it of the panel.
                
                gamePanel.repaint();  // redraw screen
                
                // We must wait for the drawing.  It will happen at some time in the near future.
                //   Since we are in an infinite loop, we could just loop until we leave the draw
                //   state.  This would waste battery life on a low power device, so instead
                //   I choose to sleep the current thread for a very short while (so that it
                //   will be briefly inactive).
                
                try { Thread.sleep(5); } catch (Exception e) {}
                
                // Do not advance the state here.  The 'draw' method will advance the state after it draws.
            }
            
            else if (state == GameState.WAIT)
            {
                // Wait 1/10th a second.  This code is not ideal, we'll explore a better way soon.
                
                try { Thread.sleep(100); } catch (Exception e) {}
                
                // Drawing is complete, waiting is complete.  It is time to move
                //   the objects in the game again.  Re-enter the UPDATE state.
                
                state = GameState.UPDATE;
            }
            
            else if (state == GameState.END)
            {
                SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(
                        null,
                        gameIsWon ? "Victory!" : "Defeat...",
                        "End of Game",
                        JOptionPane.INFORMATION_MESSAGE ));
                return;
                // Do cleanup if any.  (We don't need to do anything here yet.)
            }
        }
    }
    
    /**
     * This setup function is called when the game is in the UPDATE state.
     * It just sets up a game, then enters any valid game state.
     */
    private void doSetupStuff ()
    {
        // Do setup tasks
        // Create the JFrame and the JPanel
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        f.setTitle("Basil Vetas's Tower Defense Game");
        f.setContentPane(gamePanel);
        f.pack();
        f.setVisible(true);

        JOptionPane.showMessageDialog(null,  "Rules of the game:\n" +
        		"1. Place towers on the map to stop enemies from reaching the Earth.\n" +
        		"2. Black holes shoot star dust and are cheaper, Suns shoot sun spots and are faster.\n" +
        		"3. You earn money for stopping enemies, but as the game progresses, new enemies attack.\n" +
        		"4. If you stop 500 enemies you win, but if you lose 10 lives the game is over.");

        Difficulty[] options = Difficulty.values();
        int choice = JOptionPane.showOptionDialog(
                null,
                "Choose a difficulty level:",
                "Difficulty Selection",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                Difficulty.NORMAL);
        Difficulty difficulty = (choice >=0) ? options[choice] : Difficulty.NORMAL;

        player = new Player(difficulty);

        // Reset the time
        lastTime = System.currentTimeMillis();
        
        // Use the loader to build a scanner on the path data text file, then build the 
        // path points object from the data in the file.
		ClassLoader myLoader = this.getClass().getClassLoader();
        InputStream pointStream = myLoader.getResourceAsStream("resources/path_1.txt");
        Scanner s = new Scanner (pointStream);
        line  = new PathPoints(s);

        // Fill enemy list with new LinkedList
        enemies = new LinkedList<Enemy>();
        
        // Fill tower list with new LinkedList
        towers = new LinkedList<Tower>();
        
        // Fill effects list with new LinkedList
        effects = new LinkedList<Effect>();

        // Initialize Managers
        waveManager = new WaveManager(line);
        towerManager = new TowerManager();
        renderer = new GameRenderer();

        // initialize
        gameIsOver = false;
    	gameIsWon = false;
        
        // Change the game state to start the game.
        state = GameState.UPDATE;  // You could also enter the 'DRAW' state.
    }
    
    /**
     * This function is called repeatedly (once per game 'frame').
     * The update function should change the positions of objects in the game.
     * (It could also add new enemies, detect collisions, etc.)  This
     * function is responsible for the 'physics' of the game.
     */
    private void doUpdateTasks()
    {	
    	if(gameIsOver)
    	{	state = GameState.END;
    		return;
    	}
    	
    	if(gameIsWon)
    	{	state = GameState.END;
    		return;
    	}
    	
    	// See how long it was since the last frame.
        long currentTime = System.currentTimeMillis();  // Integer number of milliseconds since 1/1/1970.
        elapsedTime = ((currentTime - lastTime) / 1000.0);  // Compute elapsed seconds
        lastTime = currentTime;  // Our current time is the next frame's last time
    	
    	// for each tower, interact in this game
    	for(Tower t: new LinkedList<Tower>(towers))
    	{	
    		t.interact(this, elapsedTime);
    		
    	}
        // for each effect, interact in this game      
    	for(Effect e: new LinkedList<Effect>(effects))
    	{	
    		e.interact(this, elapsedTime);
    		if(e.isDone())
    			effects.remove(e);	// add to list that has reached the end	
    	}
    	
    	// Advance each enemy on the path.
    	for(Enemy e: new LinkedList<Enemy>(enemies))
    	{	
    		e.advance();
     		if(e.getPosition().isAtTheEnd())
    		{
    			enemies.remove(e);	// add to list that has reached the end
    			player.loseLife();		// if they have reached the end, reduce lives
    		}

    	}
    	
        // Fill elements in an enemy list
        waveManager.update(enemies);

    	// Place towers if user chooses
        towerManager.update(gamePanel, player, line, towers);

    	if(player.isDead())
    	{	gameIsOver = true;
    	}
    	
    	if(player.hasWon())
    	{	gameIsWon = true;
    	}
    	
        // After we have updated the objects in the game, we need to
        //   redraw them.  Enter the 'DRAW' state.
        
        state = GameState.DRAW;
       
        // Careful!  At ANY time after we set this state, the draw method
        //   may execute.  Don't do any further updating.
    }
    
    public Player getPlayer() {
        return player;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public List<Tower> getTowers() {
        return towers;
    }

    public List<Effect> getEffects() {
        return effects;
    }

    /**
     * Draws all the game objects, then enters the wait state.
     * 
     * @param g a valid graphics object.
     */
    public void draw(Graphics g)
    {
        // If we're not in the DRAW state, do not draw!
        if (state != GameState.DRAW)
            return;
        	
        renderer.draw(g, player, enemies, towers, effects);
        towerManager.draw(g);

        if (gameIsOver || gameIsWon) {
            renderer.drawGameOver(g, gameIsWon);
        }

        // Drawing is now complete.  Enter the WAIT state to create a small
        //   delay between frames.
        
        state = GameState.WAIT;
    }
}
