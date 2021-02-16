
public class lastMove 
{
	int aLastPosition;
	Piece aLastPiece;
	
	public lastMove(int pLastPosition, Piece pLastPiece)
	{
		aLastPosition = pLastPosition;
		aLastPiece = pLastPiece;
	}
	
	public int getLastPosition()
	{
		return aLastPosition;
	}
	
	public Piece getLastPiece()
	{
		return aLastPiece;
	}
}
