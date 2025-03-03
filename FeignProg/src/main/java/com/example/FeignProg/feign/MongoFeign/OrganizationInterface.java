package com.example.FeignProg.feign.MongoFeign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "Mongo-Organization", url = "http://localhost:8086/api/organization")
public interface OrganizationInterface {
}
