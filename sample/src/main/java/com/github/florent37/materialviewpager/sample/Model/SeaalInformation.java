package com.github.florent37.materialviewpager.sample.Model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Abderahmane on 04/05/2017.
 */

public class SeaalInformation {

    public String getInfoDatePro() {
        return infoDatePro;
    }

    public void setInfoDatePro(String infoDatePro) {
        this.infoDatePro = infoDatePro;
    }

    public String getInfoId() {
        return infoId;
    }

    public void setInfoId(String infoId) {
        this.infoId = infoId;
    }

    public String getInfoNom() {
        return infoNom;
    }

    public void setInfoNom(String infoNom) {
        this.infoNom = infoNom;
    }

    public String getInfoDate() {
        return infoDate;
    }

    public void setInfoDate(String infoDate) {
        this.infoDate = infoDate;
    }

    String infoId;

    public void setIndex(int index) {
        this.index = index;
    }

    int index;
    String infoNom;
    String infoDate;
    String infoDatePro;
    String InfoEtat;

    public String getInfoEtat(){
        return InfoEtat;
    }
    public int getIndex() {
        int ret = 0;
        switch (InfoEtat){
            case "Ok":
                ret = 0 ;
                break;
            case "Non Ok" :
                ret =  1;
                break;
            case "A voir" :
                ret =  2 ;
                break;
        }
        return ret;
    }

    public void setInfoEtat(String etat) {
        this.InfoEtat = etat;
    }


    public SeaalInformation (String id , String nom , String etat , String date){
        this.infoId = id ;
        this.infoNom = nom ;
        this.infoDate = date ;
        this.InfoEtat = etat ;
        this.infoDatePro = "" ;
    }


    public SeaalInformation (JSONObject info){
        try {
            this.infoId = info.getString("infoId") ;
            this.infoNom = info.getString("infoNom") ;
            this.infoDate = info.getString("infoDate") ;
            this.InfoEtat = info.getString("infoEtat") ;
            this.infoDatePro = info.getString("infoDatePro") == null? "" :  info.getString("infoDatePro");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public  SeaalInformation(){

    }

}
