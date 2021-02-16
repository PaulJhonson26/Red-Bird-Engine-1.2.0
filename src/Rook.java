import java.util.*;

public class Rook extends Piece
{
	
	public Rook(int pPosition, Colour pColour) 
	{	
		aPosition = pPosition;
		aColour = pColour;
		x = ((pPosition-1) % 8) + 1;
		y = ((pPosition-1) / 8) + 1;
	//	aLegalMoves = legalMoves();
	}
	
	public Rook(Rook pRook)
	{
		aPosition = pRook.getLocation();
		aColour = pRook.getColour();
		aLegalMoves = pRook.getLegalMoves();
		x = pRook.getX();
		y = pRook.getY();
	}

	@Override
	protected void calculateLegalMoves(ArrayList<Piece> pList, lastMove aLastMove) 
	{
		ArrayList<Integer> legalMoves = new ArrayList<Integer>();
		int checkPosition = aPosition;

		int tempFile = x;
		int tempRank = y;
		
		here:while(tempFile > 1)
		{
			tempFile--;
			for(Piece pPiece : pList)
			{
				if(pPiece.getX() == tempFile && pPiece.getY() == tempRank  && pPiece.getColour() == this.getColour())
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
		here:while(tempFile < 8)
		{

			tempFile++; //IF YOU'RE NOT AT 8 GO RIGHT
			for(Piece pPiece : pList)
			{
				if(pPiece.getX() == tempFile && pPiece.getY() == tempRank && pPiece.getColour() == this.getColour()) //if there's a piece with same rank and file, 
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
		here:while(tempRank > 1)
		{
			tempRank--;
			for(Piece pPiece : pList)
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
		here:while(tempRank < 8) //if you're not at rank 8 keep going up
		{
			tempRank++;
			for(Piece pPiece : pList)
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
	public Rook copyPiece() 
	{
		return new Rook(this);
	}

}
