package com.example.FeignProg.dto.MongoDTO;



import java.io.Serializable;

public class MongoOrganizationDTO implements Serializable {
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
