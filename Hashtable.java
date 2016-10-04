package hastable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Hastable {

    public static void main(String[] args) throws IOException {

        Document doc = Jsoup.connect("http://uinsby.ac.id").get();
        Elements links = doc.select("body");
        String a = links.text();
        
        String ss;
        ss = a.replaceAll("dan", "").replaceAll("tapi", "").replaceAll("dengan", "").replaceAll("yang", "").
                replaceAll("untuk", "").replaceAll("namun", "").replaceAll("karena", "").replaceAll("jika", "").
                replaceAll("agar", "").replaceAll("&", "").replaceAll("-", "").replaceAll(":", "");
        StringTokenizer b = new StringTokenizer(ss, " ");

        Vector<String> yeye = new Vector();
        Vector<String> yuyu = new Vector();

        while (b.hasMoreTokens()) {
            yeye.add(b.nextToken());
        }

        for (int i = 0; i < yeye.size(); i++) {
            yuyu.add(yeye.get(i));
        }

        for (int i = 0; i < yeye.size(); i++) {
            for (int j = 0; j < i; j++) {
                if (i != j && yeye.get(i).equals(yeye.get(j))) {
                    yuyu.remove(yeye.get(i));
                    break;
                }
            }
        }

        Vector jumlah = new Vector();

        //menghitung jumlah kata
        for (Object key : yuyu) {
            int jum = 0;
            for (String w : yeye) {
                if (key.equals(w)) {
                    jum++;
                }
            }
            jumlah.add(jum);
        }


        Hashtable data = new Hashtable();
        
        for (int i = 0; i < yuyu.size(); i++) {
            data.put(yuyu.get(i), jumlah.get(i));
        }
        Enumeration k;
        k = data.keys();
        String v;
        while (k.hasMoreElements()) {
            v = (String) k.nextElement();
            System.out.println(v + ": " + data.get(v));
        }

    }
}
