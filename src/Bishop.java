import java.util.ArrayList;

public class Bishop extends Piece
{
	//constructor
	public Bishop(int pPosition, Colour pColour) 
	{	
		aPosition = pPosition;
		aColour = pColour;
		x = ((pPosition-1) % 8) + 1;
		y = ((pPosition-1) / 8) + 1;
	}
	
	public Bishop(Bishop pBishop)
	{
		aPosition = pBishop.getLocation();
		aColour = pBishop.getColour();
		aLegalMoves = pBishop.getLegalMoves();
		x = pBishop.getX();
		y = pBishop.getY();
	}

	@Override
	protected void calculateLegalMoves(ArrayList<Piece> pList, lastMove aLastMove) 
	{
		ArrayList<Integer> legalMoves = new ArrayList<Integer>();
		int checkPosition = aPosition;
	
		int tempFile = x;
		int tempRank = y;
		
		//y=x above diag ... RISING UPPERCUT! get the ref? nice.
		here:while(tempRank < 8 && tempFile > 1 )
		{
			tempRank++;
			tempFile--;
			
			for(Piece pPiece: pList)
			{
				if(pPiece.getX() == tempFile && pPiece.getY() == tempRank && pPiece.getColour() == this.getColour())
				{
					break here;
				}
				else if(pPiece.getX() == tempFile && pPiece.getY() == tempRank && pPiece.getColour() != this.getColour())
				{
					legalMoves.add((((tempRank-1)*8) + tempFile));
					break here;
				}
			}
			legalMoves.add((((tempRank-1)*8) + tempFile));
			
		}
				//reset
		tempFile = x;
		tempRank = y;
		here:while(tempRank > 1 && tempFile < 8)
		{
			tempRank--;
			tempFile++;
			
			for(Piece pPiece: pList)
			{
				if(pPiece.getX() == tempFile && pPiece.getY() == tempRank && pPiece.getColour() == this.getColour())
				{
					break here;
				}
				else if(pPiece.getX() == tempFile && pPiece.getY() == tempRank && pPiece.getColour() != this.getColour())
				{
					legalMoves.add((((tempRank-1)*8) + tempFile));
					break here;
				}
			}
			legalMoves.add((((tempRank-1)*8) + tempFile));

		}
				//reset
		tempFile = x;
		tempRank = y;
		
		//y = -x diag
		here:while(tempRank < 8 && tempFile < 8)
		{
			tempRank++;
			tempFile++;
			for(Piece pPiece: pList)
			{
				if(pPiece.getX() == tempFile && pPiece.getY() == tempRank && pPiece.getColour() == this.getColour())
				{
					break here;
				}
				else if(pPiece.getX() == tempFile && pPiece.getY() == tempRank && pPiece.getColour() != this.getColour())
				{
					legalMoves.add((((tempRank-1)*8) + tempFile));
					break here;
				}
			}
			legalMoves.add((((tempRank-1)*8) + tempFile));
		}
				//reset
		tempFile = x;
		tempRank = y;
	
		here:while(tempRank > 1 && tempFile > 1)
		{
			tempRank--;
			tempFile--;
			for(Piece pPiece: pList)
			{
				if(pPiece.getX() == tempFile && pPiece.getY() == tempRank && pPiece.getColour() == this.getColour())
				{
					break here;
				}
				else if(pPiece.getX() == tempFile && pPiece.getY() == tempRank && pPiece.getColour() != this.getColour())
				{
					legalMoves.add((((tempRank-1)*8) + tempFile));
					break here;
				}
			}
			legalMoves.add((((tempRank-1)*8) + tempFile));
		}
		
		aLegalMoves = legalMoves;
		
	}

	@Override
	public Bishop copyPiece() 
	{
		return new Bishop(this);
	}

}
