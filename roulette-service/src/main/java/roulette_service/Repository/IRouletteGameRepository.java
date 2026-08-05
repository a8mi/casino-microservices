package roulette_service.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import roulette_service.Model.RouletteGame;

public interface IRouletteGameRepository extends JpaRepository<RouletteGame, Long> {
    
}