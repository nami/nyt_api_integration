package nyt.model.nyt;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// if it's not in your class, it will not map it
@JsonIgnoreProperties(ignoreUnknown = true)
public class NYTRoot {

    // root variables of the API page
    String status;
    String copyright;

    Response response;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }
    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

}
