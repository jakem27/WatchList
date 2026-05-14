package learn.watchlist.models;

import java.util.List;

public class Folder {
    private int id;
    private String name;
    private boolean isPublic;
    private User user;
    private int parentId;

    public Folder() {}

    public Folder(String name, boolean isPublic, User user, int parentId) {
        this.name = name;
        this.isPublic = isPublic;
        this.user = user;
        this.parentId = parentId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }
}
