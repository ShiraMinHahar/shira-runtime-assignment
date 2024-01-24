package core.entities;

import com.fasterxml.jackson.databind.JsonNode;
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

    @Column(name = "policy_name", unique = true, nullable = false, length = 255)
    private String policyName;

    @Column(name = "author", nullable = false, length = 255)
    private String author;

    @Column(name = "controls", nullable = false)
    //@Convert(converter = JsonConverter.class)
    private String controls;
}
