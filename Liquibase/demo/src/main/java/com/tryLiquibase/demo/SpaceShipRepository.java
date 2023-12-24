package com.tryLiquibase.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpaceShipRepository extends JpaRepository<SpaceShip,Long> {
}
