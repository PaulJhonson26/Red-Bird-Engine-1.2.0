import java.util.*;
import java.io.IOException;
public class Computer
{
	public static void main(String[] args) throws IOException
	{
		Scanner scanner = new Scanner(System.in);
		System.out.println("Who you got?: ");
		String playAs = scanner.nextLine();
		
		
		int depth = 3;
		Node root = new Node(new Position()); //so we have a starting set up in there.
		System.out.println("Start eval: " + root.getPosition().getEvaluation());
		
		
		String input = "";
		while(input != "stop" || root.getPosition().getEvaluation() == Double.NEGATIVE_INFINITY ||  root.getPosition().getEvaluation() == Double.POSITIVE_INFINITY)
		{
			

			System.out.println("Updated Board: " + root.getPosition().getTurn() + "'s Move");
			root.getPosition().printBoard();
			root.getChildren().clear();
			//createTree(root, depth);
			System.out.println("Creating tree (maximizing for " + root.getPosition().getTurn() + ")");
			
			if(playAs.equals(root.getPosition().getTurn().toString()))
			{
				long startTime = System.currentTimeMillis();
				Node bestPosition = findBestMove(root, depth);
				long endTime = System.currentTimeMillis();
				System.out.println("Finding best Move took: " +  ((double) (endTime-startTime)) /1000 + "Seconds");
				java.lang.System.gc();
				System.out.println("====================== Computer Moves: ");
				bestPosition.getPosition().printBoard();
				System.out.println("======================");
				root = bestPosition;
			}
			else
			{
				//Node bestPosition = findBestMove(root, depth);
				
				java.lang.System.gc();
				
				
				Scanner scanner2 = new Scanner(System.in);
				System.out.println("Enter Move: ");
				input = scanner2.nextLine();
				System.out.println(input.substring(0,2));
				System.out.println(input.substring(2,4));
				root.getPosition().movePiece(input.substring(0,2), input.substring(2,4));
				System.out.println("root");
				
				here: for(Node children : root.getChildren())
				{
				//	System.out.println("Child " + i);
				//	children.getPosition().printBoard();
					boolean allEqual = true;
					if(children.getPosition().getBoard().size() == root.getPosition().getBoard().size()) //avoids out of bounds exception due to taking = less peices
					{
						for(Piece pPiece : children.getPosition().getBoard())
						{
						//	System.out.println(pPiece.getColour() + "'s LegalMoves Length: " + pPiece.getLegalMoves().size());
							//System.out.println(pPiece.getLocation() + " = " + root.getPosition().getBoard().get(children.getPosition().getBoard().indexOf(pPiece)).getLocation());
							if(pPiece.getLocation() != (root.getPosition().getBoard().get(children.getPosition().getBoard().indexOf(pPiece)).getLocation()))
							{
								allEqual = false;
								break;
							}
						}
						if(allEqual == true)
						{
							root = children;
							break here;
						}

					}
				}
			}
				
		}
		
		
	}
	
