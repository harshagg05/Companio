package com.companio.controller;

import java.math.BigDecimal;
import java.nio.file.AccessDeniedException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.companio.dto.TripRequest;
import com.companio.dto.TripResponse;
import com.companio.service.TripService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("v1/api/trip")
public class TripController {

    @Autowired
    private TripService tripService;

    @PostMapping
    public ResponseEntity<TripResponse> createTrip(@Valid @RequestBody TripRequest tripRequest,Authentication authentication){
        String userEmail = authentication.getName();//This Will Give the email of user Spring Security
        TripResponse response = tripService.createtrip(tripRequest, userEmail);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<List<TripResponse>> searchTrip(@RequestParam(required = false) String city,
                                                        @RequestParam(required = false) String state,
                                                        @RequestParam(required = false)String country,
                                                        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                        @RequestParam(required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate endDate,
                                                        @RequestParam(required = false)BigDecimal maxBudget){
        List<TripResponse> results = tripService.searchTrips(city, state, country, startDate, endDate, maxBudget);
        return ResponseEntity.ok(results);
    }


    @GetMapping("/my-trips/search")
    public ResponseEntity<List<TripResponse>> searchMyTripUsingSlug(
                                            @RequestParam String prefix,
                                            @AuthenticationPrincipal UserPrinciple userPrinciple
    ){
        List<TripResponse> foundTrips = tripService.searchMyTripBySlug(prefix, userPrinciple.getUsername());
        return ResponseEntity.ok(foundTrips);
    }

    //For a User Personal Trip
    @GetMapping("/my-trips")
    public ResponseEntity<List<TripResponse>> myTrips(@AuthenticationPrincipal UserPrinciple userPrinciple){
        List<TripResponse> myTrip = tripService.myTrips(userPrinciple.getUsername());
        return ResponseEntity.ok(myTrip);
    }

    @PutMapping("/{tripId}")
    public ResponseEntity<TripResponse> updatetrip(@PathVariable UUID tripId,
                                                    @Valid @RequestBody TripRequest request,
                                                    @AuthenticationPrincipal UserPrinciple userPrinciple
    )throws AccessDeniedException{
        String userEmail = userPrinciple.getUsername();
        TripResponse updatedTrip = tripService.updateTrip(tripId,request,userEmail);
        return ResponseEntity.ok(updatedTrip);
    }
    
    @DeleteMapping("/{tripId}")
    public ResponseEntity<?> deleteEntity(@PathVariable UUID tripId, @AuthenticationPrincipal UserPrinciple userPrinciple){
        return tripService.deleteTrip(tripId,userPrinciple.getUsername());
    }
}
