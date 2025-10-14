package com.companio.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.companio.Specification.TripSpecification;
import com.companio.dto.TripRequest;
import com.companio.dto.TripResponse;
import com.companio.exception.UserEmailNotFoundException;
import com.companio.model.Trip;
import com.companio.model.User;
import com.companio.repo.TripRepository;
import com.companio.repo.UserRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TripService {

    private final TripRepository tripRepo;
    @Autowired
    private final UserRepo userRepo;


    @Transactional
    public TripResponse createtrip (TripRequest request,String userEmail){
        User user = userRepo.findByEmail(userEmail).orElseThrow(()-> new RuntimeException("User not found"));

        Trip trip = new Trip();
        trip.setUser(user);
        trip.setTitle(request.getTitle());
        trip.setDescription(request.getDescription());
        trip.setLocationName(request.getLocationName());
        trip.setCityName(request.getCityName());
        trip.setStateName(request.getStateName());
        trip.setCountryName(request.getCountryName());
        trip.setPlaceId(request.getPlaceId());
        trip.setLocationLat(request.getLocationLat());
        trip.setLocationLng(request.getLocationLng());
        trip.setStartDate(request.getStartDate());
        trip.setEndDate(request.getEndDate());
        trip.setBudgetMin(request.getBudgetMin());
        trip.setBudgetMax(request.getBudgetMax());
        
        String slug = generateSlug(request.getTitle());
        trip.setSlug(slug);

        Trip savedtrip  = tripRepo.save(trip);
        return mapToResponse(savedtrip);
    }   

    public List<TripResponse> searchTrips(String city,String state,String country,LocalDate startDate,LocalDate endDate,BigDecimal maxBudget){
        Specification<Trip> spec = Specification.where(null);

        if(city!=null && !city.isBlank()){
            spec=spec.and(TripSpecification.hasCity(city));
        }

        if(state!=null && !state.isBlank()){
            spec=spec.and(TripSpecification.hasState(state));
        }

        if(country!=null && !country.isBlank()){
            spec=spec.and(TripSpecification.hasCountry(country));
        }

        if(startDate!=null && endDate!=null){
            spec=spec.and(TripSpecification.datesOverlap(startDate, endDate));
        }

        if(maxBudget!=null){
            spec=spec.and(TripSpecification.budgetMaxIs(maxBudget));
        }

        return tripRepo.findAll(spec).stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    private TripResponse mapToResponse(Trip trip){
        TripResponse resp = new TripResponse();
        resp.setId(trip.getId());
        resp.setTitle(trip.getTitle());
        resp.setDescription(trip.getDescription());
        resp.setLocationName(trip.getLocationName());
        resp.setCityName(trip.getCityName());
        resp.setStateName(trip.getStateName());
        resp.setCountryName(trip.getCountryName());
        resp.setLocationLat(trip.getLocationLat());
        resp.setLocationLng(trip.getLocationLng());
        resp.setStartDate(trip.getStartDate());
        resp.setEndDate(trip.getEndDate());
        resp.setBudgetMin(trip.getBudgetMin());
        resp.setBudgetMax(trip.getBudgetMax());
        resp.setPostedByUserId(trip.getUser().getId());
        resp.setPostedBy(trip.getUser().getFullName()); // Assuming User has a getDisplayName() method
        return resp;
    }

    private String generateSlug(String title){
        return title.toLowerCase()
                    .replaceAll("[^a-z0-9\\s-]","")
                    .replaceAll("\\s+","-")
                    .replaceAll("-+","-");
    }

    public List<TripResponse> searchMyTripBySlug(String prefix,String userEmail){
        User currentUser = userRepo.findByEmail(userEmail).orElseThrow(() -> new UserEmailNotFoundException("User Not Found"));

        List<Trip> trips = tripRepo.findByUserIdAndSlugStartingWithIgnoreCase(currentUser.getId(), prefix);

        return trips.stream()
                    .map(this::mapToResponse)
                    .collect(Collectors.toList());
    }
}
