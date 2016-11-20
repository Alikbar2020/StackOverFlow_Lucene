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
public class AnswerData extends PostData {


    Utils util;
    String parentId;

    public AnswerData(String parentId, String Id, String postTypeId, String creationDate, String score, String body, String ownerUserId, String lastEditorUserId, String lastEditDate, String lastActivityDate, String commentCount) {
        super(Id, postTypeId, creationDate, score, body, ownerUserId, lastEditorUserId, lastEditDate, lastActivityDate, commentCount);
        this.parentId = parentId;
        util = new Utils();
    }

    

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @Override
    public void addDoc(IndexWriter w) throws IOException {

        Document doc = new Document();
        doc.add(new StringField("Id", this.Id, Field.Store.YES));
        doc.add(new StringField("postTypeId", this.postTypeId, Field.Store.YES));
        doc.add(new StringField("creationDate", this.creationDate, Field.Store.YES));
        doc.add(new StringField("score", this.score, Field.Store.YES));
        doc.add(new TextField("body", util.cleanBody(this.body), Field.Store.YES));
        doc.add(new StringField("ownerUserId", this.ownerUserId, Field.Store.YES));
        doc.add(new StringField("lastEditorUserId", this.lastEditorUserId, Field.Store.YES));
        doc.add(new StringField("lastEditDate", this.lastEditDate, Field.Store.YES));
        doc.add(new StringField("lastActivityDate", this.lastActivityDate, Field.Store.YES));
        doc.add(new StringField("commentCount", this.commentCount, Field.Store.YES));
        doc.add(new StringField("parentId", this.parentId, Field.Store.YES));
        w.addDocument(doc);
    }

}
