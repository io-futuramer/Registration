package io.futuramer.registration.dao;

import io.futuramer.registration.model.Appuser;

public interface UserDao {
	
	Appuser findById(int id);

	Appuser findUserByUsername(String username);

    Appuser findUserByEmail(String email);
	
    boolean isUsernameUnique(Integer id, String username);

    boolean isEmailUnique(Integer id, String email);
    
    void saveIn(Appuser appuser) ;
}