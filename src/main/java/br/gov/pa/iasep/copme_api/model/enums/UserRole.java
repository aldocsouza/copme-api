package br.gov.pa.iasep.copme_api.model.enums;

public enum UserRole {

    ADMIN("admin"),
    COTADOR("cotador");

    private String role;

    UserRole(String role){
        this.role = role;
    }

    public String getRole() {
        return role;
    }

}
