import java.util.ArrayList;

public class Queen extends Piece
{
	public Queen(int pPosition, Colour pColour) 
	{	
		aPosition = pPosition;
		aColour = pColour;
		x = ((pPosition-1) % 8) + 1;
		y = ((pPosition-1) / 8) + 1;
	}
	
	public Queen(Queen pQueen)
	{
		aPosition = pQueen.getLocation();
		aColour = pQueen.getColour();
		aLegalMoves = pQueen.getLegalMoves();
		x = pQueen.getX();
		y = pQueen.getY();
	}

	@Override
	protected void calculateLegalMoves(ArrayList<Piece> pList, lastMove aLastMove) 
	{
		ArrayList<Integer> legalMoves = new ArrayList<Integer>();
		long checkPosition = aPosition;

		int tempFile = x;
		int tempRank = y;
		/////////////////////////////add rook-like moves///////////////////////////////
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
		
		///////////////////////	Diagonals now /////////////////////////////
		
		//reset
		tempFile = x;
		tempRank = y;
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
	public Queen copyPiece() 
	{
		return new Queen(this);
	}
}
