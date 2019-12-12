package praktika.Model;

import javafx.scene.image.Image;

public class Patiekalas {
    int id;
    int kiekis;
    String pavadinimas, aprasymas;
    double kainabepvm, kainasupvm;
    Image paveikslelis;
    int id_krepselyje;

    public Patiekalas(){

    }

    public Patiekalas(int id, String pavadinimas, String aprasymas, double kainabepvm, Image paveikslelis) {
        this.id = id;
        this.pavadinimas = pavadinimas;
        this.aprasymas = aprasymas;
        this.kainabepvm = kainabepvm;
        this.kainasupvm = this.kainabepvm * 1.21;
        this.paveikslelis = paveikslelis;
    }

    @Override
    public String toString() {
        return pavadinimas + "\t\t\t" + kiekis + "\t\t" + kainabepvm;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPavadinimas() {
        return pavadinimas;
    }

    public void setPavadinimas(String pavadinimas) {
        this.pavadinimas = pavadinimas;
    }

    public String getAprasymas() {
        return aprasymas;
    }

    public void setAprasymas(String aprasymas) {
        this.aprasymas = aprasymas;
    }

    public double getKainabepvm() {
        return kainabepvm;
    }

    public void setKainabepvm(double kainabepvm) {
        this.kainabepvm = kainabepvm;
    }

    public Image getPaveikslelis() {
        return paveikslelis;
    }

    public void setPaveikslelis(Image paveikslelis) {
        this.paveikslelis = paveikslelis;
    }

    public double getKainasupvm() {
        return kainasupvm;
    }

    public void setKainasupvm(double kainasupvm) {
        this.kainasupvm = kainasupvm;
    }

    public int getId_krepselyje() {
        return id_krepselyje;
    }

    public void setId_krepselyje(int id_krepselyje) {
        this.id_krepselyje = id_krepselyje;
    }

    public int getKiekis() {
        return kiekis;
    }

    public void setKiekis(int kiekis) {
        this.kiekis = kiekis;
    }
}
