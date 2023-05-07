package com.team.identityprovider.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Value;

public enum AuthenticationDto {;
  public enum Request{;
    private interface Email {@jakarta.validation.constraints.Email String getEmail(); }
    private interface Password {@NotBlank String getPassword(); }

    @Value
    public static class Common implements Email, Password {
      String email;
      String password;

      @JsonCreator()
      public Common(@JsonProperty("email") String email,
                    @JsonProperty("password") String password) {
        this.email = email;
        this.password = password;
      }
    }
  }

  public enum Response {;
    private interface AccessToken { @NotBlank String getAccess();}
    private interface RefreshToken { @NotBlank String getRefresh();}

    @Value
    public static class Common implements AccessToken, RefreshToken {
      String access;
      String refresh;

      @JsonCreator
      public Common(@JsonProperty("access") String access,
                    @JsonProperty("refresh") String refresh) {
        this.access = access;
        this.refresh = refresh;
      }
    }
  }
}
