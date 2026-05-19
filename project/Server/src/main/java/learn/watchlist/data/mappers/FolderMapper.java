package learn.watchlist.data.mappers;

import learn.watchlist.models.Folder;
import learn.watchlist.models.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FolderMapper implements RowMapper<Folder> {

    @Override
    public Folder mapRow(ResultSet rs, int i) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("u.id"));
        user.setUsername(rs.getString("u.username"));

        Folder folder = new Folder();
        folder.setId(rs.getInt("f.id"));
        folder.setName(rs.getString("f.name"));
        folder.setPublic(rs.getBoolean("f.is_public"));
        folder.setParentId(rs.getInt("f.parent_id"));
        folder.setUser(user);

        return folder;
    }

}
