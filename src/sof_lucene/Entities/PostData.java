/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sof_lucene.Entities;

import java.io.IOException;
import org.apache.lucene.index.IndexWriter;

/**
 *
 * @author Alikbar
 */
public class PostData {

    String Id;
    String postTypeId;
    String creationDate;
    String score;
    String body;
    String ownerUserId;
    String lastEditorUserId;
    String lastEditDate;
    String lastActivityDate;
    String commentCount;

    public PostData(String Id, String postTypeId, String creationDate, String score, String body, String ownerUserId, String lastEditorUserId, String lastEditDate, String lastActivityDate, String commentCount) {
        this.Id = Id;
        this.postTypeId = postTypeId;
        this.creationDate = creationDate;
        this.score = score;
        this.body = body;
        this.ownerUserId = ownerUserId;
        this.lastEditorUserId = lastEditorUserId;
        this.lastEditDate = lastEditDate;
        this.lastActivityDate = lastActivityDate;
        this.commentCount = commentCount;
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getPostTypeId() {
        return postTypeId;
    }

    public void setPostTypeId(String postTypeId) {
        this.postTypeId = postTypeId;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getOwnerUserId() {
        return ownerUserId;
    }

    public void setOwnerUserId(String ownerUserId) {
        this.ownerUserId = ownerUserId;
    }

    public String getLastEditorUserId() {
        return lastEditorUserId;
    }

    public void setLastEditorUserId(String lastEditorUserId) {
        this.lastEditorUserId = lastEditorUserId;
    }

    public String getLastEditDate() {
        return lastEditDate;
    }

    public void setLastEditDate(String lastEditDate) {
        this.lastEditDate = lastEditDate;
    }

    public String getLastActivityDate() {
        return lastActivityDate;
    }

    public void setLastActivityDate(String lastActivityDate) {
        this.lastActivityDate = lastActivityDate;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    public void addDoc(IndexWriter w) throws IOException {

    }
}
