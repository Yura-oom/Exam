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
     * 科目コードと学校情報から、1件の科目情報を取得する
     */
    public Subject get(String cd, School school) throws Exception {
        Subject subject = null;

        Connection connection = getConnection();

        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            String sql = "select * from subject where cd = ? and school_cd = ?";

            statement = connection.prepareStatement(sql);
            statement.setString(1, cd);
            statement.setString(2, school.getCd());

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                subject = new Subject();

                subject.setCd(resultSet.getString("cd"));
                subject.setName(resultSet.getString("name"));
                subject.setSchool(school);
            }

        } finally {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connection != null) {
                connection.close();
            }
        }

        return subject;
    }

    /**
     * 学校情報から、その学校に所属する科目一覧を取得する
     */
    public List<Subject> filter(School school) throws Exception {
        List<Subject> list = new ArrayList<>();

        Connection connection = getConnection();

        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            String sql = "select * from subject where school_cd = ? order by cd";

            statement = connection.prepareStatement(sql);
            statement.setString(1, school.getCd());

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Subject subject = new Subject();

                subject.setCd(resultSet.getString("cd"));
                subject.setName(resultSet.getString("name"));
                subject.setSchool(school);

                list.add(subject);
            }

        } finally {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connection != null) {
                connection.close();
            }
        }

        return list;
    }

    /**
     * 科目情報を登録・更新する
     * すでに存在する場合は更新、存在しない場合は新規登録する
     */
    public boolean save(Subject subject) throws Exception {
        Connection connection = getConnection();

        PreparedStatement statement = null;

        int count = 0;

        try {
            Subject old = get(subject.getCd(), subject.getSchool());

            String sql;

            if (old == null) {
                sql = "insert into subject(cd, name, school_cd) values(?, ?, ?)";

                statement = connection.prepareStatement(sql);
                statement.setString(1, subject.getCd());
                statement.setString(2, subject.getName());
                statement.setString(3, subject.getSchool().getCd());

            } else {
                sql = "update subject set name = ? where cd = ? and school_cd = ?";

                statement = connection.prepareStatement(sql);
                statement.setString(1, subject.getName());
                statement.setString(2, subject.getCd());
                statement.setString(3, subject.getSchool().getCd());
            }

            count = statement.executeUpdate();

        } finally {
            if (statement != null) {
                statement.close();
            }

            if (connection != null) {
                connection.close();
            }
        }

        return count > 0;
    }

    /**
     * 科目情報を削除する
     */
    public boolean delete(Subject subject) throws Exception {
        Connection connection = getConnection();

        PreparedStatement statement = null;

        int count = 0;

        try {
            String sql = "delete from subject where cd = ? and school_cd = ?";

            statement = connection.prepareStatement(sql);
            statement.setString(1, subject.getCd());
            statement.setString(2, subject.getSchool().getCd());

            count = statement.executeUpdate();

        } finally {
            if (statement != null) {
                statement.close();
            }

            if (connection != null) {
                connection.close();
            }
        }

        return count > 0;
    }
}