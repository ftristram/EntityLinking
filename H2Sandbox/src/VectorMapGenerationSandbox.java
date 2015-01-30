import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.nustaq.serialization.FSTObjectInput;
import org.nustaq.serialization.FSTObjectOutput;

import configuration.Config;
import stopwatch.Stopwatch;
import tfidf.DocumentFrequency;
import tfidf.TFIDF;
import databaseConnectors.DatabaseConnector;
import databaseConnectors.H2Connector;
import databaseConnectors.InMemoryConnector;
import datatypes.TFIDFResult;
import datatypes.VectorEntry;


public class VectorMapGenerationSandbox {

	final static String dfPath = "../../data/toyData/documentFrequency_fst";
	final static String abstractPath = "../../data/toyData/abstracts.txt";
	final static String dbPathH = "E:/Master Project/data/toyData/anchorH2";
	final static String semsigPath = "../../data/toyData/semsig.txt";
	final static String vectorMapOutputPath = "../../data/toyData/vectorMap";
	final static String inMemoryDataContainerPath = "../data/toyData/inMemoryDataContainer.bin";
	
//	final static String dfPath = "data/documentFrequency_fst";
//	final static String abstractPath = "data/abstracts_cleaned_correct.txt";
//	final static String dbPathH = "~/vectormap/data/h2_anchors_pagelinks";
//	final static String semsigPath = "../semsig/semsig.txt";
//	final static String vectorMapOutputPath = "vectorMap_inMemory";
//	final static String inMemoryDataContainerPath = "../anchor_db/inMemoryDataContainer.bin";
	
	final static int initialMapSize = 15000000;
	
	public VectorMapGenerationSandbox() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
		Stopwatch sw2 = new Stopwatch(Stopwatch.UNIT.HOURS);
		
		// get df object
		FileInputStream fileInputStream = new FileInputStream(dfPath);
		
//		ObjectInputStream objectReader = new ObjectInputStream(fileInputStream);
//		DocumentFrequency df = (DocumentFrequency) objectReader.readObject(); 
//		objectReader.close();
		
		FSTObjectInput in = new FSTObjectInput(fileInputStream);	// read object using FST library
		DocumentFrequency df = (DocumentFrequency)in.readObject();
		in.close();
		
		// get abstract reader
		
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(abstractPath), "UTF8"));
		String line;
		int counter = 0;
		
		// create vector map
		HashMap<Integer, VectorEntry> vectorMap = new HashMap<Integer, VectorEntry>(initialMapSize);
		
		// Set up H2 Connector
		String sql = "select entitySinkIDList from EntityToEntity where EntitySourceID is (?)";
		DatabaseConnector entityDB = new H2Connector(dbPathH, "sa", "", sql, false);
