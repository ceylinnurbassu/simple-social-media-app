package facebook;

import java.sql.SQLOutput;
import java.util.Scanner;

import static org.junit.Assert.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SocialNetwork socialNetwork = new SocialNetwork();
        String continueAddingUser;
        do {
            // Kullanıcı bilgilerini al
            System.out.print("Kullanıcı Adı: ");
            String name = scanner.nextLine();
            System.out.print("Email: ");
            String email = scanner.nextLine();

            User user = UserFactory.createUser(name, email);
            SearchStrategy searchStrategyName = new SearchByName();
            SearchStrategy searchStrategyEmail = new SearchByEmail();
            boolean emailExists = false;
            boolean nameExists = false;

// Search by email
            User userByEmail = searchStrategyEmail.search(socialNetwork.getUsers(), email);
            if (userByEmail != null) {
                emailExists = email.equalsIgnoreCase(userByEmail.getEmail());
            }

// Search by name
            User userByName = searchStrategyName.search(socialNetwork.getUsers(), name);
            if (userByName != null) {
                nameExists = name.equalsIgnoreCase(userByName.getName());
            }

            if (emailExists || nameExists) {
                System.out.println("bu kullanıcı adı veya emailini kullanan bir hesap zaten var");
            }

            else {

                // Kullanıcının aramalarda görünürlüğünü belirle
                System.out.print("Aramalarda Görünürlük (E/H): ");
                boolean visibleInSearch = scanner.nextLine().equalsIgnoreCase("E");
                user.setVisibilityInSearch(visibleInSearch);

                socialNetwork.addUser(user);

                System.out.println("Kullanıcı oluşturuldu: " + user.getName());

                // Arkadaş ekleme işlemi
                while (true) {
                    System.out.print("Arkadaş eklemek istiyor musunuz? (E/H): ");
                    String choice = scanner.nextLine();

                    if (choice.equalsIgnoreCase("E")) {
                        System.out.print("Arkadaşın Kullanıcı Adı: ");
                        String friendName = scanner.nextLine();

                        // Arkadaşı bul
                        // SearchStrategy searchStrategyName = new SearchByName();
                        User friend = searchStrategyName.search(socialNetwork.getUsers(), friendName);

                        if (friend != null) {
                            // Arkadaş ekle
                            socialNetwork.addFriendship(user, friend);
                            System.out.println("Arkadaş eklendi: " + friendName);
                        } else {
                            System.out.println("Bu kullanıcı bulunamadı.");
                        }
                    } else {
                        break;
                    }
                }

                System.out.print("Duvara Post Ekle (E/H): ");
                String postChoice = scanner.nextLine();

                if (postChoice.equalsIgnoreCase("E")) {
                    System.out.print("Post İçeriği: ");
                    String postContent = scanner.nextLine();
                    user.getWall().addPost(postContent);
                    System.out.println("Post başarıyla eklendi.");
                }

                // Duvarı ekrana yazdır
                System.out.println("\n" + user.getName() + "'nin Duvarı:");
                for (String post : user.getWall().getPosts()) {
                    System.out.println(post);
                }

            }

            System.out.println("Do you want to add another user (E/H): ");
            continueAddingUser = scanner.nextLine();
        } while (continueAddingUser.equalsIgnoreCase("E"));


    }
}