package ra.business.entity;

public class ResultDetail {
    private int resultId;
    private int indexQuestion;
    private int indexChoice;
    private boolean check;

    public ResultDetail() {
    }

    public ResultDetail(int resultId, int indexQuestion, int indexChoice, boolean check) {
        this.resultId = resultId;
        this.indexQuestion = indexQuestion;
        this.indexChoice = indexChoice;
        this.check = check;
    }

    public int getResultId() {
        return resultId;
    }

    public void setResultId(int resultId) {
        this.resultId = resultId;
    }

    public int getIndexQuestion() {
        return indexQuestion;
    }

    public void setIndexQuestion(int indexQuestion) {
        this.indexQuestion = indexQuestion;
    }

    public int getIndexChoice() {
        return indexChoice;
    }

    public void setIndexChoice(int indexChoice) {
        this.indexChoice = indexChoice;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
    public void displayData(){
//        this.resultId ;
//        this.indexQuestion ;
//        this.indexChoice;
//        this.check;

    }

}
