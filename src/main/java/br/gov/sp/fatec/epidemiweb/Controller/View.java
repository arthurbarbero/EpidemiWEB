package br.gov.sp.fatec.epidemiweb.Controller;

public class View {
    
    public static class User {}

    public static class Group {}

    public static class DiseaseResume {}

    public static class DiseaseSymptons extends DiseaseResume {}

    public static class DiseaseIncidences extends DiseaseResume {}

    public static class IncidenceResumed {}

    public static class IncidenceComplete extends IncidenceResumed {}

    public static class SymptomResumed {}

    public static class SymptomCompleted extends SymptomResumed {}

}
