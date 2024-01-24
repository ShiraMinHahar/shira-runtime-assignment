package core.repositories;

import core.entities.RuntimePolicies;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RuntimePolicyRepository extends CrudRepository<RuntimePolicies, Long> {

    RuntimePolicies findByPolicyName(String policyName);

    List<RuntimePolicies> getAllByAuthor(String author);

    @Query(value = "SELECT * FROM runtime_policies", nativeQuery = true)
    List<RuntimePolicies>  getAll();


}
