import java.util.List;

public class Question {
    private String topic;
    private String content;
    private int difficultyRankNumber;
    private List<Response> responses;
    private int id;

    public Question (int parId,String parTopic, int parDifficultyRankNumber, String parContent, List<Response> parResponses){
        id = parId;
        topic = parTopic;
        difficultyRankNumber = parDifficultyRankNumber;
        content = parContent;
        responses = parResponses;
    }
    public Question (String parTopic, int parDifficultyRankNumber, String parContent, List<Response> parResponses){
        topic = parTopic;
        difficultyRankNumber = parDifficultyRankNumber;
        content = parContent;
        responses = parResponses;
    }
public Question (){

}
    public int GetId(){return id;}
    public String GetTopic() {
        return topic;
    }
    public String GetContent() {
        return content;
    }
    public int GetDifficultyRankNumber() {
        return difficultyRankNumber;
    }
    public List<Response> GetResponses(){
        return responses;
    }
    public void SetTopic(String parTopic){
        topic = parTopic;
    }
    public void SetContent(String parContent){
        content = parContent;
    }
    public void SetId(int id){
        this.id = id;
    }
    public void SetDifficultyRankNumber(int parDifficultyRankNumber) {
        difficultyRankNumber = parDifficultyRankNumber;
    }
    public void SetResponse(List<Response> parResponse){
        responses = parResponse;
    }
}