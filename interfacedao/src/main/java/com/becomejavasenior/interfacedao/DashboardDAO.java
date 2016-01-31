package com.becomejavasenior.interfacedao;

import java.util.List;
import java.util.Map;

/**
 * Created by Peter on 27.01.2016.
 */
public interface DashboardDAO {
    List<Map<String,Object>> readDasboardInformation();
}
