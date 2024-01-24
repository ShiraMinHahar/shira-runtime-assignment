package core.enforcers;

import core.entities.RuntimePolicies;
import core.repositories.RuntimePolicyRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Log4j2
public class RuntimeEnforcer {

    private static final Map<String, RuntimePolicies> policyCache = new ConcurrentHashMap<>();

    private final RuntimePolicyRepository runtimePolicyRepository;

    public RuntimeEnforcer(RuntimePolicyRepository runtimePolicyRepository) {
        this.runtimePolicyRepository = runtimePolicyRepository;
        loadPoliciesFromDatabase();
    }

    private void loadPoliciesFromDatabase() {
        List<RuntimePolicies> policies = runtimePolicyRepository.getAll();
        for (RuntimePolicies policy : policies) {
            policyCache.put(policy.getPolicyName(), policy);
        }
    }

    public boolean updateRuntimePolicy(RuntimePolicies runtimePolicies) {
        if (runtimePolicies == null) {
            return false;
        }
        RuntimePolicies runtimePolicies1 = policyCache.compute(runtimePolicies.getPolicyName(), (key, existingHash) -> {
            // Calculate and update the hash atomically
            if (existingHash == null || !existingHash.equals(runtimePolicies)) {
                return runtimePolicies;
            } else {
                return existingHash;
            }
        });
        policyCache.put(runtimePolicies1.getPolicyName(), runtimePolicies1);
        return true;
    }

    public boolean deleteRuntimePolicy(RuntimePolicies runtimePolicies) {
        if (runtimePolicies == null || policyCache.remove(runtimePolicies.getPolicyName()) == null) {
            return false;
        }
        return true;
    }

    //    private RuntimePolicies calculateHash(String input) {
    //        // Your hash calculation logic goes here
    //        // Ensure it's consistent with the logic used in the PolicyCache class
    //        return new RuntimePolicies();
    //    }
}
