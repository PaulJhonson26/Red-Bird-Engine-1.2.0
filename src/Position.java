import java.util.*;
public class Position 
{
	private ArrayList<Piece> aBoard;
	private double evaluation;
	public lastMove aLastMove;
	public Colour aTurn;
	public boolean whiteCastled;
	public boolean blackCastled;
	public boolean whiteChecked;
	public boolean blackChecked;
	
	public Position()
	{
		aBoard = new ArrayList<Piece>();
		//white pawns
		aBoard.add(new Pawn(9, Colour.White));
		aBoard.add(new Pawn(10, Colour.White));
		aBoard.add(new Pawn(11, Colour.White));
		aBoard.add(new Pawn(12, Colour.White));
		aBoard.add(new Pawn(13, Colour.White));
		aBoard.add(new Pawn(14, Colour.White));
		aBoard.add(new Pawn(15, Colour.White));
		aBoard.add(new Pawn(16, Colour.White));
		
		//white pieces
		aBoard.add(new King(5, Colour.White));
		
		aBoard.add(new Knight(7, Colour.White));
		aBoard.add(new Knight(2, Colour.White));
		
		aBoard.add(new Rook(1, Colour.White));
		aBoard.add(new Rook(8, Colour.White));
		
		aBoard.add(new Bishop(3, Colour.White));	
		aBoard.add(new Bishop(6, Colour.White));
		
		aBoard.add(new Queen(4, Colour.White));
		
		//black pawns
		aBoard.add(new Pawn(56, Colour.Black));
		aBoard.add(new Pawn(55, Colour.Black));
		aBoard.add(new Pawn(54, Colour.Black));
		aBoard.add(new Pawn(53, Colour.Black));
		aBoard.add(new Pawn(52, Colour.Black));
		aBoard.add(new Pawn(51, Colour.Black));
		aBoard.add(new Pawn(50, Colour.Black));
		aBoard.add(new Pawn(49, Colour.Black));
		//black pieces
		
		aBoard.add(new Knight(58, Colour.Black));
		aBoard.add(new Knight(63, Colour.Black));
		
		aBoard.add(new Bishop(59, Colour.Black));	
		aBoard.add(new Bishop(62, Colour.Black));	

		aBoard.add(new Rook(57, Colour.Black));
		aBoard.add(new Rook(64, Colour.Black));
		
		aBoard.add(new Queen(60, Colour.Black));	


		
		aTurn = Colour.White;
		aLastMove = new lastMove(0, new NullPiece());
		evaluateLegalMoves();
	}
	
	public Position(Piece pPiece)
	{
		aBoard = new ArrayList<Piece>();
		aBoard.add(pPiece);
		
		aTurn = pPiece.getColour();
		aLastMove = new lastMove(0, new NullPiece());
	}
	
	public Position(Position pPosition)
	{
		ArrayList<Piece> newPieces = new ArrayList<Piece>();
		for(Piece pPiece : pPosition.getBoard())
		{
			Piece copyPiece = pPiece.copyPiece();
			newPieces.add(copyPiece);
			
		}
		aBoard = newPieces;
		evaluation = pPosition.getEvaluation();
		aLastMove = pPosition.getLastMove();
		aTurn = pPosition.getTurn();
		aLastMove = new lastMove(0, new NullPiece());
	}


	public void addPiece(Piece pPiece)
	{
		aBoard.add(pPiece);
	}
	
