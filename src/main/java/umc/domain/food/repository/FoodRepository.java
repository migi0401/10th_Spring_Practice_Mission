package umc.domain.food.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.domain.food.entity.Food;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Long> {
    List<Food> findAllByIdIn(List<Long> ids);
}
