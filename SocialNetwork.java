package facebook;

import java.util.ArrayList;
import java.util.List;
public class SocialNetwork {
    private static SocialNetwork instance;
    private List<User> users;
    private RelationshipObserver observer;

    SocialNetwork() {
        users = new ArrayList<>();
        observer = new RelationshipObserver();
    }

    public static synchronized SocialNetwork getInstance() {
        if (instance == null) {
            instance = new SocialNetwork();
        }
        return instance;
    }

    public void addUser(User user) {
        if (!users.contains(user)) {
            users.add(user);
            observer.observe(user);
        }
    }

    public void addFriendship(User user1, User user2) {
        user1.addFriend(user2);
        observer.notifyFriendship(user1, user2); // Observer bildirim yapar
    }

    public List<User> getUsers() {
        return users;
    }
}