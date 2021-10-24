package com.tridang.assignment.vo;/*
 * @author Tri Dang
 */

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MerriamVO {

    @JsonProperty("meta")
    MerriamMeta meta;
    @JsonProperty("fl")
    String functionalLabel;
    @JsonProperty("shortdef")
    List<String> shortDefinition;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class MerriamMeta {
        @JsonProperty("id")
        String id;
    }
}
