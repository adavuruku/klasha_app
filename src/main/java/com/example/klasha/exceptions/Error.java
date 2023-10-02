package com.example.klasha.exceptions;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Objects;

/**
 * Created by Adesegun.Adeyemo on 25/01/2022
 */
@Data
public final class Error {
  private final String fieldName;
  private final String message;

  /**
   *
   */
  @JsonCreator
  public Error(
      @JsonProperty("fieldName") String fieldName,
      @JsonProperty("message") String message) {
    this.fieldName = fieldName;
    this.message = message;

  }

  public String fieldName() {
    return fieldName;
  }

  public String message() {
    return message;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj == null || obj.getClass() != this.getClass()) {
      return false;
    }
    var that = (Error) obj;
    return Objects.equals(this.fieldName, that.fieldName) &&
        Objects.equals(this.message, that.message);
  }

  @Override
  public int hashCode() {
    return Objects.hash(fieldName, message);
  }

  @Override
  public String toString() {
    return "Error[" +
        "fieldName=" + fieldName + ", " +
        "message=" + message + ']';
  }

}
