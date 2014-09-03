import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

import neo4j.Neo4jCore;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.schema.IndexDefinition;
import org.neo4j.graphdb.schema.Schema;
import org.neo4j.tooling.GlobalGraphOperations;


public class Neo4jSandbox extends Neo4jCore{
	
	private static final String DB_PATH = "../../data/DBs/BatchTest";
	
	static GraphDatabaseService graphDb;
	static Node firstNode;
	static Node secondNode;
	static Relationship relationship;
	
	static Label entityLabel = DynamicLabel.label( "Entity" );
	
	public static void main(String[] args) {
		
		System.out.println("about to open db");
		graphDb = new GraphDatabaseFactory().newEmbeddedDatabase( DB_PATH );
		registerShutdownHook( graphDb );
// Anchor Test	
		Label anchorLabel = DynamicLabel.label( "Anchor" );
		Label partialAnchorLabel = DynamicLabel.label( "PartialAnchor" );
		
//		try (Transaction tx = graphDb.beginTx()) {
//			GlobalGraphOperations global = GlobalGraphOperations.at(graphDb);
//			for(Node n: global.getAllNodesWithLabel(partialAnchorLabel)){
//				System.out.println(n.getProperty("name"));
//				for(Relationship toAnchor: n.getRelationships(Direction.OUTGOING, RelTypes.PARTIALMATCH)){
//					Node anchor = toAnchor.getEndNode();
//					System.out.println("	" + anchor.getProperty("name"));
//				}
//			}
//			tx.success();
//		}
		
// SemSig Test
		String search = "Aristotle";
		System.out.println("Done! Perform test query for '" + search + "'.");
		System.out.println("Entity:");
		try (Transaction tx = graphDb.beginTx()) {
			for ( Node node : graphDb.findNodesByLabelAndProperty( entityLabel, "name", search ) ){
				Node entity = node;
				for(Relationship r: entity.getRelationships(Direction.OUTGOING)){
					System.out.println("anchor of: " + r.getEndNode().getProperty("name"));
				}
				for(Relationship r: entity.getRelationships(Direction.INCOMING)){
					System.out.println("in:  " + r.getStartNode().getProperty("name"));
				}
			}
			
			System.out.println("Anchor");
			for ( Node node : graphDb.findNodesByLabelAndProperty( anchorLabel, "name", search ) ){
				Node entity = node;
				for(Relationship r: entity.getRelationships(Direction.OUTGOING)){
					System.out.println("anchor of: " + r.getEndNode().getProperty("name"));
				}
				for(Relationship r: entity.getRelationships(Direction.INCOMING)){
					System.out.println("in:  " + r.getStartNode().getProperty("name"));
				}
			}
			tx.success();
		}
		
	}
	
	private static void registerShutdownHook(final GraphDatabaseService graphDb) {
		// Registers a shutdown hook for the Neo4j instance so that it
		// shuts down nicely when the VM exits (even if you "Ctrl-C" the
		// running application).
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				graphDb.shutdown();
			}
		});
	}
	
}
