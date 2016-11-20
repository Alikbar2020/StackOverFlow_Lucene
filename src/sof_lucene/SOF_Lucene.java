/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sof_lucene;

import Parser.XMLParser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;

/**
 *
 * @author Alikbar
 */
public class SOF_Lucene {

    public SOF_Lucene() {
        analyzer = new StandardAnalyzer();

        try {
            index = FSDirectory.open(new File("index").toPath());
        } catch (IOException ex) {
            Logger.getLogger(SOF_Lucene.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static Directory index;
    static StandardAnalyzer analyzer;

    public static void main(String[] args) throws IOException, ParseException {
        analyzer = new StandardAnalyzer();

        index = FSDirectory.open(new File("index").toPath());

//        IndexWriterConfig config = new IndexWriterConfig(analyzer);
//
//        IndexWriter w = new IndexWriter(index, config);
//
//        FileReader fr = new FileReader("/Volumes/Alikbar/Data/Posts/Posts.xml");
//        BufferedReader bfr = new BufferedReader(fr);
//        int count = 0;
//        while (bfr.ready()) {
//            String xml = bfr.readLine();
//            if (xml.contains("row")) {
//                new XMLParser().parseData(xml).addDoc(w);
//            }
//            System.out.println(count);
//            count++;
//        }
//        w.close();
        // **1
        //new SOF_Lucene().searchQuestionBodyByTerm("how do i");
        //**2
        //new SOF_Lucene().searchQuestionBodyByQueryDateRange("java", 2008, 7, 31, 2009, 2, 12);
        // **3
//        ArrayList<String> al = new ArrayList<>();
//        al.add("IOS");
//        al.add("Windows");
//        new SOF_Lucene().searchQuestionByTag("java android", al);
        //**4
        // new SOF_Lucene().searchAnswerBodyByQuestionTermAndAcceptedFlag("python", true);
        //**5
        // new SOF_Lucene().getBestAnswer("19164");
    }

    public ArrayList<String> searchQuestionBodyByTerm(String input) throws IOException {
        String querystr = input;

        Query bodyQuery = null;
        try {
            bodyQuery = new QueryParser("body", analyzer).parse(querystr);
        } catch (org.apache.lucene.queryparser.classic.ParseException e) {
            e.printStackTrace();
        }
        Query typeQuery = null;
        try {
            typeQuery = new QueryParser("postTypeId", analyzer).parse("1");
        } catch (org.apache.lucene.queryparser.classic.ParseException e) {
            e.printStackTrace();
        }
        BooleanQuery.Builder a = new BooleanQuery.Builder();
        a.add(typeQuery, Occur.MUST);
        a.add(bodyQuery, Occur.MUST);
        BooleanQuery finalQuery = a.build();

        int hitsPerPage = 100;
        IndexReader reader = DirectoryReader.open(index);
        IndexSearcher searcher = new IndexSearcher(reader);
        TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage);
        searcher.search(finalQuery, collector);
        ScoreDoc[] hits = collector.topDocs().scoreDocs;

        System.out.println("Found " + hits.length + " hits.");
        for (int i = 0; i < hits.length; ++i) {
            int docId = hits[i].doc;
            Document d = searcher.doc(docId);
            System.out.println((i + 1) + ". " + d.get("Id") + "\t" + d.get("postTypeId") + "\t" + d.get("body"));
        }

        reader.close();
        return null;
    }

    public ArrayList<String> searchQuestionBodyByQueryDateRange(String query, int startYear, int startMonth, int startDay,
            int endYear, int endMonth, int endDay) throws IOException {
        String querystr = query;
        String minDate = startYear + "-" + String.format("%02d", startMonth) + "-"
                + String.format("%02d", startDay) + "T00:00:00.000";
        String maxDate = endYear + "-" + String.format("%02d", endMonth) + "-"
                + String.format("%02d", endDay) + "T23:59:59.999";
        Query bodyQuery = null;
        try {
            bodyQuery = new QueryParser("body", analyzer).parse(querystr);
        } catch (org.apache.lucene.queryparser.classic.ParseException e) {
            e.printStackTrace();
        }

        Query queryDate = new TermRangeQuery("creationDate", new BytesRef(minDate), new BytesRef(maxDate), true, true);
        BooleanQuery.Builder a = new BooleanQuery.Builder();
        a.add(queryDate, Occur.MUST);
        a.add(bodyQuery, Occur.MUST);
        BooleanQuery finalQuery = a.build();

        int hitsPerPage = 100;
        IndexReader reader = DirectoryReader.open(index);
        IndexSearcher searcher = new IndexSearcher(reader);
        TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage);
        searcher.search(queryDate, collector);
        ScoreDoc[] hits = collector.topDocs().scoreDocs;

        System.out.println("Found " + hits.length + " hits.");
        for (int i = 0; i < hits.length; ++i) {
            int docId = hits[i].doc;
            Document d = searcher.doc(docId);
            System.out.println((i + 1) + ". " + d.get("Id") + "\t" + d.get("postTypeId") + "\t" + d.get("body"));
        }

        reader.close();
        return null;
    }

    public ArrayList<String> searchQuestionByTag(String query, ArrayList<String> tags) throws IOException {
        String querystr = query;
        String tagQuery = "";
        for (String tag : tags) {
            tagQuery = tagQuery + " " + tag;
        }
        Query bodyQuery = null;
        try {
            bodyQuery = new QueryParser("body", analyzer).parse(querystr);
        } catch (org.apache.lucene.queryparser.classic.ParseException e) {
            e.printStackTrace();
        }
        Query queryTags = null;
        try {
            queryTags = new QueryParser("tags", analyzer).parse(tagQuery);
        } catch (org.apache.lucene.queryparser.classic.ParseException e) {
            e.printStackTrace();
        }
        BooleanQuery.Builder a = new BooleanQuery.Builder();
        a.add(queryTags, Occur.MUST);
        a.add(bodyQuery, Occur.MUST);
        BooleanQuery finalQuery = a.build();

        int hitsPerPage = 100;
        IndexReader reader = DirectoryReader.open(index);
        IndexSearcher searcher = new IndexSearcher(reader);
        TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage);
        searcher.search(finalQuery, collector);
        ScoreDoc[] hits = collector.topDocs().scoreDocs;

        System.out.println("Found " + hits.length + " hits.");
        for (int i = 0; i < hits.length; ++i) {
            int docId = hits[i].doc;
            Document d = searcher.doc(docId);
            System.out.println((i + 1) + ". " + d.get("Id") + "\t" + d.get("postTypeId") + "\t" + d.get("body"));
        }

        reader.close();
        return null;
    }

