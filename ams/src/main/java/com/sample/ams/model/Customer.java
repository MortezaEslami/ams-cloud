package com.sample.ams.model;

import com.sample.ams.model.enumeration.Gender;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@EntityListeners(AuditingEntityListener.class)
@Data
@Entity
@Table(name = "TBL_CUSTOMER")
public class Customer extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "C_FIRST_NAME", length = 50)
    private String firstName;

    @Column(name = "C_LAST_NAME", length = 50)
    private String lastName;

    @Column(name = "D_BIRTH_DATE")
    private LocalDate birthDate;

    @Column(name = "N_GENDER")
    private Gender gender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "N_BIRTH_PLACE_ID", nullable = false, insertable = false, updatable = false)
    private Area birthPlace;

    @Setter
    @Column(name = "N_BIRTH_PLACE_ID", nullable = false)
    private Long birthPlaceId;

    @Column(name = "C_EMAIL", length = 50)
    private String email;

    @Column(name = "C_MOBILE", length = 15)
    private String mobile;

    @Version
    @Column(name = "N_VERSION", nullable = false)
    private Integer version;

    @Column(name = "C_COMMENT")
    private String comment;
}
