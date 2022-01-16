package com.vladimir.deliveryfood.repository;

import com.vladimir.deliveryfood.entity.RestaurantEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;


@Repository
public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Long> {



     Optional<RestaurantEntity> findByName(String name);

     List<RestaurantEntity> findByCuisine(String cuisine);

}
