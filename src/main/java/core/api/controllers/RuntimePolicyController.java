package core.api.controllers;

import core.entities.RuntimePolicies;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import core.servises.RuntimePolicyService;


@RestController
@RequestMapping("/runtimePolicy")
@AllArgsConstructor
public class RuntimePolicyController {

    private final RuntimePolicyService runtimePolicyService;

    @GetMapping("/GetAll")
    public ResponseEntity<String> getAllRuntimePolicies() {
        return ResponseEntity.status(HttpStatus.OK).body("success");
    }

    @PostMapping("/Update")
    public ResponseEntity<String> updateRuntimePolicy(@RequestBody RuntimePolicies runtimePolicies) {
        RuntimePolicies savedRuntimePolicies = runtimePolicyService.save(runtimePolicies);
        if (savedRuntimePolicies == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update");
        }
        return ResponseEntity.status(HttpStatus.OK).body("success");
    }

    @PutMapping("/Create")
    public ResponseEntity<String> createRuntimePolicy(@RequestBody RuntimePolicies runtimePolicies) {
        RuntimePolicies savedRuntimePolicies = runtimePolicyService.save(runtimePolicies);
        if (savedRuntimePolicies == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create");
        }
        return ResponseEntity.status(HttpStatus.OK).body("success");
    }

    @DeleteMapping("/Delete")
    public ResponseEntity<String> DeleteRuntimePolicy(@RequestBody RuntimePolicies runtimePolicies) {
        boolean deletedRuntimePolicy = runtimePolicyService.delete(runtimePolicies);
        if (deletedRuntimePolicy == false) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete");
        }
        return ResponseEntity.status(HttpStatus.OK).body("success");
    }
}
