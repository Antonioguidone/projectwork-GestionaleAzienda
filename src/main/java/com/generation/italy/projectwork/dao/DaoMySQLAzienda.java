package com.generation.italy.projectwork.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.generation.italy.projectwork.model.Azienda;
import com.generation.italy.projectwork.util.BasicDao;
import com.generation.italy.projectwork.util.IMappablePro;

@Repository
public class DaoMySQLAzienda extends BasicDao implements IDaoAziende {

	public DaoMySQLAzienda(
			@Value("${db.address}") String dbAddress, 
			@Value("${db.user}") String user,
			@Value("${db.password}") String password) {
		super(dbAddress, user, password);
	
	}

	@Override
	public List<Azienda> aziende() {
		List<Azienda> ris = new ArrayList<>();
		
		List<Map<String, String>> maps = getAll("SELECT * FROM aziende");
		System.out.println(maps);
		for (Map<String, String> map : maps) {
			ris.add(IMappablePro.fromMap(Azienda.class, map));
		}
		
		return ris;
	}

	@Override
	public Azienda azienda(int id) {
		Azienda ris = null;
		
		Map<String, String> map = getOne("SELECT * FROM aziende WHERE azienda_id = ?", id);
		
		if (map != null) {
			ris = IMappablePro.fromMap(Azienda.class, map);
			
		}
		
		return ris;
	}

	@Override
	public void add(Azienda a) {
		execute("INSERT INTO aziende (ragione_sociale, partita_iva, indirizzo, email, n_telefono) VALUES "
				+ "(?, ?, ?, ?, ?)", a.getRagione_sociale(), a.getPartita_iva(), a.getIndirizzo(),
				a.getEmail(), a.getN_telefono());
		
	}

	@Override
	public void delete(int id) {
		execute("DELETE FROM aziende WHERE azienda_id = ?", id);
		
	}

	@Override
	public void update(Azienda a) {
		execute("UPDATE aziende SET ragione_sociale = ?, partita_iva = ?, indirizzo = ?, email = ?, "
				+ "n_telefono = ? WHERE azienda_id = ?", a.getRagione_sociale(), a.getPartita_iva(), a.getIndirizzo(),
				a.getEmail(), a.getN_telefono(), a.getAzienda_id());
				
		
	}

}
