package entityLinker;

import java.io.IOException;
import java.util.List;

import nif.ITSRDF_SchemaGen;
import nif.NIF_SchemaGen;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.util.Triple;

public class InputStringHandler {
	AbstractSequenceClassifier<CoreLabel> classifier;
	Model model;
	int stringCounter = 0;
	
	public InputStringHandler() throws ClassCastException, ClassNotFoundException, IOException{
		String serializedClassifier = "E:/Master Project/data/stanford models/english.conll.4class.distsim.crf.ser.gz";
		classifier = CRFClassifier.getClassifier(serializedClassifier);
		
		// Create Model
		model = ModelFactory.createDefaultModel();
		model.setNsPrefix("nif", NIF_SchemaGen.getURI());
		model.setNsPrefix("itsrdf", ITSRDF_SchemaGen.getURI());
	}
	
	public void handleString(String input){
		Resource tmp = model.createResource("Sentence_" + stringCounter++).addProperty(NIF_SchemaGen.isString, input);
		model.add(tmp, RDF.type, NIF_SchemaGen.Context);
		model.add(tmp, RDF.type, NIF_SchemaGen.String);
		
		List<Triple<String,Integer,Integer>> triples = classifier.classifyToCharacterOffsets(input);
		for(Triple<String,Integer,Integer> triple: triples){
			//System.out.println(triple);
			String token = input.substring(triple.second, triple.third);
			Resource wordResource = model.createResource(tmp + "#char=" + triple.second + "," + triple.third).addProperty(NIF_SchemaGen.referenceContext, tmp);
			model.add(wordResource, NIF_SchemaGen.anchorOf, token);
			model.add(wordResource, NIF_SchemaGen.beginIndex, triple.second.toString());
			model.add(wordResource, NIF_SchemaGen.endIndex, triple.third.toString());
		}
	}
	
	public int getStringCounter(){
		return stringCounter;
	}
	
	public Model getModel(){
		return model;
	}
}













