package search;

import  java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;


import com.google.gson.JsonObject;
import org.apache.lucene.analysis.commongrams.CommonGramsQueryFilter;
import  org.apache.lucene.queryparser.classic.QueryParser;
import  org.apache.lucene.queryparser.classic.ParseException;
import  org.apache.lucene.analysis.Analyzer;
import  org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import  org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.search.*;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import com.google.gson.*;

public class search_index {
    public  static JsonObject search(String[] str1, String[] str2)
    {
        JsonObject obj=new JsonObject();
        try {
            Directory directory = FSDirectory.open(Paths.get("F:\\web\\untitled\\index"));
            IndexReader ireader = DirectoryReader.open(directory);
            IndexSearcher searcher = new IndexSearcher(ireader);
            String[] searchField={"name","enterprisename"};
            String[] q={"陆","电科院"};

            BooleanClause.Occur[] clauses = { BooleanClause.Occur.MUST, BooleanClause.Occur.MUST };
            Query query = MultiFieldQueryParser.parse(q, searchField, clauses, new StandardAnalyzer());
            TopDocs hits=searcher.search(query, 3);
            System.out.println("匹配 "+q[0] +"And"+q[1]+"，总共查询到"+hits.totalHits+"个文档");
            //scoreDoc.doc是匹配记录的序号0,1,2
            for(ScoreDoc scoreDoc:hits.scoreDocs){
                System.out.println(scoreDoc.doc);
                Document doc=searcher.doc(scoreDoc.doc);
                System.out.println(doc.get("name"));
                System.out.println(doc.get("enterprisename"));
                obj.addProperty("name",doc.get("name"));
                obj.addProperty("enterprisename",doc.get("enterprisename"));
            }
            ireader.close();
        }catch (Exception e)
        {
            System.out.println(e);
        }

        return obj;
    }

}
