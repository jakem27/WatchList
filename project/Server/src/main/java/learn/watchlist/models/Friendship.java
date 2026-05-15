package learn.watchlist.models;

import java.util.Objects;

public class Friendship {
    private int user1Id;
    private int user2Id;
    private boolean pending;

    public Friendship() {}

    public Friendship(int user1Id, int user2Id) {
        this.user1Id = user1Id;
        this.user2Id = user2Id;
        this.pending = true;
    }

    public int getUser1Id() {
        return user1Id;
    }

    public void setUser1Id(int user1Id) {
        this.user1Id = user1Id;
    }

    public int getUser2Id() {
        return user2Id;
    }

    public void setUser2Id(int user2Id) {
        this.user2Id = user2Id;
    }

    public boolean isPending() {
        return pending;
    }

    public void setPending(boolean pending) {
        this.pending = pending;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Friendship that = (Friendship) o;
        return user1Id == that.user1Id && user2Id == that.user2Id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(user1Id, user2Id);
    }
}
