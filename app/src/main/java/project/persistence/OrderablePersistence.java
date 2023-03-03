package project.persistence;

import project.objects.Orderable;
import project.objects.Product;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrderablePersistence implements OrderableDatabase{
    private DatabaseManager db;

    public OrderablePersistence(){
        this.db = new DatabaseManager();
        if(!db.databaseExists()) {
            db.createDB();
        }
        db.forceCurrentVersion();
    }


    @Override
    public void addOrderable(Orderable orderable) {
        String values = this.getSQLOrderableString(orderable);
        try {
            Statement statement = this.db.exportStatement();
            statement.execute("insert into orderable values " + values);
            this.db.terminate();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void removeOrderable(String name) {
        try {
            Statement statement = this.db.exportStatement();
            statement.execute("delete from orderable where name ="+ "'"+name+"'");
            this.db.terminate();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void replaceOrderable(Orderable orderable) {
        this.removeOrderable(orderable.getName());
        this.addOrderable(orderable);
    }

    @Override
    public Orderable getOrderable(String name) {
        try{
            Statement statement = db.exportStatement();
            ResultSet res = statement.executeQuery("select * from orderable where name = " + "'" + name+ "'");
            Orderable ord = extractOrderableFromResultSet(res);
            db.terminate();
            return ord;
        } catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }



    @Override
    public List<Orderable> getOrderableList() {
        ArrayList<Orderable> ords = new ArrayList<>();
        try{
            Statement statement = db.exportStatement();
            ResultSet res = statement.executeQuery("select name from orderable");
            ords = this.extractOrderableListFromResultSet(res);
            db.terminate();
        }catch(SQLException e){
            e.printStackTrace();
        }

        return ords;
    }


    private String getSQLOrderableString(Orderable orderable){
        String price = orderable.getPrice().toString();
        String shelfLife = orderable.getShelfLife().toString();
        return "(" + "'" + orderable.getName() + "'"
                + ", " + "'" + price + "'"
                + ", " + "'" + shelfLife + "'" + ")";
    }


    private Orderable extractOrderableFromResultSet(ResultSet res) {
        String name;
        Float price;
        Integer shelfLife;

        try{
            if(res != null){
                res.next();

                name = res.getString("name");
                price = res.getFloat("price");
                shelfLife = res.getInt("shelf_life");

                return new Orderable(name, price, shelfLife);
            }
            else {
                return null;
            }
        } catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    private ArrayList<Orderable> extractOrderableListFromResultSet(ResultSet res){
        ArrayList<Orderable> ords = new ArrayList<>();
        try{
            if(res != null){
                Orderable o;
                while(res.next()){
                    o = this.getOrderable(res.getString("name"));
                    ords.add(o);
                }
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        return ords;
    }

}
