package cale.spring.Movies.model;

import java.util.Objects;

public class Result {
    private Long id;
    private String header;
    private String photoUrl;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Result(Long id, String header, String photoUrl, String type) {
        this.id = id;
        this.header = header;
        this.photoUrl = photoUrl;
        this.type = type;
    }

    public Result(Long id, String header, String type) {
        this.id = id;
        this.header = header;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Result result = (Result) o;
        return id.equals(result.id) && type.equals(result.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type);
    }
}
