package com.example.covidtracker.controller;

import com.example.covidtracker.models.Country;
import com.example.covidtracker.service.CovidDataService;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class PublicController 
{
    @Autowired
    CovidDataService covidDataService;
    
    @GetMapping("/")
    public String index(Model model)
    {
        List<Country> allStats = covidDataService.getAllStats();
        int allNewCases = covidDataService.getAllNewCases();
        
        // Get a random country name for search input placeholder
        Random rand = new Random();
        String randomCountryName = allStats.get(rand.nextInt(allStats.size())).getName();
        
        model.addAttribute("allNewCases", allNewCases);
        model.addAttribute("countries", allStats);
        model.addAttribute("randomCountryName", randomCountryName);
        return "index";
    }
}
