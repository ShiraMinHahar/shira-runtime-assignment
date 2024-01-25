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
//The enforcer contains a static map of all the runtime policies
//When creating the first object, all the runtime policies are loaded from the db.
//Each creation, update or deletion of a runtime policy updates the map at runtime.
//When creating an enforcer, a RuntimeEnforcer object is created and because the map is static, any changes to the map will be immediately transferred to the enforcer.
//When a new enforcer object is created, an object is created with an updated map and the enforcer contains rules for how it goes over the map to enforce the policies.
    private static final Map<String, RuntimePolicies> policyCache = loadPoliciesFromDatabase();

    private final RuntimePolicyRepository runtimePolicyRepository;

    public RuntimeEnforcer(RuntimePolicyRepository runtimePolicyRepository) {
        this.runtimePolicyRepository = runtimePolicyRepository;
        if (policyCache.isEmpty()) {
            loadPoliciesFromDatabase();
        }
    }

    private static void loadPoliciesFromDatabase() {
        policyCache = new ConcurrentHashMap<>();
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
