package com.generation.italy.projectwork.dao;

import java.util.List;


import com.generation.italy.projectwork.model.Personale;

public interface IDaoPersonale {

	List<Personale> personale();
	
    Personale personale(int id);
    
    void add(Personale p);
    
    void delete(int id);
    
    void update(Personale p);
    
    List<Personale> personaleAzienda(int id);

}
