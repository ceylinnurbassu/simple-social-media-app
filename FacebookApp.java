package facebook;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class FacebookApp {
    private SocialNetwork socialNetwork = new SocialNetwork();
    private JFrame frame;
    private JTextField nameField;
    private JTextField emailField;
    private JTextField visibilityField;
    private JTextArea outputArea;


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new FacebookApp().createVeryFirstPage();
        });
    }

    private void showMainAppGUICreate() {
        frame.dispose(); // Giriş ekranını kapat
        createLoginGUI();
    }

    private void showMainAppGUI() {
        frame.dispose(); // Giriş ekranını kapat
        createUserGUI();
    }

    private void showAppAfterLogin(User user) {
        frame.dispose();
        createHomePage(user);
    }

    private void backToFirstPage() {
        frame.dispose();
        createVeryFirstPage();
    }

    public void createVeryFirstPage() {
        frame = new JFrame("Facebook Uygulaması - Giriş");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setLayout(new GridLayout(3, 1));
        frame.getContentPane().setBackground(Color.PINK);

        JButton loginButton = new JButton("Giriş Yap");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMainAppGUICreate();
            }
        });
        frame.add(loginButton);

        JButton createUserButton = new JButton("Kullanıcı Oluştur");
        createUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMainAppGUI();
            }
        });
        frame.add(createUserButton);

        frame.setVisible(true);
    }

    private void createLoginGUI() {
        SearchStrategy searchStrategyName = new SearchByName();

        frame = new JFrame("Giriş Yap");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLayout(new GridLayout(6, 1));
        frame.getContentPane().setBackground(Color.PINK);

        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
        JLabel nameLabel = new JLabel("Kullanıcı Adı:");
        JTextField usernameField = new JTextField(20);
        JLabel emailLabel = new JLabel("Email:");
        JTextField userEmailField = new JTextField(20);

        inputPanel.add(nameLabel);
        inputPanel.add(usernameField);
        inputPanel.add(emailLabel);
        inputPanel.add(userEmailField);

        inputPanel.setBackground(Color.PINK); // Panelin arka plan rengini mavi olarak ayarla

        nameLabel.setBackground(Color.LIGHT_GRAY);
        emailLabel.setBackground(Color.LIGHT_GRAY);


        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        JButton createUserButton = new JButton("Giriş Yap");
        createUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String email = userEmailField.getText();

                User userByName = searchStrategyName.search(socialNetwork.getUsers(), username);

                if (userByName != null && username.equals(userByName.getName()) && email.equals(userByName.getEmail())) {
                    JOptionPane.showMessageDialog(outputArea, "Giriş Yapıldı.", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
                    frame.dispose();
                    showAppAfterLogin(userByName);
                } else {
                    JOptionPane.showMessageDialog(outputArea, "Giriş Yapılamadı. Lütfen giriş bilgilerinizi kontrol edin.", "Başarısız", JOptionPane.INFORMATION_MESSAGE);
                    usernameField.setText("");
                    userEmailField.setText("");
                }

            }
        });

        JButton backButton = createBackButton();

        buttonPanel.add(createUserButton);

        frame.add(inputPanel);
        frame.add(buttonPanel);
        frame.add(backButton, inputPanel);

        frame.setVisible(true);
    }

    private void createUserGUI() {
        frame = new JFrame("Kullanıcı Oluştur");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(4, 4));
        JLabel nameLabel = new JLabel("Kullanıcı Adı:");
        nameField = new JTextField(20);
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField(20);

        frame.getContentPane().setBackground(Color.PINK);
        nameLabel.setBackground(Color.PINK);
        emailLabel.setBackground(Color.PINK);
        inputPanel.setBackground(Color.PINK);


        JCheckBox visibilityCheckBox = new JCheckBox("Aramalarda Görünürlük");

        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(emailLabel);
        inputPanel.add(emailField);
        inputPanel.add(visibilityCheckBox);

        JButton addUserButton = new JButton("Kullanıcı Ekle");
        addUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = nameField.getText();
                String email = emailField.getText();
                boolean visibleInSearch = visibilityCheckBox.isSelected(); // CheckBox'ın durumunu kontrol edin

                if (!username.isEmpty() && !email.isEmpty()) {
                    if (isUserAlreadyExists(username, email)) {
                        JOptionPane.showMessageDialog(outputArea, "Bu kullanıcı adı veya email zaten kullanılıyor.", "Hata", JOptionPane.ERROR_MESSAGE);
                        nameField.setText("");
                        emailField.setText("");
                        visibilityCheckBox.setSelected(false);
                    } else {
                        User newUser = new User(username, email);
                        newUser.setVisibilityInSearch(visibleInSearch);
                        socialNetwork.addUser(newUser);
                        JOptionPane.showMessageDialog(outputArea, "Kullanıcı oluşturuldu.", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
                        frame.dispose();
                        backToFirstPage();
                    }

                } else {
                    JOptionPane.showMessageDialog(outputArea, "Kullanıcı adı ve email alanlarını doldurun.", "Hata", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        inputPanel.add(addUserButton);

        JButton backButton = createBackButton();

        // Butonu panelin alt kısmına ekle
        frame.add(backButton, BorderLayout.SOUTH);

        frame.add(inputPanel, BorderLayout.NORTH);

        frame.setVisible(true);
    }

    private void createHomePage(User user) {
        frame = new JFrame("Anasayfa");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLayout(new GridLayout(5, 1));
        frame.getContentPane().setBackground(Color.PINK);

        JButton viewWallButton = new JButton("Duvarı Görüntüle");
        viewWallButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                viewWall(user);
            }
        });

        JButton addFriendButton = new JButton("Arkadaş Ekle");
        addFriendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addFriends(user);
            }
        });

        JButton viewFriendsButton = new JButton("Arkadaşları Görüntüle");
        viewFriendsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewFriends(user);
            }
        });

        JButton createGroupButton = new JButton("Grup Oluştur");
        createGroupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createGroup(user);
            }
        });

        JButton logoutButton = new JButton("Çıkış Yap");
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                logout();
            }
        });

        frame.add(viewWallButton);
        frame.add(viewWallButton);
        frame.add(addFriendButton);
        frame.add(viewFriendsButton);
        frame.add(createGroupButton);
        frame.add(logoutButton);

        frame.setVisible(true);
    }

    //////
    private void viewWall(User user) {
        frame = new JFrame("Duvarlar");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 600);
        frame.getContentPane().setBackground(Color.PINK);


        JTextArea wallTextArea = new JTextArea();
        wallTextArea.setEditable(false); // Metin değiştirilemez

        JScrollPane scrollPane = new JScrollPane(wallTextArea);
        frame.add(scrollPane);

        StringBuilder wallContent = new StringBuilder();

        for (User friend : socialNetwork.getUsers()) {
            if (friend.getFriends().contains(user) || friend.equals(user)) {
                if (!friend.getWall().getPosts().isEmpty()) {
                    for (String post : friend.getWall().getPosts()) {
                        wallContent.append(post).append("\n");
                    }
                    wallContent.append("\n");
                }
            }
        }
        wallTextArea.setText(wallContent.toString());

        JButton backButton = new JButton("Geri");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Frame'i kapat
                createHomePage(user);
            }
        });
        frame.add(backButton, BorderLayout.SOUTH);

        // Post ekleme butonu ekle
        JButton addPostButton = new JButton("Post Ekle");
        addPostButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPostToUserWall(user, wallContent);

                // Duvar içeriğini yeniden oluştur
                wallContent.setLength(0); // Clear the current content
                for (User friend : socialNetwork.getUsers()) {
                    if (friend.getFriends().contains(user) || friend.equals(user)) {
                        if (!friend.getWall().getPosts().isEmpty()) {
                            for (String post : friend.getWall().getPosts()) {
                                wallContent.append(post).append("\n");
                            }
                            wallContent.append("\n");
                        }
                    }
                }
                wallTextArea.setText(wallContent.toString()); // JTextArea'ya duvar içeriğini ekle
            }
        });

        frame.add(addPostButton, BorderLayout.NORTH);

        wallTextArea.setVisible(true);
        frame.setVisible(true);
    }

    private void addPostToUserWall(User user, StringBuilder wallContent) {
        String morePost = "yes";
        while (morePost.equalsIgnoreCase("yes")) {
            int postResponse = JOptionPane.showConfirmDialog(frame, "Duvara post eklemek ister misiniz?", "Post Ekle", JOptionPane.YES_NO_OPTION);
            if (postResponse == JOptionPane.YES_OPTION) {
                String postContent = JOptionPane.showInputDialog(frame, "Post İçeriği:");
                if (postContent != null && !postContent.isEmpty()) {
                    String post = "\nKULLANICI ADI: " + user.getName() + "\n" +
                            "POST:\n" + postContent + "\n";
                    wallContent.append(post);
                    user.getWall().addPost(post); // Add the post to the user's wall
                }
            }

            int morePostR = JOptionPane.showConfirmDialog(frame, "Başka post eklemek ister misiniz?", "Post Ekle", JOptionPane.YES_NO_OPTION);
            if (morePostR == JOptionPane.YES_OPTION) {
                morePost = "yes";
            } else {
                morePost = "no";
            }
        }
    }

    //////
    private void addFriends(User user) {
        String moreFriends = "yes";
        SearchStrategy searchStrategyName = new SearchByName();
        while (moreFriends.equalsIgnoreCase("yes")) {
            int friendResponse = JOptionPane.showConfirmDialog(frame, "Arkadaş eklemek ister misiniz?", "Arkadaş Ekle", JOptionPane.YES_NO_OPTION);
            if (friendResponse == JOptionPane.YES_OPTION) {
                String friendName = JOptionPane.showInputDialog(frame, "Arkadaşın Kullanıcı Adı:");
                if (friendName != null && !friendName.isEmpty() && !friendName.equals(user.getName())) {
                    User friend = searchStrategyName.search(socialNetwork.getUsers(), friendName);
                    if (friend != null) {
                        if (friend.isVisibleInSearch()) { // Kullanıcının görünürlüğünü kontrol et
                            socialNetwork.addFriendship(user, friend);
                            JOptionPane.showMessageDialog(frame, "Arkadaş eklendi.", "Arkadaş Ekleme: Başarılı", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(frame, "Kullanıcı bulunamadı.", "Arkadaş Ekleme: Başarısız", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "Kullanıcı bulunamadı.", "Arkadaş Ekleme: Başarısız", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Hatalı Giriş.", "Arkadaş Ekleme: Başarısız", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                break;
            }
        }
    }

    private void viewFriends(User user) {
        frame = new JFrame("Arkadaşları Görüntüle");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(Color.PINK);

        JTextArea friendsTextArea = new JTextArea();
        friendsTextArea.setEditable(false);

        // Kullanıcının ekli arkadaşlarını al
        List<User> friendsOfUser= user.getFriends();

        // Arkadaş listesini görüntüle
        StringBuilder friendList = new StringBuilder();
        friendList.append("Arkadaşlarınız:\n");
        for (User friend : friendsOfUser) {
            friendList.append("- ").append(friend.getName()).append("\n");
        }
        friendsTextArea.setText(friendList.toString());

        JScrollPane scrollPane = new JScrollPane(friendsTextArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        JButton backButton = new JButton("Geri");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                createHomePage(user);
            }
        });
        frame.add(backButton, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
    //////

    private void createGroup(User user) {
        frame = new JFrame("Grup");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 600);
        frame.getContentPane().setBackground(Color.PINK);

        List<User> groupMembers = new ArrayList<>();
        SearchStrategy searchStrategyName = new SearchByName();
        String moreGroups = "yes";

        while (moreGroups.equalsIgnoreCase("yes")) {
            int response = JOptionPane.showConfirmDialog(frame, "Grup oluşturmak ister misiniz?", "Grup Oluştur", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                String memberName = JOptionPane.showInputDialog(frame, "Gruba eklemek istediğiniz kullanıcı adını girin:");
                if (memberName != null && !memberName.isEmpty()) {
                    User member = searchStrategyName.search(socialNetwork.getUsers(), memberName);
                    if (member != null) {
                        // Kullanıcının arkadaş listesine bakıyoruz
                        if (user.getFriends().contains(member)) {
                            groupMembers.add(member);
                            JOptionPane.showMessageDialog(frame, "Kullanıcı grup üyelerine eklendi.", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(frame, "Bu kullanıcı arkadaş listenizde değil.", "Başarısız", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "Kullanıcı bulunamadı.", "Başarısız", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            } else {
                break;
            }

            moreGroups = JOptionPane.showInputDialog(frame, "Başka bir kullanıcı eklemek istiyor musunuz? (yes/no)");
        }

        if (!groupMembers.isEmpty()) {
            StringBuilder memberList = new StringBuilder("Grup Üyeleri:\n");
            for (User member : groupMembers) {
                memberList.append(member.getName()).append("\n");
            }

            JOptionPane.showMessageDialog(frame, memberList.toString(), "Grup Üyeleri", JOptionPane.INFORMATION_MESSAGE);

            createGroupChatFrame(user, groupMembers);

        } else {
            JOptionPane.showMessageDialog(frame, "Gruba hiç üye eklenmedi.", "Grup Üyeleri", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void createGroupChatFrame(User user, List<User> groupMembers) {
        JFrame chatFrame = new JFrame("Grup Sohbeti");
        chatFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        chatFrame.setSize(600, 600);
        chatFrame.getContentPane().setBackground(Color.PINK);

        JTextArea chatTextArea = new JTextArea();
        chatTextArea.setEditable(false);
        JScrollPane chatScrollPane = new JScrollPane(chatTextArea);
        chatFrame.add(chatScrollPane, BorderLayout.CENTER);

        JTextField chatInputField = new JTextField();
        chatFrame.add(chatInputField, BorderLayout.SOUTH);

        // Group chat history
        StringBuilder chatHistory = new StringBuilder();

        // Display existing chat history (if any)
        chatTextArea.setText(chatHistory.toString());

        chatInputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = chatInputField.getText();
                if (!message.isEmpty()) {
                    String formattedMessage = user.getName() + ": " + message + "\n";
                    chatHistory.append(formattedMessage);

                    for (User member : groupMembers) {
                        chatTextArea.setText(chatHistory.toString());
                        chatInputField.setText("");
                    }


                }
            }
        });

        chatFrame.setVisible(true);
    }
    //////

    private void logout() {
        JOptionPane.showMessageDialog(frame, "Oturum Sonlandırıldı.", "Çıkış Yapıldı", JOptionPane.INFORMATION_MESSAGE);
        backToFirstPage();
    }

    private boolean isUserAlreadyExists(String name, String email) {
        boolean emailExists = socialNetwork.getUsers().stream()
                .anyMatch(user -> user.getEmail().equalsIgnoreCase(email));

        boolean nameExists = socialNetwork.getUsers().stream()
                .anyMatch(user -> user.getName().equalsIgnoreCase(name));

        return emailExists || nameExists;
    }

    private JButton createBackButton() {
        JButton backButton = new JButton("Geri");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backToPreviousPage();
            }
        });
        return backButton;
    }

    private void backToPreviousPage() {
        frame.dispose(); // Mevcut sayfayı kapat

        // Önceki sayfaya geri dön
        if (frame.getTitle().equals("Giriş Yap")) {
            createVeryFirstPage();
        } else if (frame.getTitle().equals("Kullanıcı Oluştur")) {
            createVeryFirstPage();
        } else if (frame.getTitle().equals("Anasayfa")) {
            createVeryFirstPage();
        }
    }

}