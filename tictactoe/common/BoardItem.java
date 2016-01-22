
package tictactoe.common;

/**
 *
 * @author Серега
 */
public enum BoardItem 
{
    	UNDEFINED(""),
	X("X"),
	O("0"),
	X_WIN_FIELD("X"),
	O_WIN_FIELD("0");

	private final String name;

	private BoardItem(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
    
    

