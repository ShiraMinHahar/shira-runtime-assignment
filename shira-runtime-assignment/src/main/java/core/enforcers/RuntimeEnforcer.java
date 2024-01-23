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
        List<RuntimePolicies> policies = runtimePolicyRepository.getAllByAuthor("admin");
        for (RuntimePolicies policy : policies) {
            policyCache.put(policy.getPolicyName(), calculateHash(policy.getControls()));
        }
    }

    public boolean updateRuntimePolicy(RuntimePolicies runtimePolicies) {
        if (runtimePolicies == null) {
            return false;
        }

        policyCache.compute(runtimePolicies.getPolicyName(), (key, existingHash) -> {
            // Calculate and update the hash atomically
            RuntimePolicies newHash = calculateHash(runtimePolicies.getControls());
            if (existingHash == null || !existingHash.equals(newHash)) {
                // Policies have changed or it's the first time, process the new policies
                // Your logic to process the updated policy goes here
                return newHash;
            } else {
                // Policies are the same, skip processing
                return existingHash;
            }
        });

        return true;
    }

    public boolean deleteRuntimePolicy(RuntimePolicies runtimePolicies) {
        if (runtimePolicies == null) {
            return false;
        }

        policyCache.remove(runtimePolicies.getPolicyName());

        // Your logic to handle policy deletion goes here

        return true;
    }

    private RuntimePolicies calculateHash(String input) {
        // Your hash calculation logic goes here
        // Ensure it's consistent with the logic used in the PolicyCache class
        return new RuntimePolicies();
    }
}
