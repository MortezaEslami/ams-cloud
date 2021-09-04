package com.sample.ams.model;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@Data
@Entity
@Table(name = "tbl_bank")
public class Bank extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "N_PARENT_ID", insertable = false, updatable = false)
    private Bank parent;

    @Setter
    @Column(name = "N_PARENT_ID", nullable = false)
    private Long parentId;

    @Column(name = "C_NAME", length = 50)
    private String name;

    @Column(name = "C_CODE", nullable = false, length = 30, unique = true)
    private String code;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "N_AREA_ID", insertable = false, updatable = false)
    private Area area;

    @Setter
    @Column(name = "N_AREA_ID", nullable = false)
    private Long areaId;

    @Version
    @Column(name = "N_VERSION", nullable = false)
    private Integer version;

    @Column(name = "C_COMMENT")
    private String comment;
}
