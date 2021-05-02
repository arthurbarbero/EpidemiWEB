package br.gov.sp.fatec.epidemiweb.Security;

public class LoginModel {
    
    private String email;

    private String password;

    private String tokenKey;

    private String[] groups;

    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTokenKey() {
        return tokenKey;
    }

    public void setTokenKey(String tokenKey) {
        this.tokenKey = tokenKey;
    }

    public String[] getGroups() {
        return groups;
    }

    public void setGroups(String[] groups) {
        this.groups = groups;
    }


    public LoginModel() {

    }

    public LoginModel(String email, String password) {
        this.email = email;
        this.password = password;
    }

}
