package learn.watchlist.data;

import learn.watchlist.models.Folder;

public interface FolderRepository {
    Folder findById(int id);

    Folder add(Folder folder);
}
