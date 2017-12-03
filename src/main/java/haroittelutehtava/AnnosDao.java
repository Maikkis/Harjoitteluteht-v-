package haroittelutehtava;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnnosDao implements Dao <Annos, Integer> {

    private Database db;

    public AnnosDao(Database db) {
        this.db = db;
    }

    public Annos findOne(Integer key) throws SQLException {
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Annos WHERE id = ?");
        stmt.setInt(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Annos a = new Annos(rs.getInt("id"), rs.getString("nimi"));

        stmt.close();
        rs.close();
        conn.close();

        return a;
    }

    @Override
    public List findAll() throws SQLException {
        List<Annos> annokset = new ArrayList<>();
        try (Connection conn = db.getConnection();
                ResultSet result = conn.prepareStatement("SELECT id, nimi FROM Annos").executeQuery()) {

            while (result.next()) {
                annokset.add(new Annos(result.getInt("id"), result.getString("nimi")));
            }
        }

        return annokset;
    }

    public void delete(Integer key) throws SQLException {
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Annos WHERE id = ?");

        stmt.setInt(1, key);
        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }

    public Annos saveOrUpdate(Annos a) throws SQLException {
        if (a.id == -1) {
            return save(a);
        } else {
            return update(a);
        }
    }

    private Annos save(Annos annos) throws SQLException {
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Annos"
                + " (nimi)"
                + " VALUES (?)");
        stmt.setString(1, annos.getNimi());

        stmt.executeUpdate();
        stmt.close();

        stmt = conn.prepareStatement("SELECT * FROM Annos"
                + " WHERE nimi = ?");
        stmt.setString(1, annos.getNimi());

        ResultSet rs = stmt.executeQuery();
        rs.next();

        Annos a = new Annos(rs.getInt("id"), rs.getString("nimi"));

        stmt.close();
        rs.close();
        conn.close();

        return a;
    }

    public Annos update(Annos annos) throws SQLException {
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("UPDATE Annos SET"
                + " id = ?, nimi = ?");
        stmt.setInt(1, annos.getId());
        stmt.setString(2, annos.getNimi());

        stmt.executeUpdate();

        stmt.close();
        conn.close();

        return annos;
    }

}