	public static Node findBestMove(Node root, int depth)
	{
		
		long startTime = System.currentTimeMillis();
		createTree(root, depth);
		long endTime = System.currentTimeMillis();
		System.out.println("Creating Tree took: " +  ((double) (endTime-startTime)) /1000 + "Seconds");
		
		//Collections.shuffle(root.getChildren());
		ArrayList<Node> candidates = new ArrayList<Node>();
		boolean turn = true;
		double CurrentEval = 0;
		double childEval = 0;
		Node tempBest = root.getChildren().get(0);
		if(root.getPosition().getTurn() == Colour.White)
		{
			turn = true;
			CurrentEval = minMax(root, depth, turn, -1000000, 1000000);
			System.out.println("CurrentEval: " + CurrentEval);
			
			System.out.println("Candidates Size: " + root.getChildren().size());
			for(Node children: root.getChildren())
			{
				startTime = System.currentTimeMillis();
				childEval = minMax(children, depth -1, !turn, -1000000, 1000000);
				endTime = System.currentTimeMillis();
				System.out.println("Min Max took: " +  ((double) (endTime-startTime)) /1000 + "Seconds");
			//	System.out.println("child's min max: " + childEval);
				children.setMinMax(childEval);
//				System.out.println("checking Eval : " + children.getPosition().getEvaluation() + "checking minMax : " + children.getMinMax());
				if(children.getMinMax() >= tempBest.getMinMax())
				{
					Node lastBest = tempBest;
					tempBest = children;
					if(children.getMinMax() > lastBest.getMinMax())
					{
						candidates.clear(); //so if it's greater, all current candidates are shit. so clear
					}
					candidates.add(children);
					System.out.println("new Candidate Eval : " + children.getPosition().getEvaluation() + "Candidate minMax : " + tempBest.getMinMax());
					children.getPosition().printBoard();
				}
			}
			
			Random randomGenerator = new Random();
			int randMoveIndex = randomGenerator.nextInt(candidates.size());
			
			tempBest = candidates.get(randMoveIndex);
			
		}
		else if(root.getPosition().getTurn() == Colour.Black)
		{
			turn = false;
			CurrentEval = minMax(root, depth, turn, -1000000, 1000000);
			System.out.println("CurrentEval: " + CurrentEval);
			
			System.out.println("Candidates Size: " + root.getChildren().size());
			for(Node children: root.getChildren())
			{
				
				childEval = minMax(children, depth -1, !turn, -1000000, 1000000);
				children.setMinMax(childEval);
//				System.out.println("checking Eval : " + children.getPosition().getEvaluation() + "checking minMax : " + children.getMinMax());
				if(children.getMinMax() <= tempBest.getMinMax())
				{
					Node lastBest = tempBest;
					tempBest = children;
					if(children.getMinMax() < lastBest.getMinMax())
					{
						candidates.clear(); //so if it's greater, all current candidates are shit. so clear
					}
					candidates.add(children);
					System.out.println("new Candidate Eval : " + children.getPosition().getEvaluation() + " new Candidate minMax : " + children.getMinMax());
					children.getPosition().printBoard();
				}
			}
			Random randomGenerator = new Random();
			int randMoveIndex = randomGenerator.nextInt(candidates.size());
			tempBest = candidates.get(randMoveIndex);

		}
		
	
		//nextBestMove.getPosition().printBoard();
		
		return tempBest;
	}
	
	public static void createTree(Node root, int depth)
	{	
		//System.out.println("-----" + depth + "-----");
		Position pPosition = root.getPosition();
		//pPosition.evaluateLegalMoves();
		evaluateNode(root); /// set the evaluation of the board position inside the root
		if(depth != 0)
		{
			
			
			for(Piece pPiece : pPosition.getBoard())
			{
					//System.out.print(pPiece.getClass().getName() + " on " + pPiece.getLocation() + ": " );
					for(int legalMove : pPiece.getLegalMoves())
					{
						
						if(pPiece.getColour().equals(pPosition.getTurn())) //if it's the right person's turn add the nodes for that move.
						{
						//	System.out.println("adding node for move: " + legalMove);
						//	System.out.print(legalMove + ", ");
							Position newPosition = new Position(pPosition);//copy position
							
							long startTime = System.currentTimeMillis();
							newPosition.movePiece(newPosition.getPiece(pPiece.getLocation()), legalMove);
							long endTime = System.currentTimeMillis();
							//System.out.println("MovePiece: " +  ((double) (endTime-startTime)) + "Seconds");
							
							
						//	newPosition.printBoard();
							Node newNode = new Node(newPosition);
							
							root.addChild(newNode);
					//		System.out.println("Creating subtree for: " + (pPiece.getClass().getName() + " on " + pPiece.getLocation() + ", " ));
							createTree(newNode, depth-1);
							
						}
					}
					//System.out.println();
			}
		}
	//	System.out.println("Back Up 1");
		

	}
	
