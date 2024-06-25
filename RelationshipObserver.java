package facebook;

import java.util.ArrayList;
import java.util.List;

// Observer Deseni: RelationshipObserver
public class RelationshipObserver {
    private List<User> observedUsers;

    public RelationshipObserver() {
        observedUsers = new ArrayList<>();
    }

    public void observe(User user) {
        if (!observedUsers.contains(user)) {
            observedUsers.add(user);
        }
    }

    public void notifyFriendship(User user1, User user2) {
        System.out.println("Yeni arkadaşlık: " + user1.getName() + " ve " + user2.getName());
    }
}