package slotmachine_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import slotmachine_service.model.SlotGame;

import java.util.List;

public interface SlotGameRepository extends JpaRepository<SlotGame, Long> {
    List<SlotGame> findAllByOrderByIdAsc();

    List<SlotGame> findByUserIdOrderByIdAsc(Long userId);
}
