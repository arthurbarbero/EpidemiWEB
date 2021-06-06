package br.gov.sp.fatec.epidemiweb.Entities.RequestModel;

import java.util.List;

public class SymptomRequest {
    private String name;
    private String description;
    private int severity;
    private List<Integer> diseases;

    public SymptomRequest(String name, String description, int severity, List<Integer> diseases) {
        this.name = name;
        this.description = description;
        this.severity = severity;
        this.diseases = diseases;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSeverity() {
        return this.severity;
    }

    public void setSeverity(int severity) {
        this.severity = severity;
    }

    public List<Integer> getDiseases() {
        return this.diseases;
    }

    public void setDiseases(List<Integer> diseases) {
        this.diseases = diseases;
    }
    
}
