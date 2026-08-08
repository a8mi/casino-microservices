package slotmachine_service.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import slotmachine_service.Model.SlotGame;
import java.util.List;

public interface ISlotGameRepository extends JpaRepository<SlotGame, Long> {
    List<SlotGame> findAllByOrderByIdAsc();

    List<SlotGame> findByUserIdOrderByIdAsc(Long userId);
}
