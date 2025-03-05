package org.example;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Data {
    @JsonProperty("Ref_Key")
    private String ref_Key;
    @JsonProperty("Code")
    private String code;
    @JsonProperty("Description")
    private String description;
    @JsonProperty("НаименованиеПолное")
    private String fullName;
    @JsonProperty("МеждународноеСокращение")
    private String interName;

    public Data() {
    }


    public String getRef_Key() {
        return ref_Key;
    }

    public void setRef_Key(String ref_Key) {
        this.ref_Key = ref_Key;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getInterName() {
        return interName;
    }

    public void setInterName(String interName) {
        this.interName = interName;
    }

    @Override
    public String toString() {
        return "Data{" +
                "ref_Key='" + ref_Key + '\'' +
                ", code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", fullName='" + fullName + '\'' +
                ", interName='" + interName + '\'' +
                '}';
    }
}
