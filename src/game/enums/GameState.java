package game.enums;

/**
 * This 'enum' just creates a new type of data (like int or char).
 * The type is 'GameState', and the legal values are shown below.
 * We can create variables of this type, and we can store the
 * values shown below into those variables.
 *
 * The alternative would have been to use integers to represent states.
 * For example, we could have said 0=setup, 1=update, etc.  I don't like
 * using integers in this way because I would have to remember what
 * they mean.  By using an enum, I can store values that look like what
 * they represent.  SETUP = The game is setting up, etc.
 */
public enum GameState { SETUP, UPDATE, DRAW, WAIT, END }
