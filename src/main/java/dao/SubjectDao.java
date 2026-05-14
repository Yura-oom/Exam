package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;

public class SubjectDao extends DAO {

    /**
     * 科目コードと学校情報から、1件の科目情報を取得するメソッド
     * @param cd 科目コード
     * @param school 学校情報
     * @return 該当する科目情報。存在しない場合は null
     */
    public Subject get(String cd, School school) throws Exception {
        Subject subject = null;

        // データベース接続を取得する
        Connection connection = getConnection();

        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // 科目コードと学校コードを条件にして科目情報を検索するSQL
            String sql = "select * from subject where cd = ? and school_cd = ?";

            // SQL文を準備する
            statement = connection.prepareStatement(sql);

            // 1つ目の「?」に科目コードをセットする
            statement.setString(1, cd);

            // 2つ目の「?」に学校コードをセットする
            statement.setString(2, school.getCd());

            // SQLを実行し、検索結果を取得する
            resultSet = statement.executeQuery();

            // 検索結果が存在する場合
            if (resultSet.next()) {
                subject = new Subject();

                // ResultSetから取得した値をSubjectオブジェクトにセットする
                subject.setCd(resultSet.getString("cd"));
                subject.setName(resultSet.getString("name"));

                // 引数で受け取った学校情報をセットする
                subject.setSchool(school);
            }

        } finally {
            // ResultSetを閉じる
            if (resultSet != null) {
                resultSet.close();
            }

            // PreparedStatementを閉じる
            if (statement != null) {
                statement.close();
            }

            // データベース接続を閉じる
            if (connection != null) {
                connection.close();
            }
        }

        return subject;
    }

    /**
     * 学校情報から、その学校に所属する科目一覧を取得するメソッド
     * @param school 学校情報
     * @return 科目情報のリスト
     */
    public List<Subject> filter(School school) throws Exception {
        List<Subject> list = new ArrayList<>();

        // データベース接続を取得する
        Connection connection = getConnection();

        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // 指定された学校コードに所属する科目を、科目コード順に取得するSQL
            String sql = "select * from subject where school_cd = ? order by cd";

            // SQL文を準備する
            statement = connection.prepareStatement(sql);

            // 「?」に学校コードをセットする
            statement.setString(1, school.getCd());

            // SQLを実行し、検索結果を取得する
            resultSet = statement.executeQuery();

            // 検索結果を1件ずつ取り出す
            while (resultSet.next()) {
                Subject subject = new Subject();

                // ResultSetの値をSubjectオブジェクトにセットする
                subject.setCd(resultSet.getString("cd"));
                subject.setName(resultSet.getString("name"));

                // 学校情報をセットする
                subject.setSchool(school);

                // 作成したSubjectオブジェクトをリストに追加する
                list.add(subject);
            }

        } finally {
            // ResultSetを閉じる
            if (resultSet != null) {
                resultSet.close();
            }

            // PreparedStatementを閉じる
            if (statement != null) {
                statement.close();
            }

            // データベース接続を閉じる
            if (connection != null) {
                connection.close();
            }
        }

        return list;
    }

    /**
     * 科目情報を登録または更新するメソッド
     * すでに存在する場合は更新し、存在しない場合は新規登録する
     * @param subject 登録・更新する科目情報
     * @return 登録または更新に成功した場合 true
     */
    public boolean save(Subject subject) throws Exception {
        // データベース接続を取得する
        Connection connection = getConnection();

        PreparedStatement statement = null;

        // insertまたはupdateで更新された件数を入れる変数
        int count = 0;

        try {
            // 同じ科目コードと学校コードのデータがすでに存在するか確認する
            Subject old = get(subject.getCd(), subject.getSchool());

            String sql;

            // 既存データがない場合は新規登録する
            if (old == null) {
                // 科目情報を新しく登録するSQL
                sql = "insert into subject(cd, name, school_cd) values(?, ?, ?)";

                statement = connection.prepareStatement(sql);

                // 科目コードをセットする
                statement.setString(1, subject.getCd());

                // 科目名をセットする
                statement.setString(2, subject.getName());

                // 学校コードをセットする
                statement.setString(3, subject.getSchool().getCd());

            } else {
                // 既存データがある場合は科目名を更新するSQL
                sql = "update subject set name = ? where cd = ? and school_cd = ?";

                statement = connection.prepareStatement(sql);

                // 新しい科目名をセットする
                statement.setString(1, subject.getName());

                // 更新対象の科目コードをセットする
                statement.setString(2, subject.getCd());

                // 更新対象の学校コードをセットする
                statement.setString(3, subject.getSchool().getCd());
            }

            // insertまたはupdateを実行する
            count = statement.executeUpdate();

        } finally {
            // PreparedStatementを閉じる
            if (statement != null) {
                statement.close();
            }

            // データベース接続を閉じる
            if (connection != null) {
                connection.close();
            }
        }

        // 更新件数が1件以上なら成功
        return count > 0;
    }

    /**
     * 科目情報を削除するメソッド
     * @param subject 削除する科目情報
     * @return 削除に成功した場合 true
     */
    public boolean delete(Subject subject) throws Exception {
        // データベース接続を取得する
        Connection connection = getConnection();

        PreparedStatement statement = null;

        // deleteで削除された件数を入れる変数
        int count = 0;

        try {
            // 科目コードと学校コードを条件にして科目情報を削除するSQL
            String sql = "delete from subject where cd = ? and school_cd = ?";

            statement = connection.prepareStatement(sql);

            // 削除対象の科目コードをセットする
            statement.setString(1, subject.getCd());

            // 削除対象の学校コードをセットする
            statement.setString(2, subject.getSchool().getCd());

            // deleteを実行する
            count = statement.executeUpdate();

        } finally {
            // PreparedStatementを閉じる
            if (statement != null) {
                statement.close();
            }

            // データベース接続を閉じる
            if (connection != null) {
                connection.close();
            }
        }

        // 削除件数が1件以上なら成功
        return count > 0;
    }
}