package learn.watchlist.data;

import learn.watchlist.models.Folder;

import java.util.List;

public interface FolderRepository {
    Folder findById(int id);

    Folder findRoot(int userId);

    List<Folder> findChildren(int folderId);

    List<Folder> findAllByUser(int userId);

    List<Folder> findFriendsFolders(int userId);

    Folder add(Folder folder);

    boolean update(Folder folder);
}
