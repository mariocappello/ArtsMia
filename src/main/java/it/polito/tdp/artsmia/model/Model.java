package it.polito.tdp.artsmia.model;

import java.util.*;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.traverse.DepthFirstIterator;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;
import it.polito.tdp.artsmia.db.Connessione;

public class Model {
	
	ArtsmiaDAO artsmiaDao;
	Map<Integer ,ArtObject> idMap;
	Graph<ArtObject,DefaultWeightedEdge> grafo;
	
	public Model() {
		
		artsmiaDao = new ArtsmiaDAO();
		idMap = new HashMap<Integer, ArtObject>();
		grafo = new SimpleWeightedGraph <ArtObject,DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
	}
	
	
	public void creaGrafo() {
		// occhio anche al caso in cui il grafo vada definito nel crea grafo, quando
		// si definisce al di fuori vuol dire che o si crea un solo grafo o c'è il
		// rischio che il grafo venga riscritto/sovrascritto
		
		// aggiungo i vertici
		
		List<ArtObject> listaVertici=artsmiaDao.listObjects(idMap);
		Graphs.addAllVertices(grafo, listaVertici);
		
		// aggiungo gli archi
		
		List<Connessione> listaArchi = artsmiaDao.getOggettiConnessi(idMap);
		for(Connessione a : listaArchi) {
			Graphs.addEdgeWithVertices(grafo, a.getId1() , a.getId2(), a.getPeso());
			
		}
		
		System.out.println("VERTICI" + grafo.vertexSet().size());
		System.out.println("ARCHI" + grafo.edgeSet().size());
	}
	
	public boolean getEsistenzaVertice(int id ) {
		return artsmiaDao.getEsistenzaVertice(id);
	}
	
	
	public boolean isGrafoCreato() {
		if (grafo.vertexSet().size()>0 ) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean isOggettoNelGrafo (Integer id) {
		if (idMap.get(id) != null) {
			return true;
		}
		else {
			return false;
		}
	}
	
	

}
