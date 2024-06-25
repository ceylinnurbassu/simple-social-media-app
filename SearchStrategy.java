package facebook;

import java.util.List;

public interface SearchStrategy {
    User search(List<User> users, String query);// Strategy Deseni: SearchStrategy

}
