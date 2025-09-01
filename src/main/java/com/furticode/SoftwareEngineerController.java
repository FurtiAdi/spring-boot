package com.furticode;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/software-engineer")
public class SoftwareEngineerController {
    public SoftwareEngineerController(SoftwareEngineerService softwareEngineerService) {
        this.softwareEngineerService = softwareEngineerService;
    }

    private SoftwareEngineerService softwareEngineerService;


    @GetMapping
    public List<SoftwareEngineer> getEngineer(){
        return softwareEngineerService.getAllSoftwareEngineers();
    }
}
