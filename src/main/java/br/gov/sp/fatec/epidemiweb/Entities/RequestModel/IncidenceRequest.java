package br.gov.sp.fatec.epidemiweb.Entities.RequestModel;

import java.time.LocalDate;

import br.gov.sp.fatec.epidemiweb.Entities.Disease;
import br.gov.sp.fatec.epidemiweb.Entities.Users;

public class IncidenceRequest {
    private Disease disease;
    private Users user;
    private LocalDate incidenceDate;

    public IncidenceRequest() {

    }

    public IncidenceRequest(Disease disease, Users user) {
        this.disease = disease;
        this.user = user;
        this.incidenceDate = LocalDate.now();
    }

    public Disease getDisease() {
        return this.disease;
    }

    public void setDisease(Disease disease) {
        this.disease = disease;
    }

    public Users getUser() {
        return this.user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public LocalDate getIncidenceDate() {
        return this.incidenceDate;
    }

    public void setIncidenceDate(LocalDate incidenceDate) {
        this.incidenceDate = incidenceDate;
    }
    
}
