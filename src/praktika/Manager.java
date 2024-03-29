package praktika;

import javafx.scene.image.Image;
import praktika.Model.Product;
import praktika.Model.User;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.*;
import java.util.ArrayList;

// visas valdymas
public class Manager {

    Connection conn = null;
    private boolean isAdmin = false;
    private boolean isOnline = false;

    private void connectDB() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String DB_URL = "jdbc:mysql://localhost:3306/meniu";
        String DB_NAME = "root";
        String DB_PASS = "0000";
        conn = DriverManager.getConnection(DB_URL, DB_NAME, DB_PASS);
    }

    private void disconnectDB() throws SQLException {
        if(!conn.isClosed())
            conn.close();
    }

    public ArrayList<Product> getAllProductsFromSession(String sessionCode) throws Exception {
        ArrayList<Product> prekes = new ArrayList<>();
        connectDB();
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM prekes p, krepselis k WHERE sesijos_kodas = ? AND p.id = k.prekes_id"); // preparesStatement, kai reikia inputo "?". Ir nori apsisauogti nuo sql injection.
        ps.setString(1, sessionCode); // sita vieta parodo, kad vietoj pirmo klaustuko irasyti reikia.
        ResultSet rs = ps.executeQuery(); // exucute gauni rezultata

        while(rs.next()){
            int id = rs.getInt("id");
            String pavadinmas = rs.getString("pavadinimas");
            String aprasymas = rs.getString("aprasymas");
            double kainabepvm = rs.getDouble("bepvm");
            int id_krepselyje = rs.getInt("krepselio_id");
            int kiekis = rs.getInt("kiekis");

            InputStream is= rs.getBlob("paveikslelis").getBinaryStream();
            OutputStream os=new FileOutputStream(new File("img.jpg"));

            byte [] content= new byte[1024];
            int size=0;

            while ((size=is.read(content))!=-1)
                os.write(content, 0, size);

            os.close();
            is.close();

            Image image1=new Image("file:img.jpg");

            Product pr = new Product(id, pavadinmas, aprasymas, kainabepvm, image1);
            pr.setId_krepselyje(id_krepselyje);
            pr.setKiekis(kiekis);
            prekes.add(pr);
        }

        rs.close();
        ps.close();
        disconnectDB();
        return prekes;
    }

    public void editProductInfo(int productId, int price) throws Exception {
        Product product = getProductByID(productId);

        connectDB();
        PreparedStatement ps = conn.prepareStatement("UPDATE prekes SET bepvm = ? WHERE id = ?");
        ps.setInt(1, price);
        ps.setInt(2, product.getId());
        ps.executeUpdate();

        ps.close();
        disconnectDB();
    }

    public void insertIntoBasket(Product p, String sessionCode) throws Exception {
        connectDB();
        PreparedStatement ps = conn.prepareStatement("INSERT INTO krepselis (sesijos_kodas, prekes_id, kiekis) VALUES(?, ?, ?)");
        ps.setString(1, sessionCode);
        ps.setInt(2, p.getId());
        ps.setInt(3, p.getKiekis());
        ps.executeUpdate();
        ps.close();
        disconnectDB();
    }

    public void updateProcuctInfo(Product p) throws Exception {
        connectDB();
        PreparedStatement ps = conn.prepareStatement("UPDATE krepselis SET kiekis = ? WHERE krepselio_id = ?");
        ps.setInt(1, p.getKiekis());
        ps.setInt(2, p.getId_krepselyje());
        ps.executeUpdate();
        ps.close();
        disconnectDB();
    }

    public Product getProductByID(int productID) throws Exception {
        connectDB();
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM prekes WHERE id = ?");
        ps.setInt(1, productID);
        ResultSet rs = ps.executeQuery();
        Product preke = null;

        while(rs.next()){
            int id = rs.getInt("id");
            String pavadinmas = rs.getString("pavadinimas");
            String aprasymas = rs.getString("aprasymas");
            double kainabepvm = rs.getDouble("bepvm");

            InputStream is= rs.getBlob("paveikslelis").getBinaryStream();
            OutputStream os=new FileOutputStream(new File("img.jpg"));

            byte [] content= new byte[1024];
            int size=0;

            while ((size=is.read(content))!=-1)
                os.write(content, 0, size);

            os.close();
            is.close();

            Image image1=new Image("file:img.jpg");
            preke = new Product(id, pavadinmas, aprasymas, kainabepvm, image1);
        }

        rs.close();
        ps.close();
        disconnectDB();
        return preke;
    }

    public User getUserByUsername(String username) throws Exception {
        connectDB();
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM vartotojai WHERE slapyvardis = ?");
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
        User user = null;

        while(rs.next()){
            int id = rs.getInt("id");
            String nickname = rs.getString("slapyvardis");
            String pass = rs.getString("slaptazodis");
            boolean isAdmin = rs.getBoolean("admin");

            user = new User(id, nickname, pass, isAdmin);
        }

        rs.close();
        ps.close();
        disconnectDB();
        return user;
    }
    // tikrinamas loginas
    public boolean isValidLogin(User user, String pass){
        if(user != null)
            return user.getPassword().equals(pass);
        return false;
    }

    public void insertUser(User user) throws Exception {
        connectDB();
        PreparedStatement ps = conn.prepareStatement("INSERT INTO vartotojai (id, slapyvardis, slaptazodis, admin) VALUES (?, ?, ?, ?)");
        ps.setInt(1, user.getId());
        ps.setString(2, user.getUsername());
        ps.setString(3, user.getPassword());
        ps.setBoolean(4, false);
        ps.executeUpdate();

        ps.close();
        disconnectDB();
    }

    public void deleteProduct(Product p) throws Exception {
        connectDB();
        PreparedStatement ps = conn.prepareStatement("DELETE FROM krepselis WHERE krepselio_id = ?");
        ps.setInt(1, p.getId_krepselyje());
        ps.executeUpdate();
        ps.close();
        disconnectDB();
    }

    public String getSumOfProductsWithoutVAT(String sessionCode) throws Exception {
        double sum = 0;
        ArrayList<Product> prekes = getAllProductsFromSession(sessionCode);
        for(Product p : prekes)
            sum += (p.getKainabepvm() * p.getKiekis());
        String suma = String.format("%.2f", sum);
        return suma;
    }

    public String getSumOfProductsWithVAT(String sessionCode) throws Exception {
        double sum = 0;
        ArrayList<Product> prekes = getAllProductsFromSession(sessionCode);
        for(Product p : prekes)
            sum += (p.getKainasupvm() * p.getKiekis());
        return String.format("%.2f", sum);
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }
}