//		DatabaseConnector entityDB = new InMemoryConnector(inMemoryDataContainerPath);
		
		System.out.println("Starting Loop");
		Stopwatch sw = new Stopwatch(Stopwatch.UNIT.SECONDS);
		while((line = br.readLine()) != null){
			counter++;
			
			if(counter % 100000 == 0){
				System.out.println(counter + " - " + sw.stop() + " s");
				sw.start();
			}
			
//			if(counter % 100000 == 0){
//				ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(outputPath + partCounter));
//				out.writeObject(vectorMap);
//				out.close();
//				System.out.println("Wrote 'bigmap_"+partCounter + "' to file - " + sw.stop() + " s");
//				sw.start();
//				partCounter++;
//				vectorMap = new HashMap<Integer, VectorEntry>(initialMapSize);
//			}
			
			// Get EntityID and Semantic Signature
			//String title = StringConverter.convert(br.readLine().replace(" ", "_"), "UTF-8");
			String title = br.readLine().replace(" ", "_");
			line = br.readLine();
			Integer id = entityDB.resolveName(title);
			if(id == null){
				String normalized = Normalizer.normalize(title, Normalizer.Form.NFD);
				normalized = normalized.replaceAll("[^\\p{ASCII}]", "");
				id = entityDB.resolveName(normalized);
				if(id == null){
					//System.err.println(title + "/" + normalized + " --> Not in DB");
					continue;
				}
			}
			
			//System.out.println("Start on TFIDF");
			// calculate TF/IDF
			
			List<TFIDFResult> resultList = new ArrayList<TFIDFResult>();			
			resultList = TFIDF.compute(line, df);
			Collections.sort(resultList);
			Collections.reverse(resultList);
			
			VectorEntry vEntry = new VectorEntry();
			for(int i = 0; i < 100 && i < resultList.size(); i++){
				vEntry.tfVector[i] = df.getWordID(resultList.get(i).token);
				vEntry.tfScore[i]  = resultList.get(i).tfidf;
			}
			vectorMap.put(id, vEntry);

			
		}
		br.close();

		
		System.out.println("Done with TF/IDF. #Abstracts: " + counter);
		
		System.out.println("Start on Semantic Signature");
		System.err.println("Start on Semantic Signature");
		counter = 0;
		sw.start();
		br = new BufferedReader(new InputStreamReader(new FileInputStream(semsigPath)));
		while((line = br.readLine()) != null){
			//line = line.toLowerCase();
			counter++;
			if(counter % 100000 == 0){
				System.out.println(counter + " semsig entries:\t" + sw.stop() + " s");
				sw.start();
			}
			Integer id = entityDB.resolveName(line);
			if(!vectorMap.containsKey(id)){
				System.err.println(line + " not in vectorMap");
				while(line != null && !line.equals("")) line = br.readLine(); // skip entry
				continue;
			}else{
				VectorEntry entry = vectorMap.get(id);
				for(int i = 0; i < 100; i++){
					line = br.readLine();
					if (line == null || line.equals(""))
						break;
					String lineArray[] = line.split("\t");
					Integer tmpID = entityDB.resolveName(lineArray[0]);
					if (tmpID != null) {
						entry.semSigVector[i] = tmpID;
						entry.semSigCount[i] = Integer.parseInt(lineArray[1]);
					} else {
						i--; // skip this line
					}
				}

				// include entity in its own signature
//				entry.semSigVector[0] = id;
//				if(entry.semSigCount[1] > 0) entry.semSigCount[0] = (int)Math.floor(entry.semSigCount[1] * 1.5);
//				else entry.semSigCount[0] = 1;
				
				while(line != null && !line.equals("")) line = br.readLine();
			}
		}
		
		br.close();
		entityDB.close();
		System.out.println("Done with semsig."  + " - time taken: " + sw2.stop() + " hours");
		
		System.out.println("Write map to binary file");
		sw.start();
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(vectorMapOutputPath));
		out.writeObject(vectorMap);
		out.close();
		
		System.out.println(("Write to binary file: Done! Write map to text file."));
		BufferedWriter fw = new BufferedWriter(new FileWriter(vectorMapOutputPath + ".txt"));
		for(Entry<Integer, VectorEntry> entry: vectorMap.entrySet()){
			VectorEntry vEntry = entry.getValue();
			
			fw.write(entry.getKey() + "\t" + vEntry.pagerank + "\n");
			for(Integer i: vEntry.semSigVector) fw.write(i + "\t");
			fw.write("\n");
			for(Integer i: vEntry.semSigCount) fw.write(i + "\t");
			fw.write("\n");
			for(Integer i: vEntry.tfVector) fw.write(i + "\t");
			fw.write("\n");
			for(Float f: vEntry.tfScore) fw.write(f + "\t");
			fw.write("\n");
			fw.write("\n");
		}
		fw.close();
		
//		System.out.println("Write map to fst binary file");
//		FSTObjectOutput out_fst = new FSTObjectOutput(new FileOutputStream(vectorMapOutputPath + "_fst"));
//		out_fst.writeObject( vectorMap );
//		out_fst.close(); // required !
		System.out.println("All done! Time taken for file writing: " + sw.stop() + " s");
	}

}