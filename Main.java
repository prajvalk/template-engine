import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Vector;

public class Main {

    public static void main(String[] args) {

        String template = "template.spec";

        String[] targets = {};

        for(String target : targets) {
            Vector<String> t = read(template);

            t = replace(t, "{{ title }}", target);
            t = replace(t, "{{ date }}", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));

            Vector<String> buffer = new Vector<>(1,1);
            Vector<String> content = read(target);
            int i;
            for(i = 0; i < t.size(); i++) {
                if(t.elementAt(i).trim().equals("{{ content }}")) break;
                buffer.addElement(t.elementAt(i));
            }

            buffer = join(buffer, content);

            for(i = i + 1; i < t.size(); i++) buffer.addElement(t.elementAt(i));

            write(target+".gen", buffer);
        }

    }

    public static Vector<String> read(String fn) {
        Vector<String> vec = new Vector<>(1,1);
        try {
            BufferedReader br = new BufferedReader(new FileReader(fn));
            while(true) {
                String s = br.readLine();
                if(s == null) break;
                vec.addElement(s);
            }
            br.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
        return vec;
    }

    public static void write(String fn, Vector<String> data) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(fn));
            for(String st : data) bw.write(st + "\n");
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Vector<String> replace(Vector<String> vector, String target, String replacement) {
        Vector<String> v = new Vector<>(1,1);
        for(int i = 0; i < vector.size(); i++) v.addElement(vector.elementAt(i).replace(target, replacement));
        return v;
    }

    public static Vector<String> join(Vector<String> v1, Vector<String> v2) {
        Vector<String> v = new Vector<>(1,1);
        for(String s : v1) v.addElement(s);
        for(String s : v2) v.addElement(s);
        return v;
    }

}
