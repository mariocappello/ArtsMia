package it.polito.tdp.artsmia.db;

import java.util.List;
import java.util.Map;

import it.polito.tdp.artsmia.model.ArtObject;

public class TestArtsmiaDAO {

	public static void main(String[] args) {

		ArtsmiaDAO dao = new ArtsmiaDAO();
			
		List<ArtObject> objects = dao.listObjects(null);
		System.out.println(objects.get(0));
		System.out.println(objects.size());
		
		
		//List<Connessione> edges = dao.getOggettiConnessi(null);
		//System.out.println(edges.get(0));
		//System.out.println(edges.size());
	}

}
