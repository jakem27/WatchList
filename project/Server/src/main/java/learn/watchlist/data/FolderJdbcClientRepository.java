package learn.watchlist.data;

import learn.watchlist.data.mappers.FolderMapper;
import learn.watchlist.models.Folder;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FolderJdbcClientRepository implements FolderRepository {
    private final JdbcClient jdbcClient;

    private final String BASE_SQL = """
                select f.id, f.name, f.is_public, f.parent_id,
                u.id, u.username
                from folder f
                join user u on u.id = f.user_id
                """;

    public FolderJdbcClientRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public Folder findById(int id) {
        final String sql = BASE_SQL + " where f.id = ?;";

        return jdbcClient.sql(sql)
                .param(id)
                .query(new FolderMapper())
                .optional().orElse(null);
    }

    @Override
    public Folder findRoot(int userId) {
        final String sql = BASE_SQL + " where f.name = 'My WatchList' and u.id = ?";

        return jdbcClient.sql(sql)
                .param(userId)
                .query(new FolderMapper())
                .optional().orElse(null);
    }

    @Override
    public List<Folder> findAllByUser(int userId) {
        final String sql = BASE_SQL + " where u.id = ?";

        return jdbcClient.sql(sql)
                .param(userId)
                .query(new FolderMapper())
                .list();
    }

    @Override
    public List<Folder> findChildren(int folderId) {
        final String sql = """
                select f.id, f.name, f.is_public, f.parent_id,
                u.id, u.username
                from folder f
                join user u on u.id = f.user_id
                join folder p on f.parent_id = p.id
                where p.id = ?
                order by f.name;
                """;

        return jdbcClient.sql(sql)
                .param(folderId)
                .query(new FolderMapper())
                .list();
    }

    @Override
    public List<Folder> findFriendsFolders(int userId) {
        final String sql = """
                select f.id, f.name, f.is_public, f.parent_id,
                u.id, u.username
                from folder f
                join user u on u.id = f.user_id
                join friendship fs
                on ((fs.user1_id = u.id and fs.user2_id = :id)
                or (fs.user1_id = :id and fs.user2_id = u.id))
                where fs.pending = 0
                and f.is_public = 1;
                """;

        return jdbcClient.sql(sql)
                .param("id", userId)
                .query(new FolderMapper())
                .list();
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
                .param("parent_id", folder.getParentId() == 0 ? null : folder.getParentId())
                .update(keyHolder, "id");

        if(rowsAffected == 0) {
            return null;
        }

        folder.setId(keyHolder.getKey().intValue());
        folder.getUser().setPassword("");
        return folder;
    }

    @Override
    public boolean update(Folder folder) {
        final String sql = """
                update folder
                set is_public = :is_public, name = :name
                where id = :id;
                """;

        return jdbcClient.sql(sql)
                .param("is_public", folder.isPublic())
                .param("name", folder.getName())
                .param("id", folder.getId())
                .update() > 0;
    }

    @Override
    public boolean delete(int folderId) {
        final String sql = """
                delete from folder
                where id = ?;
                """;
        return jdbcClient.sql(sql)
                .param(folderId)
                .update() > 0;
    }
}
