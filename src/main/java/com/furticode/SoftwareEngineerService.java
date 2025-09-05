package com.furticode;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SoftwareEngineerService {

    private SoftwareEngineerRepository softwareEngineerRepository;
    private final AiService aiService;

    public SoftwareEngineerService(SoftwareEngineerRepository softwareEngineerRepository, AiService aiService) {
        this.softwareEngineerRepository = softwareEngineerRepository;
        this.aiService = aiService;
    }


    public List<SoftwareEngineer> getAllSoftwareEngineers(){
        return softwareEngineerRepository.findAll();
    }


    public void insertSoftwareEngineer(SoftwareEngineer softwareEngineer) {
        // basic input guard (prevents NPEs)
        if (softwareEngineer.getName() == null || softwareEngineer.getTechStack() == null) {
            throw new IllegalArgumentException("name and techStack are required");
        }

        String prompt = """
            Based on the programming tech stack %s that %s has given 
            Provide a full learning path and recommendations for the person
            """.formatted(softwareEngineer.getTechStack(), softwareEngineer.getName());

        String chatRes;
        try {
            chatRes = aiService.chat(prompt);
        } catch (Exception ex) {
            // log and degrade gracefully instead of 500
            // log.warn("AI generation failed", ex);
            chatRes = "AI generation unavailable right now. Provide a manual learning path.";
            // (optional) or rethrow a 502/503 instead of 500
            // throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "AI provider failed", ex);
        }

        softwareEngineer.setLearningPathRecommendation(chatRes);
        softwareEngineer.setId(null); // ensure insert, not update
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
