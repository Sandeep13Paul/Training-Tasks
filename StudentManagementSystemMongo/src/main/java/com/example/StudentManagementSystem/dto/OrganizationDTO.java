package com.example.StudentManagementSystem.dto;



import java.io.Serializable;

public class OrganizationDTO implements Serializable {
    private String organizationId;
    private String organizationName;

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }
}
