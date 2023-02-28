package com.generation.italy.projectwork.model;



import com.generation.italy.projectwork.util.IMappablePro;

public class Personale implements IMappablePro {

	private int personale_id; 
	private String nome; 
	private String cognome; 
	private String foto_path; 
	private String dob; 
	private double stipendio; 
	private String data_assunzione; 
	private String ruolo; 
	private Azienda aziende;
	
	public Personale() {
		super();
	}
	
	public Personale(int personale_id, String nome, String cognome, String foto_path, String dob, double stipendio,
			String data_assunzione, String ruolo, Azienda aziende) {
		super();
		this.personale_id = personale_id;
		this.nome = nome;
		this.cognome = cognome;
		this.foto_path = foto_path;
		this.dob = dob;
		this.stipendio = stipendio;
		this.data_assunzione = data_assunzione;
		this.ruolo = ruolo;
		this.aziende = aziende;
	}

	

	public int getPersonale_id() {
		return personale_id;
	}

	public void setPersonale_id(int personale_id) {
		this.personale_id = personale_id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getFoto_path() {
		return foto_path;
	}

	public void setFoto_path(String foto_path) {
		this.foto_path = foto_path;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public double getStipendio() {
		return stipendio;
	}

	public void setStipendio(double stipendio) {
		this.stipendio = stipendio;
	}

	public String getData_assunzione() {
		return data_assunzione;
	}

	public void setData_assunzione(String data_assunzione) {
		this.data_assunzione = data_assunzione;
	}

	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

	public Azienda getAziende() {
		return aziende;
	}

	public void setAziende(Azienda aziende) {
		this.aziende = aziende;
	}


	
	
	
	



}
