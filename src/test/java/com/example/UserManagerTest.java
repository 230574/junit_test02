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
    //課題5
    @Test
    void 正常系_userListMap初期生成() {
        // 1. テスト用のUserManagerインスタンスを取得
        UserManager manager = UserManager.getInstance();

        // 2. 検証：getUserList() の戻り値が null ではなく、かつ中身が空であることを一気に検証
        assertThat(manager.getUserList()).isNotNull().isEmpty();

        UserManager manager = UserManager.getInstance();
    
        // 2. 検証：getUserMap() の戻り値が null ではなく、かつ中身が空であることを一気に検証
        assertThat(manager.getUserMap()).isNotNull().isEmpty();
    }
    @Test
    void 正常系_userList順序保証() {
        // 1. テスト用のUserManagerインスタンスを取得
        UserManager manager = UserManager.getInstance();

        // シングルトンのため、念のため一度Listをクリアしておく（他のテストの影響を防ぐ）
        manager.getUserList().clear();

        // 2. テスト用のユーザーを3人作成
        User user1 = new User("U011");
        User user2 = new User("U012");
        User user3 = new User("U013");

        // 3. 順番にListへ登録する
        manager.setUserToList(user1);
        manager.setUserToList(user2);
        manager.setUserToList(user3);

        // 4. 検証：登録した順番通りにListに格納されていること
        // containsExactly を使うことで、「順番も中身も完全に一致していること」を検証できます
        assertThat(manager.getUserList()).containsExactly(user1, user2, user3);
    }
    @Test
    void 正常系_userMapキー格納() {
        // 1. テスト用のUserManagerインスタンスを取得
        UserManager manager = UserManager.getInstance();

        // シングルトンのため、一度Mapをクリアしておく（他のテストの影響を防ぐ）
        manager.getUserMap().clear();

        // 2. テスト用のユーザーを2人作成
        User user1 = new User("U021");
        user1.setName("マニアック太郎");

        User user2 = new User("U022");
        user2.setName("マニアック次郎");

        // 3. UserManagerのMapにユーザーを登録する
        manager.setUserToMap(user1);
        manager.setUserToMap(user2);
    
        // 4. 検証：Mapの中に「キーと値のペア」が正しく格納されていること
        assertThat(manager.getUserMap())
            .containsEntry("U021", user1)
            .containsEntry("U022", user2);
    }
    @AfterAll
    static void テスト後処理(){
        userManager = null;
    }
}