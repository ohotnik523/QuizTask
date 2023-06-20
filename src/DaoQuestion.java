import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class DaoQuestion {
    private Connection connection;

    public DaoQuestion(Connection parConnection) {
        connection = parConnection;
    }

    public void saveQuestion(Question question) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO questions (topic, content, difficultyNumber) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, question.GetTopic());
        statement.setString(2, question.GetContent());
        statement.setInt(3, question.GetDifficultyRankNumber());
        statement.executeUpdate();

        ResultSet generatedKeys = statement.getGeneratedKeys();
        if (generatedKeys.next()) {
            int questionId = generatedKeys.getInt(1);
            SaveResponses(questionId, question.GetResponses());
        }
    }

    private void SaveResponses(int questionId, List<Response> responses) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO responses (question_id, text, correct) VALUES (?, ?, ?)");
        for (Response response : responses) {
            statement.setInt(1, questionId);
            statement.setString(2, response.GetText());
            statement.setBoolean(3, response.IsCorrect());
            statement.addBatch();
        }
        statement.executeBatch();
    }

    public void UpdateQuestion(Question question) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("UPDATE questions SET topic = ?, difficulty_rank = ?, content = ? WHERE question_id = ?");
        statement.setString(1, question.GetTopic());
        statement.setInt(2, question.GetDifficultyRankNumber());
        statement.setString(3, question.GetContent());
        statement.setInt(4, question.GetId());
        statement.executeUpdate();

        DeleteResponses(question.GetId());
        SaveResponses(question.GetId(), question.GetResponses());
    }

    public void DeleteQuestion(int questionId) throws SQLException {
        DeleteResponses(questionId);
        PreparedStatement statement = connection.prepareStatement("DELETE FROM questions WHERE question_id = ?");
        statement.setInt(1, questionId);
        statement.executeUpdate();
    }

    private void DeleteResponses(int questionId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM responses WHERE question_id = ?");
        statement.setInt(1, questionId);
        statement.executeUpdate();
    }

    public List<Question> searchQuestionsByTopic(String topic) throws SQLException {
        List<Question> questions = new ArrayList<>();

        PreparedStatement statement = connection.prepareStatement("SELECT * FROM questions WHERE topic = ?");
        statement.setString(1, topic);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            int questionId = resultSet.getInt("question_id");
            String content = resultSet.getString("content");
            int difficultyRank = resultSet.getInt("difficulty_rank");
            List<Response> responses = getResponses(questionId);

            Question question = new Question(topic, difficultyRank, content, responses);
            question.SetId(questionId);
            questions.add(question);
        }

        return questions;
    }

    private List<Response> getResponses(int questionId) throws SQLException {
        List<Response> responses = new ArrayList<>();

        PreparedStatement statement = connection.prepareStatement("SELECT * FROM responses WHERE question_id = ?");
        statement.setInt(1, questionId);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            String text = resultSet.getString("text");
            boolean correct = resultSet.getBoolean("correct");

            Response response = new Response(text, correct);
            responses.add(response);
        }

        return responses;
    }
}

