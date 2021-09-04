package com.sample.ams.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BankDTO {

    @NotEmpty
    @ApiModelProperty(required = true, example = "Bank ME")
    private String name;

    @NotEmpty
    @ApiModelProperty(required = true, example = "Me-123")
    private String code;

    @ApiModelProperty(example = "null")
    private Long parentId;

    @NotNull
    @ApiModelProperty(required = true)
    private Long areaId;

    @ApiModelProperty(example = " this field is for comment")
    private String comment;


    // ------------------------------

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Accessors(chain = true)
    @ApiModel("BankInfo")
    public static class Info extends BankDTO {
        private Long id;
        private Date createdDate;
        private Date lastModifiedDate;
        private AreaDTO.Info area;
//        private Integer version;
    }
    // ------------------------------

    @Getter
    @Setter
    @Accessors(chain = true)
    @ApiModel("BankCreateRq")
    public static class Create extends BankDTO {

    }

    // ------------------------------

    @Getter
    @Setter
    @Accessors(chain = true)
    @ApiModel("BankUpdateRq")
    public static class Update extends BankDTO {
        @NotNull
        @ApiModelProperty(required = true)
        private Integer version;
    }

    // ------------------------------

    @Getter
    @Setter
    @Accessors(chain = true)
    @ApiModel("BankDeleteRq")
    public static class Delete {
        @NotNull
        @ApiModelProperty(required = true)
        private List<Long> ids;
    }
}
