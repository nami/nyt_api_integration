package nyt.model.nyt;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import nyt.model.nyt.Docs;
import nyt.model.nyt.Meta;

// if it's not in your class, it will not map it
@JsonIgnoreProperties(ignoreUnknown = true)
public class Response {

    // array of docs
    Docs[] docs;
    Meta meta;

    // getters and setters
    public Docs[] getDocs() {
        return docs;
    }

    public void setDocs(Docs[] docs) {
        this.docs = docs;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }
}
