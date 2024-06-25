package facebook;

import java.util.List;

public class SearchByEmail implements SearchStrategy {
    @Override
    public User search(List<User> users, String query) {
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(query)) {
                return user;
            }
        }
        return null;
    }
}