	public void movePiece(Piece pPiece, int newPosition)
	{
		blackChecked = false;
		whiteChecked = false;
		lastMove pLastMove = getLastMove();
		if(pPiece.getColour() == aTurn) //if it's the right person's turn to move
		{
			
			//More en-passent shit Basically if the move is a diagonal and there's no piece there, it means it's en-passent so delete the piece from last Move
			if(pPiece.getClass().getName() == "Pawn")//if it's a Pawn 
			{
				//System.out.println("getpiece was null");
				if(getPiece(newPosition) == null) //so if there's no piece there 
				{
					if(pPiece.getColour() == Colour.White) //if the pawn you're moving is White
					{
						if(newPosition == pPiece.getLocation()+7 || newPosition == pPiece.getLocation()+9)
						{
							aBoard.remove(pLastMove.getLastPiece());
						}
					}
					if(pPiece.getColour() == Colour.Black)
					{
						if(newPosition == pPiece.getLocation()-7 || newPosition == pPiece.getLocation()-9)
						{
							aBoard.remove(pLastMove.getLastPiece()); //remove en passent pawn
						}
					}
				}
			}
			
			//this is for taking.
			if(getPiece(newPosition) != null && getPiece(newPosition).getColour() != pPiece.getColour())
			{
				aBoard.remove(getPiece(newPosition));
			}
			
			
			aLastMove = pPiece.move(newPosition); //actually make the move
//			System.out.println("Actually made the move");
			
			//////////////////////////////////More Castling stuff ////////////////////////////////////
			///basically making the rook move aswell
			
			//////////////////////////////////More Castling stuff ////////////////////////////////////

			if(pPiece.getClass().getName() == "King") 
			{
				
				if(pPiece.getColour() == Colour.White)
				{
					whiteChecked = ((King) pPiece).inCheck;
					if(pPiece.getLocation() == 7 && aLastMove.getLastPosition() == 5) //i.e castle kingside
					{
						for(Piece tempPiece : aBoard)
						{
							if(tempPiece.getLocation() == 8 && tempPiece.getClass().getName() == "Rook")
							{
								tempPiece.aPosition = 6;
								tempPiece.x = 6;
								setWhiteCastled(true);
							}
						}
					}
					else if(pPiece.getLocation() == 3 && aLastMove.getLastPosition() == 5) //i.e castle Quenside
					{
						for(Piece tempPiece : aBoard)
						{
							if(tempPiece.getLocation() == 1 && tempPiece.getClass().getName() == "Rook")
							{
								tempPiece.aPosition = 4;
								tempPiece.x = 4;
								setWhiteCastled(true);
							}
						}
					}
				}
				else if(pPiece.getColour() == Colour.Black)
				{
					blackChecked = ((King) pPiece).inCheck;
					if(pPiece.getLocation() == 63 && aLastMove.getLastPosition() == 61) //i.e castle kingside
					{
						for(Piece tempPiece : aBoard)
						{
							if(tempPiece.getLocation() == 64 && tempPiece.getClass().getName() == "Rook")
							{
								tempPiece.aPosition = 62;
								tempPiece.x = 6;
								setBlackCastled(true);
							}
						}
					}
					else if(pPiece.getLocation() == 59 && aLastMove.getLastPosition() == 61) //i.e castle Queenside
					{
						for(Piece tempPiece : aBoard)
						{
							if(tempPiece.getLocation() == 57 && tempPiece.getClass().getName() == "Rook")
							{
								tempPiece.aPosition = 60;
								tempPiece.x = 4;
								setBlackCastled(true);
							}
						}
					}
				}
			}
		
			/////////////////////////////////////////
			/////////// Promotion //////////////////
			////////////////////////////////////////
			
			if(pPiece.getClass().getName() == "Pawn")
			{
				if(pPiece.getY() == 8)
				{
					if(pPiece.getColour() == Colour.White) //if y = 8
					{
						aBoard.remove(pPiece);
						Queen newQueen = new Queen(pPiece.getLocation(), Colour.White);
						aBoard.add(newQueen);
						newQueen.calculateLegalMoves(aBoard, pLastMove);
					}
				}
				else if(pPiece.getY() == 1)
				{
					if(pPiece.getColour() == Colour.Black) //if y = 1
					{
						aBoard.remove(pPiece);
						Queen newQueen = new Queen(pPiece.getLocation(), Colour.Black);
						aBoard.add(newQueen);
						newQueen.calculateLegalMoves(aBoard, pLastMove);
					}
				}
			}
			
			//first change turns
			if(getTurn() == Colour.White)
			{
				aTurn = Colour.Black;
			}
			else if(getTurn() == Colour.Black)
			{
				aTurn = Colour.White;
			}
			evaluateLegalMoves();
		}
		else //if wrong person moves
		{
			System.out.println("It's not your turn to move idiot");
		}
		//changing turn;

	}
	public void movePiece(String startPos, String destPos)
	{
		blackChecked = false;
		whiteChecked = false;
		char startPosFile = startPos.charAt(0);
		char startPosRank = startPos.charAt(1);
		char destPosFile = destPos.charAt(0);
		char destPosRank = destPos.charAt(1);
		int xStart = fileToInt(startPosFile);
		int yStart = rankToInt(startPosRank);
		
		int xEnd = fileToInt(destPosFile);
		int yEnd = rankToInt(destPosRank);
		
		Piece pPiece = new NullPiece();
		for(Piece piece: aBoard) //find the piece on starting square
		{
			if(piece.getX() == xStart && piece.getY() == yStart)
			{
				pPiece = piece;
			}
		}
		//(((tempRank-1)*8) + tempFile))
		int newPosition = (((yEnd-1)*8) + xEnd);

		lastMove pLastMove = getLastMove();
		if(pPiece.getColour() == aTurn) //if it's the right person's turn to move
		{
			
			//More en-passent shit Basically if the move is a diagonal and there's no piece there, it means it's en-passent so delete the piece from last Move
			if(getPiece(newPosition) == null) //so if there's no piece there 
			{
				//System.out.println("getpiece was null");
				if(pPiece.getClass().getName() == "Pawn") //if it's a Pawn
				{
					if(pPiece.getColour() == Colour.White) //if the pawn you're moving is White
					{
						if(newPosition == pPiece.getLocation()+7 || newPosition == pPiece.getLocation()+9)
						{
							aBoard.remove(pLastMove.getLastPiece());
						}
					}
					if(pPiece.getColour() == Colour.Black)
					{
						if(newPosition == pPiece.getLocation()-7 || newPosition == pPiece.getLocation()-9)
						{
							aBoard.remove(pLastMove.getLastPiece()); //remove en passent pawn
						}
					}
				}
			}
			
			//this is for taking.
			if(getPiece(newPosition) != null && getPiece(newPosition).getColour() != pPiece.getColour())
			{
				aBoard.remove(getPiece(newPosition));
			}
			
			
			aLastMove = pPiece.move(newPosition); //actually make the move
//			System.out.println("Actually made the move");
			//////////////////////////////////More Castling stuff ////////////////////////////////////
			///basically making the rook move aswell

			if(pPiece.getClass().getName() == "King") 
			{
				System.out.println("it's a King");
				if(pPiece.getColour() == Colour.White)
				{
					System.out.println("it's white");
					if(pPiece.getLocation() == 7) //i.e castle kingside
					{
						whiteChecked = ((King) pPiece).inCheck;
						System.out.println("it's actually on 7");
						for(Piece tempPiece : aBoard)
						{
							if(tempPiece.getLocation() == 8 && tempPiece.getClass().getName() == "Rook")
							{
								tempPiece.aPosition = 6;
								tempPiece.x = 6;
								setWhiteCastled(true);
							}
						}
					}
					else if(pPiece.getLocation() == 3) //i.e castle Quenside
					{
						blackChecked = ((King) pPiece).inCheck;
						for(Piece tempPiece : aBoard)
						{
							if(tempPiece.getLocation() == 1 && tempPiece.getClass().getName() == "Rook")
							{
								tempPiece.aPosition = 4;
								tempPiece.x = 4;
								setWhiteCastled(true);
							}
						}
					}
				}
				else if(pPiece.getColour() == Colour.Black)
				{
					System.out.println("it's black");
					System.out.println("newPositiono " + newPosition);
					System.out.println("King's at: " + pPiece.getLocation());
					if(pPiece.getLocation() == 63) //i.e castle kingside
					{
						System.out.println("it's moved twice");
						for(Piece tempPiece : aBoard)
						{
							if(tempPiece.getLocation() == 64 && tempPiece.getClass().getName() == "Rook")
							{
								System.out.println("rook's there");
								tempPiece.aPosition = 62;
								tempPiece.x = 6;
								setBlackCastled(true);
							}
						}
					}
					else if(pPiece.getLocation() == 59) //i.e castle Queenside
					{
						for(Piece tempPiece : aBoard)
						{
							if(tempPiece.getLocation() == 57 && tempPiece.getClass().getName() == "Rook")
							{
								tempPiece.aPosition = 60;
								tempPiece.x = 4;
								setBlackCastled(true);
							}
						}
					}
				}
			}
		
			/////////////////////////////////////////
			/////////// Promotion //////////////////
			////////////////////////////////////////
			
			if(pPiece.getClass().getName() == "Pawn")
			{
				if(pPiece.getColour() == Colour.White)
				{
					if(pPiece.getY() == 8) //if y = 8
					{
						aBoard.remove(pPiece);
						aBoard.add(new Queen(pPiece.getLocation(), Colour.White));
					}
				}
				else if(pPiece.getColour() == Colour.Black)
				{
					if(pPiece.getY() == 1) //if y = 8
					{
						aBoard.remove(pPiece);
						aBoard.add(new Queen(pPiece.getLocation(), Colour.Black));
					}
				}
			}
			
			//printBoard(); //printboard
			//first change turns
			if(getTurn() == Colour.White)
			{
				aTurn = Colour.Black;
			}
			else if(getTurn() == Colour.Black)
			{
				aTurn = Colour.White;
			}
			evaluateLegalMoves();
		}
		else //if wrong person moves
		{
			System.out.println("It's not your turn to move idiot");
		}
		//changing turn;

	}
	public void evaluateLegalMoves()
	{
		for(Piece pPiece : getBoard())
		{
			pPiece.calculateLegalMoves(getBoard(), getLastMove());
		}
//		for(Piece pPiece : getBoard())
//		{
////				System.out.println(getKing(aTurn).getColour() + " King in Check : " + getKing(aTurn).evaluateKingCheck(getBoard()));
//				if(getKing(aTurn).evaluateKingCheck(getBoard()) == true) // CHecking King CHeck
//				{
//					Position copyPosition = new Position(this);
//					
//					Piece copyPiece = copyPosition.getPiece(pPiece.getLocation()); //set copy piece to piece in same location
//					if(copyPiece.getColour() == aTurn)
//					{
//						ArrayList<Long> toRemove = new ArrayList<Long>(); //cause you can't delete while iterating for some dumb reason.
//						for(long move : copyPiece.getLegalMoves())
//						{
//							copyPiece.move(move); //make move
//							for(Piece somePiece : copyPosition.getBoard()) //reevaluate white pieces available squares
//							{
//								if(somePiece.getColour() != copyPosition.getTurn())
//								{
//									somePiece.calculateLegalMoves(copyPosition.getBoard(), copyPosition.getLastMove());
//								}
//							}
//							if(copyPosition.getKing(aTurn).evaluateKingCheck(copyPosition.getBoard()) == true) //if the king is still in check after moving
//							{
//							//	copyPiece.getLegalMoves().remove(move);
//								toRemove.add(move);
//							}
//						}
//						copyPiece.getLegalMoves().removeAll(toRemove);
//					}
//					pPiece.setLegalMoves(copyPiece.getLegalMoves()); //set the legal moves copyPOsition with updated legalMOves.
//				}
//		}
	}
	
