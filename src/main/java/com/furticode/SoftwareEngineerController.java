package com.furticode;

import org.springframework.web.bind.annotation.*;


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

    @GetMapping("{id}")
    public SoftwareEngineer getEngineerById(@PathVariable Integer id){
        return softwareEngineerService.getSoftwareEngineersById(id);
    }

    @DeleteMapping("{id}")
    public void deleteEngineerById(@PathVariable Integer id){
        softwareEngineerService.deleteSoftwareEngineerById(id);
    }

    @PutMapping("{id}")
    public void updateEngineerById(@PathVariable Integer id, @RequestBody SoftwareEngineer newSoftwareEngineer){
        softwareEngineerService.updateSoftwareEngineerById(id, newSoftwareEngineer);
    }

    @PostMapping
    public void addNewSoftwareEngineer(@RequestBody SoftwareEngineer softwareEngineer){
        softwareEngineerService
                .insertSoftwareEngineer(softwareEngineer);
    }


}
