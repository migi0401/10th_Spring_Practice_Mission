package umc.domain.policy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.domain.policy.entity.Policy;

import java.util.Collection;
import java.util.List;

public interface PolicyRepository extends JpaRepository<Policy, String> {
    List<Policy> findAllByNameIn(Collection<String> names);
}
