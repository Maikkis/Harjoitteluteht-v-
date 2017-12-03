package haroittelutehtava;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnnosRaakaAineDao implements Dao<AnnosRaakaAine, Integer> {

    private Database db;

    public AnnosRaakaAineDao(Database db) {
        this.db = db;
    }

    public List<String> findRaakaAineNames(Integer key) throws SQLException {
        Connection conn = db.getConnection();

        PreparedStatement stmt = conn.prepareStatement("SELECT AnnosRaakaAine.jarjestys AS jarjestys, "
                + "RaakaAine.nimi AS nimi, "
                + "AnnosRaakaAine.maara AS maara, "
                + "AnnosRaakaAine.ohje AS ohje "
                + "FROM AnnosRaakaAine "
                + "LEFT JOIN RaakaAine "
                + "ON AnnosRaakaAine.raakaAine_id = RaakaAine.id "
                + "WHERE AnnosRaakaAine.annos_id = ? "
                + "ORDER BY jarjestys ASC");

        stmt.setInt(1, key);

        ResultSet result = stmt.executeQuery();
        boolean hasOne = result.next();
        if (!hasOne) {
            return null;
        }
        String tmp = "";
        List<String> lista = new ArrayList<>();
        do{
            tmp = result.getInt("jarjestys") + ".  " + result.getString("nimi")
                    + ",  " + result.getInt("maara") + "kpl,  " + result.getString("ohje");
            lista.add(tmp);
        } while ((result.next()));

        stmt.close();
        result.close();
        conn.close();

        return lista;

    }

    public List findAnnosAineet(Integer annos_id) throws SQLException {
        List<AnnosRaakaAine> lista = new ArrayList<>();

        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM AnnosRaakaAine WHERE annos_id = ?");
        stmt.setInt(1, annos_id);
        ResultSet result = stmt.executeQuery();
        boolean hasOne = result.next();
        if (!hasOne) {
            return null;
        }

        while (result.next()) {
            lista.add(new AnnosRaakaAine(result.getInt("annos_id"),
                    result.getInt("raakaAine_id"), result.getInt("jarjestys"),
                    result.getInt("maara"), result.getString("ohje")));
        }

        return lista;
    }

    @Override
    public AnnosRaakaAine findOne(Integer key) throws SQLException {
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM AnnosRaakaAine WHERE annos_id = ?");
        stmt.setInt(1, key);

        ResultSet result = stmt.executeQuery();
        boolean hasOne = result.next();
        if (!hasOne) {
            return null;
        }

        AnnosRaakaAine ara = new AnnosRaakaAine(result.getInt("annos_id"),
                result.getInt("raakaAine_id"), result.getInt("jarjestys"),
                result.getInt("maara"), result.getString("ohje"));

        stmt.close();
        result.close();
        conn.close();

        return ara;
    }

    @Override
    public List findAll() throws SQLException {
        List<AnnosRaakaAine> lista = new ArrayList<>();

        try (Connection conn = db.getConnection();
                ResultSet result = conn.prepareStatement("SELECT * FROM AnnosRaakaAine").executeQuery()) {

            while (result.next()) {
                lista.add(new AnnosRaakaAine(result.getInt("annos_id"),
                        result.getInt("raakaAine_id"), result.getInt("jarjestys"),
                        result.getInt("maara"), result.getString("ohje")));
            }
        }

        return lista;
    }

    public AnnosRaakaAine saveOrUpdate(AnnosRaakaAine ara) throws SQLException {
        return save(ara);
    }

    public void delete(Integer key) throws SQLException {
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM AnnosRaakaAine WHERE raakaaine_id = ?");

        stmt.setInt(1, key);
        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }

    private AnnosRaakaAine save(AnnosRaakaAine ara) throws SQLException {
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO AnnosRaakaAine"
                + "(annos_id, raakaAine_id, jarjestys, maara, ohje)"
                + " VALUES (?, ?, ?, ?, ?)");
        stmt.setInt(1, ara.getAnnosId());
        stmt.setInt(2, ara.getRaakaAineId());
        stmt.setInt(3, ara.getJarjestys());
        stmt.setInt(4, ara.getMaara());
        stmt.setString(5, ara.getOhje());

        stmt.executeUpdate();
        stmt.close();

        stmt = conn.prepareStatement("SELECT * FROM AnnosRaakaAine"
                + " WHERE annos_id = ?");
        stmt.setInt(1, ara.getAnnosId());

        ResultSet result = stmt.executeQuery();
        result.next();

        AnnosRaakaAine a = new AnnosRaakaAine(result.getInt("annos_id"),
                result.getInt("raakaAine_id"), result.getInt("jarjestys"),
                result.getInt("maara"), result.getString("ohje"));

        stmt.close();
        result.close();
        conn.close();

        return a;
    }

    public AnnosRaakaAine update(AnnosRaakaAine ara) throws SQLException {
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("UPDATE AnnosRaakaAine SET "
                + "(annos_id, raakaAine_id, jarjestys, maara, ohje) "
                + " VALUES (?, ?, ?, ?, ?) ");
        stmt.setInt(1, ara.getAnnosId());
        stmt.setInt(2, ara.getRaakaAineId());
        stmt.setInt(3, ara.getJarjestys());
        stmt.setInt(4, ara.getMaara());
        stmt.setString(5, ara.getOhje());

        stmt.executeUpdate();

        stmt.close();
        conn.close();

        return ara;
    }

}
