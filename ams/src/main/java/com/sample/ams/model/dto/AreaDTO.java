package com.sample.ams.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sample.ams.model.enumeration.AreaType;
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

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AreaDTO {

    @NotNull
    @ApiModelProperty(required = true)
    private AreaType areaType;

    @NotEmpty
    @ApiModelProperty(required = true, example = "Germany")
    private String name;

    @NotEmpty
    @ApiModelProperty(required = true, example = "Cu-De")
    private String code;

    @ApiModelProperty(example = "null")
    private Long parentId;

    @ApiModelProperty(example = " this field is for comment")
    private String comment;

    // ------------------------------

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Accessors(chain = true)
    @ApiModel("AreaInfo")
    public static class Info extends AreaDTO {
        private Long id;
        private Date createdDate;
        private Date lastModifiedDate;
    }
    // ------------------------------

    @Getter
    @Setter
    @Accessors(chain = true)
    @ApiModel("AreaCreateRq")
    public static class Create extends AreaDTO {

    }

    // ------------------------------

    @Getter
    @Setter
    @Accessors(chain = true)
    @ApiModel("AreaUpdateRq")
    public static class Update extends AreaDTO {
        @NotNull
        @ApiModelProperty(required = true)
        private Integer version;
    }
}
