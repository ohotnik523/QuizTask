import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class QuizApplicationTest {
    private Connection connection;
    private DaoQuestion daoQuestion;

    @BeforeEach
    public void setUp() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/KuehneTaskQuiz", "root", "root");
        daoQuestion = new DaoQuestion(connection);
    }

    @Test
    public void testSaveQuestion() throws SQLException {
        Response response1 = new Response("Option A", true);
        Response response2 = new Response("Option B", false);
        Question question = new Question("Topic A", 1, "Question content", Arrays.asList(response1, response2));

        daoQuestion.saveQuestion(question);

        assertNotNull(question.GetId());
    }

    @Test
    public void testUpdateQuestion() throws SQLException {
        Response response1 = new Response("Option A", true);
        Response response2 = new Response("Option B", false);
        Question question = new Question("Topic A", 1, "Question content", Arrays.asList(response1, response2));

        daoQuestion.saveQuestion(question);

        question.SetTopic("Topic B");
        question.SetDifficultyRankNumber(2);
        question.SetContent("Updated question content");
        Response response3 = new Response("Option C", false);
        question.SetResponse(Arrays.asList(response1, response3));

        daoQuestion.UpdateQuestion(question);

        Question updatedQuestion = daoQuestion.searchQuestionsByTopic("Topic B").get(0);
        assertEquals(question.GetTopic(), updatedQuestion.GetTopic());
        assertEquals(question.GetDifficultyRankNumber(), updatedQuestion.GetDifficultyRankNumber());
        assertEquals(question.GetContent(), updatedQuestion.GetContent());
        assertEquals(question.GetResponses(), updatedQuestion.GetResponses());
    }

    @Test
    public void testDeleteQuestion() throws SQLException {
        Response response1 = new Response("Option A", true);
        Response response2 = new Response("Option B", false);
        Question question = new Question("Topic A", 1, "Question content", Arrays.asList(response1, response2));

        daoQuestion.saveQuestion(question);

        int questionId = question.GetId();
        daoQuestion.DeleteQuestion(questionId);

        // Assert the question is deleted from the database
        List<Question> questions = daoQuestion.searchQuestionsByTopic("Topic A");
        assertTrue(questions.isEmpty());
    }
}