package htmlparser;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HtmlParser {

    public Connection conn;

    private void koneksi() throws SQLException {
        try {
            conn = null;
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/parsehtml", "root", "");
        } catch (Exception es) {
            System.out.println(es.getMessage());
        }
    }

    public void link() throws IOException, SQLException {
        Document doc = (Document) Jsoup.connect("http://uinsby.ac.id").get();

        // URL.
        Elements links = doc.getElementsByTag("a");
        Vector url = new Vector();
        Vector url_2 = new Vector();
        int b = 0;
        int no = 1;
        for (Element link : links) {
            String l = link.absUrl("href");
            if (l.length() > 0) {
                if (l.length() < 4) {
                    l = doc.baseUri() + l.substring(1);

                } else if (!l.substring(0, 4).equals("http")) {
                    l = doc.baseUri() + l.substring(1);
                }
            }
            if (no <= 10) {
                url.add(l);
            } else {
                break;
            }

            //URL_2
            int c = url.size() - 1;
            Document doc_2 = (Document) Jsoup.connect((String) url.get(c)).timeout(0).get();
            System.out.println("=============INI URL " + url.get(c) + "=============");
            String main = (String) url.get(c);
            int as = url.size();
            if (as == 10) {
                //do nothing url.get(c).equals("http://pesma.uinsby.ac.id/"
            } else if (url.get(c).equals("http://pesma.uinsby.ac.id/")) {

            } else {
                // URL.
                Elements links_2 = doc_2.getElementsByTag("a");
                int nomer = 1;
                for (Element link_2 : links_2) {
                    String m = link_2.absUrl("href");
                    if (m.length() > 0) {
                        if (m.length() < 4) {
                            m = doc_2.baseUri() + m.substring(1);

                        } else if (!l.substring(0, 4).equals("http")) {
                            m = doc_2.baseUri() + m.substring(1);
                        }
                    }

                    if (nomer <= 10) {
                        url_2.add(m);
                        System.out.println(m);
                        String sub = m;

                        try {
                            koneksi();
                            Statement stat = conn.createStatement();
                            String sql = "insert into link (main, sub) values ('" + main + "', '" + sub + "');";
                            int a = stat.executeUpdate(sql);
                            System.out.println(sql);
                        } catch (Exception es) {
                            System.out.println(es.getMessage());
                        }
                    }
                    nomer++;
                }
            }
            no++;
        }
        System.out.println(url.size());
        System.out.println(url_2.size());
    }

    public static void main(String[] args) throws IOException, SQLException {
        HtmlParser print = new HtmlParser();
        print.link();
    }

}