package main.java.entityLinker.evaluation;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

import main.java.utility.Stopwatch;
import main.java.datatypes.FragmentCandidateTuple;
import main.java.datatypes.SimpleFileWriter;
import main.java.datatypes.annotatedSentence.AnnotatedSentence;
import main.java.datatypes.annotatedSentence.Candidate;
import main.java.datatypes.annotatedSentence.Fragment;
import main.java.datatypes.databaseConnectors.DatabaseConnector;
import main.java.datatypes.databaseConnectors.H2Connector;
import main.java.datatypes.graph.Graph;
import main.java.datatypes.graph.GraphEdge;
import main.java.datatypes.graph.Node;

public class BabelfyEvaluation extends EvaluationEngine{

	private DatabaseConnector semanticSignatureDB;
	private Graph<FragmentCandidateTuple> graph;
	private double numberOfFragments = 0.0;
	private double minimumScore = 0.0;
	private int ambiguityLevel = 1;
	
	public double lookUpTime = 0.0;
	public double searchSetTime = 0.0;
	
	static HashMap<Integer, ArrayList<Integer>> semsig = null;
	
	public BabelfyEvaluation(DatabaseConnector semanticSignatureDB, double minimumScore, int ambiguityLevel){
		this.semanticSignatureDB = semanticSignatureDB;
		this.minimumScore = minimumScore;
		this.ambiguityLevel = ambiguityLevel;
		
		if(semsig == null){
			try{
			FileInputStream fileInputStream = new FileInputStream("/home/felix/semsig/semsigObject.bin");
			ObjectInputStream objectReader = new ObjectInputStream(fileInputStream);
			System.out.println("Semsig map not loaded yet. Loading now...");
			semsig = (HashMap<Integer, ArrayList<Integer>>) objectReader.readObject();
			objectReader.close();
			fileInputStream.close();
			System.out.println("Done.");
			}catch(Exception e){
				e.printStackTrace();
				System.exit(-3);
			}
		}
	}
	
	private void writeGraphToDotFile(String path, Graph<FragmentCandidateTuple> graph, H2Connector connector){
		SimpleFileWriter fw = new SimpleFileWriter(path);
		fw.writeln("strict digraph {");
		fw.writeln("overlap = false;");
		fw.writeln("splines = true");
		
		for(Node<FragmentCandidateTuple> n: graph.nodeMap.values()){
			for(GraphEdge<FragmentCandidateTuple> e: n.outgoingEdges){
				fw.writeln("\t" + n + "[label=\"" + connector.resolveID(n.toString()) + "\"];");
				fw.writeln("\t" + e.sink + "[label=\"" + connector.resolveID(e.sink.toString()) + "\"];");
				String tmp = n + " -> " + e.sink + ";";
				fw.writeln("\t" + tmp);
			}
		}
		
		fw.writeln("}");
		fw.close();
	}
	
	private Graph<FragmentCandidateTuple> trimToDenseSubgraph(Graph<FragmentCandidateTuple> graph){
		Graph<FragmentCandidateTuple> trimmedGraph = graph.deepcopy();
		double avrgDegree = 0.0;
		
		for (int sanityCounter = 0; sanityCounter < 50000; sanityCounter++) {
			// Get most ambiguous fragment
			Fragment mostAmbigousFragment = null;
			HashMap<Fragment, Integer> tmpMap = new HashMap<Fragment, Integer>();
			int max = 0;
			for (FragmentCandidateTuple fct : graph.nodeMap.keySet()) {
				if(fct.fragment == null) continue;
				if (tmpMap.get(fct.fragment) == null){
					tmpMap.put(fct.fragment, 1);
					if(1 > max){
						max = 1;
						mostAmbigousFragment = fct.fragment;
					}
				}	
				else{
					int currentCount = tmpMap.get(fct.fragment) + 1;
					tmpMap.put(fct.fragment, currentCount);
					if(currentCount > max){
						max = currentCount;
						mostAmbigousFragment = fct.fragment;
					}
				}
					
			}
			
			if(sanityCounter % 500 == 0) System.out.println(sanityCounter + " - Max: " + max + " AmbiguityLevel: " + ambiguityLevel + " - graphsize: " + graph.size());
			if(max <= ambiguityLevel) return trimmedGraph; // end algorithm if ambiguity low
			
			//TODO: only update nodes that are changed
			scoreAllFragments();
			
			double score = Double.MAX_VALUE;
			FragmentCandidateTuple weakestCandidate = null;
			for (FragmentCandidateTuple fct : graph.nodeMap.keySet()) {
				//System.out.println(fct.fragment + " ?= " + mostAmbigousFragment + "\t" + fct.score);
				if(fct == null) continue;
				if(fct.fragment == mostAmbigousFragment && fct.score < score){
					score = fct.score;
					weakestCandidate = fct;
				}
			}
			if(weakestCandidate == null){
				System.err.println("WeakestCandidate is null! Terminating loop.");
				System.err.println("Most ambiguous fragment origin entity:" + mostAmbigousFragment.getOriginEntity());
				//System.exit(-2);
				return trimmedGraph;
			}
			graph.removeNode(weakestCandidate);
			
			if(graph.avrgDegree() > avrgDegree){
				avrgDegree = graph.avrgDegree();
				trimmedGraph = graph.deepcopy();
			}
		}
		
		System.out.println("		Graph trimming cancelled! Loop too long!");
		return trimmedGraph;
	}
	
