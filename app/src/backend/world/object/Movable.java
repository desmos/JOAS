package backend.world.object;

/*
 * Movable: Interface for movable objects in game, such as the character and bombs.  
 * 
 *
 */
public interface Movable {
  
  public static enum Direction {
    DIRECTION_UP,
    DIRECTION_DOWN,
    DIRECTION_RIGHT,
    DIRECTION_LEFT,
  }
  
  /*
   * Attempt to move in given direction. 
   * 
   * @param direction : the direction
   * @return : returns true on success, false on failure.
   * */
  public boolean move(Direction direction);
  /*
   * Attempt to turn into given direction, immediately.
   * (Can this ever fail?) 
   * 
   * @param direction : the direction
   * @return : returns true on success, false on failure.
   * */
  public boolean face(Direction direction);

  
  public void setCoord(int xCoord, int yCoord);
  
  public int getCoordX();
  public int getCoordY();
  
  public Direction getOrientation();
  
  
  public void kill();

}
