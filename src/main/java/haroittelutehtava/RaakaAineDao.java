package haroittelutehtava;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RaakaAineDao implements Dao<RaakaAine, Integer> {

    private Database db;

    public RaakaAineDao(Database db) {
        this.db = db;
    }

    public RaakaAine findOne(Integer key) throws SQLException {
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM RaakaAine WHERE id = ?");
        stmt.setInt(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        RaakaAine ra = new RaakaAine(rs.getInt("id"), rs.getString("nimi"));

        stmt.close();
        rs.close();
        conn.close();

        return ra;
    }

    @Override
    public List findAll() throws SQLException {
        List<RaakaAine> raakaAineet = new ArrayList<>();

        try (Connection conn = db.getConnection();
                ResultSet result = conn.prepareStatement("SELECT id, nimi FROM RaakaAine").executeQuery()) {
            while (result.next()) {
                raakaAineet.add(new RaakaAine(result.getInt("id"), result.getString("nimi")));
            }
        }
        return raakaAineet;
    }

    public RaakaAine saveOrUpdate(RaakaAine ra) throws SQLException {
        System.out.println(ra.id);
        if (ra.id == -1) {
            return save(ra);
        } else {
            return update(ra);
        }
    }

    public void delete(Integer key) throws SQLException {
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM RaakaAine WHERE id = ?");

        stmt.setInt(1, key);
        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }

    private RaakaAine save(RaakaAine ra) throws SQLException {
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO RaakaAine"
                + " (nimi)"
                + " VALUES (?)");
        stmt.setString(1, ra.getNimi());

        stmt.executeUpdate();
        stmt.close();

        stmt = conn.prepareStatement("SELECT * FROM RaakaAine"
                + " WHERE nimi = ?");
        stmt.setString(1, ra.getNimi());

        ResultSet rs = stmt.executeQuery();
        rs.next();

        RaakaAine raakaAine = new RaakaAine(rs.getInt("id"), rs.getString("nimi"));

        stmt.close();
        rs.close();
        conn.close();

        return raakaAine;
    }

    public RaakaAine update(RaakaAine ra) throws SQLException {
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("UPDATE RaakaAine SET"
                + " id = ?, nimi = ?");
        stmt.setInt(1, ra.getId());
        stmt.setString(2, ra.getNimi());

        stmt.executeUpdate();

        stmt.close();
        conn.close();
        return ra;
    }

}
