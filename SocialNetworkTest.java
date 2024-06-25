package facebook;
// Testler: JUnit Kullanılarak SocialNetworkTest
import org.junit.*;
import static org.junit.Assert.*;

public class SocialNetworkTest {
    private SocialNetwork socialNetwork;

    @Before
    public void setUp() {
        socialNetwork = new SocialNetwork();
        User alice = UserFactory.createUser("Alice", "alice@example.com");
        User bob = UserFactory.createUser("Bob", "bob@example.com");
        socialNetwork.addUser(alice);
        socialNetwork.addUser(bob);
    }

    @Test
    public void testAddFriendship() {
        User alice = socialNetwork.getUsers().get(0);
        User bob = socialNetwork.getUsers().get(1);
        socialNetwork.addFriendship(alice, bob);
        assertTrue(alice.getFriends().contains(bob));
        assertTrue(bob.getFriends().contains(alice));
    }

    @Test
    public void testAddPostToWall() {
        User alice = socialNetwork.getUsers().get(0);
        alice.getWall().addPost("Hello, World!");
        assertEquals("Hello, World!", alice.getWall().getPosts().get(0));
    }

    @Test
    public void testSearchByName() {
        SearchStrategy searchStrategy = new SearchByName();
        User result = searchStrategy.search(socialNetwork.getUsers(), "Alice");
        assertNotNull(result);
        assertEquals("alice@example.com", result.getEmail());
    }

    @Test
    public void testSearchByEmail() {
        SearchStrategy searchStrategy = new SearchByEmail();
        User result = searchStrategy.search(socialNetwork.getUsers(), "bob@example.com");
        assertNotNull(result);
        assertEquals("Bob", result.getName());
    }
}
