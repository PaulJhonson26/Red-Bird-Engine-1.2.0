import java.util.ArrayList;

public class Knight extends Piece
{ 
	public Knight(int pPosition, Colour pColour) 
	{	
		aPosition = pPosition;
		aColour = pColour;
		x = ((pPosition-1) % 8) + 1;
		y = ((pPosition-1) / 8) + 1;
	}

	public Knight(Knight pKnight)
	{
		aPosition = pKnight.getLocation();
		aColour = pKnight.getColour();
		aLegalMoves = pKnight.getLegalMoves();
		x = pKnight.getX();
		y = pKnight.getY();
	}
	@Override
	protected void calculateLegalMoves(ArrayList<Piece> pList, lastMove aLastMove) 
	{
		ArrayList<Integer> legalMoves = new ArrayList<Integer>();
		long checkPosition = aPosition;
		
	
		
		int tempFile = x;
		int tempRank = y;
		
		//if 6 remove top 2, if 2 remove bottom 2 
		//if 7 remove top 4, if 1 remove bottom 4
		
		//same for rank and file. 
		
		if(tempFile < 8 && tempRank < 7) //1 right up 2
		{
			boolean taken = false;
			for(Piece pPiece : pList)
			{
				if(pPiece.getX() == tempFile + 1 && pPiece.getY() == tempRank + 2 && pPiece.getColour() == this.getColour() )
				{
					taken = true;
				}
			}
			if(taken == false)
			{
				tempFile++;
				tempRank+=2;
				
				legalMoves.add((((tempRank-1)*8) + tempFile));
			}
		}
		//reset
		tempFile = x;
		tempRank = y;
		if(tempFile < 7 && tempRank < 8) //2 right up 1
		{
		
			boolean taken = false;
			for(Piece pPiece : pList)
			{
				if(pPiece.getX() == tempFile + 2 && pPiece.getY() == tempRank + 1 && pPiece.getColour() == this.getColour() )
				{
					taken = true;
				}
			}
			if(taken == false)
			{
				tempFile+=2;
				tempRank++;
				
				legalMoves.add((((tempRank-1)*8) + tempFile));
			}
		}
		//reset
		tempFile = x;
		tempRank = y;
		if(tempFile < 7 && tempRank > 1) //2 right down 1
		{
			
			boolean taken = false;
			for(Piece pPiece : pList)
			{
				if(pPiece.getX() == tempFile + 2 && pPiece.getY() == tempRank - 1 && pPiece.getColour() == this.getColour() )
				{
					taken = true;
				}
			}
			if(taken == false)
			{
				tempFile+=2;
				tempRank--;
				
				legalMoves.add((((tempRank-1)*8) + tempFile));
			}
		}
		//reset
		tempFile = x;
		tempRank = y;
		if(tempFile < 8 && tempRank > 2) //1 right down 2
		{
		
			boolean taken = false;
			for(Piece pPiece : pList)
			{
				if(pPiece.getX() == tempFile + 1 && pPiece.getY() == tempRank - 2 && pPiece.getColour() == this.getColour() )
				{
					taken = true;
				}
			}
			if(taken == false)
			{
				tempFile++;
				tempRank-=2;
			
				legalMoves.add((((tempRank-1)*8) + tempFile));
			}
		}
		//reset
		tempFile = x;
		tempRank = y;
		if(tempFile > 1 && tempRank > 2) //1 left down 2
		{
		
			boolean taken = false;
			for(Piece pPiece : pList)
			{
				if(pPiece.getX() == tempFile - 1 && pPiece.getY() == tempRank - 2 && pPiece.getColour() == this.getColour() )
				{
					taken = true;
				}
			}
			if(taken == false)
			{
				tempFile--;
				tempRank-=2;
				
				legalMoves.add((((tempRank-1)*8) + tempFile));
			}
		}
		//reset
		tempFile = x;
		tempRank = y;
		if(tempFile > 2 && tempRank > 1) //2 left down 1
		{
		
			boolean taken = false;
			for(Piece pPiece : pList)
			{
				if(pPiece.getX() == tempFile - 2 && pPiece.getY() == tempRank - 1 && pPiece.getColour() == this.getColour() )
				{
					taken = true;
				}
			}
			if(taken == false)
			{
				tempFile-=2;
				tempRank--;
				
				legalMoves.add((((tempRank-1)*8) + tempFile));
			}
		}
		//reset
		tempFile = x;
		tempRank = y;
		if(tempFile > 2 && tempRank < 8) //2 left up 1
		{
			
			boolean taken = false;
			for(Piece pPiece : pList)
			{
				if(pPiece.getX() == tempFile - 2 && pPiece.getY() == tempRank + 1 && pPiece.getColour() == this.getColour() )
				{
					taken = true;
				}
			}
			if(taken == false)
			{
				tempFile-=2;
				tempRank++;
				
				legalMoves.add((((tempRank-1)*8) + tempFile));
			}
		}
		//reset
		tempFile = x;
		tempRank = y;
		if(tempFile > 1 && tempRank < 7) //1 left up 2
		{
			
			boolean taken = false;
			for(Piece pPiece : pList)
			{
				if(pPiece.getX() == tempFile - 1 && pPiece.getY() == tempRank + 2 && pPiece.getColour() == this.getColour() )
				{
					taken = true;
				}
			}
			if(taken == false)
			{
				tempFile--;
				tempRank+=2;
				
				legalMoves.add((((tempRank-1)*8) + tempFile));
			}
		}
		aLegalMoves = legalMoves;
	}


	@Override
	public Knight copyPiece() 
	{
		return new Knight(this);
	}
	
}