	public ArrayList<Piece> getBoard()
	{
		return aBoard;
	}
	
	public Piece getPiece(int pPosition)
	{
		for(Piece piece : aBoard)
		{
			if(piece.getLocation() == pPosition)
			{
				return piece;
			}
		}
		return null;
	}
	
//	public void removePiece(long pPosition)
//	{
//		for(Piece piece : aBoard)
//		{
//			if(piece.getLocation() == pPosition)
//			{
//				aBoard.remove(piece);
//			}
//		}
//	}

	public void printBoard()
	{
		String boardStrings[][] = new String[8][8];
		for(int i = 0; i < 8; i++) //fill with blanks
		{
			for(int j = 0; j < 8; j++)
			{
				boardStrings[i][j] = "\u25A1 ";
			}
		}
		ArrayList<Piece> boardPieces = getBoard();
		for(Piece pPiece : boardPieces)
		{
			
			int checkPosition = pPiece.getLocation();
			int rank  = pPiece.getY();
			int file = pPiece.getX();
			
			String pieceAsString = "";
			
			switch(pPiece.getClass().getName())
			{
				case "King":
					if(pPiece.getColour() == Colour.White)
						pieceAsString = "K ";
					else
						pieceAsString = "k ";
					break;
				case "Queen":
					if(pPiece.getColour() == Colour.White)
						pieceAsString = "Q ";
					else
						pieceAsString = "q ";
					break;
				case "Rook":
					if(pPiece.getColour() == Colour.White)
						pieceAsString = "R ";
					else
						pieceAsString = "r ";
					break;
				case "Bishop":
					if(pPiece.getColour() == Colour.White)
						pieceAsString = "B ";
					else
						pieceAsString = "b ";
					break;
				case "Knight":
					if(pPiece.getColour() == Colour.White)
						pieceAsString = "N ";
					else
						pieceAsString = "n ";
					break;
				case "Pawn":
					if(pPiece.getColour() == Colour.White)
						pieceAsString = "P ";
					else
						pieceAsString = "p ";
					break;
				
			}
			boardStrings[file-1][rank-1] = pieceAsString;
		}
		for(int r = 7; r >=0 ; r--)
		{
			for(int f = 0; f < 8; f++)
			{
				System.out.print(boardStrings[f][r]);
			}
			System.out.println("");
		}
		System.out.println();
	}
	public lastMove getLastMove()
	{
		return aLastMove;
	}
	
