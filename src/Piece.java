import java.util.*;
public abstract class Piece
{
	int aPosition;
	int x;
	int y;
	Colour aColour;
	ArrayList<Integer> aLegalMoves;
	boolean hasMoved = false;

	protected abstract void calculateLegalMoves(ArrayList<Piece> pList, lastMove pLastMove);
	public abstract Piece copyPiece();
	
	protected lastMove move(int pNewPosition) 
	{
		int tempPosition = aPosition;
		if(checkLegal(pNewPosition)) //if the move is a legal move, well move it... :)
		{
			aPosition = pNewPosition; 
			x = ((pNewPosition-1) % 8) + 1;
			y = ((pNewPosition-1) / 8) + 1;
		}
		setHasMoved(true);
		return new lastMove(tempPosition, this);
	
	}

	protected boolean checkLegal(int pPosition) 
	{
		for(int move : aLegalMoves)
		{
			if(move == pPosition)
				return true;
		}
		return false;
		
	}
	
	public void printPiece() //prints binary of position
	{
		String[] file = {"a","b","c","d","e","f","g","h"};
		System.out.print(this.getClass().getName() + " " + file[x-1] + y + " ");
	}
	
	public void printLegalMoves() //prints all legal moves of the piece
	{
		System.out.println("legal moves: ");
		boolean[] allMoves = new boolean[64];
		String StringMoves = "";
		for(int move : aLegalMoves)
		{
			allMoves[move-1] = true; //set each correspoding square to true for legal move
		}

		for(int i = 7; i >= 0; i--)//column
		{

			for(int j = 0; j < 8; j++)//row
			{
			
				if(allMoves[(i*8) + j] == true)
				{
					StringMoves = StringMoves + "\u25A0 ";
				}
				else
				{
					StringMoves = StringMoves + "\u25A1 ";
				}
			}
			StringMoves = StringMoves + "\n"; //if you've reached a new row do the thing you know
		}
		
		System.out.print(StringMoves);
	}
	
	public int getLocation() 
	{
		return aPosition;
	}
	
	public Colour getColour()
	{
		return aColour;
	}
	
	public ArrayList<Integer> getLegalMoves()
	{
		return aLegalMoves;
	}
	
	public void setLegalMoves(ArrayList<Integer> pLegalMoves)
	{
		aLegalMoves = pLegalMoves;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public void setHasMoved(boolean bool)
	{
		hasMoved = bool;
	}
	
	public boolean getHasMoved()
	{
		return hasMoved;
	}
}
