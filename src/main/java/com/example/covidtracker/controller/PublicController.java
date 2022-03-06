package com.example.covidtracker.controller;

import com.example.covidtracker.service.CovidDataService;
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
        model.addAttribute("stats", covidDataService.getAllStats());
        return "index";
    }
}
