/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Parser;

import java.util.StringTokenizer;
import sof_lucene.Entities.AnswerData;
import sof_lucene.Entities.PostData;
import sof_lucene.Entities.QuestionData;

/**
 *
 * @author Alikbar
 */
public class XMLParser {

    public PostData parseData(String xml) {

        String Id = "";
        String postTypeId = "";
        String creationDate = "";
        String score = "";
        String body = "";
        String ownerUserId = "";
        String lastEditorUserId = "";
        String lastEditDate = "";
        String lastActivityDate = "";
        String commentCount = "";
        String parentId = "";
        String acceptedAnswerId = "";
        String viewCount = "";
        String lastEditorDisplayName = "";
        String title = "";
        String tags = "";
        String answerCount = "";
        String favoriteCount = "";
        String communityOwnDate = "";
        String[] result = xml.split("[\"][ ]");
        for (int x = 0; x < result.length; x++) {
            StringTokenizer st = new StringTokenizer(result[x], "[=][\"]");
            String label = st.nextToken();
            String value = "";
            while (st.hasMoreTokens()) {
                value += st.nextToken();
            }
            if (label.contains("PostTypeId")) {
                postTypeId = value;
            } else if (label.contains("AcceptedAnswerId")) {
                acceptedAnswerId = value;
            } else if (label.contains("OwnerUserId")) {
                ownerUserId = value;
            } else if (label.contains("LastEditorUserId")) {
                lastEditorUserId = value;
            } else if (label.contains("ParentId")) {
                parentId = value;
            } else if (label.contains("Id")) {
                Id = value;
            } else if (label.contains("CreationDate")) {
                creationDate = value;
            } else if (label.contains("Score")) {
                score = value;
            } else if (label.contains("ViewCount")) {
                viewCount = value;
            } else if (label.contains("Body")) {
                body = value;
            } else if (label.contains("LastEditorDisplayName")) {
                lastEditorDisplayName = value;
            } else if (label.contains("LastEditDate")) {
                lastEditDate = value;
            } else if (label.contains("LastActivityDate")) {
                lastActivityDate = value;
            } else if (label.contains("Title")) {
                title = value;
            } else if (label.contains("Tags")) {
                tags = value;
            } else if (label.contains("AnswerCount")) {
                answerCount = value;
            } else if (label.contains("CommentCount")) {
                commentCount = value;
            } else if (label.contains("FavoriteCount")) {
                favoriteCount = value;
            } else if (label.contains("CommunityOwnDate")) {
                communityOwnDate = value;
            }
        }
        if (postTypeId.equals("1")) {
            return new QuestionData(acceptedAnswerId, viewCount, lastEditorDisplayName, title, tags, answerCount, favoriteCount, communityOwnDate, Id, postTypeId, creationDate, score, body, ownerUserId, lastEditorUserId, lastEditDate, lastActivityDate, commentCount);
        } else {
            return new AnswerData(parentId, Id, postTypeId, creationDate, score, body, ownerUserId, lastEditorUserId, lastEditDate, lastActivityDate, commentCount);
        }

    }

}