    public ArrayList<String> searchAnswerBodyByQuestionTermAndAcceptedFlag(String questionTerm, Boolean accpeted) throws IOException {
        String querystr = questionTerm;

        Query bodyQuery = null;
        try {
            bodyQuery = new QueryParser("body", analyzer).parse(querystr);
        } catch (org.apache.lucene.queryparser.classic.ParseException e) {
            e.printStackTrace();
        }
        Query typeQuery = null;
        try {
            typeQuery = new QueryParser("postTypeId", analyzer).parse("1");
        } catch (org.apache.lucene.queryparser.classic.ParseException e) {
            e.printStackTrace();
        }
        BooleanQuery.Builder a = new BooleanQuery.Builder();
        a.add(typeQuery, Occur.MUST);
        a.add(bodyQuery, Occur.MUST);
        BooleanQuery finalQuery = a.build();

        int hitsPerPage = 100;
        IndexReader reader = DirectoryReader.open(index);
        IndexSearcher searcher = new IndexSearcher(reader);
        TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage);
        searcher.search(finalQuery, collector);
        ScoreDoc[] hits = collector.topDocs().scoreDocs;

        if (accpeted) {
            for (int i = 0; i < hits.length; ++i) {
                int docId = hits[i].doc;
                Document d = searcher.doc(docId);

                String id = d.get("acceptedAnswerId");
                String parentId = d.get("Id");
                if (!id.equals("")) {
                    System.out.println((i + 1) + ". " + d.get("Id") + "\t" + d.get("postTypeId") + "\t" + d.get("body"));
                    TopScoreDocCollector collectorfinal = TopScoreDocCollector.create(hitsPerPage);
                    Query idQuery = null;
                    try {
                        idQuery = new QueryParser("Id", analyzer).parse(id);
                    } catch (org.apache.lucene.queryparser.classic.ParseException e) {
                        e.printStackTrace();
                    }

                    Query typeQuery1 = null;
                    try {
                        typeQuery1 = new QueryParser("postTypeId", analyzer).parse("2");
                    } catch (org.apache.lucene.queryparser.classic.ParseException e) {
                        e.printStackTrace();
                    }
                    Query parentIdQuery = null;
                    try {
                        parentIdQuery = new QueryParser("parentId", analyzer).parse(parentId);
                    } catch (org.apache.lucene.queryparser.classic.ParseException e) {
                        e.printStackTrace();
                    }
                    IndexSearcher searcher1 = new IndexSearcher(reader);
                    BooleanQuery.Builder a1 = new BooleanQuery.Builder();
                    a1.add(typeQuery1, Occur.MUST);
                    a1.add(idQuery, Occur.MUST);
                    a1.add(parentIdQuery, Occur.MUST);
                    BooleanQuery finalQuery1 = a1.build();
                    searcher1.search(finalQuery1, collectorfinal);
                    ScoreDoc[] hitsFinal = collectorfinal.topDocs().scoreDocs;
                    for (int j = 0; j < hitsFinal.length; ++j) {
                        int docIdFinal = hitsFinal[j].doc;
                        Document dFinal = searcher1.doc(docIdFinal);
                        System.out.println((j + 1) + ". " + dFinal.get("Id") + "\t" + dFinal.get("postTypeId") + "\t" + dFinal.get("body"));
                    }

                }

            }
        } else {
            for (int i = 0; i < hits.length; ++i) {
                int docId = hits[i].doc;
                Document d = searcher.doc(docId);

                String id = d.get("acceptedAnswerId");
                String parentId = d.get("Id");
                if (!id.equals("")) {
                    System.out.println((i + 1) + ". " + d.get("Id") + "\t" + d.get("postTypeId") + "\t" + d.get("body"));
                    TopScoreDocCollector collectorfinal = TopScoreDocCollector.create(hitsPerPage);
                    Query idQuery = null;
                    try {
                        idQuery = new QueryParser("Id", analyzer).parse(id);
                    } catch (org.apache.lucene.queryparser.classic.ParseException e) {
                        e.printStackTrace();
                    }

                    Query typeQuery1 = null;
                    try {
                        typeQuery1 = new QueryParser("postTypeId", analyzer).parse("2");
                    } catch (org.apache.lucene.queryparser.classic.ParseException e) {
                        e.printStackTrace();
                    }
                    Query parentIdQuery = null;
                    try {
                        parentIdQuery = new QueryParser("parentId", analyzer).parse(parentId);
                    } catch (org.apache.lucene.queryparser.classic.ParseException e) {
                        e.printStackTrace();
                    }
                    IndexSearcher searcher1 = new IndexSearcher(reader);
                    BooleanQuery.Builder a1 = new BooleanQuery.Builder();
                    a1.add(typeQuery1, Occur.MUST);
                    a1.add(idQuery, Occur.MUST_NOT);
                    a1.add(parentIdQuery, Occur.MUST);
                    BooleanQuery finalQuery1 = a1.build();
                    searcher1.search(finalQuery1, collectorfinal);
                    ScoreDoc[] hitsFinal = collectorfinal.topDocs().scoreDocs;
                    for (int j = 0; j < hitsFinal.length; ++j) {
                        int docIdFinal = hitsFinal[j].doc;
                        Document dFinal = searcher1.doc(docIdFinal);
                        System.out.println((j + 1) + ". " + dFinal.get("Id") + "\t" + dFinal.get("postTypeId") + "\t" + dFinal.get("body"));
                    }

                }

            }
        }
        reader.close();
        return null;
    }

    public String getBestAnswer(String questionId) throws IOException {
        String querystr = questionId;

        Query idQuery = null;
        try {
            idQuery = new QueryParser("parentId", analyzer).parse(querystr);
        } catch (org.apache.lucene.queryparser.classic.ParseException e) {
            e.printStackTrace();
        }
        Query typeQuery = null;
        try {
            typeQuery = new QueryParser("postTypeId", analyzer).parse("2");
        } catch (org.apache.lucene.queryparser.classic.ParseException e) {
            e.printStackTrace();
        }
        BooleanQuery.Builder a = new BooleanQuery.Builder();
        a.add(idQuery, Occur.MUST);
        a.add(typeQuery, Occur.MUST);
        BooleanQuery finalQuery = a.build();

        int hitsPerPage = 100;
        IndexReader reader = DirectoryReader.open(index);
        IndexSearcher searcher = new IndexSearcher(reader);
        TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage);
        searcher.search(finalQuery, collector);
        ScoreDoc[] hits = collector.topDocs().scoreDocs;

        int score = -1000;
        Document d = null;
        for (int i = 0; i < hits.length; ++i) {
            int docId = hits[i].doc;
            if (Integer.valueOf(searcher.doc(docId).get("score")) > score) {
                d = searcher.doc(docId);
            }

        }
        if (d != null) {
            System.out.println("Best Answer. " + d.get("Id") + "\t" + d.get("postTypeId") + "\t" + d.get("body"));
        }
        reader.close();
        return null;
    }
}
