package com.companio.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TripRequest {

    @NotBlank(message = "Title is Required")
    @Size(min=5, max=100, message="Title must be between 5 and 100 Characters")
    private String title;

    @Size(max=1000,message = "Description can be upto 1000 Charcters")
    private String description;

    @NotBlank(message = "Location is Required")
    private String locationName;

    @NotBlank(message = "City Name is Required")
    private String cityName;   

    @NotBlank(message = "State Name is Required")
    private String stateName;

    @NotBlank(message = "Country Name is Required")
    private String countryName;

    private String placeId;

    @NotNull(message = "Latitude is Required")
    private Double locationLat;

    @NotNull(message = "Longitude is Required")
    private Double locationLng;

    @NotNull(message = "Start date is Required")
    @FutureOrPresent(message = "Start date cannot be in Past")
    private LocalDate startDate;

    @NotNull(message = "End date is Required")
    @FutureOrPresent(message = "End date cannot be in Past")
    private LocalDate endDate;

    @PositiveOrZero(message = "Minimum budget must be a Positive number or zero")
    private BigDecimal budgetMin;

    @Positive(message = "Maximum budget must be positive number")
    private BigDecimal budgetMax;

}
