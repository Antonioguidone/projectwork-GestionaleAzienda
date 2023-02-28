package com.generation.italy.projectwork.dao;

import java.util.List;



import com.generation.italy.projectwork.model.Azienda;

public interface IDaoAziende {

	List<Azienda> aziende();
	
    Azienda azienda(int id);
    
    void add(Azienda a);
    
    void delete(int id);
    
    void update(Azienda a);
	
}
