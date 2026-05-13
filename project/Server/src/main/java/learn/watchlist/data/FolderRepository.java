package learn.watchlist.data;

import learn.watchlist.models.Folder;

import java.util.List;

public interface FolderRepository {
    Folder findById(int id);

    List<Folder> findRoot(String username);

    Folder add(Folder folder);
}
