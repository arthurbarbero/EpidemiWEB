package br.gov.sp.fatec.epidemiweb.Entities.RequestModel;

import br.gov.sp.fatec.epidemiweb.Entities.Address;
import br.gov.sp.fatec.epidemiweb.Entities.Users;

public class UserRequest {
    private Users user;
    private Address address;
    private String role;

    public UserRequest() {
        
    }

    public UserRequest(Users user, Address address, String role) {
        this.user = user;
        this.address = address;
        this.role = role;
    }

    public Users getUser() {
        return this.user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Address getAddress() {
        return this.address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }


}
