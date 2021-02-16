import java.util.ArrayList;

public class NullPiece extends Piece
{
	public NullPiece() {}

	@Override
	protected void calculateLegalMoves(ArrayList<Piece> pList, lastMove pLastMove) 
	{
		
	}

	@Override
	public Piece copyPiece() 
	{
		return new NullPiece();
	}
}
