package demo.lucene;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.FSDirectory;


public class TestLucene {

    //创建索引存储位置
    public static void main(String[] args) {
        String usage = "java org.apache.lucene.demo.IndexFiles [-index INDEX_PATH] [-docs DOCS_PATH] [-update]\n\nThis indexes the documents in DOCS_PATH, creating a Lucene indexin INDEX_PATH that can be searched with SearchFiles";
        String basicPath="E:\\public_space\\Java-Interview\\target";
        String indexPath = basicPath+"\\indexs";
        String docsPath = basicPath+"\\docs";
        boolean create = true;
        if (docsPath == null) {
            System.err.println("Usage: " + usage);
            System.exit(1);
        }
        // 获取需要分析的文件目录
        Path docDir = Paths.get(docsPath);
        if (!Files.isReadable(docDir)) {
            System.out.println("Document directory '" + docDir.toAbsolutePath()
                    + "' does not exist or is not readable, please check the path");
//            System.exit(1);
        }
        Date start = new Date();
        try {
            System.out.println("Indexing to directory '" + indexPath + "'...");
            // 打开需要创建索引的文件夹
            FSDirectory fSDirectory = FSDirectory.open(Paths.get(indexPath));
            // 创建标准的分析器
            StandardAnalyzer standardAnalyzer = new StandardAnalyzer();
            // 创建索引写入配置
            IndexWriterConfig iwc = new IndexWriterConfig(standardAnalyzer);
            // 创建当前的索引写入模式
            if (create) {
                iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
            } else {
                iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
            }
            // 通过需要添加索引的文件夹和写入配置创建索引写入器
            IndexWriter writer = new IndexWriter(fSDirectory, iwc);
            // 开始创建索引文件夹中的文件
            indexDocs(writer, docDir);
            writer.close();
            Date end = new Date();
            System.out.println((end.getTime() - start.getTime()) + " total milliseconds");
        } catch (IOException e) {
            System.out.println(" caught a " + e.getClass() + "\n with message: " + e.getMessage());
        }
    }

    static void indexDocs(final IndexWriter writer, Path path) throws IOException {
        // 判断当前的路径是否为文件夹
        if (Files.isDirectory(path)) {
            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    try {
                        // 调用下面的方法开始索引当前的文件夹
                        TestLucene.indexDoc(writer, file, attrs.lastModifiedTime().toMillis());
                    } catch (IOException iOException) {
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } else {
            indexDoc(writer, path, Files.getLastModifiedTime(path, new java.nio.file.LinkOption[0]).toMillis());
        }
    }

    static void indexDoc(IndexWriter writer, Path file, long lastModified) throws IOException {
        try (InputStream stream = Files.newInputStream(file)) {
            // 创建Lucene的文档
            Document doc = new Document();
            // 创建字符字段，path写为当前的文件夹中的文件路径
            StringField stringField = new StringField("path", file.toString(), Field.Store.YES);
            // 为文档写入当前的path字段
            doc.add(stringField);
            // 为文档写入modified字段
            doc.add(new LongPoint("modified", lastModified));
            // 为文档添加contents字段其内容为当前的文本内容
            doc.add(new TextField("contents",
                    new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))));
            // 最后更加模式使用添加还是更新的文档
            if (writer.getConfig().getOpenMode() == IndexWriterConfig.OpenMode.CREATE) {
                System.out.println("adding " + file);
                writer.addDocument(doc);
            } else {
                System.out.println("updating " + file);
                writer.updateDocument(new Term("path", file.toString()), doc);
            }
        }
    }
}