	public static void evaluateNode(Node node)
	{
		final double[] whiteKingPos = {  2, 3, 1, 0, 0, 1, 3, 2,
										 2, 2, 0, 0, 0, 0, 2, 2,
										-1,-2,-2,-2,-2,-2,-2,-1,
										-2,-3,-3,-4,-4,-3,-3,-2,
										-3,-4,-4,-5,-5,-4,-4,-3,
										-3,-4,-4,-5,-5,-4,-4,-3,
										-3,-4,-4,-5,-5,-4,-4,-3,
										-3,-4,-4,-5,-5,-4,-4,-3
										};
		final double[] blackKingPos = {  	 3, 4, 4, 5, 5, 4, 4, 3,
											 3, 4, 4, 5, 5, 4, 4, 3,
											 3, 4, 4, 5, 5, 4, 4, 3,
											 3, 4, 4, 5, 5, 4, 4, 3,
										     2, 3, 3, 4, 4, 3, 3, 2,
										     1, 2, 2, 2, 2, 2, 2, 1,
										     -2,-2, 0, 0, 0, 0,-2,-2,
										     -2,-3,-1, 0, 0,-1,-3,-2 
										};
		
		final double[] whiteQueenPos = { -2,-1,-1,-0.5,-0.5,-1,-1,-2,
										 -1, 0 , 0.5, 0, 0, 0, 0, -1,
										 -1, 0.5, 0.5, 0.5, 0.5, 0.5, 0,-1,
										  0, 0, 0.5, 0.5, 0.5, 0.5, 0,-0.5,
										 -0.5, 0, 0.5, 0.5, 0.5, 0.5, 0,-0.5,
										 -1, 0, 0.5, 0.5, 0.5, 0.5, 0, -1,
										 -1, 0, 0, 0, 0, 0, 0, -1,
										 -2,-1,-1,-0.5,-0.5,-1,-1,-2
										};
		
		final double[] blackQueenPos = {  2, 1, 1, 0.5, 0.5, 1, 1, 2,
										  1, 0, 0, 0, 0, 0, 0, 1,
										  1, 0,-0.5,-0.5,-0.5,-0.5, 0, 1,
										  0.5, 0,-0.5,-0.5,-0.5,-0.5, 0,0.5,
										  0, 0,-0.5,-0.5,-0.5,-0.5, 0, 0.5,
										  1,-0.5,-0.5,-0.5,-0.5,-0.5, 0,1,
										  1, 0 ,-0.5, 0, 0, 0, 0, 1,
										  2, 1, 1, 0.5, 0.5, 1, 1, 2
										};
		
		final double[] whiteRookPos = { 0, 0, 0, 0.5, 0.5, 0, 0, 0,
										-0.5, 0, 0, 0, 0, 0, 0, -0.5,
										-0.5, 0, 0, 0, 0, 0, 0, -0.5,
										-0.5, 0, 0, 0, 0, 0, 0, -0.5,
										-0.5, 0, 0, 0, 0, 0, 0, -0.5,
										-0.5, 0, 0, 0, 0, 0, 0, -0.5,
										0.5, 1, 1, 1, 1, 1, 1, 0.5,
										0, 0, 0, 0, 0, 0, 0, 0
										};
		
		final double[] blackRookPos = {  0, 0, 0, 0, 0, 0, 0, 0,
										 -0.5,-1,-1,-1,-1,-1,-1,-0.5,
										 0.5, 0, 0, 0, 0, 0, 0, 0.5,
										 0.5, 0, 0, 0, 0, 0, 0, 0.5,
										 0.5, 0, 0, 0, 0, 0, 0, 0.5,
										 0.5, 0, 0, 0, 0, 0, 0, 0.5,
										 0.5, 0, 0, 0, 0, 0, 0, 0.5,
										 0, 0, 0,-0.5,-0.5, 0, 0, 0
										
									};
		
		final double[] whiteBishopPos = { -2,-1,-1,-1,-1,-1,-1,-2,
										  -1, 0.5, 0, 0, 0, 0, 0.5,-1,
										  -1, 1, 1, 1, 1, 1, 1, -1,
										  -1, 0, 1, 1, 1, 1, 0, -1,
										  -1, 0.5, 0.5, 1, 1, 0.5, 0.5, -1,
										  -1, 0, 0.5, 1, 1, 0.5, 0, -1,
										  -1, 0, 0, 0, 0, 0, 0, -1,
										  -2, -1, -1, -1, -1, -1, -1, -2	  
										};
		
		final double[] blackBishopPos = {  2, 1, 1, 1, 1, 1, 1, 2,
										   1, 0, 0, 0, 0, 0, 0, 1,
										   1, 0, -0.5,-1,-1,-0.5, 0, 1,
										   1,-0.5,-0.5,-1,-1,-0.5,-0.5, 1,
										   1, 0,-1,-1,-1,-1, 0, 1,
										   1,-1,-1,-1,-1,-1,-1, 1,
										   1,-0.5, 0, 0, 0, 0,-0.5,1,
										   2, 1, 1, 1, 1, 1, 1, 2
										};
		
		final double[] whiteKnightPos = {  -5, -4, -3, -3, -3, -3, -4, -5,
										   -4, -2,  0, 0.5, 0.5, 0, -2, -4,
										   -3, 0.5, 1, 1.5, 1.5, 1, 0.5, -3,
										   -3, 0, 1.5, 2, 2, 1.5, 0, -3,
										   -3, 0.5, 1.5, 2, 2, 1.5, 0.5, -3,
										   -3, 0, 1, 1.5, 1.5, 1, 0, -3,
										   -4, -2, 0, 0, 0, 0, -2, -4,
										   -5, -4, -3, -3, -3, -3, -4, -5
										};
		final double[] blackKnightPos = {   5,  4, 3, 3, 3, 3, 4, 5,
										    4, 2, 0, 0, 0, 0, 2, 4,
										    3, 0,-1,-1.5,-1.5,-1, 0, 3,
										    3,-0.5,-1.5,-2,-2,-1.5,-0.5, 3,
										    3, 0,-1.5,-2,-2,-1.5, 0, 3,
										    3,-0.5,-1,-1.5,-1.5,-1,-0.5, 3,
										    4, 2, 0,-0.5,-0.5, 0, 2, 4,
										    5, 4, 3, 3, 3, 3, 4, 5
										};
		
		final double[] whitePawnPos = { 0, 0, 0, 0, 0, 0, 0, 0,
										0.5, 1, 1, -2, -2, 1, 1, 0.5,
										0.5, -0.5, -1, 0, 0, -1, -0.5, 0.5,
										0, 0, 0, 2, 2, 0, 0, 0,
										0.5, 0.5, 1, 2.5, 2.5, 1, 0.5, 0.5,
										1, 1, 2, 3, 3, 2, 1, 1,
										5, 5, 5, 5, 5, 5, 5, 5,
										0, 0, 0, 0, 0, 0, 0, 0
				                      };
		final double[] blackPawnPos = { 0, 0, 0, 0, 0, 0, 0, 0,
										-5,-5,-5,-5,-5,-5,-5,-5,
										-1,-1,-2,-3,-3,-2,-1,-1,
										-0.5,-0.5,-1,-2.5,-2.5,-1,-0.5,-0.5,
										0, 0, 0,-2,-2, 0, 0, 0,
										-0.5, 0.5, 1, 0, 0, 1, 0.5,-0.5,
										-0.5,-1,-1, 2, 2,-1,-1,-0.5,
										0, 0, 0, 0, 0, 0, 0, 0
						              };
		
		
		
		Position aPosition = node.getPosition();
		double eval = 0;
		double whiteNumberOfMovesDif = 0;
		double blackNumberOfMovesDif = 0;
		double whiteMovesRatio = 0;
		double blackMovesRatio = 0;
		boolean blackKingDead = true;
		boolean whiteKingDead = true;
		for(Piece pPiece: aPosition.getBoard())
		{
			
			if(pPiece.getColour() == Colour.White)
			{
				if(pPiece.getClass().getName() == "Knight")
				{
					eval += 30;
					eval = eval+whiteKnightPos[pPiece.getLocation()-1];

				}
				if(pPiece.getClass().getName() == "Bishop")
				{
					eval += 33;
					eval = eval+whiteBishopPos[pPiece.getLocation()-1];

				}
				if(pPiece.getClass().getName() == "Rook")
				{
					eval += 50;
					eval = eval+whiteRookPos[pPiece.getLocation()-1];

				}
				if(pPiece.getClass().getName() == "Queen")
				{
					eval += 90;
					eval = eval+whiteQueenPos[pPiece.getLocation()-1];
				}
				if(pPiece.getClass().getName() == "King")
				{
					eval += 100000;
					eval = eval+whiteKingPos[pPiece.getLocation()-1];
					whiteKingDead = false;
				}
				if(pPiece.getClass().getName() == "Pawn")
				{
					eval += 10;
					eval = eval+whitePawnPos[pPiece.getLocation()-1];
				}
				
			}
			else if(pPiece.getColour() == Colour.Black)
			{
				if(pPiece.getClass().getName() == "Knight")
				{
					eval -= 30;
					eval = eval+blackKnightPos[pPiece.getLocation()-1];

				}
				if(pPiece.getClass().getName() == "Bishop")
				{
					eval -= 33;
					eval = eval+blackBishopPos[pPiece.getLocation()-1];

				}
				if(pPiece.getClass().getName() == "Rook")
				{
					eval -= 50;
					eval = eval+blackRookPos[pPiece.getLocation()-1];

				}
				if(pPiece.getClass().getName() == "Queen")
				{
					eval -= 90;
					eval = eval+blackQueenPos[pPiece.getLocation()-1];

				}
				if(pPiece.getClass().getName() == "King")
				{
					eval -= 100000;
					eval = eval+blackKingPos[pPiece.getLocation()-1];
					whiteKingDead = false;
				}
				if(pPiece.getClass().getName() == "Pawn")
				{
					eval -= 10;
					eval = eval+blackPawnPos[pPiece.getLocation()-1];
				}
				
			}
			
			//////////////////////////////////////Castled
			if(aPosition.whiteCastled == true)
			{
				eval += 3;
			}
			if(aPosition.whiteCastled == false)
			{
				eval -= 3;
			}
			if(aPosition.blackCastled == true)
			{
				eval -= 3;
			}
			if(aPosition.blackCastled == false)
			{
				eval += 3;
			}

			
			
			///////Number Of MOves
			
			if(pPiece.getColour() == Colour.White)
			{
				whiteNumberOfMovesDif += pPiece.getLegalMoves().size();
			}
			else if(pPiece.getColour() == Colour.Black)
			{
				blackNumberOfMovesDif += pPiece.getLegalMoves().size();
			}
			///// if king is in check not so good
//			if(aPosition.whiteChecked == true)
//			{
//				eval -= 1;
//			}
//			if(aPosition.blackChecked == true)
//			{
//				eval += 1;
//			}
			

	
		}
		whiteMovesRatio = 0.5*((whiteNumberOfMovesDif * 7)/(blackNumberOfMovesDif));
		blackMovesRatio = 0.5*((blackNumberOfMovesDif * 7)/(whiteNumberOfMovesDif));
		
		
		
		//System.out.println("Move Diff: " + numberOfMovesDif);
		eval = eval + whiteMovesRatio;
		eval = eval - blackMovesRatio;
		
		
		if((eval >= -110000 && eval < -90000)) //if king is captured
		{
			//eval = Double.NEGATIVE_INFINITY;
			eval = Double.NEGATIVE_INFINITY; //If white has an advantage and kings trade (white king dies first), remove advantage 
		}
		if(eval <= 110000 && eval > 90000)
		{
			//eval = Double.POSITIVE_INFINITY;
			eval = Double.POSITIVE_INFINITY;
		}
		
		eval = (eval)/10;
		aPosition.setEvaluation(eval);
	}
	
