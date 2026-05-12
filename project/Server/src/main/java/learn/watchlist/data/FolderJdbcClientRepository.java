package learn.watchlist.data;

import learn.watchlist.models.Folder;
import org.springframework.jdbc.core.simple.JdbcClient;
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
        return null;
    }
}
