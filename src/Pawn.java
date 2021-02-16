import java.util.ArrayList;

public class Pawn extends Piece
{	
	public Pawn(int pPosition, Colour pColour) 
	{	
		aPosition = pPosition;
		aColour = pColour;
		x = ((pPosition-1) % 8) + 1;
		y = ((pPosition-1) / 8) + 1;
	}
	public Pawn(Pawn pPawn)
	{
		aPosition = pPawn.getLocation();
		aColour = pPawn.getColour();
		aLegalMoves = pPawn.getLegalMoves();
		x = pPawn.getX();
		y = pPawn.getY();
	}

	
	protected void calculateLegalMoves(ArrayList<Piece> pList, lastMove pLastMove) 
	{
		ArrayList<Integer> legalMoves = new ArrayList<Integer>();
		int checkPosition = aPosition;

//		int file = aFile; //gets file
		int tempFile = x;
		
//		int tempFile = file;
		int tempRank = y;
		
		if(aColour == Colour.Black)
		{
			
			if(tempRank < 7 && tempRank > 1) // already moved from 6th and not on 1st
			{
				boolean taken = false;
				for(Piece pPiece : pList)
				{
					if(pPiece.getX() == tempFile && pPiece.getY() == tempRank-1)
					{
						taken = true;
					}
				}
				if(taken == false)
				{
					tempRank--; 
					legalMoves.add((((tempRank-1)*8) + tempFile));//down 1
				}

			}
			//reset
			tempFile = x;
			tempRank = y;
			if(tempRank == 7) //if pawn hasn't moved
			{
				boolean taken = false;
				for(Piece pPiece : pList)
				{
					if(pPiece.getX() == tempFile && pPiece.getY() == tempRank-1)
					{
						taken = true;
					}
				}
				if(taken == false)
				{
					tempRank--;
					legalMoves.add((((tempRank-1)*8) + tempFile));//down 1
				}
				//reset
				tempFile = x;
				tempRank = y;
				for(Piece pPiece : pList)
				{
					if(pPiece.getX() == tempFile && pPiece.getY() == tempRank-2)
					{
						taken = true;
					}
				}
				if(taken == false)
				{
					tempRank-=2;
					legalMoves.add((((tempRank-1)*8) + tempFile));//down 1
				}
				

				
			}
			//reset
			tempFile = x;
			tempRank = y;
			for(Piece pPiece : pList) //taking
			{
				if(pPiece.getX() == tempFile - 1 && pPiece.getY() == tempRank-1  && pPiece.getColour() != this.getColour())
				{
					tempFile--;
					tempRank--;
					legalMoves.add((((tempRank-1)*8) + tempFile)); //down 1 left 1 (left from black perspective)
				}
				//reset
				tempFile = x;
				tempRank = y;
					
			}
			//reset
			tempFile = x;
			tempRank = y;
			for(Piece pPiece : pList)
			{
				if(pPiece.getX() == tempFile + 1 && pPiece.getY() == tempRank-1 && pPiece.getColour() != this.getColour())
				{
					tempFile++;
					tempRank--;
					legalMoves.add((((tempRank-1)*8) + tempFile)); 	//down 1 right 1 (right from black perspective)
				}
				//reset
				tempFile = x;
				tempRank = y;
			
			}
			
				
		}
		//reset
		tempFile = x;
		tempRank = y;
		if(aColour == Colour.White)
		{
			
			if(tempRank > 2 && tempRank < 8) // moved from 2nd not on 8th
			{
				boolean taken = false;
				for(Piece pPiece : pList)
				{
					if(pPiece.getX() == tempFile && pPiece.getY() == (tempRank+1))
					{
						taken = true;
					}
				}
				if(taken == false)
				{
					tempRank++;
					legalMoves.add((((tempRank-1)*8) + tempFile));//up 1
				}
			}
			//reset
			tempFile = x;
			tempRank = y;
			if(tempRank == 2)
			{
				boolean taken = false;
				for(Piece pPiece : pList)
				{
					if(pPiece.getX() == tempFile && pPiece.getY() == tempRank + 1)
					{
						taken = true;
					}
				}
				if(taken == false)
				{
					tempRank++;
					legalMoves.add((((tempRank-1)*8) + tempFile));//up 1
				}
				//reset
				tempFile = x;
				tempRank = y;
				for(Piece pPiece : pList)
				{
					if(pPiece.getX() == tempFile && pPiece.getY() == tempRank + 2)
					{
						taken = true;
					}
				}
				if(taken == false)
				{
					tempRank+=2;
					legalMoves.add((((tempRank-1)*8) + tempFile));//up 2
				}
			}
			//reset
			tempFile = x;
			tempRank = y;
			for(Piece pPiece : pList)
			{
				if(pPiece.getX() == tempFile - 1 && pPiece.getY() == tempRank+1  && pPiece.getColour() != this.getColour())
				{
					tempFile--;
					tempRank++;
					legalMoves.add((((tempRank-1)*8) + tempFile)); //up 1 left 1 (left from white perspective)
				}
				//reset
				tempFile = x;
				tempRank = y;
			}
			//reset
			tempFile = x;
			tempRank = y;
			for(Piece pPiece : pList)
			{
				if(pPiece.getX() == tempFile + 1  && pPiece.getY() == tempRank + 1  && pPiece.getColour() != this.getColour())
				{
					tempFile++;
					tempRank++;
					legalMoves.add((((tempRank-1)*8) + tempFile)); //down 1 left 1 (left from black perspective)
				}
				//reset
				tempFile = x;
				tempRank = y;
			}
			
			

		}
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/////////////////////////////////////////////////// GOTTA ADD PROMOTION MAH GUY//////////////////////////////////////////////////////////////////
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		////////////////////////////////////////////////////////////////////////////////////////
		//////////////////                add en-passent					//////////////////// 
		////////////////////////////////////////////////////////////////////////////////////////
		int tempPosition = aPosition; //be careful. like an idiot i named the 'position of a piece' aPosition, and the General Board Position as the class.
//		System.out.println(pLastMove.getLastPiece().getClass().getName());
		if(pLastMove.getLastPiece().getClass() == this.getClass()) //if last move was a pawn
		{
			if(pLastMove.getLastPiece().getColour() == Colour.Black && this.getColour() == Colour.White) //if it's white to move.
			{
				if(pLastMove.getLastPiece().getLocation() == pLastMove.getLastPosition()-16)//if last move was down 2
				{
					if(this.getRank() == 5 && (pLastMove.getLastPiece().getLocation() == this.getLocation() - 1 || pLastMove.getLastPiece().getLocation() == this.getLocation() + 1))
					{
						legalMoves.add(pLastMove.getLastPosition()-8);
					}
				}
			}
			if(pLastMove.getLastPiece().getColour() == Colour.White  &&  this.getColour() == Colour.Black) // if it's black to move
			{
				if(pLastMove.getLastPiece().getLocation() == pLastMove.getLastPosition()+16)//if last move was pawn up 2
				{
					if(this.getRank() == 4 && (pLastMove.getLastPiece().getLocation() == this.getLocation() - 1 || pLastMove.getLastPiece().getLocation() == this.getLocation() + 1)) //if it's on rank 4 and the other piece is left or right of it
					{
						legalMoves.add(pLastMove.getLastPosition()+8); // add en passent
					}
				}
			}
		}
		aLegalMoves = legalMoves;
	}

	@Override
	protected lastMove move(int pNewPosition)
	{
		int tempPosition = aPosition;
		if(checkLegal(pNewPosition))
		{
			aPosition = pNewPosition;
			x = ((pNewPosition-1) % 8) + 1;
			y = ((pNewPosition-1) / 8) + 1;
		}
		return new lastMove(tempPosition, this);
	}

	public int getRank()
	{
		return y;
	}
	
	public void setRank(int pRank)
	{
		y = pRank;
	}

	
	@Override
	public Pawn copyPiece() 
	{
		return new Pawn(this);
	}
}
