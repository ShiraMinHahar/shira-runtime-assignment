package core.repositories;

import core.entities.RuntimePolicies;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RuntimePolicyRepository extends CrudRepository<RuntimePolicies, Long> {

    RuntimePolicies findByPolicyName(String policyName);

    List<RuntimePolicies> getAllByAuthor(String author);

}
