package learn.watchlist.data.mappers;

import learn.watchlist.models.Folder;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FolderMapper implements RowMapper<Folder> {

    @Override
    public Folder mapRow(ResultSet rs, int i) throws SQLException {
        Folder folder = new Folder();
        folder.setId(rs.getInt("f.id"));
        folder.setName(rs.getString("f.name"));
        folder.setPublic(rs.getBoolean("f.is_public"));
        folder.setParentId(rs.getInt("f.parent_id"));

        UserMapper userMapper = new UserMapper();
        folder.setUser(userMapper.mapRow(rs, i));

        return folder;
    }

}
