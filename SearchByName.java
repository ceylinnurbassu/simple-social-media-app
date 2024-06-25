package facebook;

import java.util.List;

public class SearchByName implements SearchStrategy {
    @Override
    public User search(List<User> users, String query) {
        for (User user : users) {
            if (user.getName().equalsIgnoreCase(query)) {
                return user;
            }
        }
        return null;
    }
}