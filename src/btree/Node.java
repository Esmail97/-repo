package btree;


public class Node implements Comparable<Node>
{
	private int key;
	private int offset;
	
	public Node()
	{
		
	}
	@Override
	public String toString() {
		return key + "," + offset;
	}
	public Node(int key, int offset) 
	{
		this.key = key;
		this.offset = offset;
	}
	public int getKey() {
		return key;
	}
	public void setKey(int key) {
		this.key = key;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	
	@Override
	public int compareTo(Node o) 
	{
		return this.key - o.key;
	}
	
	

}
