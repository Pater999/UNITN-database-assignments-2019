import java.sql.* ;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Random;

public class Soluzione {

    static Connection conn;
    
    public static void main(String[] args) {
        try{
        
        String url = "jdbc:postgresql://sci-didattica.unitn.it/db_118";
        Properties props = new Properties();
        props.setProperty("user", "db_118");
        props.setProperty("password", "root");
        props.setProperty("ssl", "false");
        conn = DriverManager.getConnection(url, props);
        
        
        long currentTime;
        
        // STEP 1
        currentTime = System.nanoTime();
        step1();
        System.out.println("Step 1 needs "+ (System.nanoTime() - currentTime) +" ns");
        
        // STEP 2
        currentTime = System.nanoTime();
        step2();
        System.out.println("Step 2 needs "+ (System.nanoTime() - currentTime) +" ns");
        
        // STEP 3
        currentTime = System.nanoTime();
        ArrayList<Integer> idList = step3();
        System.out.println("Step 3 needs "+ (System.nanoTime() - currentTime) +" ns");
        
        // STEP 4
        currentTime = System.nanoTime();
        step4(idList);
        System.out.println("Step 4 needs "+ (System.nanoTime() - currentTime) +" ns");
        
        // STEP 5
        currentTime = System.nanoTime();
        step5();
        System.out.println("Step 5 needs "+ (System.nanoTime() - currentTime) +" ns");
        
        // STEP 6
        currentTime = System.nanoTime();
        step6();
        System.out.println("Step 6 needs "+ (System.nanoTime() - currentTime) +" ns");
        
        // STEP 7
        currentTime = System.nanoTime();
        step7();
        System.out.println("Step 7 needs "+ (System.nanoTime() - currentTime) +" ns");
        
        // STEP 8
        currentTime = System.nanoTime();
        step8();
        System.out.println("Step 8 needs "+ (System.nanoTime() - currentTime) +" ns");
        
        // STEP 9
        currentTime = System.nanoTime();
        step9();
        System.out.println("Step 9 needs "+ (System.nanoTime() - currentTime) +" ns");
        
        // STEP 10
        currentTime = System.nanoTime();
        step10();
        System.out.println("Step 10 needs "+ (System.nanoTime() - currentTime) +" ns");
        
        // STEP 11
        currentTime = System.nanoTime();
        step11();
        System.out.println("Step 11 needs "+ (System.nanoTime() - currentTime) +" ns");
        
        conn.close();
        
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    
    
    // METODI STEP
    
    private static void step1() throws SQLException
    {
        Statement  st = conn.createStatement();
        String sql = "DROP TABLE if exists \"Professor\" CASCADE;";
        st.executeUpdate(sql);
        sql = "DROP TABLE if exists \"Course\" CASCADE;";
        st.executeUpdate(sql);
        st.close();
    }
    
    private static void step2() throws SQLException
    {
        Statement  st = conn.createStatement();
        String sql = "create table \"Professor\"(\n" +
                     "id int primary key NOT NULL,\n" +
                     "name char(50) NOT NULL,\n" +
                     "address char(50) NOT NULL,\n" +
                     "age int NOT NULL,\n" +
                     "height float NOT NULL\n" +
                     ");";
        st.executeUpdate(sql);
        sql = "create table \"Course\"(\n" +
              "cid char(25) primary key NOT NULL,\n" +
              "title char(50) NOT NULL,\n" +
              "area char(30) NOT NULL,\n" +
              "instructor int NOT null,\n" +
              "FOREIGN KEY (instructor) REFERENCES \"Professor\"(id)\n" +
              ");";
        st.executeUpdate(sql);
        st.close();
    }
    
    private static ArrayList<Integer> step3() throws SQLException
    {
        ArrayList<Float> altezze = generaAltezze();
        ArrayList<Integer> ids = generaId();
        
        Random rand = new Random(); 
        PreparedStatement st = conn.prepareStatement("INSERT INTO \"Professor\" (id,name,address,age,height) VALUES (?,?,?,?,?);");
        for(int i = 0; i < 1000000; i++)
        {
            st.setInt(1, ids.get(i));
            st.setString(2, randomAlpha(rand.nextInt((20 - 5)) + 5, "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghilmnopqrstuvzyxjk"));
            st.setString(3, randomAlpha(rand.nextInt((40 - 9)) + 9, "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghilmnopqrstuvzyxjk1234567890"));
            st.setInt(4, rand.nextInt((88 - 25)) + 25);
            st.setFloat(5, altezze.get(i));
            st.addBatch();
            if(i % 200 == 0 || i == 999999)
            {
                st.executeBatch();
            }
        }
        st.close();
        
        return ids;
    }
    
    private static void step4(ArrayList<Integer> idList) throws SQLException
    {
        Random rand = new Random(); 
        ArrayList<String> cid = generaCid();
        PreparedStatement st = conn.prepareStatement("INSERT INTO \"Course\" (cid, title, area, instructor) VALUES (?,?,?,?);");
        for(int i = 0; i < 1000000; i++)
        {
            st.setString(1, cid.get(i));
            st.setString(2, randomAlpha(rand.nextInt((15 - 5)) + 5,"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghilmnopqrstuvzyxjk"));
            st.setString(3, randomAlpha(rand.nextInt((30 - 5)) + 5, "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghilmnopqrstuvzyxjk1234567890"));
            st.setInt(4, idList.get(rand.nextInt(999999 - 0 + 1) + 0));
            st.addBatch();
            if(i % 200 == 0 || i == 999999)
            {
                st.executeBatch();
            }
        }
        st.close();                         
    }
    
    private static void step5() throws SQLException
    {
        PreparedStatement st = conn.prepareStatement("SELECT id FROM \"Professor\";");
        ResultSet rs = st.executeQuery();
        while (rs.next())
        {
            System.err.println(rs.getString("id"));
        }
        rs.close();
        st.close();
    }
    
    private static void step6() throws SQLException
    {
        PreparedStatement st = conn.prepareStatement("UPDATE \"Professor\" SET height = ? where height = ?;");
        st.setFloat(1,200f);
        st.setFloat(2,185f);
        st.executeUpdate( );
        st.close();
    }
    
    private static void step7() throws SQLException
    {
        PreparedStatement st = conn.prepareStatement("SELECT id, address FROM \"Professor\" where height = ?;");
        st.setFloat(1,200f);
        ResultSet rs = st.executeQuery();
        StringBuilder builder;
        while (rs.next())
        {
            builder = new StringBuilder();
            builder.append(rs.getString("id"));
            builder.append(" ");
            builder.append(rs.getString("address"));
            System.err.println(builder.toString());
        }
        rs.close();
        st.close();
    }
    
    private static void step8() throws SQLException
    {
        Statement  st = conn.createStatement();
        String sql = "CREATE INDEX ON \"Professor\" USING btree(height);";
        st.executeUpdate(sql);
        st.close();
    }
    
    private static void step9() throws SQLException
    {
        PreparedStatement st = conn.prepareStatement("SELECT id FROM \"Professor\";");
        ResultSet rs = st.executeQuery();
        while (rs.next())
        {
            System.err.println(rs.getString("id"));
        }
        rs.close();
        st.close();
    }
    
    private static void step10() throws SQLException
    {
        PreparedStatement st = conn.prepareStatement("UPDATE \"Professor\" SET height = ? where height = ?;");
        st.setFloat(1,210f);
        st.setFloat(2,200f);
        st.executeUpdate( );
        st.close();
    }
    
    private static void step11() throws SQLException
    {
        PreparedStatement st = conn.prepareStatement("SELECT id, address FROM \"Professor\" where height = ?;");
        st.setFloat(1,210f);
        ResultSet rs = st.executeQuery();
        StringBuilder builder;
        while (rs.next())
        {
            builder = new StringBuilder();
            builder.append(rs.getString("id"));
            builder.append(" ");
            builder.append(rs.getString("address"));
            System.err.println(builder.toString());
        }
        rs.close();
        st.close();
    }
    
    
    // METODI GENERAZIONE DATI
    
    private static ArrayList<Float> generaAltezze()
    {
        Random rand = new Random(); 
        
        HashSet<Float> height = new HashSet<Float>();

        float min = (float)100.00000000;
        float max = (float)100.00007000;
        float num;
        while(height.size() < 999999)
        {
            if(rand.nextBoolean() == true)
            {
                num = min + rand.nextFloat() * (max - min);
                if(Float.compare(num, (float)185) != 0);
                {
                    height.add(num);
                } 
            }
            min = max;
            max = max + ((float)0.00000005 + rand.nextFloat() * ((float)0.00012000 - (float)0.00000005));
        }
        ArrayList<Float> listheight = new ArrayList<Float>(height);
        Collections.shuffle(listheight);
        listheight.add(185f);
        return listheight;
    }
    
    private static ArrayList<Integer> generaId()
    {
        Random rand = new Random(); 
        
        HashSet<Integer> ids = new HashSet<Integer>();

        int min = 1;
        int max = 5;
        int num;
        while(ids.size() < 999999)
        {
            if(rand.nextBoolean() == true)
            {
                num = min + rand.nextInt(max - min + 1) + min;
                ids.add(num);
            }
            min = max + 1;
            max = max + 1 + (rand.nextInt(4 - 0 + 1) + 0);
        }
        ArrayList<Integer> idsList = new ArrayList<Integer>(ids);
        Collections.shuffle(idsList);
        idsList.add(max + 1 + (rand.nextInt(200 - 0 + 1) + 0));
        return idsList;
    }
    
    private static ArrayList<String> generaCid()
    {
        HashSet<String> Cids = new HashSet<String>();

        while(Cids.size() < 1000000)
        {
            Cids.add(randomAlpha(10,"ABCDEFGHILMNOPQRSTUVZYKJXW1234567890"));
        }
        ArrayList<String> CidsList = new ArrayList<String>(Cids);
        Collections.shuffle(CidsList);
        return CidsList;
    }
    
    public static String randomAlpha(int count, String stringa) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int)(Math.random()*stringa.length());
            builder.append(stringa.charAt(character));
        }
        return builder.toString();
    }
    
}