	public static double minMax(Node node, int depth, boolean maximizingSide, double alpha, double beta)
	{

		if(depth == 0 || node.getPosition().getEvaluation() == Double.NEGATIVE_INFINITY || node.getPosition().getEvaluation() == Double.POSITIVE_INFINITY )
		{
		//	node.setMinMax(node.getPosition().getEvaluation());
	//		System.out.println(node.getPosition().getEvaluation());
			return node.getPosition().getEvaluation();		
		}
		if(maximizingSide)//if it's white's turn
		{
			double maxEval= -10000000;
			for(Node child : node.getChildren())
			{
				double eval = minMax(child, depth -1, false, alpha, beta);
		//		node.setMinMax(maxEval);
				maxEval = Math.max(maxEval, eval);
				
				alpha = Math.max(alpha, eval);
				if(beta <= alpha)
				{
					break;
				}
			}
		//	node.setMinMax(node.getPosition().getEvaluation());
			return maxEval;
			
		}
		else
		{
			double minEval = 10000000;
			for(Node child : node.getChildren())
			{
				double eval = minMax(child, depth - 1, true, alpha, beta);
				minEval = Math.min(minEval, eval);
				beta = Math.min(beta,eval);
				if(beta <= alpha)
				{
					break;
				}
						
			}
		//	node.setMinMax(node.getPosition().getEvaluation());
			return minEval;
			
		}
	}
}
