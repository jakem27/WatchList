package learn.watchlist.domain;

import learn.watchlist.data.FolderRepository;
import learn.watchlist.data.UserRepository;
import learn.watchlist.models.Folder;
import learn.watchlist.models.User;
import org.springframework.stereotype.Service;

@Service
public class FolderService {
    private final FolderRepository folderRepository;
    private final UserRepository userRepository;

    public FolderService(FolderRepository folderRepository, UserRepository userRepository) {
        this.folderRepository = folderRepository;
        this.userRepository = userRepository;
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

        User user = userRepository.findByUsername(username);
        if(user == null) {
            result.addMessage("Invalid user", ResultType.INVALID);
            return result;
        } else {
            folder.setUser(user);
        }

        if(folder.getParent_id() != 0) {
            Folder parent = folderRepository.findById(folder.getParent_id());
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
}
