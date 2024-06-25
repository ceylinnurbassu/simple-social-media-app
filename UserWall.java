package facebook;

import java.util.ArrayList;
import java.util.List;

public class UserWall {

    private List<String> posts;

    public UserWall() {
        this.posts = new ArrayList<>();
    }

    public void addPost(String message) {
        posts.add(message);
    }
    public void removePost(String content) {
        if (posts.contains(content)) {
            posts.remove(content);
        } else {
            System.out.println("post not found");
        }
    }
    public List<String> getPosts() {
        return posts;
    }
}
