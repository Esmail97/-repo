package btree;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

public class Record 
{

	private Node[] nodes = new Node[6];
	private int leaf;
	
	public Record()
	{
		for(int i = 0; i < 6; i++)
		{
			this.nodes[i] = new Node(-1, -1);
		}
	}
	
	public Node[] getNodes() {
		return nodes;
	}

	public void setNodes(Node[] nodes) {
		this.nodes = nodes;
	}
	
	public boolean insert(int key, int byteOffset)
	{
		for(int i = 0; i < 5; i++)
		{
			if(nodes[i].getOffset() == -1)
			{
				nodes[i] = new Node(key, byteOffset);
				return true;
			}
		}
		return false;
	}
	
	public int getLength()
	{
		int counter = 0;
		for(int i = 0; i < 5; i++)
		{
			if(nodes[i].getOffset() > -1)
			{
				counter++;
			}
		}
		return counter;
		
	}
	
	public boolean offsetExists(int offset)
	{
		for(int i = 0; i < 5; i++)
		{
			if(this.nodes[i].getOffset() == offset)
			{
				return true;
			}
		}
		return false;
	}
	
	public int getNewKeyPosition(int key)
	{
		int i;
		for(i = 0; i < 5; i++)
		{
			if(this.nodes[i].getKey() != -1)
			{
				if(key <= this.nodes[i].getKey())
				{
					return this.nodes[i].getOffset();
				}
			}
			
		}
		return this.nodes[i].getOffset();
	}
	public static Record read(Record r, RandomAccessFile file, int pos) throws IOException
	{
		if(pos == -1)
		{
			r.leaf = file.readInt();
			for(int i = 0; i < 5; i++)
			{
				r.nodes[i] = new Node();
				r.nodes[i].setKey(file.readInt());
				r.nodes[i].setOffset(file.readInt());
			}
		}
		else
		{
			file.seek(pos * 44);
			r.leaf = file.readInt();
			for(int i = 0; i < 5; i++)
			{
				r.nodes[i] = new Node();
				r.nodes[i].setKey(file.readInt());
				r.nodes[i].setOffset(file.readInt());
			}
		}
		
		return r;
	}
	
	public static void write(Record r, RandomAccessFile file, int pos) throws IOException
	{
		if(pos == -1)
		{
			file.writeInt(r.leaf);
			for(int i = 0; i < 5; i++)
			{
				file.writeInt(r.nodes[i].getKey());
				file.writeInt(r.nodes[i].getOffset());
			}
		}
		else
		{
			file.seek(pos * 44);
			file.writeInt(r.leaf);
			for(int i = 0; i < 5; i++)
			{
				file.writeInt(r.nodes[i].getKey());
				file.writeInt(r.nodes[i].getOffset());
			}
		}
		
	}
	
	public void sortNodes()
	{
		Arrays.sort(nodes, 0, this.getLength());
	}

	@Override
	public String toString() {
		return  this.leaf + " , " + "["
									+ nodes[0] + " " 
									+ nodes[1] + " "
									+ nodes[2] + " "
									+ nodes[3] + " "
									+ nodes[4] + "]";
	}

	public int getLeaf() {
		return leaf;
	}

	public void setLeaf(int leaf) {
		this.leaf = leaf;
	}
	
	
}
