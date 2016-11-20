/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sof_lucene;

import java.io.IOException;

/**
 *
 * @author Alikbar
 */
public class Utils {
     public String cleanTags(String tags){
        tags=tags.replace("<"," ");
        tags=tags.replace(">"," ");
        return tags;

    }
    public String cleanBody(String body) throws IOException {


        body = body.replace("<p>", " ");
        body = body.replace("</p>", " ");
        body = body.replace("<pre>", " ");
        body = body.replace("</pre>", " ");
        body = body.replace("<code>", " ");
        body = body.replace("</code>", " ");
        body = body.replace("<strong>", " ");
        body = body.replace("</strong>", " ");
        body = body.replace("<ul>", " ");
        body = body.replace("</ul>", " ");
        body = body.replace("<li>", " ");
        body = body.replace("</li>", " ");
        body = body.replace("<em>", " ");
        body = body.replace("</em>", " ");
        body = body.replace("<h1>", " ");
        body = body.replace("</h1>", " ");
        body = body.replace("<h2>", " ");
        body = body.replace("</h2>", " ");
        body = body.replace("<h3>", " ");
        body = body.replace("</h3>", " ");
        body = body.replace("<br>", " ");
        body = body.replace("/*", " ");
        body = body.replace("*/", " ");
        body = body.replace("<a", " ");
        body = body.replace("</a>", " ");
        body = body.replace("<blockquote>", " ");
        body = body.replace("</blockquote>", " ");
        body = body.replace(">", " ");
        body = body.replace("\n", " ");
        body = body.replace("\r", " ");
        body = body.replace("\t", " ");
        body = body.replace(",", " ");
        body = body.replace("p&gt;", " ");
        body = body.replace("&lt;", " ");
        body = body.replace("ul&gt;", " ");
        body = body.replace("&#xA;", " ");
        body = body.replace("li&gt;", " ");
        body = body.replace("&gt;;", " ");
        body = body.replace("s&gt;", " ");  
        return body;
    }
}
