package com.sample.ams.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sample.ams.model.enumeration.Gender;
import com.sample.ams.proxy.ContactNumberConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerDTO {

    @NotEmpty
    @ApiModelProperty(required = true , example = "Thomas")
    private String firstName;

    @NotEmpty
    @ApiModelProperty(required = true , example = "Müller")
    private String lastName;

    @NotNull
    @Past(message = "should be past time !")
    @ApiModelProperty(required = true , example = "1990-01-01")
    private LocalDate birthDate;

    @NotNull
    @ApiModelProperty(required = true)
    private Gender gender;

    @NotNull
    @ApiModelProperty(required = true , example = "1")
    private Long birthPlaceId;

    @Email
    @ApiModelProperty(example = "sample@sample.com")
    private String email;


    @ContactNumberConstraint(message = "شماره صحیح نمی باشد")
    @ApiModelProperty(example = "+495555112")
    private String mobile;


    @ApiModelProperty(example = " This is a comment.")
    private String comment;

    // ------------------------------

    @Getter
    @Setter
    @Accessors(chain = true)
    @ApiModel("CustomerInfo")
    public static class Info extends CustomerDTO {
        private Long id;
        private Date createdDate;
        private Date lastModifiedDate;
        private AreaDTO.Info birthPlace;
    }
    // ------------------------------

    @Getter
    @Setter
    @Accessors(chain = true)
    @ApiModel("CustomerCreateRq")
    public static class Create extends CustomerDTO {

    }

    // ------------------------------

    @Getter
    @Setter
    @Accessors(chain = true)
    @ApiModel("CustomerUpdateRq")
    public static class Update extends CustomerDTO {
        @NotNull
        @ApiModelProperty(required = true)
        private Integer version;
    }
}
