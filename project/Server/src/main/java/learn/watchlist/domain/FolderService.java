package learn.watchlist.domain;

import learn.watchlist.data.FolderRepository;
import learn.watchlist.data.UserRepository;
import learn.watchlist.models.Folder;
import learn.watchlist.models.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FolderService {
    private final FolderRepository folderRepository;
    private final UserRepository userRepository;

    public FolderService(FolderRepository folderRepository, UserRepository userRepository) {
        this.folderRepository = folderRepository;
        this.userRepository = userRepository;
    }

    public Result<Folder> findRoot(String username) {
        Result<Folder> result = new Result<>();

        User user = authenticate(result, username);
        if(!result.isSuccess()) {
            return result;
        }

        Folder root = folderRepository.findRoot(user.getId());

        result.setPayload(root);
        return result;
    }

    public Result<List<Folder>> findAllByUser(String username) {
        Result<List<Folder>> result = new Result<>();

        User user = authenticate(result, username);
        if(!result.isSuccess()) {
            return result;
        }

        List<Folder> folders = folderRepository.findAllByUser(user.getId());

        result.setPayload(folders);
        return result;
    }

    public Result<List<Folder>> findChildren(int folderId, String username) {
        Result<List<Folder>> result = new Result<>();

        authenticate(result, username);
        if(!result.isSuccess()) {
            return result;
        }

        Folder folder = folderRepository.findById(folderId);
        if(folder.getUser() == null || !username.equals(folder.getUser().getUsername())) {
            result.addMessage("Cannot access someone else's folder", ResultType.INVALID);
            return result;
        }

        List<Folder> childFolders = folderRepository.findChildren(folderId);

        result.setPayload(childFolders);
        return result;
    }

    public Result<List<Folder>> findFriendsFolders(String username) {
        Result<List<Folder>> result = new Result<>();

        User user = authenticate(result, username);
        if(!result.isSuccess()) {
            return result;
        }

        List<Folder> folders = folderRepository.findFriendsFolders(user.getId());
        result.setPayload(folders);
        return result;
    }

    public Result<Folder> add(Folder folder, String username) {
        Result<Folder> result = new Result<>();

        if(folder.getId() != 0) {
            result.addMessage("Id should not be set for `add` method", ResultType.INVALID);
            return result;
        }

        if(folder.getName().isBlank()) {
            result.addMessage("Name is required", ResultType.INVALID);
            return result;
        }

        if(folder.getName().equals("root")) {
            result.addMessage("Folder name `root` is unavailable", ResultType.INVALID);
            return result;
        }

        User user = authenticate(result, username);
        if(!result.isSuccess()) {
            return result;
        }

        folder.setUser(user);

        if(folder.getParentId() != 0) {
            Folder parent = folderRepository.findById(folder.getParentId());
            if(parent == null) {
                result.addMessage("Parent not found", ResultType.INVALID);
                return result;
            }

            if(parent.getUser().getId() != user.getId()) {
                result.addMessage("Parent and child folders must belong to same user", ResultType.INVALID);
                return result;
            }
        }

        Folder created = folderRepository.add(folder);
        result.setPayload(created);

        return result;
    }

    public Result<Void> update(Folder folder, String username) {
        Result<Void> result = new Result<>();
        User user = authenticate(result, username);

        Folder existing = folderRepository.findById(folder.getId());
        if(existing == null) {
            result.addMessage("Folder does not exist", ResultType.NOT_FOUND);
            return result;
        }

        if(existing.getUser().getId() != user.getId()) {
            result.addMessage("Cannot edit someone else's folder", ResultType.INVALID);
            return result;
        }

        boolean success = folderRepository.update(folder);
        if(!success) {
            result.addMessage("Failed to make folder public", ResultType.INVALID);
        }

        return result;
    }

    private User authenticate(Result<?> result, String username) {
        User user = userRepository.findByUsername(username);
        if(user == null) {
            result.addMessage("Invalid user", ResultType.INVALID);
        }
        return user;
    }
}
