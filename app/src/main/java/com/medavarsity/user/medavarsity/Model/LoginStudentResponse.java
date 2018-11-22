package com.medavarsity.user.medavarsity.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class LoginStudentResponse implements Serializable {

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @SerializedName("Error")
    boolean error;

    @SerializedName("Status")
    int status;
    @SerializedName("Message")
    String message;


    /*  public ArrayList<StudentResponse> getStudentResponses() {
          return studentResponses;
      }

      public void setStudentResponses(ArrayList<StudentResponse> studentResponses) {
          this.studentResponses = studentResponses;
      }
  */
    public String getAuth_token() {
        return auth_token;
    }

    public void setAuth_token(String auth_token) {
        this.auth_token = auth_token;
    }

/*
    @SerializedName("Payload")
    ArrayList<StudentResponse>studentResponses;
*/

    public StudentResponse getStudentResponse() {
        return studentResponse;
    }

    public void setStudentResponse(StudentResponse studentResponse) {
        this.studentResponse = studentResponse;
    }

    @SerializedName("Payload")
    StudentResponse studentResponse;

    @SerializedName("Authtoken")
    String auth_token;
}