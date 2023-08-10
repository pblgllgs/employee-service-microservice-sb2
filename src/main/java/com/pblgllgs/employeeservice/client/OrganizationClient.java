package com.pblgllgs.employeeservice.client;

import com.pblgllgs.employeeservice.dto.OrganizationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("ORGANIZATION-SERVICE")
public interface OrganizationClient {

    @GetMapping("/api/v1/organization/{organizationCode}")
    ResponseEntity<OrganizationDto> findOrganizationById(@PathVariable("organizationCode") String code);
}
