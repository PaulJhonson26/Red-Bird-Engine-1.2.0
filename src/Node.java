import java.util.ArrayList;

public class Node 
{
	double minmaxVal;
	Position aPosition;
	ArrayList<Node> aChildren;
	
	public Node(Position pPosition)
	{
		aPosition = pPosition;
		aChildren = new ArrayList<Node>();
	}
	
	public Position getPosition()
	{
		return aPosition;
	}
	
	public void addChild(Node pNode)
	{
		aChildren.add(pNode);
	}
	
	public ArrayList<Node> getChildren()
	{
		return aChildren;
	}
	
	public void setMinMax(double val)
	{
		minmaxVal = val;
	}
	
	public double getMinMax()
	{
		return minmaxVal;
	}
}
