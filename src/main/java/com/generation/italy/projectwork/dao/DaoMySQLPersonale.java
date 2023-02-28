package com.generation.italy.projectwork.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.generation.italy.projectwork.model.Azienda;
import com.generation.italy.projectwork.model.Personale;
import com.generation.italy.projectwork.util.BasicDao;
import com.generation.italy.projectwork.util.IMappablePro;

@Repository
public class DaoMySQLPersonale extends BasicDao implements IDaoPersonale {

	public DaoMySQLPersonale(
			@Value("${db.address}") String dbAddress, 
			@Value("${db.user}") String user,
			@Value("${db.password}") String password) {
		super(dbAddress, user, password);
		
	}

	@Override
	public List<Personale> personale() {
		List<Personale> ris = new ArrayList<>();
		
		List<Map<String, String>> maps = getAll("SELECT * FROM personale");
		System.out.println(maps);
		for (Map<String, String> map : maps) {
			ris.add(IMappablePro.fromMap(Personale.class, map));
		}
		
		return ris;
	}

	@Override
	public Personale personale(int id) {
		Personale ris = null;
		
		Map<String, String> map = getOne("SELECT * FROM personale WHERE personale_id = ?", id);
		
		if (map != null) {
			ris = IMappablePro.fromMap(Personale.class, map);
			setAziende(ris,map);
			
		}
		
		return ris;
	}

	private void setAziende(Personale ris, Map<String, String> map) {
		Map<String, String> mapAzienda = getOne("SELECT * FROM aziende WHERE azienda_id = ?",
				map.get("id_azienda"));
		//===========================================================
		ris.setAziende(IMappablePro.fromMap(Azienda.class, mapAzienda));
}

	@Override
	public void add(Personale p) {
		execute("INSERT INTO personale (nome, cognome, dob, stipendio, data_assunzione, ruolo, id_azienda) VALUES "
				+ "(?, ?, ?, ?, ?, ?, ?)", 
				p.getNome(),p.getCognome(),
				p.getDob(),p.getStipendio(),
				p.getData_assunzione(), 
				p.getRuolo(),
				p.getAziende().getAzienda_id());
	}
	
	
	@Override
	public void delete(int id) {
		execute("DELETE FROM personale WHERE personale_id = ?", id);
		
		}

	@Override
	public void update(Personale p) {
		execute("UPDATE personale SET nome = ?, cognome = ?, dob = ?, stipendio= ?,  "
				+ "data_assunzione = ?, ruolo= ?, id_azienda= ? WHERE personale_id = ?",
				p.getNome(), p.getCognome(), p.getDob(), p.getStipendio(),
				p.getData_assunzione(), p.getRuolo(), p.getAziende().getAzienda_id(), p.getPersonale_id());

	}
	
	
	@Override
	public List<Personale> personaleAzienda(int id) {
		List<Personale> ris = new ArrayList<>();
		
		List<Map<String, String>> maps = getAll("SELECT * FROM aziende INNER JOIN personale on aziende.azienda_id = personale.id_azienda "
				+ "WHERE aziende.azienda_id = ?", id);
		System.out.println(maps);
		for (Map<String, String> map : maps) {
			ris.add(IMappablePro.fromMap(Personale.class, map));
		}
		
		return ris;
	}

}
