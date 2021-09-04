package com.sample.ams.model;

import com.sample.ams.model.enumeration.AreaType;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@Data
@Entity
@Table(name = "tbl_area")
public class Area extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "N_PARENT_ID", insertable = false, updatable = false)
    private Area parent;

    @Setter
    @Column(name = "N_PARENT_ID", nullable = false)
    private Long parentId;

    @Column(name = "C_NAME", length = 50)
    private String name;

    @Column(name = "C_CODE", nullable = false, length = 30, unique = true)
    private String code;

    @Column(name = "E_AREA_TYPE")
    private AreaType areaType;

    @Version
    @Column(name = "N_VERSION", nullable = false)
    private Integer version;

    @Column(name = "C_COMMENT")
    private String comment;
}
