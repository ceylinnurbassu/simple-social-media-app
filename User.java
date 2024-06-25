package facebook;


import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;
    private String email;
    private UserWall wall;

    private List<User> friends;
    private boolean visibleInSearch;

    public User(String name, String email) {
        this.setName(name);
        this.setEmail(email);
        this.setWall(new UserWall());
        this.setFriends(new ArrayList<>());
        this.setVisibleInSearch(true); // Aramalarda görünürlük varsayılan olarak açık
    }

    public String getName() {
        return name;
    }

    public UserWall getWall() {
        return wall;
    }

    public void addFriend(User friend) {
        if (!getFriends().contains(friend)) {
            getFriends().add(friend);
            friend.addFriend(this); // Çift yönlü arkadaşlık
        }
    }

    public List<User> getFriends() {
        return friends;
    }

    public boolean isVisibleInSearch() {
        return visibleInSearch;
    }

    public void setVisibilityInSearch(boolean visibleInSearch) {
        this.setVisibleInSearch(visibleInSearch);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setWall(UserWall wall) {
        this.wall = wall;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    public void setVisibleInSearch(boolean visibleInSearch) {
        this.visibleInSearch = visibleInSearch;
    }
    private List<String> getFriendNames() {
        List<String> friendNames = new ArrayList<>();
        for (User friend : friends) {
            friendNames.add(friend.getName());
        }
        return friendNames;
    }

    @Override
    public String toString() {
        return "User{" +
                "name ='" + getName() + '\'' +
                ", email ='" + getEmail() + '\'' +
                ", wall =" + wall +
                ", friends =" + getFriendNames() +
                ", visibleInSearch =" + visibleInSearch +
                '}';
    }
}
