package com.example.rt.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/info")
public class InfoController {
    @GetMapping("/contacts")
    String getContacts() {
        return null;
    }

    @GetMapping("/what-union-provides")
    String getWhatUnionProvides() {
        return null;
    }

    @GetMapping("/history-of-union")
    String getHistoryOfUnion() {
        return null;
    }

    @GetMapping("/annual-report")
    String getAnnualReport() {
        return null;
    }

    @GetMapping("/union-plans")
    String getUnionPlans() {
        return null;
    }
}
