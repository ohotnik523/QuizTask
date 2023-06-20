public class Response {
    private String text;
    private boolean correct;

    public Response (String parText, boolean parCorrect){
        text = parText;
        correct = parCorrect;
    }

    public String GetText(){
        return text;
    }
    public  boolean IsCorrect(){
        return correct;
    }

}