	private void scoreAllFragments(){
		HashMap<Fragment, Double> fragmentWeight = new HashMap<Fragment, Double>();
		
		for(Node<FragmentCandidateTuple> node: graph.nodeMap.values()){
			node.content.weight = weight(node.content);
			
			double tmpScore = node.degree() * node.content.weight;
			if(fragmentWeight.containsKey(node.content.fragment)){	
				fragmentWeight.put(node.content.fragment, fragmentWeight.get(node.content.fragment) + tmpScore);
			}else{
				fragmentWeight.put(node.content.fragment, tmpScore);
			}
		}
		
		// debug start
//		Node<FragmentCandidateTuple> david;
//		LinkedList<Node<FragmentCandidateTuple>> list = new LinkedList<Node<FragmentCandidateTuple>>();
//		for(Node<FragmentCandidateTuple> node: graph.nodeMap.values()){
//			list.add(node);
//			if(node.content.candidate.equals("David_Beckham")) david = node;
//		}
//		Collections.sort(list, new Comparator<Node<FragmentCandidateTuple>>(){
//			@Override
//			public int compare(Node<FragmentCandidateTuple> o1, Node<FragmentCandidateTuple> o2) {
//				if(o2.content.weight > o1.content.weight) return 1;
//				else if(o2.content.weight < o1.content.weight) return -1;
//				else return 0;
//			}
//			
//		});
		
		// debug end
		
		for(Node<FragmentCandidateTuple> nodeOrigin: graph.nodeMap.values()){
			double tmp = 0.0;
			tmp = fragmentWeight.get(nodeOrigin.content.fragment);
			
			double part1 = (nodeOrigin.degree() * nodeOrigin.content.weight);
			double score = part1 / tmp;
			nodeOrigin.content.score = score;
		}
	}
	
	private double weight(FragmentCandidateTuple fct) {
		Node<FragmentCandidateTuple> node = graph.getNode(fct);
		TreeSet<Fragment> connectingFragments = new TreeSet<Fragment>(new Comparator<Fragment>() {
			@Override
			public int compare(Fragment f1, Fragment f2) {
				if(f1 == f2) return 0;
				else if(f1.start - f2.start != 0){
					return f1.start - f2.start;
				}else return f1.stop - f2.stop;
			}
		});
		for (GraphEdge<FragmentCandidateTuple> edge : node.incomingEdges) {
			connectingFragments.add(edge.source.content.fragment);
		}
		for (GraphEdge<FragmentCandidateTuple> edge: node.outgoingEdges){
			connectingFragments.add(edge.sink.content.fragment);
		}
		//TODO: check division by 0?
		double weight = (double)connectingFragments.size() / (double)(numberOfFragments - 1);
		//System.out.println("incoming/outgoing: " + node.incomingEdges.size() + "/" + node.outgoingEdges.size() +" weight: " + weight + " connectingSize: "+ connectingFragments.size() + " nr of fragments: " + (numberOfFragments - 1));
		return weight;
	}
	
