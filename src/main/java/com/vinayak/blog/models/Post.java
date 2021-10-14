package com.vinayak.blog.models;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "post_id")
    private int id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "excerpt", columnDefinition = "TEXT")
    private String excerpt;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "author", columnDefinition = "TEXT")
    private String author;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Date updatedAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "published_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Date publishedAt;

    @Column(name = "is_published")
    private boolean isPublished;

    @Column(name = "authorId")
    private int authorId;

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    @ManyToMany(cascade= {CascadeType.PERSIST, CascadeType.REMOVE}, fetch=FetchType.EAGER)
    @JoinTable(name = "post_tags",
                joinColumns = @JoinColumn(name = "post_id",referencedColumnName = "post_id"),
                    inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
    private List<Tag> tags;

    public Post() {
    }

    public Post(String title, String excerpt, String content, String author, Date createdAt, Date updatedAt, Date publishedAt, boolean isPublished, List<Tag> tags) {
        this.title = title;
        this.excerpt = excerpt;
        this.content = content;
        this.author = author;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.publishedAt = publishedAt;
        this.isPublished = isPublished;
        this.tags = tags;
    }

    public List<Tag> getTags() {
        return tags;
    }


    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public boolean isPublished() {
        return isPublished;
    }

    public void setPublished(boolean published) {
        isPublished = published;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date created_at) {
        this.createdAt = created_at;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updated_at) {
        this.updatedAt = updated_at;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Date published_at) {
        this.publishedAt = published_at;
    }

}
