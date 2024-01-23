package core.servises;

import core.enforcers.RuntimeEnforcer;
import core.entities.RuntimePolicies;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.NonTransientDataAccessException;
import core.repositories.RuntimePolicyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
@AllArgsConstructor
public class RuntimePolicyService {

    private final RuntimePolicyRepository runtimePolicyRepository;

    private final RuntimeEnforcer runtimeEnforcer;

    public RuntimePolicies save(RuntimePolicies runtimePolicies) {
        if (runtimePolicies == null) {
            return null;
        }
        RuntimePolicies saveRuntimePolicies = null;
        try {
            RuntimePolicies existingRuntimePolicies = runtimePolicyRepository.findByPolicyName(runtimePolicies.getPolicyName());
            if (existingRuntimePolicies != null) {
                existingRuntimePolicies.setControls(runtimePolicies.getControls());
                saveRuntimePolicies = runtimePolicyRepository.save(existingRuntimePolicies);
            } else {
                saveRuntimePolicies = runtimePolicyRepository.save(runtimePolicies);
            }
        } catch (NonTransientDataAccessException e) {
            log.error("Failed to save - database error '{}'.", runtimePolicies.getPolicyName(), e);
            return null;
        } catch (DataAccessException e) {
            log.error("Failed to save - non transient database error '{}'.", runtimePolicies.getPolicyName(), e);
            throw e;
        } catch (Exception e) {
            log.error("Failed to save '{}'.", runtimePolicies.getPolicyName(), e);
            return null;
        }
        updateRuntimeEnforcer(saveRuntimePolicies);
        return saveRuntimePolicies;
    }

    public boolean delete(RuntimePolicies runtimePolicies) {
        if (runtimePolicies == null) {
            return false;
        }
        RuntimePolicies saveRuntimePolicies = null;
        try {
            RuntimePolicies existingRuntimePolicies = runtimePolicyRepository.findByPolicyName(runtimePolicies.getPolicyName());
            if (existingRuntimePolicies != null) {
                runtimePolicyRepository.delete(existingRuntimePolicies);
                deleteRuntimeEnforcer(existingRuntimePolicies);
                return true;
            }
        } catch (NonTransientDataAccessException e) {
            log.error("Failed to delete - database error '{}'.", runtimePolicies.getPolicyName(), e);
            return false;
        } catch (DataAccessException e) {
            log.error("Failed to delete - non transient database error '{}'.", runtimePolicies.getPolicyName(), e);
            throw e;
        } catch (Exception e) {
            log.error("Failed to delete '{}'.", runtimePolicies.getPolicyName(), e);
            return false;
        }
        return false;
    }

    public List<RuntimePolicies> getAllRuntimePolicies() {
        try {
            return runtimePolicyRepository.getAllByAuthor("admin");
        } catch (DataAccessException e) {
            log.error("Failed to get all runtime policies - database error.", e);
            return null;
        } catch (Exception e) {
            log.error("Failed to get all runtime policies.", e);
            return null;
        }
    }

    private boolean updateRuntimeEnforcer(RuntimePolicies runtimePolicies) {
        return runtimeEnforcer.updateRuntimePolicy(runtimePolicies);
    }

    private boolean deleteRuntimeEnforcer(RuntimePolicies runtimePolicies) {
        return runtimeEnforcer.deleteRuntimePolicy(runtimePolicies);
    }
}
