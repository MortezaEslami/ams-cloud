package com.sample.ams.model;

import com.sample.ams.model.enumeration.AddressType;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;


@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@Data
@Entity
@Table(name = "tbl_address")
public class Address extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter(AccessLevel.NONE)
    @ManyToOne
    @JoinColumn(name = "N_CUSTOMER_ID", nullable = false, insertable = false, updatable = false)
    private Customer customer;


    @Column(name = "N_CUSTOMER_ID", nullable = false)
    private Long customerId;

    @Setter(AccessLevel.NONE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "N_AREA_ID", nullable = false, insertable = false, updatable = false)
    private Area area;

    @Column(name = "N_AREA_ID", nullable = false)
    private Long areaId;

    @Column(name = "C_FULL_ADDRESS", nullable = false, length = 500)
    private String fullAddress;

    @Column(name = "C_POSTAL_CODE", length = 15)
    private String postalCode;

    @Column(name = "E_ADDRESS_TYPE", nullable = false)
    private AddressType addressType;

    @Column(name = "N_LATITUDE", length = 10)
    private Double latitude;

    @Column(name = "N_LONGITUDE", length = 10)
    private Double longitude;

    @Version
    @Column(name = "N_VERSION", nullable = false)
    private Integer version;

    @Column(name = "C_COMMENT")
    private String comment;

    @Column(name = "C_TEL")
    private String tel;
}