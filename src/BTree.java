import java.io.IOException;
import java.io.RandomAccessFile;

public class BTree 
{
	public void CreateIndexFile(String filename, int numberOfRecords) throws IOException
	{
		RandomAccessFile index = new RandomAccessFile(filename, "rw");
		index.seek(0);
		Record r = new Record();
		r.setLeaf(-1);
		r.setNodes(new Node[]{new Node(-1,-1),new Node(-1,-1),new Node(-1,-1),new Node(-1,-1),new Node(-1,-1)});
		for(int i = 0; i < numberOfRecords; i++)
		{
			r.getNodes()[0].setKey(i + 1);
			if(i == numberOfRecords - 1)
			{
				r.getNodes()[0].setKey(-1);
			}
			Record.write(r, index, -1);
		}
	}
	
	public int InsertNewRecordAtIndex(String filename, int Key, int ByteOffset) throws IOException
	{
		RandomAccessFile index = new RandomAccessFile(filename, "rw");
		int recordIndex = 1;
		

		
		while(true)
		{
			Record r = new Record();
			Record.read(r, index, recordIndex);
			//If record is a leaf
			if(r.getLeaf() == 0 || r.getLeaf() == -1)
			{
				if(r.getLeaf() == -1)
				{
					updateHeader(index, r);
				}
				if(r.insert(Key, ByteOffset))
				{
					r.sortNodes();
					r.setLeaf(0);
					Record.write(r, index, recordIndex);
					return ByteOffset;
				}
				else
				{
					if(recordIndex == 1)
					{
						fullRootSplit(index, r, Key, ByteOffset);
						return ByteOffset;
					}
					else
					{
						fullNormalSplit(index, r, Key, ByteOffset, recordIndex);
						break;
						
					}
					
				}
				
			}
			else
			{
				int newKeyPosition = r.getNewKeyPosition(Key);
				recordIndex = newKeyPosition;
				continue;
			}
			
			/*recordIndex++;
			if(recordIndex * 44 >= index.length())
			{
				break;
			}
			*/
		}
		
		
		return -1;
	}
	
	public Record updateHeader(RandomAccessFile index, Record r) throws IOException
	{
		Record header = new Record();
		header.getNodes()[0].setKey(r.getNodes()[0].getKey());
		Record.write(header, index, 0);
		return header;
	}
	
	public void fullNormalSplit(RandomAccessFile index, Record r, int newKey, int newOffset, int parentIndex) throws IOException
	{
		Record r2 = new Record();
		Record r3 = new Record();
		normalSplit(index, r, r2, r3, newKey, newOffset);
		
		Record header = new Record();
		int emptyIndex = Record.read(header, index, 0).getNodes()[0].getKey();
		
		Record newRecord = new Record();
		Record.read(newRecord, index, emptyIndex);
		
		updateHeader(index, newRecord);
		
		Record.write(r2, index, emptyIndex);
		
		int recordIndex = 1; 
		Record.write(r3, index, parentIndex);
		
		while(true)
		{
			Record fileRecord = new Record();
			Record.read(fileRecord, index, recordIndex);
			
			
			if(fileRecord.offsetExists(parentIndex))
			{
				
				System.out.println(fileRecord);
				if(fileRecord.insert(r2.getNodes()[r2.getLength() - 1].getKey(), emptyIndex))
				{
					fileRecord.sortNodes();
					Record.write(fileRecord, index, recordIndex);
					return;
				}
				else
				{
					if(recordIndex == 1)
					{
						fullRootSplit(index, fileRecord, newKey, newOffset);
						return;
					}
					else
					{
						int i = fileRecord.getNewKeyPosition((r2.getNodes()[(r2.getLength())].getKey()));
						if(i - 1 >= 0)
						{
							
						}
					}
					if(true)
					{
						fullNormalSplit(index, fileRecord, newKey, newOffset, recordIndex);
					}
				}
			}
			
			recordIndex++;
		}
		
	}
	
	public void normalSplit(RandomAccessFile index, Record r, Record r2, Record r3, int newKey, int newOffset)
	{
		r.getNodes()[r.getLength() - 1].setKey(newKey);
		r.getNodes()[r.getLength() - 1].setOffset(newOffset);
		r.sortNodes();
		
		for(int i = (6/2); i < 6; i++)
		{
			r3.insert(r.getNodes()[i].getKey(), r.getNodes()[i].getOffset());
			r2.insert(r.getNodes()[5 - i].getKey(), r.getNodes()[5 - i].getOffset()); 
		}
		r2.sortNodes();
		
		System.out.println(r2);
		System.out.println(r3);
		
	}
	
	public void fullRootSplit(RandomAccessFile index, Record r, int key, int offset) throws IOException
	{
		Record r2 = new Record();
		Record r3 = new Record();
		
		rootSplit(r,r2,r3,key,offset);
		
		Record header = new Record();
		int emptyIndex = Record.read(header, index, 0).getNodes()[0].getKey();
		
		Record newRecord = new Record();
		Record.read(newRecord, index, emptyIndex);
		
		Record.write(r2, index, emptyIndex);
		
		
		
		r.getNodes()[0].setOffset(emptyIndex);
		

		emptyIndex = updateHeader(index, newRecord).getNodes()[0].getKey();
		
		Record.read(newRecord, index, emptyIndex);
		
		updateHeader(index, newRecord);
		Record.write(r3, index, emptyIndex);
		
		r.getNodes()[1].setOffset(emptyIndex);
		r.setLeaf(1);
		Record.write(r, index, 1);

	}
	
	public void rootSplit(Record r, Record r2, Record r3, int newKey, int newOffset)
	{
		r.getNodes()[5].setKey(newKey);
		r.getNodes()[5].setOffset(newOffset);
		r.sortNodes();
		for(int i = 0; i < (6/2); i++)
		{
			
			r2.insert(r.getNodes()[i].getKey(), r.getNodes()[i].getOffset());
			if(i != (5 - i))
			{
				r3.insert(r.getNodes()[5 - i].getKey(), r.getNodes()[5 - i].getOffset());
			}
			
		}

		

		r2.sortNodes();
		r3.sortNodes();
		
		r.getNodes()[0].setKey(r2.getNodes()[r2.getLength() - 1].getKey());
		r.getNodes()[1].setKey(r3.getNodes()[r3.getLength() - 1].getKey());

		r.getNodes()[2] = new Node(-1, -1);
		r.getNodes()[3] = new Node(-1, -1);
		r.getNodes()[4] = new Node(-1, -1);
	}
	
	public void SearchARecord()
	{
		
	}
	
	public void DisplayIndexFileContent(String filename) throws IOException
	{
		RandomAccessFile index = new RandomAccessFile(filename, "rw");
		index.seek(0);
		System.out.println("...................." + filename + ".......................");
		int numberOfRecords = (int)index.length()/44;
		Record r = new Record();
		for(int i = 0; i < numberOfRecords; i++)
		{
			Record.read(r, index, -1);
			System.out.println(r);
		}

		System.out.println("....................END OF FILE.......................");
	}
}
