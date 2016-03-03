package com.becomejavasenior.formatters;

import com.becomejavasenior.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

/**
 * Created by kramar on 02.03.16.
 */
@Component
public class SubjectFormatter implements Formatter<Subject> {

    private static final Logger LOGGER = LogManager.getLogger(DealStatusFormatter.class);

    @Autowired
    CompanyService companyService;

    @Autowired
    ContactService contactService;

    @Autowired
    DealService dealService;

    @Override
    public Subject parse(String id, Locale locale) throws ParseException {
        try {
            int subjectId = Integer.parseInt(id);
            Subject subject = null;
            Contact contact = contactService.findContactById(subjectId);
            if(contact == null){
                Company company = companyService.findCompanyById(subjectId);
                if(company == null){
                    Deal deal = dealService.findDealById(subjectId);
                    if(deal != null){
                        deal.setComments(null);
                        deal.setContacts(null);
                        deal.setFiles(null);
                        deal.setTasks(null);
                        subject = deal;
                    }
                }else{
                    company.setComments(null);
                    company.setTasks(null);

                    company.setContacts(null);
                    company.setDeals(null);
                    company.setFiles(null);
                    subject = company;
                }
            }else{

                contact.setComments(null);
                contact.setDeals(null);
                contact.setFiles(null);
                contact.setTasks(null);

                subject = contact;
            }
            if(subject != null){
                subject.setTags(null);
                subject.setUser(null);
            }
            return subject;
        } catch (DataBaseException e) {
            LOGGER.error(e);
        }
        return null;
    }

    @Override
    public String print(Subject subject, Locale locale) {
        return subject.getName();
    }
}
