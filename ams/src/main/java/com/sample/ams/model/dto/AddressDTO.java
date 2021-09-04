package com.sample.ams.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sample.ams.model.enumeration.AddressType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressDTO {

    @NotNull
    @ApiModelProperty(required = true)
    private Long customerId;

    @NotNull
    @ApiModelProperty(required = true , example = "2")
    private Long areaId;

    @NotEmpty
    @ApiModelProperty(required = true , example = "Karl-Marx-Allee 90C, 10243 Berlin ")
    private String fullAddress;

    @ApiModelProperty(example = "123456-45")
    private String postalCode;

    @ApiModelProperty(example = " This is a comment.")
    private String comment;

    @NotNull
    @ApiModelProperty(required = true)
    private AddressType addressType;

    @ApiModelProperty(hidden = true , example = "52.5169392")
    private Double latitude;

    @ApiModelProperty(hidden = true , example = "13.4381119")
    private Double longitude;

    @ApiModelProperty(example = "4945645612")
    private String tel;

    // ------------------------------

    @Getter
    @Setter
    @Accessors(chain = true)
    @ApiModel("AddressInfo")
    public static class Info extends AddressDTO {
        private Long id;
        private Date createdDate;
        private Date lastModifiedDate;
    }
    // ------------------------------

    @Getter
    @Setter
    @Accessors(chain = true)
    @ApiModel("AddressCreateRq")
    public static class Create extends AddressDTO {

    }

    // ------------------------------

    @Getter
    @Setter
    @Accessors(chain = true)
    @ApiModel("AddressUpdateRq")
    public static class Update extends AddressDTO {
        @NotNull
        @ApiModelProperty(required = true)
        private Integer version;
    }
}