	@Override
	public void evaluate(AnnotatedSentence annotatedSentence) {
		System.out.println("Starting evaluation... ");
		//SimpleFileWriter fw = new SimpleFileWriter("../../data/out.txt");
		//.writeln("Nodes:");
		
		Stopwatch stopwatch = new Stopwatch(Stopwatch.UNIT.SECONDS);
		List<Fragment> fragmentList = annotatedSentence.getFragmentList();
		numberOfFragments = fragmentList.size();
		// add fragments/candidates to graph
		graph = new Graph<FragmentCandidateTuple>();
		int counter = 0;
		for(Fragment fragment: fragmentList){
			for(Candidate candidate: fragment.getCandidates()){
				String entity = candidate.getEntity().split("_")[0];
				graph.addNode(new FragmentCandidateTuple(entity, fragment));
				counter++;
				//if(counter % 1000 == 0) System.out.println(counter);
				//fw.writeln(fragment.originWord + " - candidate: " + ((H2Connector)semanticSignatureDB).resolveID(candidate.getEntity()));
			}
		}
		
		System.out.println(graph.nodeMap.size() + " graph nodes added. " + stopwatch.stop() + " s");
		stopwatch.start();
		// build edges
		//fw.writeln("\nEdges:");
		Stopwatch sw = new Stopwatch(Stopwatch.UNIT.MILLISECONDS);
		counter = 0;
		for(Node<FragmentCandidateTuple> nodeSource: graph.nodeMap.values()){
			counter++;
			//if(counter % 1000 == 0) System.out.println(counter);
			
			sw.start();
			TreeSet<String> semSig = new TreeSet<String>();
			//long start = System.nanoTime();
			
			
			//List<String> tmp = semanticSignatureDB.getFragmentTargets(nodeSource.content.candidate);
			List<String> tmp = new LinkedList();
			Integer id = Integer.parseInt(nodeSource.content.candidate);
			if(semsig.containsKey(id)){
				for(Integer semsigEntryId: semsig.get(id)){
					tmp.add(semsigEntryId.toString());
				}
			}else{
				System.err.println("No semsig entry for: " + semanticSignatureDB.resolveID(nodeSource.content.candidate));
			}
			
			
			//long end = System.nanoTime();
			//double passedTime = (end - start) / 1000000000.0;
			//System.out.println("Passed time: " + passedTime + " s");
			
			sw.stop();
			lookUpTime += sw.doubleTime;
			sw.start();
			for (String s : tmp) {
				semSig.add(s);
			}
			
			
			
			for(Node<FragmentCandidateTuple> nodeSink: graph.nodeMap.values()){
				// if fragment is the same, skip
				if(nodeSource.content.fragment == nodeSink.content.fragment) continue;
				
				if(semSig.contains(nodeSink.content.candidate)){ // conditions fullfilled, build edge
					//System.out.println("	Adding edge: " + nodeSource.content.candidate + " --> " + nodeSink.content.candidate);
					graph.addEdge(nodeSource, nodeSink);
					//fw.writeln(semanticSignatureDB).resolveID(nodeSource.content.candidate) + " --> " + (semanticSignatureDB).resolveID(nodeSink.content.candidate));
				}
			}
			sw.stop();
			searchSetTime += sw.doubleTime;
		}
		System.out.println("Graph edges added. Time: " + stopwatch.stop() + " s");
		//fw.close();
		//writeGraphToDotFile("../../data/graph.dot", graph, semanticSignatureDB);
		
		
		// Trim Graph
		stopwatch.start();
		trimToDenseSubgraph(graph);
		scoreAllFragments();
		System.out.println("Graph trimmed. Time: " + stopwatch.stop() + " s");
		//writeGraphToDotFile("../../data/graph_trimmed.dot", graph, (H2Connector)semanticSignatureDB);
		
		HashMap<Fragment, Double> scoreMap = new HashMap<Fragment, Double>();
		for(Node<FragmentCandidateTuple> node: graph.nodeMap.values()){
			Double tmp = scoreMap.get(node.content.fragment);
			if(tmp == null || tmp < node.content.score){
				node.content.fragment.probability = node.content.score;
				node.content.fragment.setEntity(semanticSignatureDB.resolveID(node.content.candidate));
				scoreMap.put(node.content.fragment, node.content.score);
			}
		}
		
		//annotatedSentence.assign(minimumScore);
		
//		if(semanticSignatureDB instanceof H2Connector){
//			for(Word w: annotatedSentence.getWordList()){
//				Fragment f = w.getDominantFragment();
//				if(f != null){
//					String tmp = f.getID();
//					tmp = ((H2Connector)semanticSignatureDB).resolveID(tmp);
//					f.setEntity(tmp);
//				}
//			}
//		}
		
		System.out.println("	Evaluation complete.");
	}

}
