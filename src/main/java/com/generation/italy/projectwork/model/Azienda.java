package com.generation.italy.projectwork.model;

import com.generation.italy.projectwork.util.IMappablePro;

public class Azienda implements IMappablePro  {

	private int azienda_id; 
	private String ragione_sociale; 
	private String partita_iva; 
	private String indirizzo; 
	private String email; 
	private String n_telefono;
	
	public Azienda() {
		super();
	}

	
	public Azienda(int azienda_id, String ragione_sociale, String partita_iva, String indirizzo, String email,
			String n_telefono) {
		super();
		this.azienda_id = azienda_id;
		this.ragione_sociale = ragione_sociale;
		this.partita_iva = partita_iva;
		this.indirizzo = indirizzo;
		this.email = email;
		this.n_telefono = n_telefono;
	}

	

	public int getAzienda_id() {
		return azienda_id;
	}


	public void setAzienda_id(int azienda_id) {
		this.azienda_id = azienda_id;
	}


	public String getRagione_sociale() {
		return ragione_sociale;
	}

	public void setRagione_sociale(String ragione_sociale) {
		this.ragione_sociale = ragione_sociale;
	}

	public String getPartita_iva() {
		return partita_iva;
	}

	public void setPartita_iva(String partita_iva) {
		this.partita_iva = partita_iva;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getN_telefono() {
		return n_telefono;
	}

	public void setN_telefono(String n_telefono) {
		this.n_telefono = n_telefono;
	}

	

	
	
}
