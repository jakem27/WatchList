package learn.watchlist.data;

import learn.watchlist.models.Folder;

import java.util.List;

public interface FolderRepository {
    Folder findById(int id);

    Folder findRoot(int userId);

    List<Folder> findChildren(int folderId);

    List<Folder> findFriendsFolders(int userId);

    Folder add(Folder folder);
}
