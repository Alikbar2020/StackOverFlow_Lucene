/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sof_lucene.Entities;

import java.io.IOException;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import sof_lucene.Utils;

/**
 *
 * @author Alikbar
 */
public class QuestionData extends PostData {

    String acceptedAnswerId;
    Utils util;
    public QuestionData(String acceptedAnswerId, String viewCount, String lastEditorDisplayName, String title, String tags, String answerCount, String favoriteCount, String communityOwnDate, String Id, String postTypeId, String creationDate, String score, String body, String ownerUserId, String lastEditorUserId, String lastEditDate, String lastActivityDate, String commentCount) {
        super(Id, postTypeId, creationDate, score, body, ownerUserId, lastEditorUserId, lastEditDate, lastActivityDate, commentCount);
        this.acceptedAnswerId = acceptedAnswerId;
        this.viewCount = viewCount;
        this.lastEditorDisplayName = lastEditorDisplayName;
        this.title = title;
        this.tags = tags;
        this.answerCount = answerCount;
        this.favoriteCount = favoriteCount;
        this.communityOwnDate = communityOwnDate;
        util = new Utils();
    }
    String viewCount;
    String lastEditorDisplayName;
    String title;
    String tags;
    String answerCount;
    String favoriteCount;
    String communityOwnDate;

    public String getAcceptedAnswerId() {
        return acceptedAnswerId;
    }

    public void setAcceptedAnswerId(String acceptedAnswerId) {
        this.acceptedAnswerId = acceptedAnswerId;
    }

    public String getViewCount() {
        return viewCount;
    }

    public void setViewCount(String viewCount) {
        this.viewCount = viewCount;
    }

    public String getLastEditorDisplayName() {
        return lastEditorDisplayName;
    }

    public void setLastEditorDisplayName(String lastEditorDisplayName) {
        this.lastEditorDisplayName = lastEditorDisplayName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getAnswerCount() {
        return answerCount;
    }

    public void setAnswerCount(String answerCount) {
        this.answerCount = answerCount;
    }

    public String getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(String favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public String getCommunityOwnDate() {
        return communityOwnDate;
    }

    public void setCommunityOwnDate(String communityOwnDate) {
        this.communityOwnDate = communityOwnDate;
    }
    @Override
    public void addDoc(IndexWriter w) throws IOException {
        Document doc = new Document();
        doc.add(new StringField("Id", this.Id, Field.Store.YES));
        doc.add(new StringField("postTypeId", this.postTypeId, Field.Store.YES));
        doc.add(new StringField("acceptedAnswerId", this.acceptedAnswerId, Field.Store.YES));
        doc.add(new StringField("creationDate", this.creationDate, Field.Store.YES));
        doc.add(new StringField("score", this.score, Field.Store.YES));
        doc.add(new TextField("body", util.cleanBody(this.body), Field.Store.YES));
        doc.add(new StringField("ownerUserId", this.ownerUserId, Field.Store.YES));
        doc.add(new StringField("lastEditorUserId", this.lastEditorUserId, Field.Store.YES));
        doc.add(new StringField("lastEditDate", this.lastEditDate, Field.Store.YES));
        doc.add(new StringField("viewCount", this.viewCount, Field.Store.YES));
        doc.add(new StringField("lastActivityDate", this.lastActivityDate, Field.Store.YES));
        doc.add(new StringField("commentCount", this.commentCount, Field.Store.YES));
        doc.add(new TextField("lastEditorDisplayName", this.lastEditorDisplayName, Field.Store.YES));
        doc.add(new TextField("title", this.title, Field.Store.YES));
        doc.add(new TextField("tags", util.cleanTags(this.tags), Field.Store.YES));
        doc.add(new StringField("answerCount", this.answerCount, Field.Store.YES));
        doc.add(new StringField("favoriteCount", this.favoriteCount, Field.Store.YES));
        doc.add(new StringField("communityOwnDate", this.communityOwnDate, Field.Store.YES));
        w.addDocument(doc);
    }
}