	public void setTurn(Colour pTurn)
	{
		aTurn = pTurn;
	}
	public double getEvaluation() 
	{
		return evaluation;
	}
	
	public Colour getTurn()
	{
		return aTurn;
	}
	
	public int fileToInt(char file)
	{
		int x = 0;
		switch(file)
		{
			case 'a':
				x = 1;
				break;
			case 'b':
				x = 2;
				break;
			case 'c':
				x = 3;
				break;
			case 'd':
				x = 4;
				break;
			case 'e':
				x = 5;
				break;
			case 'f':
				x = 6;
				break;
			case 'g':
				x = 7;
				break;
			case 'h':
				x = 8;
				break;
			
		}
		return x;
	}
	
	public int rankToInt(char rank)
	{
		int x = 0;
		switch(rank)
		{
			case '1':
				x = 1;
				break;
			case '2':
				x = 2;
				break;
			case '3':
				x = 3;
				break;
			case '4':
				x = 4;
				break;
			case '5':
				x = 5;
				break;
			case '6':
				x = 6;
				break;
			case '7':
				x = 7;
				break;
			case '8':
				x = 8;
				break;
			
		}
		return x;
	}
	
	public void setEvaluation(double eval)
	{
		evaluation = eval;
	}
	
	public King getKing(Colour pColour)
	{
		for(Piece pPiece : aBoard)
		{
			if(pPiece.getClass().getName() == "King" && pPiece.getColour() == pColour)
			{
				return (King) pPiece;
			}
		}
		return new King(0, pColour);
	}
	
	public void setWhiteCastled(boolean hasCastled)
	{
		whiteCastled = hasCastled;
	}
	public void setBlackCastled(boolean hasCastled)
	{
		blackCastled = hasCastled;
	}
}
