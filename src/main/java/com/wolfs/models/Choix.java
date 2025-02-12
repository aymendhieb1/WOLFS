package com.wolfs.models;

public class Choix {
    private int idChoix;
    private int postId;
    private String choix;
    private float pourcentage;
    private int choiceVotesCount;

    public Choix(int idChoix, int postId, String choix, float pourcentage, int choiceVotesCount) {
        this.idChoix = idChoix;
        this.postId = postId;
        this.choix = choix;
        this.pourcentage = pourcentage;
        this.choiceVotesCount = choiceVotesCount;
    }

    public Choix() {

    }

    public int getIdChoix() { return idChoix; }
    public void setIdChoix(int idChoix) { this.idChoix = idChoix; }

    public int getPostId() { return postId; }
    public void setPostId(int postId) { this.postId = postId; }

    public String getChoix() { return choix; }
    public void setChoix(String choix) { this.choix = choix; }

    public float getPourcentage() { return pourcentage; }
    public void setPourcentage(float pourcentage) { this.pourcentage = pourcentage; }

    public int getChoiceVotesCount() { return choiceVotesCount; }
    public void setChoiceVotesCount(int choiceVotesCount) { this.choiceVotesCount = choiceVotesCount; }

    @Override
    public String toString() {
        return "Choix{" +
                "idChoix=" + idChoix +
                ", postId=" + postId +
                ", choix='" + choix + '\'' +
                ", pourcentage=" + pourcentage +
                ", choiceVotesCount=" + choiceVotesCount +
                '}';
    }
}
