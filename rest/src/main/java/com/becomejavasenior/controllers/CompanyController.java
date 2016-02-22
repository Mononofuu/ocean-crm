package com.becomejavasenior.controllers;

import com.becomejavasenior.Company;
import com.becomejavasenior.CompanyService;
import com.becomejavasenior.DataBaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * created by Alekseichenko Sergey <mononofuu@gmail.com>
 */
@RestController
@RequestMapping("/companies")
public class CompanyController {
    @Autowired
    CompanyService companyService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Company> getCompanies() throws DataBaseException {
        return companyService.findCompanies();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Company getCompany(@PathVariable int id) throws DataBaseException {
        return companyService.findCompanyById(id);
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public void addCompany(@RequestBody Company company) throws DataBaseException {
        companyService.saveCompany(company);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public void editCompany(@PathVariable int id, @RequestBody Company company) throws DataBaseException {
        companyService.saveCompany(company);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteCompany(@PathVariable int id) throws DataBaseException {
        companyService.deleteCompany(id);
    }
}
