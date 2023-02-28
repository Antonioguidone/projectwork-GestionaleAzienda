package com.generation.italy.projectwork.util;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

// La maggior parte degli oggetti e dei metodi che utilizzeremo all'interno di quest'interfaccia fanno
// parte dell'API reflect di Java (riflessivo)
// Grazie a questa libreria possiamo operare direttamente sulla struttura di una classe
public interface IMappablePro {

	// Utilizzeremo dei metodi di default: sono dei metodi già implementati che la
	// classe
	// che implementerà l'interfaccia si troverà già fatti
	/*
	 * Questo metodo verrà invocato su di un oggetto (appartenente alla classe che
	 * implementerà quest'interfaccia) e dovrà tramutare questo oggetto in una mappa
	 * con chiave string e valore associato string Prendiamo la classe -> prendiamo
	 * di questa classe i metodi -> nello specifico i getter -> dai metodi getter
	 * recuperiamo il nome delle proprietà -> utilizziamo il nome per caricare le
	 * chiavi della mappa -> utilizziamo i getter per caricare i valori della mappa
	 * Il tutto senza sapere niente
	 */
	default Map<String, String> toMap() {
		// questa sarà la mappa da caricare con le chiavi e i valori
		Map<String, String> ris = new HashMap<>();

		// Dove siamo? questo metodo a chi apparterrà?
		// questo metodo è come se fosse scritto nella classe che implementa
		// quest'interfaccia.
		// Sarà un metodo concreto della classe
		// Verrà invocato su un'istanza di quella classe
		// Qua operiamo sull'oggetto
		// Class è un Generics
		// Class è un tipo "grezzo", converrebbe specificare il tipo di appartenenza
		// Indico semplicemente che non so il tipo specifico, l'unica cosa che so è
		// che questo tipo apparterrà ad una classe che estende IMappablePro
//		Class<Persona> classe = getClass();
		Class<? extends IMappablePro> classe = getClass();

		// della classe voglio recuperare i metodi
		// li registro dentro ad un vettore di oggetti di tipo Method
		Method[] metodi = classe.getMethods();

		// itero il vettore
		for (Method metodo : metodi) {
			// Abbiamo i metodi, tutti quanti
//			System.out.println(metodo);

			// a me servono solamente i metodi getter in quanto dell'oggetto
			// vorrò prendere i valori delle proprietà per caricarli dentro alla mappa

			String nomeMetodo = metodo.getName();
//			System.out.println(nomeMetodo);

			// se il nome inizia con get o con is (per i boolean)
			if (nomeMetodo.startsWith("get") || nomeMetodo.startsWith("is")) {
				// invoco il metodo sull'oggetto su cui è stato invocato il toMap
				// p.getNome() invoco il metodo getNome sul'oggetto p
				// voglio
//				System.out.println(nomeMetodo);
				try {
					// prendo il metodo ad es getId()
					// lo invoco -> p.getId()
					// lui mi ritorna un int con valore 1
					// il toString lo trasforma in
					// 1 -> "1"
					// this indica l'oggetto su cui sto lavorando
					// senza conoscerne il reference, il nome...
//					String valore = metodo.invoke(this).toString();
//					System.out.println(valore);

					Object obj = metodo.invoke(this);
					
					String valore = "";
					
					if (obj != null) {
						valore = obj.toString();
					}
					
					// mi serve la chiave
					// se il nome inizia con get, l'indice di partenza sarà 3
					// altrimenti sarà 2
					int indicePartenza = nomeMetodo.startsWith("get") ? 3 : 2;

					String chiave = nomeMetodo.substring(indicePartenza).toLowerCase();

					ris.put(chiave, valore);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}

		// put ricordo che sovrascrive il valore se la chiave esiste già
		ris.put("class", classe.getSimpleName());

		return ris;
	}

	// Questo metodo andrà a settare i valori contenuti nella mappa nelle proprietà
	// dell'oggetto su cui viene invocato il metodo
	// L'oggetto DEVE esistere.
	// prenderà la mappa e utilizzerà i metodi setter per caricarci i valori

	// Prendiamo la classe -> della classe andiamo a prendere i metodi -> dei metodi
	// andiamo a prendere quelli che iniziano con set -> andiamo, in base al nome
	// del metodo
	// setter, a prendere il valore a quella chiave -> ?? dobbiamo parsare dove
	// serve ->
	// invochiamo il metodo setter andando a passargli come parametro il valore
	// parsato se serve
	default void fromMap(Map<String, String> map) {
		// prendo la classe
		Class<? extends IMappablePro> classe = getClass();

		// dalla classe prendo i suoi metodi
		Method[] metodi = classe.getMethods();

		// itero i metodi
		for (Method metodo : metodi) {
			// del metodo prendo il nome
			String nomeMetodo = metodo.getName();

			// se il metodo parte con set
			if (nomeMetodo.startsWith("set")) {
				// devo eliminare la parte di set
				String chiave = nomeMetodo.substring(3).toLowerCase();

				// grazie alla chiave vado a recuperare dalla mappa il valore associato
				String valore = map.get(chiave);
//				System.out.println(chiave + " " + valore);

				// Abbiamo un problema: il valore è una stringa. Non va sempre bene
				// in quanto i setter di proprietà numeriche, boolean oppure di tipo char
				// vogliono un parametro di tipo differente da String
				// public void setId(int id) -> qua il parametro è un int, se metto una stringa
				// va tutto a male
				// recupero la classe del tipo del parametro
				// ho messo [0] i parametri possono essere pi� di uno
				// gli devo specificare la posizione del parametro da prendere
				Class<?> tipoParametro = metodo.getParameterTypes()[0];
//				System.out.println(metodo + " " + tipoParametro);

				try {
					// setMaggiorenne(boolean maggiorenne) 
					// da qualche parte mi arriva una stringa
					// io controllo se è "1" (immagino arrivi da mysql) oppure "true"
					// se una delle due � vera, metto true come parametro
					// se invece tutte e due le condizioni sono false, metterò false
					if (tipoParametro.equals(boolean.class)) {
						metodo.invoke(this, valore.equals("1") || valore.equals("true"));
					} else if (tipoParametro.equals(char.class)) {
						// "A" e devo tramutarla in 'A' -> char
						// della stringa passa come parametro al metodo il carattere alla posizione 0
						metodo.invoke(this, valore.charAt(0));
					} else if (tipoParametro.isPrimitive()) {
						// se il tipo parametro è primitivo (double, int, long,...)
						
						// Questa è magia... e io sono pigro
						// Non voglio fare un if per ogni tipo primitivo. Quelli rimanenti devo essere parsati
						// Problema: se io ho i tipi primitivi (int, double, long...) esiste un modo
						// per recuperare i rispettivi tipi boxati
						// Array.newInstance(tipoParametro, 1) crea un vettore di grandezza 1 con
						// del tipo del parametro
						// array.get(vettore, posizione) prende da un vettore l'elemento a quella posizione
						// ma lo restituisce boxato int -> Integer
						// di questo elemento prendo la classe
						Class<?> tipoBoxato = Array.get(Array.newInstance(tipoParametro, 1), 0).getClass();
						
						Method[] metodiBoxati = tipoBoxato.getMethods();
						
						for (Method metodoBoxato : metodiBoxati) {
							// Integer ha 3 metodi parseInt, quindi oltre a controllare che parte con
							// parse controllo che abbia un solo parametro
							if (metodoBoxato.getName().startsWith("parse") && 
									metodoBoxato.getParameterCount() == 1 && valore != null) {
//								System.out.println(metodoBoxato);
								// getId l'oggetto su cui invocare il parse (statico quindi null)
								// il valore che c'era nella mappa
								metodo.invoke(this, metodoBoxato.invoke(null, valore));
								break;
							}
						}
					}
					else if(tipoParametro.equals(java.sql.Date.class))
                    {
						// la proprietà dovrà essere di tipo Date
						metodo.invoke(this, Date.valueOf(valore));
					} else {
						// tutti gli altri casi: Stringhe, oggetti, liste ecc ecc
						// setNome(String nome)
						metodo.invoke(this, valore);
					}
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
	}
	// Non voglio fare questo, troppo monotono
//				try {
//					if (tipoParametro.equals(int.class) || tipoParametro.equals(Integer.class)) {
//						// invoca il metodo (setter) su questo oggetto e come parametro mettigli
//						// il valore parsato ad int
//						metodo.invoke(this, Integer.parseInt(valore));
//					}
//				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
//					e.printStackTrace();
//				}
	
	// Non voglio più fare new degli oggetti
	// lo dovrà fare in automatico
	static <T extends IMappablePro> T fromMap(Class<T> type, Map<String, String> map) {
		T ris = null;
		
		if(map != null) {
			try {
				// Dal type che passo da parametro (ad esempio Persona.class) vado a prendere
				// il costruttore senza parametri e me lo salvo dentro ad un oggetto
				// constructor
				Constructor<T> constructor = type.getConstructor(null);
				// Creo l'istanza dell'oggetto utilizzando il costruttore appena salvato
				ris = constructor.newInstance();
				// invoco quindi il metodo from map sull'oggetto creato
				ris.fromMap(map);
			} catch (NoSuchMethodException | SecurityException |
					InstantiationException | IllegalAccessException |
					IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
				System.err.println("Manca il costruttore senza parametri, impossibile istanziare l'oggetto");
			}
		}
		
		return ris;
	}
	
}