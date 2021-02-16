import java.util.ArrayList;

public class King extends Piece
{
	boolean inCheck;
	public King(int pPosition, Colour pColour) 
	{	
		aPosition = pPosition;
		aColour = pColour;
		x = ((pPosition-1) % 8) + 1;
		y = ((pPosition-1) / 8) + 1;
		inCheck = false;
	}
	
	public King(King pKing)
	{
		aPosition = pKing.getLocation();
		aColour = pKing.getColour();
		aLegalMoves = pKing.getLegalMoves();
		x = pKing.getX();
		y = pKing.getY();
		inCheck = pKing.inCheck;
		
	}
	@Override
	protected void calculateLegalMoves(ArrayList<Piece> pList, lastMove aLastMove) 
	{
		ArrayList<Integer> legalMoves = new ArrayList<Integer>();

		int tempFile = x;
		int tempRank = y;
	
		////////////////////////////////////////////////// Castling
		if(getHasMoved() == false && inCheck == false)
		{
			if(getColour() == Colour.White)
			{
				boolean nof1 = true;
				boolean nog1 = true;
				boolean nod1 = true;
				boolean noc1 = true;
				boolean nob1 = true;
				for(Piece pPiece : pList)
				{
					if(pPiece.getLocation() == 6)
					{
						nof1 = false;
					}
					if(pPiece.getLocation() == 7)
					{
						nog1 = false;
					}

					if(pPiece.getLocation() == 4)
					{
						nod1 = false;
					}
					if(pPiece.getLocation() == 3)
					{
						noc1 = false;
					}
					if(pPiece.getLocation() == 2)
					{
						nob1 = false;
					}

				}
				for(Piece pPiece : pList)
				{
					if(pPiece.getLocation() == 8 && pPiece.getClass().getName() == "Rook" && pPiece.getHasMoved() == false  && pPiece.getColour() == Colour.White && nof1 == true && nog1 == true)
					{
						//White Queenside Castle
						evaluateKingCheck(pList);
					//	System.out.println("Whiet King incheck: " + inCheck);
						if(inCheck == false)
						{
							legalMoves.add(7);
						}
						
					}
					
					if(pPiece.getLocation() == 1 && pPiece.getClass().getName() == "Rook" && pPiece.getHasMoved() == false && pPiece.getColour() == Colour.White && nod1 == true && noc1 == true && nob1 == true)
					{
						evaluateKingCheck(pList);
						//White Kingside castle
						//System.out.println("Whiet King incheck: " + inCheck);
						if(inCheck == false)
						{
							legalMoves.add(3);
						}
					}
				}
				
			}
			else if(getColour() == Colour.Black)
			{
				boolean nof8 = true;
				boolean nog8 = true;
				boolean nod8 = true;
				boolean noc8 = true;
				boolean nob8 = true;
				for(Piece pPiece : pList)
				{
					if(pPiece.getLocation() == 62)
					{
						nof8 = false;
					}
					if(pPiece.getLocation() == 63)
					{
						nog8 = false;
					}

					if(pPiece.getLocation() == 60)
					{
						nod8 = false;
					}
					if(pPiece.getLocation() == 59)
					{
						noc8 = false;
					}
					if(pPiece.getLocation() == 58)
					{
						nob8 = false;
					}

				}
				for(Piece pPiece : pList)
				{
					if(pPiece.getLocation() == 64 && pPiece.getClass().getName() == "Rook" && pPiece.getHasMoved() == false  && pPiece.getColour() == Colour.Black && nof8 == true && nog8 == true)
					{
						//black Kingside castle
						evaluateKingCheck(pList);
					//	System.out.println("black King incheck: " + inCheck);
						if(inCheck == false)
						{
							legalMoves.add(63);
						}
					}
					
					if(pPiece.getLocation() == 57 && pPiece.getClass().getName() == "Rook" && pPiece.getHasMoved() == false  && pPiece.getColour() == Colour.Black && nod8 == true && noc8 == true && nob8 == true)
					{
						//Black Queenside Castle
						evaluateKingCheck(pList);
					//	System.out.println("Whiet King incheck: " + inCheck);
						if(inCheck == false)
						{
							legalMoves.add(59);
						}
					}
				}
			}
		}
		////////////////////////////////////////////castling
		if(tempRank < 8) //if lower than 8th rank
		{
			boolean taken = false;
			for(Piece pPiece : pList)
			{
				if(pPiece.getX() == tempFile && pPiece.getY() == tempRank + 1 && pPiece.getColour() == this.getColour()) //if there's a piece up 1
				{
					taken = true;
				}
			}
			if(taken == false)
			{
				tempRank++;
				legalMoves.add((((tempRank-1)*8) + tempFile)); //up 1
			}
			//reset
			tempRank = y;
			tempFile = x;
			if(tempFile < 8) //if right of 'a'
			{
				taken = false;
				for(Piece pPiece : pList)
				{
					if(pPiece.getX() == tempFile + 1 && pPiece.getY() == tempRank + 1  && pPiece.getColour() == this.getColour()) //if there's a same colour piece
					{
						taken = true;
					}
				}
				if(taken == false)
				{
					tempRank++;
					tempFile++;
					legalMoves.add((((tempRank-1)*8) + tempFile)); //up 1 right 1
				}
				//reset
				tempRank = y;
				tempFile = x;
			}
			
			if(tempFile > 1) //if left of 'h'
			{
				taken = false;
				for(Piece pPiece : pList)
				{
					if(pPiece.getX() == tempFile - 1 && pPiece.getY() == tempRank + 1  && pPiece.getColour() == this.getColour()) //if there's a same colour piece
					{
						taken = true;
					}
				}
				if(taken == false)
				{
					tempRank++;
					tempFile--;
					legalMoves.add((((tempRank-1)*8) + tempFile)); //up 1 left 1
				}
				//reset
				tempRank = y;
				tempFile = x;
			}

		}
		if(tempRank > 1) //if > rank 1
		{
			boolean taken = false;
			for(Piece pPiece : pList)
			{
				if(pPiece.getX() == tempFile && pPiece.getY() == tempRank - 1  && pPiece.getColour() == this.getColour()) //if there's a black piece
				{
					taken = true;
				}
			}
			if(taken == false)
			{
				tempRank--;
				legalMoves.add((((tempRank-1)*8) + tempFile)); //down 1
			}
			//reset
			tempRank = y;
			tempFile = x;

			if(tempFile<8) //if left of 'a'
			{
				taken = false;
				for(Piece pPiece : pList)
				{
					if(pPiece.getX() == tempFile + 1 && pPiece.getY() == tempRank - 1  && pPiece.getColour() == this.getColour()) //if there's a black piece
					{
						taken = true;
					}
				}
				if(taken == false)
				{
					tempRank--;
					tempFile++;
					legalMoves.add((((tempRank-1)*8) + tempFile)); //down 1 right 1
				}
				//reset
				tempRank = y;
				tempFile = x;
			}
				
			if(tempFile > 1) //if right of 'h'
			{
				taken = false;
				for(Piece pPiece : pList)
				{
					if(pPiece.getX() == tempFile - 1 && pPiece.getY() == tempRank - 1  && pPiece.getColour() == this.getColour()) //if there's a black piece
					{
						taken = true;
					}
				}
				if(taken == false)
				{
					tempRank--;
					tempFile--;
					legalMoves.add((((tempRank-1)*8) + tempFile)); //down 1 left 1
				}
				//reset
				tempRank = y;
				tempFile = x;
			}
				
		}
		if(tempFile > 1) //left
		{
			boolean taken = false;
			for(Piece pPiece : pList)
			{
				if(pPiece.getX() == tempFile - 1 && pPiece.getY() == tempRank  && pPiece.getColour() == this.getColour()) //if there's a black piece
				{
					taken = true;
				}
			}
			if(taken == false)
			{
				tempFile--;
				legalMoves.add((((tempRank-1)*8) + tempFile)); //down 1 left 1
			}
			//reset
			tempRank = y;
			tempFile = x;
		}
		if(tempFile < 8) //rigght
		{
			boolean taken = false;
			for(Piece pPiece : pList)
			{
				if(pPiece.getX() == tempFile + 1 && pPiece.getY() == tempRank  && pPiece.getColour() == this.getColour()) //if there's a black piece
				{
					taken = true;
				}
			}
			if(taken == false)
			{
				tempFile++;
				legalMoves.add((((tempRank-1)*8) + tempFile)); //right
			}
		}
		aLegalMoves = legalMoves;
	}

	@Override
	public King copyPiece() 
	{
		return new King(this);
	}
	
	protected void evaluateKingCheck(ArrayList<Piece> pList)
	{
		loop: for(Piece pPiece: pList)
		{
				if(pPiece.getColour() != getColour())
				{
					for(int pMove : pPiece.getLegalMoves())
					{
						if(pMove == aPosition)
						{
							//King is in check
							inCheck = true;
							break loop;
						}
					}
				}
				inCheck = false;
		}
		
	}	
}