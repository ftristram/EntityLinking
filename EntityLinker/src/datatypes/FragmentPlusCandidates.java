package datatypes;

import java.util.LinkedList;

public class FragmentPlusCandidates {
	//TODO: create fragment-container class so we wouldn't have to use EntityOccurance
	public EntityOccurance fragment;
	public LinkedList<String> candidates;
	
	public FragmentPlusCandidates(EntityOccurance fragment, LinkedList<String> candidates){
		this.fragment = fragment;
		this.candidates = candidates;
	}
}
