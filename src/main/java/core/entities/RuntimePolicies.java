package core.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "runtime_policies")
@EntityListeners(AuditingEntityListener.class)
public class RuntimePolicies {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "policy_id", nullable = false)
    private Long policyId;

    private String policyName;

    private String author;

    private String controls;
}
