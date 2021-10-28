package com.example.aplicatie;

public class Mesaje {
   String IDtrimis,mesaj;
    public Mesaje() {
    }
    public Mesaje(String IDtrimis, String mesaj) {
        this.IDtrimis = IDtrimis;
        this.mesaj = mesaj;
    }

    public String getIDtrimis() {
        return IDtrimis;
    }

    public void setIDtrimis(String IDtrimis) {
        this.IDtrimis = IDtrimis;
    }

    public String getMesaj() {
        return mesaj;
    }

    public void setMesaj(String mesaj) {
        this.mesaj = mesaj;
    }
}
