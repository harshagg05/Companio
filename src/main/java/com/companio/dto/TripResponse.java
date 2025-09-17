package com.companio.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import lombok.Data;

@Data
public class TripResponse {

    private UUID id;
    private String title;
    private String description;
    private String locationName;
    private String cityName;
    private String stateName;
    private String countryName;
    private Double locationLat;
    private Double locationLng;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal budgetMin;
    private BigDecimal budgetMax;

    private UUID postedByUserId;
    private String postedBy; //This display user name
}
