package com.companio.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;

import com.companio.model.Trip;

public class TripSpecification {
    public static Specification<Trip> hasCity(String city){
        return (root,query,criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("cityName")), "%" + city.toLowerCase() + "%");
    }

    public static Specification<Trip> hasState(String state){
        return (root,query,criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("stateName")), "%" + state.toLowerCase() + "%");
    }

    public static Specification<Trip> hasCountry(String country) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("countryName")), "%" + country.toLowerCase() + "%");
    }

    public static Specification<Trip> budgetMaxIs(BigDecimal maxBudget){
        return (root,query,criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("budgetMax"), maxBudget);
    }

    /**
     * This is the most complex one. It finds trips that overlap with a given date range.
     * The logic is: (Trip's StartDate <= Search's EndDate) AND (Trip's EndDate >= Search's StartDate)
     * This covers all cases: trips that start before, during, or after the search range but still overlap.
     */
    public static Specification<Trip> datesOverlap(LocalDate searchStart, LocalDate searchEnd) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.and(
                        criteriaBuilder.lessThanOrEqualTo(root.get("startDate"), searchEnd),
                        criteriaBuilder.greaterThanOrEqualTo(root.get("endDate"), searchStart));
    }
}
