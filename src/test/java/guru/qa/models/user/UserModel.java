package guru.qa.models.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class UserModel {
    int id;
    String email;
    @JsonProperty("first_name")
    String first_name;
    @JsonProperty("last_name")
    String last_name;
    String avatar;
}

