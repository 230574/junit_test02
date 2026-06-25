package com.example;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class UserTest {
    // This is a placeholder for user-related tests
    static User user = null;
    @BeforeAll
    static void テスト前処理(){
        user = new User();
    }
    @Test
    public void 正常系_ユーザー管理コード登録参照() {
        user.setCode("0001");
        assert(user.getCode().equals("0001"));
    }
    @Test
    public void 正常系_名前登録参照() {
        user.setName("山田太郎");
        assert(user.getName().equals("山田太郎"));
    }
    @Test
    public void 正常系_年齢登録参照() {
        user.setAge(25);
        assert(user.getAge() == 25);
    }
    @Test
    public void 異常系_範囲外年齢登録() {
        user.setAge(200);
        assert(user.getAge() == -1);
    }
    @Test
    void 異常系_初期状態のとき設定可能範囲外の年齢が返されること() {
    // 他のテストメソッドの影響を受けないように、新しくUserオブジェクトを生成する
        User testUser = new User("U001");
    
    // setAge() を一度も実行しない状態で getAge() を呼ぶと、
    // 範囲外の初期値「-1」が返ってくることを検証する
        assertThat(testUser.getAge()).isEqualTo(-1);
    }
    @AfterAll
    static void テスト後処理(){
        user = null;
    }
}