package learn.watchlist.data;

import learn.watchlist.models.Folder;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class FolderJdbcClientRepository implements FolderRepository {
    private final JdbcClient jdbcClient;

    public FolderJdbcClientRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }


    @Override
    public Folder findById(int id) {
        return null;
    }

    @Override
    public Folder add(Folder folder) {
        final String sql = """
                insert into folder(name, is_public, user_id, parent_id)
                values(:name, :is_public, :user_id, :parent_id);
                """;

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        int rowsAffected = jdbcClient.sql(sql)
                .param("name", folder.getName())
                .param("is_public", folder.isPublic())
                .param("user_id", folder.getUser().getId())
                .param("parent_id", folder.getParent_id())
                .update(keyHolder, "id");

        if(rowsAffected == 0) {
            return null;
        }

        folder.setId(keyHolder.getKey().intValue());
        folder.getUser().setPassword("");
        return folder;
    }
}
