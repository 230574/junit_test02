package com.example;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class UserManagerTest {
    // This is a placeholder for user manager-related tests
    private static UserManager userManager = null;
    
    @BeforeAll
    static void テスト前処理(){
        userManager = UserManager.getInstance();
    }
    @Test
    public void 正常系＿UserManagerインスタンス同一(){
        UserManager manager1 = UserManager.getInstance();
        UserManager manager2 = UserManager.getInstance();
        assertThat(manager1).isSameAs(manager2);
    }
    @Test
    public void 正常系＿userList登録参照(){
        User user1 = new User("0001");
        User user2 = new User("0002");
        userManager.setUserToList(user1);
        userManager.setUserToList(user2);
        assertThat(userManager.getUserList().size()).isEqualTo(2);
    }
    @Test
    public void 正常系＿userMap登録参照(){
        User user1 = new User("0001");
        User user2 = new User("0002");
        userManager.setUserToMap(user1);
        userManager.setUserToMap(user2);
        assertThat(userManager.getUserMap().size()).isEqualTo(2);
    }
    @Test
    public void 正常系＿user全削除(){
        User user1 = new User("0001");
        User user2 = new User("0002");
        userManager.setUserToList(user1);
        userManager.setUserToList(user2);
        userManager.setUserToMap(user1);
        userManager.setUserToMap(user2);
        assertThat(userManager.getUserList().size()).isEqualTo(2);
        assertThat(userManager.getUserMap().size()).isEqualTo(2);
        
        userManager.deleteAllUser();

        assertThat(userManager.getUserList().size()).isEqualTo(0);
        assertThat(userManager.getUserMap().size()).isEqualTo(0);
    }
    @Test
    public void 正常系＿code指定user削除(){
        User user1 = new User("0001");
        User user2 = new User("0002");
        userManager.setUserToList(user1);
        userManager.setUserToList(user2);
        userManager.setUserToMap(user1);
        userManager.setUserToMap(user2);
        assertThat(userManager.getUserList().size()).isEqualTo(2);
        assertThat(userManager.getUserMap().size()).isEqualTo(2);
        
        userManager.deleteUser("0001");
        
        assertThat(userManager.getUserList().size()).isEqualTo(1);
        assertThat(userManager.getUserMap().size()).isEqualTo(1);
    }
    @Test
        void 異常系_user削除バグ検出() {
        UserManager manager = UserManager.getInstance();
        
        // 1. バグを引き起こすための「codeがnull」のユーザーを作成して登録
        User nullUser = new User(null);
        manager.setUserToList(nullUser);
        
        // 2. 本来削除したい正しいユーザーを作成して登録
        User targetUser = new User("U005");
        manager.setUserToList(targetUser);
        manager.setUserToMap(targetUser);
            
        // 3. 正しいユーザーを削除してみる
        // 💥 バグ（NullPointerException）があると、ここでプログラムが落ちてテストが失敗します
        manager.deleteUser("U005");
            
        // 4. 検証：バグがなく正常に動けば、targetUserはListから消えているはず
        assertThat(manager.getUserList()).doesNotContain(targetUser);
    }
    @AfterAll
    static void テスト後処理(){
        userManager = null;
    }
}