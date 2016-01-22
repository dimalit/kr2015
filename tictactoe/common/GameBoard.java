
package tictactoe.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * 
 * @author Серёга.
 *
 */
public class GameBoard {
	
        private int size;
	private BoardItem[][] board;
	private final Collection<Integer[]> winItems;


	public GameBoard(int size) {
		setSize(size);
		winItems = new ArrayList<Integer[]>();
	}


	public int getSize() {
		return size;
	}

	
	public void setSize(int size) {

		if (size < 3)
			throw new IllegalArgumentException("Минимальное значение size = 3");

		this.size = size;
		board = new BoardItem[size][size];

		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				board[i][j] = BoardItem.UNDEFINED;

	}


	public BoardItem getItemAt(int i, int j) {
		return (i < size && j < size) ? board[i][j] : null;
	}


	public void setItemAt(BoardItem value, int i, int j) {

		if (!(i < size && j < size))
			throw new IllegalArgumentException(
					"Неверное значение индекса. Допустимо значение от 0 до "
							+ String.valueOf(size - 1));

		board[i][j] = value;
	}

	
	public BoardItem getNextWinner(int winCount) {
		
		if (winCount > size)
			throw new IllegalArgumentException("Длина выигрышной линии не может быть больше размера доски");
		
		BoardItem result = null;

		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++) {
				result = findWin(i,j,winCount);
				if (result != null)
					return result;
			}

		return result;
	}
	

	private BoardItem findWin(int i, int j, int winCount) {
		
		if (!board[i][j].equals(BoardItem.X) && !board[i][j].equals(BoardItem.O))
			return null;
		
		int cnti = i;
		int cntj = j;
		
        BoardItem result = null;
		
        winItems.clear();
		
		while (cntj >= 0) {
			result = searchAndReplace(i, j, i, cntj, winCount);
			cntj--;
			
			if (result != null && !result.equals(BoardItem.UNDEFINED))
				return result;
			else 
			if (result == null)
				break;
		}

		cnti = i;
		winItems.clear();
		
		while (cnti < size) {
			result = searchAndReplace(i, j, cnti, j, winCount);
			cnti++;
			
			if (result != null && !result.equals(BoardItem.UNDEFINED))
				return result;
			else 
			if (result == null)
				break;
			
		}
	
		cnti = i; cntj = j;
		winItems.clear();
		while (cnti < size && cntj  >= 0) {
			
			result = searchAndReplace(i, j, cnti, cntj, winCount);
			cnti++;
			cntj--;
			
			if (result != null && !result.equals(BoardItem.UNDEFINED))
				return result;
			else 
			if (result == null)
				break;
			
		}
		cnti = i; cntj = j;
		winItems.clear();
		while (cnti < size && cntj < size) {
			result = searchAndReplace(i, j, cnti, cntj, winCount);
			cnti++;
			cntj++;
			
			if (result != null && !result.equals(BoardItem.UNDEFINED))
				return result;
			else 
			if (result == null)
				break;
			
		}
		
		return null;
	}

	private BoardItem searchAndReplace(int i, int j, int cnti, int cntj,
			int winCount) {

		BoardItem item = board[cnti][cntj];
		
		if (item.equals(board[i][j])) {

			
			winItems.add(new Integer[] { cnti, cntj });

			
			if (winItems.size() == winCount) {
				
				for (Integer[] buff : winItems) {

					board[buff[0]][buff[1]] = board[buff[0]][buff[1]].equals(BoardItem.X) ? 
							  BoardItem.X_WIN_FIELD
							: BoardItem.O_WIN_FIELD;
				}
				return item;
			}
		} else 
			
			return null;
		
		
		return BoardItem.UNDEFINED;

	}
	
}
