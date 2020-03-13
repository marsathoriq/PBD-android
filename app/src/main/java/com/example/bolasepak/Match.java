package com.example.bolasepak;

import java.util.List;

public class Match {
    private String teamHome ;
    private String teamAway;
    private String schedule;
    private String scoreHome;
    private String scoreAway;
    private String logoHome;
    private String logoAway;
    private String idHome;
    private String idAway;
    private String event;

    public Match() {
    }

    public Match(String teamHome, String teamAway, String schedule, String scoreHome, String scoreAway,
                 String logoHome, String logoAway, String idHome, String idAway,String event) {
        this.teamHome = teamHome;
        this.teamAway = teamAway;
        this.schedule = schedule;
        this.scoreHome = scoreHome;
        this.scoreAway = scoreAway;
        this.logoHome = logoHome;
        this.logoAway = logoAway;
        this.idHome = idHome;
        this.idAway = idAway;
        this.event = event;
    }


    public String getTeamHome() {
        return teamHome;
    }

    public String getTeamAway() {
        return teamAway;
    }

    public String getSchedule() {
        return schedule;
    }

    public String getScoreHome() {
        return scoreHome;
    }

    public String getScoreAway() {
        return scoreAway;
    }

    public String getLogoHome() {
        return logoHome;
    }

    public String getLogoAway() { return logoAway; }

    public String getIdHome(){ return idHome;}

    public String getIdAway(){ return idAway;}

    public String getEvent(){ return event;}

    public void setTeamHome(String teamHome) {
        this.teamHome = teamHome;
    }

    public void setTeamAway(String teamAway) { this.teamAway = teamAway; }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public void setScoreHome(String scoreHome) {
        this.scoreHome = scoreHome;
    }

    public void setScoreAway(String scoreAway) {
        this.scoreAway = scoreAway;
    }

    public void setLogoHome(String logoHome) {
        this.logoHome = logoHome;
    }

    public void setLogoAway(String logoAway) {
        this.logoAway = logoAway;
    }

    public void setIdHome(String idHome){this.idHome = idHome; }

    public void setIdAway(String idAway){this.idAway = idAway; }

    public void setEvent(String event){this.event = event; }

}
