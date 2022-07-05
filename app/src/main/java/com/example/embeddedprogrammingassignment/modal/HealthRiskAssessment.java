package com.example.embeddedprogrammingassignment.modal;

public class HealthRiskAssessment {
    boolean fever, cough, breath, throat, headache, smell, taste, chill, diarrhea, fatigue, vomit, nose;
    boolean cluster, travel, contact;

    public HealthRiskAssessment(boolean fever, boolean cough, boolean breath, boolean throat, boolean headache, boolean smell, boolean taste, boolean chill, boolean diarrhea, boolean fatigue, boolean vomit, boolean nose, boolean cluster, boolean travel, boolean contact) {
        this.fever = fever;
        this.cough = cough;
        this.breath = breath;
        this.throat = throat;
        this.headache = headache;
        this.smell = smell;
        this.taste = taste;
        this.chill = chill;
        this.diarrhea = diarrhea;
        this.fatigue = fatigue;
        this.vomit = vomit;
        this.nose = nose;
        this.cluster = cluster;
        this.travel = travel;
        this.contact = contact;
    }

    public boolean isFever() {
        return fever;
    }

    public void setFever(boolean fever) {
        this.fever = fever;
    }

    public boolean isCough() {
        return cough;
    }

    public void setCough(boolean cough) {
        this.cough = cough;
    }

    public boolean isBreath() {
        return breath;
    }

    public void setBreath(boolean breath) {
        this.breath = breath;
    }

    public boolean isThroat() {
        return throat;
    }

    public void setThroat(boolean throat) {
        this.throat = throat;
    }

    public boolean isHeadache() {
        return headache;
    }

    public void setHeadache(boolean headache) {
        this.headache = headache;
    }

    public boolean isSmell() {
        return smell;
    }

    public void setSmell(boolean smell) {
        this.smell = smell;
    }

    public boolean isTaste() {
        return taste;
    }

    public void setTaste(boolean taste) {
        this.taste = taste;
    }

    public boolean isChill() {
        return chill;
    }

    public void setChill(boolean chill) {
        this.chill = chill;
    }

    public boolean isDiarrhea() {
        return diarrhea;
    }

    public void setDiarrhea(boolean diarrhea) {
        this.diarrhea = diarrhea;
    }

    public boolean isFatigue() {
        return fatigue;
    }

    public void setFatigue(boolean fatigue) {
        this.fatigue = fatigue;
    }

    public boolean isVomit() {
        return vomit;
    }

    public void setVomit(boolean vomit) {
        this.vomit = vomit;
    }

    public boolean isNose() {
        return nose;
    }

    public void setNose(boolean nose) {
        this.nose = nose;
    }

    public boolean isCluster() {
        return cluster;
    }

    public void setCluster(boolean cluster) {
        this.cluster = cluster;
    }

    public boolean isTravel() {
        return travel;
    }

    public void setTravel(boolean travel) {
        this.travel = travel;
    }

    public boolean isContact() {
        return contact;
    }

    public void setContact(boolean contact) {
        this.contact = contact;
    }
}
