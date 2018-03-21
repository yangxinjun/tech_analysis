package search;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Paths;
import java.security.PublicKey;
import java.sql.*;

public class creat_index {
    final static String cfn = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    final static String url = "jdbc:sqlserver://localhost:1433;DatabaseName=STIMSTEST";
    private IndexWriter writer;
    public  static  Connection con = null;
    public  static PreparedStatement statement = null;
    public  static ResultSet res = null;
    public static void main(String[] args) {
        System.out.println(res);
        long start = System.currentTimeMillis();
        int i=0;
        String indexDir="F:\\IdeaProjects\\lucene_sql\\index\\text";
        try {
            Class.forName(cfn);
            con = DriverManager.getConnection(url,"sa","ilyxjin405405");
            String sql = "select *from Expert";//查询test表
            statement = con.prepareStatement(sql);
            res = statement.executeQuery();
            System.out.println(res);
            while(res.next()){
                i=i+1;
                System.out.println(i);
                String title = res.getString("name");//获取test_name列的元素                                                                                                                                                    ;
                System.out.println("姓名："+title);
                Directory dir=FSDirectory.open(Paths.get(indexDir));
                Analyzer analyzer=new StandardAnalyzer(); // 标准分词器
                IndexWriterConfig iwc=new IndexWriterConfig(analyzer);
                IndexWriter writer=new IndexWriter(dir, iwc);
                Document doc= getDocument(res);
                writer.addDocument(doc);
                System.out.println("索引创建完毕");
                writer.close();
                System.out.print(System.currentTimeMillis() - start);
                System.out.println(" total milliseconds");
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            System.out.println(e);
        }finally{
            try {
                if(res != null) res.close();
                if(statement != null) statement.close();
                if(con != null) con.close();
            } catch (Exception e2) {
                // TODO: handle exception
                e2.printStackTrace();
            }
        }
    }

    public static Document getDocument(ResultSet res)throws Exception {

        Document doc=new Document();
        System.out.println(res);
        doc.add(new TextField("name",res.getString("name"),Field.Store.YES));
        System.out.println(res.getString("name"));
        doc.add(new TextField("enterprisename", res.getString("enterprisename"),Field.Store.YES));
        System.out.println(("22222222"));
//        doc.add(new TextField("sex", res.getString("sex"),Field.Store.YES));
        return doc;
    }
    public static void creat(ResultSet res)
    {
        System.out.println(res);
        long start = System.currentTimeMillis();
        String indexDir="F\\IdeaProjects\\lucene_sql\\index\\text";
        try {
            Directory dir=FSDirectory.open(Paths.get(indexDir));
            Analyzer analyzer=new StandardAnalyzer(); // 标准分词器
            IndexWriterConfig iwc=new IndexWriterConfig(analyzer);
            IndexWriter writer=new IndexWriter(dir, iwc);
            Document doc= getDocument(res);
            writer.addDocument(doc);
            res.close();
            statement.close();
            con.close();
            System.out.println("索引创建完毕");
            writer.close();
            System.out.print(System.currentTimeMillis() - start);
            System.out.println(" total milliseconds");
        }catch (Exception e){
            System.out.println(e);
        }

    }
}