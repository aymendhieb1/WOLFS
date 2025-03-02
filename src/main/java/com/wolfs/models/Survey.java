package com.wolfs.models;
import java.time.LocalDateTime;

public class Survey extends Post {
    private String surveyQuestion;
    private String surveyTags;
    private String surveyUserList;

    public Survey(int postId, int forumId, int idUser, int votes, LocalDateTime dateCreation,
                  LocalDateTime dateModification, String cheminFichier, String status, int nbrSignal,
                  String surveyQuestion, String surveyTags, String surveyUserList, String upVoteList, String downVoteList, String signalList) {
        super(postId, forumId, idUser, votes, dateCreation, dateModification, cheminFichier, "survey", status, nbrSignal,upVoteList,downVoteList, signalList);
        this.surveyQuestion = surveyQuestion;
        this.surveyTags = surveyTags;
        this.surveyUserList = surveyUserList;
    }

    public String getSurveyQuestion() { return surveyQuestion; }
    public void setSurveyQuestion(String surveyQuestion) { this.surveyQuestion = surveyQuestion; }
    public String getSurveyTags() { return surveyTags; }
    public void setSurveyTags(String surveyTags) { this.surveyTags = surveyTags; }
    public String getSurveyUserList() { return surveyUserList; }
    public void setSurveyUserList(String surveyUserList) { this.surveyUserList = surveyUserList; }
}