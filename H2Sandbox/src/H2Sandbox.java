import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.LinkedList;

import utility.Stopwatch;
import databaseBuilder.h2.H2AnchorBuilder;
import databaseBuilder.h2.H2DBCreator;
import databaseBuilder.h2.H2PageLinksBuilder;
import databaseBuilder.h2.H2WeightBuilder;
import datatypes.databaseConnectors.H2Connector;



public class H2Sandbox {

	public H2Sandbox() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args){
		String dbPath = "../../data/toyData/anchorH2";
//		String filePath = "../../data/Wikipedia/Pagelinks/test/toyset.txt";		
		String filePath2 = "../../data/toyData/anchors.txt";		
		String stopwordsPath = "../../data/stopwords.txt";
		
//		dbPath = "../../data/H2/AnchorsPlusPagelinks/h2_anchors_pagelinks";
		
//		String dbPath = "/home/felix/anchor_db/h2/h2_anchors";
//		String filePath = "page_links_en.nt";
//		String filePath2 = "anchors.txt";
//		String stopwordsPath = "stopwords.txt";
		
        H2DBCreator dbCreator = new H2DBCreator(dbPath);
        try {
			dbCreator.create();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        try {
			H2AnchorBuilder builder2 = new H2AnchorBuilder(dbPath, filePath2, "sa", "", stopwordsPath);
			builder2.run();
			
//        	H2PageLinksBuilder builder = new H2PageLinksBuilder(dbPath, filePath, "sa", "");
//			builder.run();
			
//			H2WeightBuilder weightBuilder = new H2WeightBuilder(dbPath, "sa", "");
//			weightBuilder.run();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		/*
		LinkedList<String> result = new LinkedList<String>();
		String sql = "select ANCHOR  from ANCHORID where ID in (select PARTIALANCHORTOANCHOR.ANCHORID from PARTIALANCHORTOANCHOR, PARTIALANCHORID where PARTIALANCHORID.ID = PARTIALANCHORTOANCHOR.PARTIALANCHORID and PARTIALANCHOR is (?)) ";
		try {
			H2Connector connector = new H2Connector("E:/Master Project/data/H2/Anchors/h2Anchors", "sa", "", sql);
			
			long timeStart = System.nanoTime();
			Stopwatch sw = new Stopwatch(Stopwatch.UNIT.SECONDS);
			result = connector.getFragmentTargets("David");
			long timeEnd = System.nanoTime();
			double passedTime = (timeEnd - timeStart) / 1000000000.0;
			sw.stop();
			
			connector.close();
			for(String s: result) System.out.println(s);
			System.out.println("Passed time: " + passedTime + " s" + sw);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
        System.out.println("All done.");
	}

}
