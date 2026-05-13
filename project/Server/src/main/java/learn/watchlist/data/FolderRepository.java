package learn.watchlist.data;

import learn.watchlist.models.Folder;

import java.util.List;

public interface FolderRepository {
    Folder findById(int id);

    Folder findRoot(String username);

    List<Folder> findChildren(int folderId);

    Folder add(Folder folder);
}
