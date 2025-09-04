package com.furticode;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SoftwareEngineerService {

    private SoftwareEngineerRepository softwareEngineerRepository;

    public SoftwareEngineerService(SoftwareEngineerRepository softwareEngineerRepository) {
        this.softwareEngineerRepository = softwareEngineerRepository;
    }


    public List<SoftwareEngineer> getAllSoftwareEngineers(){
        return softwareEngineerRepository.findAll();
    }


    public void insertSoftwareEngineer(SoftwareEngineer softwareEngineer) {
        softwareEngineerRepository.save(softwareEngineer);
    }

    public void deleteSoftwareEngineerById(Integer id) {
        boolean exists = softwareEngineerRepository.existsById(id);

        if (!exists){
            throw  new IllegalStateException(id + " not found ");
        }
        SoftwareEngineer softwareEngineer = getSoftwareEngineersById(id);
        softwareEngineerRepository.delete(softwareEngineer);
    }

    public void updateSoftwareEngineerById(Integer id, SoftwareEngineer newSoftwareEngineer) {
        SoftwareEngineer currentSoftwareEngineer = getSoftwareEngineersById(id);
        currentSoftwareEngineer.setName(newSoftwareEngineer.getName());
        currentSoftwareEngineer.setTechStack(newSoftwareEngineer.getTechStack());
        softwareEngineerRepository.save(currentSoftwareEngineer);
    }


    public SoftwareEngineer getSoftwareEngineersById(Integer id) {
        return softwareEngineerRepository.findById(id)
                .orElseThrow(()->new IllegalStateException(
                        id + " not found!"
                ));
    }
}
