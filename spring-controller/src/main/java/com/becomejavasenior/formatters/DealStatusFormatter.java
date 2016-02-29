package com.becomejavasenior.formatters;

import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.DealService;
import com.becomejavasenior.DealStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@Component
public class DealStatusFormatter implements Formatter<DealStatus> {
    private static final Logger LOGGER = LogManager.getLogger(DealStatusFormatter.class);
    @Autowired
    private DealService dealService;

    @Override
    public DealStatus parse(String dealStatusId, Locale locale) throws ParseException {
        int id = Integer.parseInt(dealStatusId);
        DealStatus result = null;
        try {
            result = dealService.findDealStatus(id);
        } catch (DataBaseException e) {
            LOGGER.error(e);
        }
        return result;
    }

    @Override
    public String print(DealStatus dealStatus, Locale locale) {
        return dealStatus.getName();
    }
}
