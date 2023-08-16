package btree;

import java.io.IOException;

public class Main 
{
	public static void main(String[] args) throws IOException
	{
		BTree btree = new BTree();
		
		btree.CreateIndexFile("index.bin", 8);
		
		
		btree.InsertNewRecordAtIndex("index.bin", 3, 12);
		btree.InsertNewRecordAtIndex("index.bin", 7, 24);
		btree.InsertNewRecordAtIndex("index.bin", 10, 48);
                btree.InsertNewRecordAtIndex("index.bin", 24, 60);
		btree.InsertNewRecordAtIndex("index.bin", 14, 72);
                 btree.DisplayIndexFileContent("index.bin");
                 
		btree.InsertNewRecordAtIndex("index.bin", 19, 84);
                btree.DisplayIndexFileContent("index.bin");
                System.out.println("-------------------------------------");
                
                btree.InsertNewRecordAtIndex("index.bin", 25, 96);
                                btree.DisplayIndexFileContent("index.bin");

                btree.InsertNewRecordAtIndex("index.bin", 26, 108);
                                btree.DisplayIndexFileContent("index.bin");

                btree.InsertNewRecordAtIndex("index.bin", 27, 120);
                                btree.DisplayIndexFileContent("index.bin");

                /*
                btree.InsertNewRecordAtIndex("index.bin", 30, 96);
                btree.InsertNewRecordAtIndex("index.bin", 15, 108);
                btree.InsertNewRecordAtIndex("index.bin", 1, 120);
                btree.InsertNewRecordAtIndex("index.bin", 5, 132);
                btree.DisplayIndexFileContent("index.bin");
                
                
                btree.InsertNewRecordAtIndex("index.bin", 2, 144);
                btree.InsertNewRecordAtIndex("index.bin", 8, 156);
                btree.InsertNewRecordAtIndex("index.bin", 9, 168);
                btree.InsertNewRecordAtIndex("index.bin", 6, 180);
                
                
                btree.DisplayIndexFileContent("index.bin");
                btree.InsertNewRecordAtIndex("index.bin", 4, 192);
                btree.InsertNewRecordAtIndex("index.bin", 11, 204);
                */
                
                System.out.println("-------------------------------------");
                
                
                
                
	}

}
