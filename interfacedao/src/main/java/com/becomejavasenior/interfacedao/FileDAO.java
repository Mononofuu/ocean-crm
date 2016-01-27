package com.becomejavasenior.interfacedao;

import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.File;

import java.util.List;

public interface FileDAO {

    public List<File> getAllFilesBySubjectId(int id) throws DataBaseException;
}
