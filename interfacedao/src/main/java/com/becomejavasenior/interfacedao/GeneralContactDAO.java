package com.becomejavasenior.interfacedao;

import com.becomejavasenior.ContactFilters;
import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.GenericDao;

import java.util.Date;
import java.util.List;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
public interface GeneralContactDAO<T> extends GenericDao<T> {
    T readContactByName(String name) throws DataBaseException;
    List<T> getAllContactsByParameters(List<ContactFilters> parameters, String userId, List<Integer> tagIdList, List<Date> taskDate, List<Date> createUpdateDate, String createUpdateFlag) throws DataBaseException;
    int findTotalEntryes() throws DataBaseException;
}
