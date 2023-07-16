package it.polito.tdp.artsmia.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.artsmia.model.ArtObject;

public class ArtsmiaDAO {

	public List<ArtObject> listObjects(Map<Integer, ArtObject> idMap) {
		
		String sql = "SELECT * from objects";
		List<ArtObject> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				ArtObject artObj = new ArtObject(res.getInt("object_id"), res.getString("classification"), res.getString("continent"), 
						res.getString("country"), res.getInt("curator_approved"), res.getString("dated"), res.getString("department"), 
						res.getString("medium"), res.getString("nationality"), res.getString("object_name"), res.getInt("restricted"), 
						res.getString("rights_type"), res.getString("role"), res.getString("room"), res.getString("style"), res.getString("title"));
				
				result.add(artObj);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Connessione> getOggettiConnessi(Map<Integer, ArtObject> idMap) {
		String sql = "SELECT eo1.object_id AS id1, eo2.object_id AS id2, COUNT(*) AS peso "
				+ "FROM exhibition_objects eo1, exhibition_objects eo2 "
				+ "WHERE eo1.exhibition_id=eo2.exhibition_id AND eo1.object_id!=eo2.object_id AND eo1.object_id>eo2.object_id "
				+ "GROUP BY eo1.object_id, eo2.object_id ";
		
		List<Connessione> connessioni = new ArrayList<Connessione>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				Connessione connessione = new Connessione(idMap.get(res.getInt("id1")), idMap.get(res.getInt("id2")), (res.getInt("peso")));
				
				connessioni.add(connessione);
			}
			conn.close();
			
			return connessioni;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	public boolean getEsistenzaVertice(int id ) {
		
		boolean esistente=false;
		
		String sql= "SELECT object_id "
				+ "FROM objects "
				+ "WHERE object_id=? "
				+ "GROUP BY object_id ";
		
		List<ArtObject> esistenza = new ArrayList<ArtObject>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				ArtObject a = new ArtObject(res.getInt("object_id"));
				esistenza.add(a);
			}
			
			if(esistenza.size()>0) {
				esistente=true;
			}
			
			conn.close();
			
			return esistente;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	
	
	
	
	
}
