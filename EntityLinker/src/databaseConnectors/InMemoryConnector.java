package databaseConnectors;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.LinkedList;
import java.util.Map;

import datatypes.InMemoryDataContainer;

public class InMemoryConnector extends DatabaseConnector {

	// Entities
	final String[] idToEntity;
	final Map<String, Integer> entityToID;
	
	// Anchors
	final Map<String, Integer> anchorID;
	final int[][] anchorToCandidates;
	final int[][] anchorToCandidatesCount;
	
	public InMemoryConnector(String inMemoryDataContainerFilePath) throws ClassNotFoundException, IOException {
		FileInputStream fileInputStream = new FileInputStream(inMemoryDataContainerFilePath);
		ObjectInputStream objectReader = new ObjectInputStream(fileInputStream);
		InMemoryDataContainer container = (InMemoryDataContainer) objectReader.readObject();
		objectReader.close();
		
		idToEntity = container.idToEntity;
		entityToID = container.entityToID;
		anchorID = container.anchorID;
		anchorToCandidates = container.anchorToCandidates;
		anchorToCandidatesCount = container.anchorToCandidatesCount;
	}

	@Override
	public String resolveID(String id) {
		int index = Integer.parseInt(id);
		return idToEntity[index];
	}

	@Override
	public Integer resolveName(String name) {
		return entityToID.get(name);
	}

	@Override
	public LinkedList<String> getFragmentTargets(String fragment) {
		//fragment = fragment.toLowerCase();
		LinkedList<String> result = new LinkedList<String>();
		Integer id = anchorID.get(fragment);
		if (id != null) {
			for (int i = 0; i < anchorToCandidates[id].length; i++) {
				result.add(anchorToCandidates[id][i] + "_" + anchorToCandidatesCount[id][i]);
			}
		}else{
			//System.err.println("InMemoryConnector: Can't find fragment '" + fragment + "'");
		}

		return result;
	}

	@Override
	public boolean fragmentExists(String fragment) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

}