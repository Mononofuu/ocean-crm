package com.becomejavasenior.interfacedao;

import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.GenericDao;
import com.becomejavasenior.Subject;

public interface SubjectDAO extends GenericDao<Subject>{
    <T extends Subject> int createSubject(T object) throws DataBaseException;

}
